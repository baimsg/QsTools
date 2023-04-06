package com.baimsg.qstool.ui.change

import android.content.res.AssetManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baimsg.qstool.ROUTER_KEY_UIN
import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.data.*
import com.baimsg.qstool.data.db.daos.CookieRecordDao
import com.baimsg.qstool.data.domain.repositories.AccountsDataResource
import com.baimsg.qstool.data.models.*
import com.baimsg.qstool.utils.JSON
import com.baimsg.qstool.utils.extensions.combine
import com.baimsg.qstool.utils.extensions.stateInDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@HiltViewModel
internal class ChangeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val assetManager: AssetManager,
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
     * 区号数据
     */
    private val _areaCodeList = MutableStateFlow<List<AreaCode>>(emptyList())

    /**
     * 验证码
     */
    private val _verifyCode = MutableStateFlow("")

    /**
     * 执行状态
     */
    private val _invokeStatus = MutableStateFlow<InvokeStatus>(InvokeLoading("加载中..."))

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

    /**
     * 验证码数据
     */
    private val _checkSmsList = MutableStateFlow<MutableList<CheckSms>>(mutableListOf())


    private val _pendingAction = MutableSharedFlow<ChangeAction>()

    val viewState = combine(
        _uin,
        _cookie,
        _areaCodeList,
        _checkRisk,
        _phoneInfo,
        _checkSmsList,
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
                        checkSms(_cookie.value ?: Cookies(), _phoneInfo.value)
                    }
                    is ChangeAction.InputPhoneNum -> {
                        _phoneInfo.apply {
                            value = value.copy(phoneNum = action.value)
                        }
                    }
                    is ChangeAction.SelectAreaCode -> {
                        _phoneInfo.apply {
                            value = value.copy(areaCode = action.areasCode)
                        }
                    }
                    is ChangeAction.VerifyPhone -> {
                        verifyMbPhone(_cookie.value ?: Cookies(), _phoneInfo.value)
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
             * 读取本地区号数据
             */
            val areaCodeStr = assetManager.open("areaCode.json").bufferedReader().readText()
            _areaCodeList.value =
                JSON.decodeFromString(ListSerializer(AreaCode.serializer()), areaCodeStr)
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
     * 检查风险(初始化)
     * @param cookies
     */
    private suspend fun chkRisk(cookies: Cookies) {
        _invokeStatus.value = InvokeLoading("检查账号风险...")
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
     * 获取验证方式（初始化）
     * @param checkRisk 风险数据包
     * @param cookies 登录数据包
     */
    private suspend fun queryVerifyMethod(checkRisk: CheckRisk, cookies: Cookies) {
        _invokeStatus.value = InvokeLoading("获取验证方式...")
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
     * 获取绑定手机（初始化）
     * @param cookies 登录数据包
     */
    private suspend fun queryMbPhone(cookies: Cookies) {
        _invokeStatus.value = InvokeLoading("获取绑定手机...")
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
        _invokeStatus.value = InvokeLoading("获取验证码...")
        accountsDataResource.getSms(
            way = phoneInfo.way, areaCode = phoneInfo.areaCode, mobile = phoneInfo.phoneNum, cookies
        ).apply {
            onFailure {
                _invokeStatus.value = InvokeFinish("${it.message}")
            }
            onSuccess { smsInfo ->
                if (smsInfo.retcode == 0) {
                    _invokeStatus.value = InvokeSuccess
                    _timeOut.value = smsInfo.resendInterval
                    startTimeOut(smsInfo.resendInterval)
                } else {
                    _invokeStatus.value = InvokeFinish(smsInfo.retmsg)
                }
            }
        }
    }

    /**
     * 验证短信
     *
     */
    private suspend fun checkSms(cookies: Cookies, phoneInfo: PhoneInfo) {
        _invokeStatus.value = InvokeLoading("验证短信...")
        when {
            _verifyCode.value.isBlank() -> {
                _invokeStatus.value = InvokeFinish("验证码不能为空")
            }
            else -> {
                accountsDataResource.checkSms(
                    phoneInfo = phoneInfo, sms = _verifyCode.value, cookies = cookies
                ).apply {
                    onFailure {
                        _invokeStatus.value = InvokeFinish("${it.message}")
                    }
                    onSuccess { checkSms ->
                        if (checkSms.retcode == 0) {
                            _checkSmsList.apply {
                                value = value.apply {
                                    add(checkSms)
                                }
                            }
                            if (phoneInfo.way == 3) {
                                _invokeStatus.value = InvokeSuccess
                                _phoneInfo.apply {
                                    value = value.copy(way = 1, phoneNum = "")
                                }
                                _timeOut.value = 0
                                _verifyCode.value = ""
                            } else {
                                changeMbPhone(
                                    phoneInfo = phoneInfo, _checkSmsList.value, cookies = cookies
                                )
                            }
                        } else {
                            _invokeStatus.value = InvokeFinish(checkSms.retmsg)
                        }
                    }
                }
            }
        }
    }

    private suspend fun verifyMbPhone(cookies: Cookies, phoneInfo: PhoneInfo) {
        _invokeStatus.value = InvokeLoading("验证手机号...")
        when {
            phoneInfo.phoneNum.isBlank() -> {
                _invokeStatus.value = InvokeFinish("手机号不能为空")
            }
            else -> {
                accountsDataResource.verifyMbPhone(phoneInfo.areaCode, phoneInfo.phoneNum, cookies)
                    .apply {
                        onFailure {
                            _invokeStatus.value = InvokeFinish("验证失败:${it.message}")
                        }
                        onSuccess { verifyMbPhone ->
                            if (verifyMbPhone.needChange || verifyMbPhone.retcode != 0) {
                                _invokeStatus.value = InvokeFinish(verifyMbPhone.retmsg)
                            } else {
                                _phoneInfo.apply {
                                    value = value.copy(way = 2)
                                }
                                _invokeStatus.value = InvokeSuccess
                            }
                        }
                    }
            }
        }
    }

    /**
     * 换绑
     */
    private suspend fun changeMbPhone(
        phoneInfo: PhoneInfo,
        checkSmsList: List<CheckSms>,
        cookies: Cookies,
    ) {
        accountsDataResource.changeMbPhone(
            phoneInfo = phoneInfo, checkSmsList = checkSmsList, cookies = cookies
        ).apply {
            onFailure {
                _invokeStatus.value = InvokeFinish("${it.message}")
            }
            onSuccess { changeMbPhone ->
                _invokeStatus.value = InvokeFinish(changeMbPhone.retmsg)
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
                if (_timeOut.value <= 0) return@flow
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