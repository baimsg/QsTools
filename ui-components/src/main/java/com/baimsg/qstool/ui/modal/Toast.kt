package com.baimsg.qstool.ui.modal

import android.view.View
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baimsg.qstool.ui.defaultCommonHorSpace
import com.baimsg.qstool.ui.defaultToastVerEdgeProtectionMargin
import kotlinx.coroutines.*

/**
 * Create by Baimsg on 2023/4/2
 *
 **/
private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

@Composable
fun Toast(
    modal: IModal,
    radius: Dp = 8.dp,
    background: Color = Color.DarkGray,
    content: @Composable BoxScope.(IModal) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(radius))
            .background(background)
    ) {
        content(modal)
    }
}

/**
 * 动画吐司
 * @param duration 显示时长
 * @param modalHostProvider 模态提供者
 * @param alignment 位置
 * @param horEdge 水平距离
 * @param verEdge 垂直距离
 * @param radius 圆角弧度
 * @param background 背景颜色
 * @param enter 进场动画
 * @param exit 退场动画
 */
fun View.toast(
    text: String,
    textColor: Color = Color.White,
    fontSize: TextUnit = 16.sp,
    duration: Long = 1000,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    alignment: Alignment = Alignment.BottomCenter,
    horEdge: Dp = defaultCommonHorSpace,
    verEdge: Dp = defaultToastVerEdgeProtectionMargin,
    radius: Dp = 8.dp,
    background: Color = Color.Black,
    enter: EnterTransition = slideInVertically(initialOffsetY = { it }) + fadeIn(),
    exit: ExitTransition = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
): IModal {
    return toast(
        duration = duration,
        modalHostProvider = modalHostProvider,
        alignment = alignment,
        horEdge = horEdge,
        verEdge = verEdge,
        radius = radius,
        background = background,
        enter = enter,
        exit = exit
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .align(alignment)
        )
    }
}

/**
 * 动画吐司
 * @param duration 显示时长
 * @param modalHostProvider 模态提供者
 * @param alignment 位置
 * @param horEdge 水平距离
 * @param verEdge 垂直距离
 * @param radius 圆角弧度
 * @param background 背景颜色
 * @param enter 进场动画
 * @param exit 退场动画
 * @param content 内容
 */
@OptIn(ExperimentalAnimationApi::class)
fun View.toast(
    duration: Long = 1000,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    alignment: Alignment = Alignment.BottomCenter,
    horEdge: Dp = defaultCommonHorSpace,
    verEdge: Dp = defaultToastVerEdgeProtectionMargin,
    radius: Dp = 8.dp,
    background: Color = Color.Black,
    enter: EnterTransition = slideInVertically(initialOffsetY = { it }) + fadeIn(),
    exit: ExitTransition = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
    content: @Composable BoxScope.(IModal) -> Unit
): IModal {
    var job: Job? = null
    return animateModal(
        mask = Color.Transparent,
        systemCancellable = false,
        maskTouchBehavior = MaskTouchBehavior.penetrate,
        uniqueId = -1,
        modalHostProvider = modalHostProvider,
        enter = enter,
        exit = exit
    ) { modal ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horEdge, vertical = verEdge),
            contentAlignment = alignment
        ) {
            Box(
                modifier = Modifier.animateEnterExit(enter = enter, exit = exit),
                contentAlignment = alignment
            ) {
                Toast(modal = modal, radius = radius, background = background, content = content)
            }
        }
    }.doOnShow {
        job = scope.launch {
            delay(duration)
            job = null
            it.dismiss()
        }
    }.doOnDismiss {
        job?.cancel()
        job = null
    }.show()
}

/**
 * 静止吐司
 * @param duration 显示时长
 * @param modalHostProvider 模态提供者
 * @param alignment 位置
 * @param horEdge 水平距离
 * @param verEdge 垂直距离
 * @param radius 圆角弧度
 * @param background 背景颜色
 */
fun View.stillToast(
    text: String,
    textColor: Color = Color.White,
    fontSize: TextUnit = 16.sp,
    duration: Long = 1000,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    alignment: Alignment = Alignment.BottomCenter,
    horEdge: Dp = defaultCommonHorSpace,
    verEdge: Dp = defaultToastVerEdgeProtectionMargin,
    radius: Dp = 8.dp,
    background: Color = Color.Black
): IModal {
    return stillToast(
        duration = duration,
        modalHostProvider = modalHostProvider,
        alignment = alignment,
        horEdge = horEdge,
        verEdge = verEdge,
        radius = radius,
        background = background
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .align(Alignment.Center)
        )
    }
}

/**
 * 静止吐司
 * @param duration 显示时长
 * @param modalHostProvider 模态提供者
 * @param alignment 位置
 * @param horEdge 水平距离
 * @param verEdge 垂直距离
 * @param radius 圆角弧度
 * @param background 背景颜色
 * @param content 内容
 */
fun View.stillToast(
    duration: Long = 1000,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    alignment: Alignment = Alignment.BottomCenter,
    horEdge: Dp = defaultCommonHorSpace,
    verEdge: Dp = defaultToastVerEdgeProtectionMargin,
    radius: Dp = 8.dp,
    background: Color = Color.Black,
    content: @Composable BoxScope.(IModal) -> Unit
): IModal {
    var job: Job? = null
    return stillModal(
        mask = Color.Transparent,
        systemCancellable = false,
        maskTouchBehavior = MaskTouchBehavior.penetrate,
        uniqueId = -1,
        modalHostProvider = modalHostProvider,
    ) { modal ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horEdge, vertical = verEdge),
            contentAlignment = alignment
        ) {
            Toast(modal = modal, radius = radius, background = background, content = content)
        }
    }.doOnShow {
        job = scope.launch {
            delay(duration)
            job = null
            it.dismiss()
        }
    }.doOnDismiss {
        job?.cancel()
        job = null
    }.show()
}