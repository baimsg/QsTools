package com.baimsg.qstool.ui.login

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.baimsg.qstool.base.utils.centeredToast
import com.baimsg.qstool.base.utils.rememberFlowWithLifecycle
import com.baimsg.qstool.ui.components.TopBar
import com.baimsg.qstool.ui.components.TopBarBackIconItem
import com.baimsg.qstool.ui.components.TopBarIconItem
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.baimsg.qstool.ui.web.setDefaultSettings
import com.baimsg.qstool.utils.extensions.logE
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.baimsg.qstool.ui.resources.R

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

    val state by rememberFlowWithLifecycle(viewModel.loginViewState)

    val value = animateIntAsState(
        targetValue = state.progress, animationSpec = tween(
            durationMillis = state.animateDuration
        )
    )

    val context = LocalContext.current

    val webViewState = rememberWebViewState(url = viewModel.url)
    Column(Modifier.fillMaxSize()) {
        TopBar(
            title = webViewState.pageTitle ?: "", leftItems = listOf(
                TopBarBackIconItem(
                    tint = QstoolComposeThem.colors.topBarIcon, onClick = onBack
                )
            ), rightItems = listOf(
                TopBarIconItem(icon = R.drawable.ic_topbar_more,
                    contentDescription = "更多",
                    tint = QstoolComposeThem.colors.topBarIcon,
                    onClick = {})
            )
        )

        if (value.value < 100) {
            LinearProgressIndicator(
                progress = value.value / 100.0f,
                modifier = Modifier.fillMaxWidth(),
                color = QstoolComposeThem.colors.progressColor,
                strokeCap = StrokeCap.Round
            )
        }
        WebView(state = webViewState, onCreated = {
            it.setDefaultSettings()
        }, client = object : AccompanistWebViewClient() {
            /**
             * 开始加载回调
             */
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (state.progress < 30) {
                    viewModel.submitAction(
                        loginAction = LoginAction.SendProgress(
                            progress = 30, animateDuration = 500
                        )
                    )
                }
            }

            /**
             * 结束加载回调
             */
//                override fun onPageFinished(view: WebView?, url: String?) {
//                    super.onPageFinished(view, url)
//                    viewModel.submitAction(
//                        loginAction = LoginAction.SendProgress(
//                            progress = 100, animateDuration = 100
//                        )
//                    )
//                }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView?, request: WebResourceRequest?, error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                logE(error?.errorCode ?: 0)
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler?, error: SslError?
            ) {
                handler?.proceed()
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                logE("onLoadResource -> $url")
                url?.let {
                    if (it.toUri().path == "/webviewCanScroll") {
                        logE(CookieManager.getInstance().getCookie("https://ui.ptlogin2.qq.com") ?: "")
                        context.centeredToast("登录成功")
                    }
                }
                super.onLoadResource(view, url)
            }

        }, chromeClient = object : AccompanistWebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress > state.progress) {
                    viewModel.submitAction(
                        loginAction = LoginAction.SendProgress(
                            progress = newProgress, animateDuration = 100
                        )
                    )
                }
            }
        }, modifier = Modifier.fillMaxSize()
        )
    }
}