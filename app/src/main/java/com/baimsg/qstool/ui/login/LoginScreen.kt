package com.baimsg.qstool.ui.login

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.util.Log
import android.webkit.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.hilt.navigation.compose.hiltViewModel
import com.baimsg.qstool.AppViewModelStore
import com.baimsg.qstool.ui.components.TopBar
import com.baimsg.qstool.ui.components.TopBarBackIconItem
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.baimsg.qstool.utils.extensions.logE
import com.baimsg.qstool.ui.web.setDefaultSettings
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
@Composable
internal fun LoginScreen(onBack: () -> Unit) {
    LoginScreen(onBack = onBack, viewModel = hiltViewModel())
}

@Composable
internal fun LoginScreen(onBack: () -> Unit, viewModel: LoginViewModel) {
    val loadProgress = remember { mutableStateOf(0f) }

    /**
     * Url地址
     */
    val webViewState =
        rememberWebViewState(url = "https://accounts.qq.com/safe/securityphone?from=setting")
    Column(Modifier.fillMaxSize()) {
        TopBar(
            title = "登录QQ", leftItems = listOf(
                TopBarBackIconItem(
                    tint = QstoolComposeThem.colors.topBarIcon, onClick = onBack
                )
            )
        )
        LinearProgressIndicator(
            progress = loadProgress.value,
            modifier = Modifier.fillMaxWidth(),
            color = QstoolComposeThem.colors.topBarTextSelect,
            strokeCap = StrokeCap.Round
        )
        WebView(state = webViewState, onCreated = {
            it.setDefaultSettings()
        },

            client = object : AccompanistWebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    loadProgress.value = 0f
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    loadProgress.value = 100f
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    Log.i("webViewClient", "加载Url资源回调")
                }

                override fun onReceivedError(
                    view: WebView?, request: WebResourceRequest?, error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.i("webViewClient", "访问地址错误回调")
                }

                override fun onReceivedSslError(
                    view: WebView?, handler: SslErrorHandler?, error: SslError?
                ) {
                    super.onReceivedSslError(view, handler, error)
                    Log.i("webViewClient", "加载SSl错误回调")
                }

            }, chromeClient = object : AccompanistWebChromeClient() {
                override fun onJsAlert(
                    view: WebView?, url: String?, message: String?, result: JsResult?
                ): Boolean {
                    Log.i("webChromeClient", "当网页调用alert()来弹出alert弹出框前回调，用以拦截alert()函数")
                    return super.onJsAlert(view, url, message, result)
                }

                override fun onJsConfirm(
                    view: WebView?, url: String?, message: String?, result: JsResult?
                ): Boolean {
                    Log.i("webChromeClient", "当网页调用confirm()来弹出confirm弹出框前回调，用以拦截confirm()函数")
                    return super.onJsConfirm(view, url, message, result)
                }

                override fun onJsPrompt(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    defaultValue: String?,
                    result: JsPromptResult?
                ): Boolean {
                    Log.i("webChromeClient", "当网页调用prompt()来弹出prompt弹出框前回调，用以拦截prompt()函数")
                    return super.onJsPrompt(view, url, message, defaultValue, result)
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    Log.i("webChromeClient", "网页的title回调")
                    super.onReceivedTitle(view, title)
                }

                override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                    Log.i("webChromeClient", "网页的icon回调")
                    super.onReceivedIcon(view, icon)
                }

                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    Log.i("webChromeClient", "文件选择回调")
                    return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                }

                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    Log.i("webChromeClient", "打印 console 信息")
                    return super.onConsoleMessage(consoleMessage)
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    loadProgress.value = newProgress.toFloat()
                    Log.i("webChromeClient", "通知程序当前页面加载进度")
                }
            }, modifier = Modifier.fillMaxSize()
        )
    }
}