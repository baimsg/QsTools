package com.baimsg.qstool.ui.modal

import android.view.View
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

/**
 * Create by Baimsg on 2023/4/2
 * 底部弹窗
 * @param mask 蒙层颜色
 * @param systemCancellable 系统可取消
 * @param draggable 可拖拽
 * @param maskTouchBehavior 蒙层触摸行为
 * @param modalHostProvider 蒙层触摸行为
 * @param enter 进场动画
 * @param exit 退场动画
 * @param widthLimit 限制宽度
 * @param heightLimit 限制高度
 * @param radius 圆角弧度
 * @param background 背景颜色
 * @param content 内容
 **/
@OptIn(ExperimentalAnimationApi::class)
fun View.bottomSheet(
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    draggable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    enter: EnterTransition = slideInVertically(tween()) { it },
    exit: ExitTransition = slideOutVertically(tween()) { it },
    widthLimit: (maxWidth: Dp) -> Dp = { it.coerceAtMost(420.dp) },
    heightLimit: (maxHeight: Dp) -> Dp = { if (it < 640.dp) it - 40.dp else it * 0.85f },
    radius: Dp = 12.dp,
    background: Color = Color.White,
    content: @Composable (IModal) -> Unit
): IModal {
    return animateModal(
        mask = Color.Transparent,
        systemCancellable = systemCancellable,
        maskTouchBehavior = maskTouchBehavior,
        modalHostProvider = modalHostProvider,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
    ) { modal ->
        BottomSheet(
            modal = modal,
            draggable = draggable,
            widthLimit = widthLimit,
            heightLimit = heightLimit,
            radius = radius,
            background = background,
            mask = mask,
            modifier = Modifier.animateEnterExit(
                enter = enter, exit = exit
            ),
            content = content
        )
    }
}

/**
 * 底部弹窗列表
 * @param modal 模态
 * @param state 列表状态
 * @param children 列表项
 */
@Composable
fun BottomSheetList(
    modal: IModal,
    state: LazyListState = rememberLazyListState(),
    children: LazyListScope.(IModal) -> Unit
) {
    LazyColumn(
        state = state, modifier = Modifier.fillMaxWidth()
    ) {
        children(modal)
    }
}


/**
 * 底部弹窗
 * @param modal 模态
 * @param draggable 可拖动
 * @param widthLimit 限制宽度
 * @param heightLimit 限制高度
 * @param radius 圆角角度
 * @param background 背景颜色
 * @param mask 蒙层颜色
 * @param modifier 修饰符
 * @param content 内容
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityScope.BottomSheet(
    modal: IModal,
    draggable: Boolean,
    widthLimit: (maxWidth: Dp) -> Dp,
    heightLimit: (maxHeight: Dp) -> Dp,
    radius: Dp = 2.dp,
    background: Color = Color.White,
    mask: Color = DefaultMaskColor,
    modifier: Modifier,
    content: @Composable (IModal) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        val wl = widthLimit(maxWidth)
        val wh = heightLimit(maxHeight)

        var contentModifier = if (wl < maxWidth) {
            Modifier.width(wl)
        } else {
            Modifier.fillMaxWidth()
        }
        contentModifier = contentModifier.heightIn(max = wh.coerceAtMost(maxHeight))
        if (radius > 0.dp) {
            contentModifier =
                contentModifier.clip(RoundedCornerShape(topStart = radius, topEnd = radius))
        }
        contentModifier = contentModifier
            .background(background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) {}

        if (draggable) {
            NestScrollWrapper(modal = modal, modifier = modifier, mask = mask) {
                Box(modifier = contentModifier) {
                    content(modal)
                }
            }
        } else {
            if (mask != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateEnterExit(
                            enter = fadeIn(tween()), exit = fadeOut(tween())
                        )
                        .background(mask)
                )
            }
            Box(modifier = modifier.then(contentModifier)) {
                content(modal)
            }
        }
    }
}


/**
 * 嵌套滚动包装
 * @param modal 模态
 * @param modifier 修饰符
 * @param mask 蒙层颜色
 * @param content 内容
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedVisibilityScope.NestScrollWrapper(
    modal: IModal, modifier: Modifier, mask: Color, content: @Composable () -> Unit
) {
    val yOffsetState = remember {
        mutableStateOf(0f)
    }
    val mutableContentHeight = remember {
        MutableHeight(0f)
    }
    val contentHeight = mutableContentHeight.height

    val percent = if (contentHeight <= 0f) 1f else {
        ((contentHeight - yOffsetState.value) / contentHeight).coerceAtMost(1f).coerceAtLeast(0f)
    }

    val nestedScrollConnection = remember(modal, yOffsetState) {
        BottomSheetNestedScrollConnection(modal, yOffsetState, mutableContentHeight)
    }
    val yOffset = yOffsetState.value
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        if (mask != Color.Transparent) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(percent)
                    .animateEnterExit(
                        enter = fadeIn(tween()), exit = fadeOut(tween())
                    )
                    .background(mask)
            )
            Box(modifier = modifier
                .graphicsLayer { translationY = yOffset }
                .nestedScroll(nestedScrollConnection)
                .onGloballyPositioned {
                    mutableContentHeight.height = it.size.height.toFloat()
                }) {
                content()
            }
        }
    }
}

/**
 * 可变高度类
 * @param height 高度
 */
private class MutableHeight(var height: Float)

/**
 * 底部弹窗嵌套滚动连接类
 * @param modal 模态
 * @param yOffsetStateFlow y轴变化量
 * @param contentHeight 内容高度
 */
private class BottomSheetNestedScrollConnection(
    val modal: IModal, val yOffsetStateFlow: MutableState<Float>, val contentHeight: MutableHeight
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (source == NestedScrollSource.Fling) {
            return Offset.Zero
        }
        val currentOffset = yOffsetStateFlow.value
        if (available.y < 0 && currentOffset > 0) {
            val consume = available.y.coerceAtLeast(currentOffset)
            yOffsetStateFlow.value = currentOffset + consume
            return Offset(0f, consume)
        }
        return super.onPreScroll(available, source)
    }

    override fun onPostScroll(
        consumed: Offset, available: Offset, source: NestedScrollSource
    ): Offset {
        if (source == NestedScrollSource.Fling) {
            return Offset.Zero
        }
        if (available.y > 0) {
            yOffsetStateFlow.value = yOffsetStateFlow.value + available.y
            return Offset(0f, available.y)
        }
        return super.onPostScroll(consumed, available, source)
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        if (yOffsetStateFlow.value > 0) {
            if (available.y > 0 || (available.y == 0f && yOffsetStateFlow.value > contentHeight.height / 2)) {
                modal.dismiss()
            } else {
                val animated = Animatable(yOffsetStateFlow.value, Float.VectorConverter)
                animated.asState()
                animated.animateTo(0f, tween()) {
                    yOffsetStateFlow.value = value
                }

            }
            return available
        }
        return Velocity.Zero
    }
}