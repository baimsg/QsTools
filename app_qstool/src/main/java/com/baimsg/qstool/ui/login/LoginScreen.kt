package com.baimsg.qstool.ui.login

import android.annotation.SuppressLint
import android.net.http.SslError
import android.webkit.*
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.baimsg.qstool.base.utils.rememberFlowWithLifecycle
import com.baimsg.qstool.data.AccountsConstant
import com.baimsg.qstool.ui.components.TopBar
import com.baimsg.qstool.ui.components.TopBarBackIconItem
import com.baimsg.qstool.ui.components.TopBarIconItem
import com.baimsg.qstool.ui.modal.stillToast
import com.baimsg.qstool.ui.resources.R
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.baimsg.qstool.ui.web.setDefaultSettings
import com.baimsg.qstool.utils.JSON
import com.baimsg.qstool.utils.extensions.isNotNullAndNotBlank
import com.google.accompanist.web.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.put

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
    LoginScreen(onBack = onBack, viewModel = viewModel) { action ->
        viewModel.submitAction(action)
    }
}

@Composable
internal fun LoginScreen(
    onBack: () -> Unit,
    viewModel: LoginViewModel,
    executor: (LoginAction) -> Unit,
) {

    val view = LocalView.current

    val state by rememberFlowWithLifecycle(viewModel.loginViewState)

    val webViewState = rememberWebViewState(url = viewModel.url)

    val webViewNavigator = rememberWebViewNavigator()

    Scaffold(
        Modifier
            .fillMaxSize()
            .background(QstoolComposeThem.colors.background)
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            LoginTopBar(title = state.title, onBack = onBack, executor = executor)
            LoginWebContent(
                webViewState = webViewState,
                webViewNavigator = webViewNavigator,
                progress = state.progress,
                animateDuration = state.animateDuration,
                executor = executor
            )
        }
    }

    /**
     * 刷新按钮监听
     */
    if (state.retry) {
        DisposableEffect(state.retryValue) {
            webViewNavigator.reload()
            onDispose {
                webViewNavigator.stopLoading()
            }
        }
    }

    /**
     * 登录成功
     */
    if (state.loginSuccessful) {
        val background = QstoolComposeThem.colors.topBarBackground
        val textColor = QstoolComposeThem.colors.topBarText
        LaunchedEffect(Unit) {
            view.stillToast(text = "登录成功", background = background, textColor = textColor)
                .doOnDismiss {
                    onBack()
                }
        }
    }

}

@Composable
internal fun LoginTopBar(title: String, onBack: () -> Unit, executor: (LoginAction) -> Unit) {
    TopBar(
        title = title, leftItems = listOf(
            TopBarBackIconItem(
                tint = QstoolComposeThem.colors.topBarIcon, onClick = onBack
            )
        ), rightItems = listOf(
            TopBarIconItem(icon = R.drawable.ic_refresh,
                contentDescription = "刷新",
                tint = QstoolComposeThem.colors.topBarIcon,
                onClick = {
                    executor(LoginAction.Refresh)
                })
        )
    )
}

@Composable
internal fun LoginWebContent(
    webViewState: WebViewState,
    webViewNavigator: WebViewNavigator = rememberWebViewNavigator(),
    progress: Int = 0,
    animateDuration: Int = 0,
    executor: (LoginAction) -> Unit,
) {
    /**
     * 加载进度监听
     */
    when (val loadingState = webViewState.loadingState) {
        is LoadingState.Loading -> {
            val newProgress = (loadingState.progress * 100).toInt()
            if (newProgress > progress) {
                executor(LoginAction.UpdateProgress(progress = newProgress, animateDuration = 100))
            }
        }
        is LoadingState.Finished -> {
            executor(LoginAction.UpdateProgress(progress = 0, animateDuration = 0))
        }
        else -> Unit
    }
    val intState = animateIntAsState(
        targetValue = progress, animationSpec = tween(
            durationMillis = animateDuration
        )
    )
    if (intState.value in 1..99) {
        LinearProgressIndicator(
            progress = intState.value / 100.0f,
            modifier = Modifier.fillMaxWidth(),
            color = QstoolComposeThem.colors.progressColor,
            strokeCap = StrokeCap.Butt
        )
    }
    WebView(state = webViewState,
        modifier = Modifier.fillMaxSize(),
        navigator = webViewNavigator,
        onCreated = {
            it.setDefaultSettings()
        },
        client = object : AccompanistWebViewClient() {

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler?, error: SslError?,
            ) {
                handler?.proceed()
            }

            override fun onLoadResource(webView: WebView?, url: String?) {
                url?.let {
                    if (it.toUri().getQueryParameter("bkn").isNotNullAndNotBlank()) {
                        val cookieManager = CookieManager.getInstance()
                        val cookies = cookieManager.getCookie(AccountsConstant.COOKIE_PATH) ?: ""
                        if (cookies.contains("p_skey")) {
                            buildJsonObject {
                                cookies.split("; ").forEachIndexed { _, s ->
                                    val values = s.split("=")
                                    put(values.first(), values.last())
                                }
                            }.apply {
                                executor(
                                    LoginAction.LoginSuccessful(
                                        JSON.decodeFromJsonElement(
                                            this
                                        )
                                    )
                                )
                            }
                            /***
                             * 清空cookie 停止加载网页
                             */
                            cookieManager.removeAllCookies {}
                            cookieManager.removeSessionCookies {}
                            cookieManager.flush()
                            webView?.stopLoading()
                            webView?.destroy()
                        }
                    }
                }
                super.onLoadResource(webView, url)
            }

        },
        chromeClient = object : AccompanistWebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                executor(LoginAction.UpdateTitle(title ?: ""))
            }
        })
}