package com.baimsg.qstool.ui.change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baimsg.qstool.base.utils.rememberFlowWithLifecycle
import com.baimsg.qstool.data.InvokeFail
import com.baimsg.qstool.data.InvokeLading
import com.baimsg.qstool.data.InvokeSuccess
import com.baimsg.qstool.data.models.PhoneInfo
import com.baimsg.qstool.ui.components.*
import com.baimsg.qstool.ui.ex.drawBottomSeparator
import com.baimsg.qstool.ui.modal.*
import com.baimsg.qstool.ui.resources.R
import com.baimsg.qstool.ui.theme.QstoolComposeThem

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@Composable
internal fun ChangeScreen(onBack: () -> Unit) {
    ChangeScreen(onBack = onBack, viewModel = hiltViewModel())
}

@Composable
internal fun ChangeScreen(onBack: () -> Unit, viewModel: ChangeViewModel) {
    ChangeScreen(onBack = onBack, viewModel = viewModel) { action ->
        viewModel.submitAction(action)
    }
}

@Composable
internal fun ChangeScreen(
    onBack: () -> Unit,
    viewModel: ChangeViewModel,
    executor: (ChangeAction) -> Unit,
) {
    val state by rememberFlowWithLifecycle(viewModel.viewState)

    val view = LocalView.current

    val background = QstoolComposeThem.colors.bottomBar

    val invokeStatus = state.invokeStatus

    val iconColor = QstoolComposeThem.colors.iconCurrent

    val textColor = QstoolComposeThem.colors.textPrimary

    when (invokeStatus) {
        is InvokeLading -> {
            val msg = rememberUpdatedState(newValue = invokeStatus.msg)
            DisposableEffect(Unit) {
                val dialog = view.dialog(
                    background = background,
                    systemCancellable = false,
                    maskTouchBehavior = MaskTouchBehavior.none,
                    widthLimit = 180.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            Modifier.padding(top = 16.dp),
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp,
                            color = iconColor
                        )
                        DialogMsgContent(
                            text = msg.value, textAlign = TextAlign.Center, color = textColor
                        )
                    }
                }.apply {
                    show()
                }
                onDispose {
                    dialog.dismiss()
                }
            }
        }
        is InvokeFail -> {
            val msg = rememberUpdatedState(newValue = invokeStatus.msg)
            LaunchedEffect(Unit) {
                view.toast(msg.value)
            }
        }
        is InvokeSuccess -> Unit
        else -> Unit
    }


    Column(
        Modifier
            .fillMaxWidth()
            .background(QstoolComposeThem.colors.background)
    ) {
        TopBar(title = "手机换绑", leftItems = listOf(TopBarBackIconItem(onClick = onBack)))
        LazyColumn(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .background(QstoolComposeThem.colors.bottomBar)
        ) {
            item(key = 0) {
                HeardItem(uin = state.uin)
            }
            if (invokeStatus is InvokeFail) {
                item(key = 1) {
                    Retry(executor = executor)
                }
            }
            if (invokeStatus is InvokeSuccess) {
                item(key = 2) {
                    VerifyCodeItem(
                        verifyCode = state.verifyCode,
                        mbPhoneInfo = state.phoneInfo,
                        state.timeOut,
                        executor = executor
                    )
                }
            }
        }
    }

}

@Composable
internal fun HeardItem(uin: Long) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .drawBehind {
                drawBottomSeparator(insetStart = 12.dp, insetEnd = 12.dp)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        CoverImage(
            data = "https://q.qlogo.cn/g?b=qq&nk=${uin}&s=100",
            size = 48.dp,
            shape = RoundedCornerShape(24.dp)
        )
        Item(title = "账号:${uin}", titleColor = QstoolComposeThem.colors.textPrimary)
    }
}

@Composable
internal fun Retry(executor: (ChangeAction) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), contentAlignment = Alignment.Center
    ) {
        DialogAction(text = "重试", color = QstoolComposeThem.colors.iconCurrent) {
            executor(ChangeAction.Retry)
        }
    }

}

@Composable
internal fun VerifyCodeItem(
    verifyCode: String,
    mbPhoneInfo: PhoneInfo,
    timeOut: Int,
    executor: (ChangeAction) -> Unit,
) {
    Column(
        Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Item(
            title = "通过手机号 ${mbPhoneInfo.areaCode} ${mbPhoneInfo.phoneNum} 接收短信验证码",
            titleColor = QstoolComposeThem.colors.textPrimary,
            paddingHor = 0.dp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            VerifyCodeTextField(modifier = Modifier
                .width(0.dp)
                .weight(3f),
                value = verifyCode,
                hint = "请输入验证码",
                onValueChange = {
                    executor(ChangeAction.InputVerificationCode(it))
                },
                trailingIcon = {
                    if (verifyCode.isNotEmpty()) {
                        IconButton(onClick = {
                            executor(ChangeAction.InputVerificationCode(""))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "清空"
                            )
                        }
                    }
                })
            DialogAction(
                text = if (timeOut > 0) "重新发送(${timeOut}s)" else "发送验证码",
                enabled = timeOut <= 0,
                color = QstoolComposeThem.colors.iconCurrent
            ) {
                executor(ChangeAction.RequestSms)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            DialogAction(text = "下一步", color = QstoolComposeThem.colors.iconCurrent) {
                executor(ChangeAction.CheckSms)
            }
        }
    }

}
