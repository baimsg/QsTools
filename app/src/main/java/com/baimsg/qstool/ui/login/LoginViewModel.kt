package com.baimsg.qstool.ui.login

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.baimsg.qstool.ui.resources.R

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@HiltViewModel
class LoginViewModel @Inject constructor(app: Application) : ViewModel() {

    val url: String by lazy {
        app.getString(R.string.qq_login_url)
    }

    val title: MutableState<String> by lazy {
        mutableStateOf("登录QQ")
    }

}