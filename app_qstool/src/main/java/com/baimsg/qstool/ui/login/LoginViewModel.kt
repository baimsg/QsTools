package com.baimsg.qstool.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baimsg.qstool.data.AccountsConstant
import com.baimsg.qstool.data.db.daos.CookieRecordDao
import com.baimsg.qstool.data.models.entities.CookieRecord
import com.baimsg.qstool.utils.extensions.combine
import com.baimsg.qstool.utils.extensions.now
import com.baimsg.qstool.utils.extensions.stateInDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@HiltViewModel
internal class LoginViewModel @Inject constructor(
    app: Application, cookieRecordDao: CookieRecordDao,
) : ViewModel() {

    val url by lazy { AccountsConstant.SAFE_URL }

    private val _title = MutableStateFlow("账号登录")
    private val _progress: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _animateDuration: MutableStateFlow<Int> = MutableStateFlow(200)

    private val _loginSuccessful: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _retry: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _retryValue: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _pendingActions = MutableSharedFlow<LoginAction>()

    val loginViewState = combine(
        _title, _progress, _animateDuration, _retry, _retryValue, _loginSuccessful, ::LoginViewState
    ).stateInDefault(
        viewModelScope, LoginViewState.EMPTY
    )

    init {
        viewModelScope.launch {
            _pendingActions.collectLatest { action ->
                when (action) {
                    is LoginAction.UpdateProgress -> {
                        if (action.progress != _progress.value) {
                            _progress.value = action.progress
                            _animateDuration.value = action.animateDuration
                        }
                    }
                    is LoginAction.LoginSuccessful -> {
                        cookieRecordDao.updateOrInsert(
                            CookieRecord(
                                uin = action.cookies.qq, loginTime = now(), cookies = action.cookies
                            )
                        )
                        _loginSuccessful.value = true
                    }
                    is LoginAction.Refresh -> {
                        _retry.value = true
                        _retryValue.apply {
                            value = !value
                        }
                    }
                    is LoginAction.UpdateTitle -> {
                        _title.value = action.title
                    }
                }
            }
        }
    }

    fun submitAction(loginAction: LoginAction) {
        viewModelScope.launch {
            _pendingActions.emit(loginAction)
        }
    }
}