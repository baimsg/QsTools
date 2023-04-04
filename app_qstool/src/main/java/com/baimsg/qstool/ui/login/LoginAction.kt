package com.baimsg.qstool.ui.login

import com.baimsg.qstool.data.models.Cookies

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
        val progress: Int, val animateDuration: Int,
    ) : LoginAction()

    /**
     * 登录成功
     * @param cookies 获取到的cookie
     */
    class LoginSuccessful(val cookies: Cookies) : LoginAction()

    /**
     * 刷新网页
     */
    object Refresh : LoginAction()

    /**
     * 更新标题
     * @param title 标题
     */
    class UpdateTitle(val title: String) : LoginAction()
}