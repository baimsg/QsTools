package com.baimsg.qstool.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

/**
 * Create by Baimsg on 2022/3/29
 *
 **/
@Composable
fun PressWithAlphaRow(
    modifier: Modifier,
    enable: Boolean = true,
    pressAlpha: Float = 0.5f,
    disableAlpha: Float = 0.5f,
    onClick: (() -> Unit)? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    Row(modifier = Modifier
        .alpha(if (!enable) disableAlpha else if (isPressed.value) pressAlpha else 1f)
        .clickable(enabled = enable, interactionSource = interactionSource, indication = null) {
            onClick?.invoke()
        }
        .then(modifier),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        content = content)
}