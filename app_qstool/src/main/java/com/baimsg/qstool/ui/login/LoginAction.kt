package com.baimsg.qstool.ui.login

/**
 * Create by Baimsg on 2023/4/2
 * 登录意图
 **/
internal sealed class LoginAction {

    /**
     * 更新进度条
     * @param progress 进度
     * @param animateDuration 动画时间
     */
    class UpdateProgress(
        val progress: Int, val animateDuration: Int
    ) : LoginAction()

    /**
     * 登录成功
     * @param cookies
     */
    class LoginSuccessful(val cookies: String) : LoginAction()
}