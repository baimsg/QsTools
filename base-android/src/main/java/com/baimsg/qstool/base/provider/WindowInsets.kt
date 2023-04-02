package com.baimsg.qstool.base.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.baimsg.qstool.ui.resources.R

/**
 * Create by Baimsg on 2023/3/30
 *
 **/
@SuppressLint("CompositionLocalNaming")
val localQstoolWindowInsets = staticCompositionLocalOf { WindowInsetsCompat.CONSUMED }

@Composable
fun QstoolWindowInsetsProvider(content: @Composable () -> Unit) {
    val view = LocalView.current
    val windowInsets: MutableState<WindowInsetsCompat> = remember(view) {
        mutableStateOf(
            view.getTag(R.id.qstool_window_inset_cache) as? WindowInsetsCompat
                ?: WindowInsetsCompat.CONSUMED
        )
    }

    LaunchedEffect(view) {
        ViewCompat.setOnApplyWindowInsetsListener(
            view
        ) { _, insets ->
            windowInsets.value = insets
            view.setTag(R.id.qstool_window_inset_cache, insets)
            insets
        }
        view.requestApplyInsets()
    }

    CompositionLocalProvider(localQstoolWindowInsets provides windowInsets.value) {
        content()
    }

}

data class DpInsets(val left: Dp, val top: Dp, val right: Dp, val bottom: Dp) {
    companion object {
        val NONE = DpInsets(0.dp, 0.dp, 0.dp, 0.dp)
    }
}

@Composable
fun Insets.dp(): DpInsets {
    if (this == Insets.NONE) {
        return DpInsets.NONE
    }
    return with(LocalDensity.current) {
        DpInsets(
            (left / density).dp,
            (top / density).dp,
            (right / density).dp,
            (bottom / density).dp
        )
    }
}