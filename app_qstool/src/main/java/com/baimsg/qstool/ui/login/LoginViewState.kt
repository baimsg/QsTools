package com.baimsg.qstool.ui.login

/**
 * Create by Baimsg on 2023/4/2
 *
 **/
data class LoginViewState(
    val progress: Int = 0, val animateDuration: Int = 0, val loginSuccessful: Boolean = false
) {
    companion object {
        val EMPTY = LoginViewState()
    }
}