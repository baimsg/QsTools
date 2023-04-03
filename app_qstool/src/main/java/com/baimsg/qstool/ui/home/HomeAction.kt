package com.baimsg.qstool.ui.home

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
sealed class HomeAction {
    class Change(type: Int) : HomeAction()

    class ShowAndHideCookieRecord(val isShow: Boolean) : HomeAction()
}