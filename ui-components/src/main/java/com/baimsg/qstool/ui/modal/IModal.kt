package com.baimsg.qstool.ui.modal

import android.os.SystemClock
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.baimsg.qstool.ui.defaultPrimaryColor
import com.baimsg.qstool.ui.resources.R

/**
 * Create by Baimsg on 2023/4/2
 *
 **/

/**
 * 默认蒙层颜色
 */
val DefaultMaskColor = Color.Black.copy(alpha = 0.5f)

val DefaultModalHostProvider = ActivityHostModalProvider()

/**
 * 蒙层触摸行为
 */
enum class MaskTouchBehavior {
    dismiss, penetrate, none
}

/**
 * 模态持有对象
 * @param current 当前模态
 */
private class ModalHolder(var current: IModal? = null)

/**
 * 模态行动
 * @param text
 * @param enabled
 * @param color
 * @param onClick
 */
class ModalAction(
    val text: String,
    val enabled: Boolean = true,
    val color: Color = defaultPrimaryColor,
    val onClick: (IModal) -> Unit
)

/**
 * 显示模态列表
 */
private class ShowingModals {
    val modals = mutableMapOf<Long, IModal>()
}

/**
 * 模态主机提供商
 */
fun interface ModalHostProvider {
    fun provide(view: View): Pair<FrameLayout, OnBackPressedDispatcher>
}

/**
 * activity模态主机提供商
 */
class ActivityHostModalProvider : ModalHostProvider {
    override fun provide(view: View): Pair<FrameLayout, OnBackPressedDispatcher> {
        val contentLayout = view.rootView.findViewById<FrameLayout>(Window.ID_ANDROID_CONTENT)
            ?: throw RuntimeException("View is not attached to Activity")
        val activity = contentLayout.context as? ComponentActivity
            ?: throw RuntimeException("view's rootView's context is not ComponentActivity")
        return contentLayout to activity.onBackPressedDispatcher
    }
}

/**
 * 模态
 * @param isVisible 是否展示
 * @param mask 蒙层
 * @param enter 进场动画
 * @param exit 退场动画
 * @param systemCancellable 系统可取消
 * @param maskTouchBehavior
 * @param doOnShow
 * @param doOnDismiss
 * @param uniqueId
 * @param modalHostProvider
 * @param content
 */
@Composable
fun Modal(
    isVisible: Boolean,
    mask: Color = DefaultMaskColor,
    enter: EnterTransition = fadeIn(tween(), 0f),
    exit: ExitTransition = fadeOut(tween(), 0f),
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    doOnShow: IModal.Action? = null,
    doOnDismiss: IModal.Action? = null,
    uniqueId: Long = SystemClock.elapsedRealtimeNanos(),
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    content: @Composable AnimatedVisibilityScope.(IModal) -> Unit
) {
    val modalHolder = remember {
        ModalHolder(null)
    }
    if (isVisible) {
        if (modalHolder.current == null) {
            val modal = LocalView.current.animateModal(
                mask,
                systemCancellable,
                maskTouchBehavior,
                uniqueId,
                modalHostProvider,
                enter,
                exit,
                content
            )
            doOnShow?.let { modal.doOnShow(it) }
            doOnDismiss?.let { modal.doOnDismiss(it) }
            modalHolder.current = modal
        }
    } else {
        modalHolder.current?.dismiss()
    }
    DisposableEffect("") {
        object : DisposableEffectResult {
            override fun dispose() {
                modalHolder.current?.dismiss()
            }
        }
    }
}

/**
 * 静止模态
 * @param mask 蒙层颜色
 * @param systemCancellable 系统可取消
 * @param maskTouchBehavior 蒙层触摸行为
 * @param uniqueId 唯一id
 * @param modalHostProvider 模态提供者
 * @param content 内容
 */
fun View.stillModal(
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    uniqueId: Long = SystemClock.elapsedRealtimeNanos(),
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    content: @Composable (IModal) -> Unit
): IModal {
    if (!isAttachedToWindow) {
        throw RuntimeException("View is not attached to window")
    }
    val modalHost = modalHostProvider.provide(this)
    val modal = StillModalImpl(
        rootLayout = modalHost.first,
        onBackPressedDispatcher = modalHost.second,
        mask = mask,
        systemCancellable = systemCancellable,
        maskTouchBehavior = maskTouchBehavior,
        content = content
    )
    val hostView = modalHost.first
    handleModelUnique(hostView, modal, uniqueId)
    return modal
}

/**
 * 动画模态
 * @param mask 蒙层颜色
 * @param systemCancellable 系统可取消
 * @param maskTouchBehavior 蒙层触摸行为
 * @param uniqueId 唯一id
 * @param modalHostProvider 模态提供者
 * @param enter 进场动画
 * @param exit 退场动画
 * @param content 内容
 */
fun View.animateModal(
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    uniqueId: Long = SystemClock.elapsedRealtimeNanos(),
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    enter: EnterTransition = fadeIn(tween(), 0f),
    exit: ExitTransition = fadeOut(tween(), 0f),
    content: @Composable AnimatedVisibilityScope.(IModal) -> Unit
): IModal {
    if (!isAttachedToWindow) {
        throw RuntimeException("View is not attached to window")
    }
    val modalHost = modalHostProvider.provide(this)
    val modal = AnimateModalImpl(
        rootLayout = modalHost.first,
        onBackPressedDispatcher = modalHost.second,
        mask = mask,
        systemCancellable = systemCancellable,
        maskTouchBehavior = maskTouchBehavior,
        enter = enter,
        exit = exit,
        content = content
    )
    val hostView = modalHost.first
    handleModelUnique(hostView, modal, uniqueId)
    return modal
}

/**
 * 处理唯一身份id
 * @param hostView 宿主view
 * @param modal 模态
 * @param uniqueId 唯一身份id
 */
private fun handleModelUnique(hostView: FrameLayout, modal: IModal, uniqueId: Long) {
    val showingModals =
        (hostView.getTag(R.id.qstool_modals) as? ShowingModals) ?: ShowingModals().also {
            hostView.setTag(R.id.qstool_modals, it)
        }
    modal.doOnShow {
        showingModals.modals.put(uniqueId, it)?.dismiss()
    }

    modal.doOnDismiss {
        if (showingModals.modals[uniqueId] == it) {
            showingModals.modals.remove(uniqueId)
        }
    }

}

/**
 * 模态接口
 */
interface IModal {
    /**
     * 显示
     */
    fun show(): IModal

    /**
     * 解雇
     */
    fun dismiss()

    /**
     * 是显示状态
     */
    fun isShowing(): Boolean

    /**
     * 添加展示监听器
     * @param listener 监听器
     */
    fun doOnShow(listener: Action): IModal

    /**
     * 解雇监听器
     * @param listener 监听器
     */
    fun doOnDismiss(listener: Action): IModal

    /**
     * 移除展示监听器
     * @param listener 监听器
     */
    fun removeOnShowAction(listener: Action): IModal

    /**
     * 移除解雇监听器
     * @param listener 监听器
     */
    fun removeOnDismissAction(listener: Action): IModal

    /**
     * 行为
     */
    fun interface Action {
        fun invoke(modal: IModal)
    }
}