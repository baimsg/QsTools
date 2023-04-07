package com.baimsg.qstool.ui.main

/**
 * Create by Baimsg on 2023/4/7
 *
 **/
sealed class MainActivityAction {
    /**
     * 阻止返回
     * @param block 返回事件
     */
    class BlockBack(block: (() -> Unit)?) : MainActivityAction()

    /**
     * 隐藏底部栏
     * @param hide 隐藏
     */
    class HideBottomBar(hide: Boolean) : MainActivityAction()

    /**
     * 全屏显示
     * @param full 全屏
     */
    class FullScreen(full: Boolean) : MainActivityAction()

    /**
     * 切换状态栏文字颜色
     * @param isLightMode 是日间模式(黑色文字)
     */
    class StatusBarMode(isLightMode: Boolean) : MainActivityAction()
}