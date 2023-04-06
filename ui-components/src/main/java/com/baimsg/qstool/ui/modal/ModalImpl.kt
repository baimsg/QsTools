package com.baimsg.qstool.ui.modal

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import com.baimsg.qstool.base.utils.extensions.hideSoftInput
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Create by Baimsg on 2023/4/2
 * 模态展示
 * @param rootLayout 根布局
 * @param onBackPressedDispatcher OnBack按下调度程序
 * @param mask 蒙层颜色
 * @param systemCancellable 系统可取消
 * @param maskTouchBehavior 蒙层触摸行为
 **/
internal abstract class ModalPresent(
    private val rootLayout: FrameLayout,
    private val onBackPressedDispatcher: OnBackPressedDispatcher,
    val mask: Color = DefaultMaskColor,
    val systemCancellable: Boolean = true,
    val maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
) : IModal {
    /**
     * 展示监听器列表
     */
    private val onShowListeners = arrayListOf<IModal.Action>()

    /**
     * 解雇监听器列表
     */
    private val onDismissListeners = arrayListOf<IModal.Action>()

    /**
     * 展示
     */
    private val visibleFlow = MutableStateFlow(false)

    /**
     * 已显示
     */
    private var isShown = false

    /**
     * 正在解雇
     */
    private var isDismissing = false

    private val composeLayout = ComposeView(rootLayout.context).apply {
        visibility = View.GONE
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (systemCancellable) {
                dismiss()
            }
        }
    }

    init {
        composeLayout.setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                val visible by visibleFlow.collectAsState(initial = false)
                ModalContent(visible = visible) {
                    if (isDismissing) {
                        doAfterDismiss()
                    }
                }
            }
        }

    }

    /**
     * 解雇后
     */
    private fun doAfterDismiss() {
        isDismissing = false
        composeLayout.visibility = View.GONE
        composeLayout.disposeComposition()
        rootLayout.removeView(composeLayout)
        onBackPressedCallback.remove()
        onDismissListeners.forEach {
            it.invoke(this)
        }
    }

    @Composable
    abstract fun ModalContent(visible: Boolean, dismissFinishAction: () -> Unit)

    override fun isShowing(): Boolean = isShown

    override fun show(): IModal {
        if (isShown || isDismissing) {
            return this
        }
        isShown = true
        rootLayout.addView(composeLayout, generateLayoutParams())
        (rootLayout.context as? ComponentActivity)?.hideSoftInput()
        composeLayout.visibility = View.VISIBLE
        visibleFlow.value = true
        onBackPressedDispatcher.addCallback(onBackPressedCallback = onBackPressedCallback)
        onShowListeners.forEach {
            it.invoke(this)
        }
        return this
    }

    /**
     * 生成布局参数
     */
    open fun generateLayoutParams(): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun dismiss() {
        if (!isShown) {
            return
        }
        isShown = false
        isDismissing = true
        visibleFlow.value = false
    }


    override fun doOnShow(listener: IModal.Action): IModal {
        onShowListeners.add(listener)
        return this
    }

    override fun doOnDismiss(listener: IModal.Action): IModal {
        onDismissListeners.add(listener)
        return this
    }

    override fun removeOnShowAction(listener: IModal.Action): IModal {
        onShowListeners.remove(listener)
        return this
    }

    override fun removeOnDismissAction(listener: IModal.Action): IModal {
        onDismissListeners.remove(listener)
        return this
    }

}

/**
 * 静止模态实现
 * @param rootLayout 根布局
 * @param onBackPressedDispatcher OnBack按下调度程序
 * @param mask 蒙层颜色
 * @param systemCancellable 系统可取消
 * @param maskTouchBehavior 蒙层触摸行为
 * @param content 内容
 */
internal class StillModalImpl(
    rootLayout: FrameLayout,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    val content: @Composable (modal: IModal) -> Unit,
) : ModalPresent(rootLayout, onBackPressedDispatcher, mask, systemCancellable, maskTouchBehavior) {
    @Composable
    override fun ModalContent(visible: Boolean, dismissFinishAction: () -> Unit) {
        if (visible) {
            Box(modifier = Modifier.fillMaxSize().background(mask).let {
                if (maskTouchBehavior == MaskTouchBehavior.penetrate) {
                    it
                } else {
                    it.clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        enabled = maskTouchBehavior == MaskTouchBehavior.dismiss
                    ) {
                        dismiss()
                    }
                }
            })
            content(this)
            DisposableEffect("") {
                onDispose {
                    dismissFinishAction()
                }
            }
        }
    }
}

/**
 * 动画模态实现
 * @param rootLayout 根布局
 * @param onBackPressedDispatcher OnBack按下调度程序
 * @param mask 蒙层颜色
 * @param systemCancellable 系统可取消
 * @param maskTouchBehavior 蒙层触摸行为
 * @param enter 进场动画
 * @param exit 退场动画
 * @param content 内容
 */
internal class AnimateModalImpl(
    rootLayout: FrameLayout,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    val enter: EnterTransition = fadeIn(tween(), 0f),
    val exit: ExitTransition = fadeOut(tween(), 0f),
    val content: @Composable AnimatedVisibilityScope.(modal: IModal) -> Unit,
) : ModalPresent(
    rootLayout, onBackPressedDispatcher, mask, systemCancellable, maskTouchBehavior
) {
    @Composable
    override fun ModalContent(visible: Boolean, dismissFinishAction: () -> Unit) {
        AnimatedVisibility(visible = visible, enter = enter, exit = exit) {
            Box(modifier = Modifier.fillMaxSize().background(mask).let {
                if (maskTouchBehavior == MaskTouchBehavior.penetrate) {
                    it
                } else {
                    it.clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        enabled = maskTouchBehavior == MaskTouchBehavior.dismiss
                    ) {
                        dismiss()
                    }
                }
            })
            content(this@AnimateModalImpl)
            DisposableEffect("") {
                onDispose {
                    dismissFinishAction()
                }
            }
        }
    }

}