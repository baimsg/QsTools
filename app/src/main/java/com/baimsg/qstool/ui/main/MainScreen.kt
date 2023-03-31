package com.baimsg.qstool.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MainScreen() {

    val webViewState =
        rememberWebViewState(url = "https://accounts.qq.com/safe/securityphone?from=setting")
    Column(Modifier.fillMaxSize()) {
        TopAppBar(Modifier.fillMaxWidth()) {}
        WebView(
            state = webViewState, onCreated = {
                it.settings.apply {
                    javaScriptEnabled = true
                }
            }, modifier = Modifier.fillMaxSize()
        )
    }
}