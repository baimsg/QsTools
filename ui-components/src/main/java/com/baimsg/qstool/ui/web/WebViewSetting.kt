package com.baimsg.qstool.ui.web

import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
fun WebView.setDefaultSettings() {
    settings.apply {
        /**
         * 允许加载js
         */
        javaScriptEnabled = true
        /**
         * js打开窗口
         */
        javaScriptCanOpenWindowsAutomatically = true

        /**
         * 保存数据和数据库
         */
        savePassword = true
        saveFormData = true
        databaseEnabled = true
        /**
         * 允许加载图片
         */
        loadsImagesAutomatically = true

        /**
         * WebView内部是否允许访问文件
         */
        allowFileAccess = true


        allowContentAccess = true

        /**
         * 缩放相关
         */
        displayZoomControls = true
        useWideViewPort = true
        loadWithOverviewMode = true

        /**
         * 字体相关
         */
        defaultTextEncodingName = "utf-8"

        /**
         * 插件相关
         */
        pluginState = WebSettings.PluginState.ON
        setRenderPriority(WebSettings.RenderPriority.HIGH)
        setNeedInitialFocus(true)
        //设置Web调试
        WebView.setWebContentsDebuggingEnabled(true)
        //解决加载Https和Http混合模式网页加载问题
        mixedContentMode = WebSettings.LOAD_NORMAL
    }
}