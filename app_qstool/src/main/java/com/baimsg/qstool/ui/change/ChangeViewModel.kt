package com.baimsg.qstool.ui.change

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baimsg.qstool.ROUTER_KEY_UIN
import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.data.InvokeFail
import com.baimsg.qstool.data.InvokeLading
import com.baimsg.qstool.data.InvokeStatus
import com.baimsg.qstool.data.InvokeSuccess
import com.baimsg.qstool.data.db.daos.CookieRecordDao
import com.baimsg.qstool.data.domain.repositories.AccountsDataResource
import com.baimsg.qstool.data.models.CheckRisk
import com.baimsg.qstool.data.models.Cookies
import com.baimsg.qstool.data.models.PhoneInfo
import com.baimsg.qstool.data.models.toPhoneInfo
import com.baimsg.qstool.utils.extensions.combine
import com.baimsg.qstool.utils.extensions.stateInDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@HiltViewModel
internal class ChangeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cookieRecordDao: CookieRecordDao,
    private val accountsDataResource: AccountsDataResource,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    /**
     * 账号
     */
    private val _uin = savedStateHandle.getStateFlow(ROUTER_KEY_UIN, 0L)

    /**
     * cookie数据
     */
    private val _cookie = MutableStateFlow<Cookies?>(null)

    /**
     * 验证码
     */
    private val _verifyCode = MutableStateFlow("")

    /**
     * 执行状态
     */
    private val _invokeStatus = MutableStateFlow<InvokeStatus>(InvokeLading("加载中..."))

    /**
     * 风险检验数据
     */
    private val _checkRisk = MutableStateFlow(CheckRisk())

    /**
     * 绑定手机
     */
    private val _phoneInfo = MutableStateFlow(PhoneInfo())

    /**
     * 倒计时
     */
    private val _timeOut = MutableStateFlow(0)


    private val _pendingAction = MutableSharedFlow<ChangeAction>()

    val viewState = combine(
        _uin,
        _cookie,
        _checkRisk,
        _phoneInfo,
        _timeOut,
        _verifyCode,
        _invokeStatus,
        ::ChangeViewState
    ).stateInDefault(
        viewModelScope, ChangeViewState.Empty
    )

    init {
        viewModelScope.launch {
            _pendingAction.collectLatest { action ->
                when (action) {
                    is ChangeAction.InputVerificationCode -> {
                        _verifyCode.value = action.value
                    }
                    is ChangeAction.Retry -> {
                        chkRisk(_cookie.value ?: Cookies())
                    }
                    is ChangeAction.RequestSms -> {
                        getSms(_cookie.value ?: Cookies(), _phoneInfo.value)
                    }
                    is ChangeAction.CheckSms -> {

                    }
                }
            }
        }

        viewModelScope.launch {
            /**
             * 获取cookie
             */
            cookieRecordDao.observeEntriesById("${_uin.value}").firstOrNull()?.let {
                _cookie.value = it.cookies
            }
            /**
             * 监听cookie变化
             */
            _cookie.collectLatest {
                it?.let { cookies ->
                    chkRisk(cookies)
                }
            }
        }

    }

    /**
     * 检查风险
     * @param cookies
     */
    private suspend fun chkRisk(cookies: Cookies) {
        _invokeStatus.value = InvokeLading("检查账号风险...")
        accountsDataResource.chkRisk(cookies).apply {
            onFailure {
                _invokeStatus.value = InvokeFail("${it.message}")
            }
            onSuccess { risk ->
                _checkRisk.value = risk
                if (risk.retcode == 0) {
                    queryVerifyMethod(risk, cookies)
                } else {
                    _invokeStatus.value = InvokeFail(risk.retmsg)
                }
            }
        }
    }

    /**
     * 获取验证方式
     */
    private suspend fun queryVerifyMethod(checkRisk: CheckRisk, cookies: Cookies) {
        _invokeStatus.value = InvokeLading("获取验证方式...")
        accountsDataResource.queryVerifyMethod(checkRisk.ticket, cookies).apply {
            onFailure {
                _invokeStatus.value = InvokeFail("${it.message}")
            }

            onSuccess { verifyMethod ->
                when {
                    verifyMethod.retcode != 0 -> {
                        _invokeStatus.value = InvokeFail(verifyMethod.retmsg)
                    }
                    verifyMethod.phoneVerify -> {
                        //支持手机号验证
                        queryMbPhone(cookies)
                    }
                    else -> {
                        //不支持手机号验证
                        _invokeStatus.value = InvokeFail("账号不支持手机号验证")
                    }
                }
            }
        }
    }

    /**
     * 获取绑定手机
     */
    private suspend fun queryMbPhone(cookies: Cookies) {
        _invokeStatus.value = InvokeLading("获取绑定手机...")
        accountsDataResource.queryMbPhone(cookies).apply {
            onFailure {
                _invokeStatus.value = InvokeFail("${it.message}")
            }
            onSuccess { mbPhoneInfo ->
                if (mbPhoneInfo.retcode == 0) {
                    _phoneInfo.value = mbPhoneInfo.toPhoneInfo()
                    _invokeStatus.value = InvokeSuccess
                } else {
                    _invokeStatus.value = InvokeFail(mbPhoneInfo.retmsg)
                }
            }
        }
    }

    /**
     * 获取验证码
     */
    private suspend fun getSms(cookies: Cookies, phoneInfo: PhoneInfo) {
        _invokeStatus.value = InvokeLading("获取验证码...")
        when {
            phoneInfo.phoneNum.isBlank() -> {
                _invokeStatus.value = InvokeFail("手机号码未输入")
            }
            else -> {
                accountsDataResource.getSms(
                    way = phoneInfo.way,
                    areaCode = phoneInfo.areaCode,
                    mobile = phoneInfo.phoneNum,
                    cookies
                ).apply {
                    onFailure {
                        _invokeStatus.value = InvokeFail("${it.message}")
                    }
                    onSuccess { smsInfo ->
                        if (smsInfo.retcode == 0) {
                            _invokeStatus.value = InvokeSuccess
                            _timeOut.value = smsInfo.resendInterval
                            startTimeOut(smsInfo.resendInterval)
                        } else {
                            _invokeStatus.value = InvokeFail(smsInfo.retmsg)
                        }
                    }
                }
            }
        }
    }

    /**
     * 倒计时开始
     * @param resendInterval 开始值
     */
    private suspend fun startTimeOut(resendInterval: Int) = withContext(dispatchers.io) {
        flow {
            for (i in resendInterval downTo 0) {
                emit(i)
                delay(1000)
            }
        }.flowOn(dispatchers.io).onStart {}.onCompletion {}.onEach {
            _timeOut.value = it
        }.launchIn(viewModelScope)
    }


    fun submitAction(action: ChangeAction) {
        viewModelScope.launch {
            _pendingAction.emit(action)
        }
    }
}