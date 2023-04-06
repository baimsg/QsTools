package com.baimsg.qstool.ui.change

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
sealed class ChangeAction {
    class InputVerificationCode(val value: String) : ChangeAction()
    object Retry : ChangeAction()

    object RequestSms : ChangeAction()
    object CheckSms : ChangeAction()
}