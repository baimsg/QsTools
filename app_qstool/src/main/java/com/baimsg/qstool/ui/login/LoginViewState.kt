package com.baimsg.qstool.ui.login

/**
 * Create by Baimsg on 2023/4/2
 * 登录页面状态
 * @param title 标题
 * @param progress 加载进度
 * @param animateDuration 动画时间
 * @param retry 重试开始
 * @param retryValue 重试值
 * @param loginSuccessful 登录成功
 **/
data class LoginViewState(
    val title: String = "",
    val progress: Int = 0,
    val animateDuration: Int = 0,
    val retry: Boolean = false,
    val retryValue: Boolean = false,
    val loginSuccessful: Boolean = false,
) {
    companion object {
        val EMPTY = LoginViewState()
    }
}