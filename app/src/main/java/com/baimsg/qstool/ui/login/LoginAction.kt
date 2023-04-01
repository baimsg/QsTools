package com.baimsg.qstool.ui.login

/**
 * Create by Baimsg on 2023/4/2
 *
 **/
internal sealed class LoginAction {
    class SendProgress(
        val progress: Int,
        val animateDuration: Int
    ) : LoginAction()
}