package com.baimsg.qstool.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.baimsg.qstool.ui.resources.R
import com.baimsg.qstool.utils.extensions.stateInDefault
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@HiltViewModel
internal class LoginViewModel @Inject constructor(app: Application) : ViewModel() {

    val url: String by lazy {
        app.getString(R.string.qq_login_url)
    }

    private val _progress: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _animateDuration: MutableStateFlow<Int> = MutableStateFlow(200)

    private val _loginSuccessful: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _pendingActions = MutableSharedFlow<LoginAction>()

    val loginViewState =
        combine(_progress, _animateDuration, _loginSuccessful, ::LoginViewState).stateInDefault(
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