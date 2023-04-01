package com.baimsg.qstool.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.core.widgets.Optimizer

/**
 * Create by Baimsg on 2022/6/7
 *
 **/
@Composable
inline fun PressWithAlphaConstraintLayout(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    pressAlpha: Float = 0.5f,
    disableAlpha: Float = 0.5f,
    noinline onClick: (() -> Unit)? = null,
    optimizationLevel: Int = Optimizer.OPTIMIZATION_STANDARD,
    crossinline content: @Composable ConstraintLayoutScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    ConstraintLayout(modifier = Modifier
        .alpha(if (!enable) disableAlpha else if (isPressed.value) pressAlpha else 1f)
        .clickable(enabled = enable, interactionSource = interactionSource, indication = null) {
            onClick?.invoke()
        }
        .then(modifier), optimizationLevel = optimizationLevel, content = content)
}