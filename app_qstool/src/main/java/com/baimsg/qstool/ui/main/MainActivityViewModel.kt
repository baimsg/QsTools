package com.baimsg.qstool.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    /**
     * 底部导航栏是否显示
     */
    var hasBottomBar by mutableStateOf(true)

    /**
     * 是否全屏模式
     */
    var isFullScreen by mutableStateOf(false)

    var blockBack by mutableStateOf(false)


}