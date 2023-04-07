package com.baimsg.qstool.ui.main

/**
 * Create by Baimsg on 2023/4/7
 *
 **/
internal class MainActivityViewState(
    val backBlock: (() -> Unit)? = null,
    val hideBottomBar: Boolean = false,
    val fullScreen: Boolean = false,
    val lightMode: Boolean = false,
) {
    companion object {
        val Empty = MainActivityViewState()
    }
}