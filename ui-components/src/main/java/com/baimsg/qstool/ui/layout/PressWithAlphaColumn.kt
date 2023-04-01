package com.baimsg.qstool.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

/**
 * Create by Baimsg on 2022/3/7
 *
 **/
@Composable
fun PressWithAlphaColumn(
    modifier: Modifier,
    enable: Boolean = true,
    pressAlpha: Float = 0.5f,
    disableAlpha: Float = 0.5f,
    onClick: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    Column(modifier = Modifier
        .alpha(if (!enable) disableAlpha else if (isPressed.value) pressAlpha else 1f)
        .clickable(enabled = enable, interactionSource = interactionSource, indication = null) {
            onClick?.invoke()
        }
        .then(modifier),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content)
}