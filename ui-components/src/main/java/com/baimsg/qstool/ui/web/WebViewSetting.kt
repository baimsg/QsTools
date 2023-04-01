package com.baimsg.qstool.ui.web

import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled

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
        databaseEnabled = true
        //开启DOM缓存，qq
        domStorageEnabled = true
        // 设置缓存模式
        cacheMode = WebSettings.LOAD_DEFAULT

        /**
         * 允许加载图片
         */
        loadsImagesAutomatically = true
        //  页面加载好以后，再放开图片
        blockNetworkImage = false
        /**
         * WebView内部是否允许访问文件
         */
        allowFileAccess = true


        allowContentAccess = true

        /**
         * 缩放相关
         */
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        setSupportZoom(true)
        builtInZoomControls = true
        displayZoomControls = false
        // 网页内容的宽度自适应屏幕
        layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        loadWithOverviewMode = true
        useWideViewPort = true
        // WebView是否新窗口打开(加了后可能打不开网页)
        setSupportMultipleWindows(false)
        javaScriptCanOpenWindowsAutomatically = true
        // webView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。MIXED_CONTENT_ALWAYS_ALLOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mixedContentMode = WebSettings.LOAD_NORMAL
        }
        /**
         * 字体相关
         */
        defaultTextEncodingName = "utf-8"
        textZoom = 100
        /**
         * 插件相关
         */
        pluginState = WebSettings.PluginState.ON
        setRenderPriority(WebSettings.RenderPriority.LOW)
        setNeedInitialFocus(true)
        //设置Web调试
        setWebContentsDebuggingEnabled(true)

    }
}