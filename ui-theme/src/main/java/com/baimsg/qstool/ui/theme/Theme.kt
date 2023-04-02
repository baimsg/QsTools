package com.baimsg.qstool.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

/**
 * Create by Baimsg on 2023/3/31
 *
 **/


/**
 * 粉色
 */
private val PinkColorPalette = QstoolComposeColors(
    topBarBackground = pink4,
    topBarIcon = white,
    topBarText = white1,
    topBarTextSelect = white1,
    bottomBar = white,
    background = white2,
    progressColor = deepPurple4,
    textPrimary = black3,
    textSecondary = grey1,
    icon = black,
    iconSelect = pink4,
)

/**
 * 日间
 */
private val LightColorPalette = QstoolComposeColors(
    topBarBackground = indigo5,
    topBarIcon = white,
    topBarText = white1,
    topBarTextSelect = white1,
    bottomBar = white,
    background = white2,
    progressColor = lightGreen2,
    textPrimary = black3,
    textSecondary = grey1,
    icon = black,
    iconSelect = indigo6,
)

/**
 * 夜间
 */
private val DarkColorPalette = QstoolComposeColors(
    topBarBackground = black1,
    topBarIcon = white1,
    topBarText = white1,
    topBarTextSelect = white1,
    bottomBar = black1,
    background = black2,
    progressColor = lightGreen2,
    textPrimary = white4,
    textSecondary = grey1,
    icon = white5,
    iconSelect = indigo5,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QstoolComposeThem(
    theme: QstoolComposeThem.Theme = QstoolComposeThem.Theme.Light,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val targetColors = if (isDarkTheme) DarkColorPalette else when (theme) {
        QstoolComposeThem.Theme.Dark -> DarkColorPalette
        QstoolComposeThem.Theme.Light -> LightColorPalette
        QstoolComposeThem.Theme.Pink -> PinkColorPalette
    }

    //动画
    val topBarBackground = animateColorAsState(targetColors.topBarBackground, TweenSpec(600))
    val topBarIcon = animateColorAsState(targetColors.topBarIcon, TweenSpec(600))
    val topBarText = animateColorAsState(targetColors.topBarText, TweenSpec(600))
    val topBarTextSelect = animateColorAsState(targetColors.topBarTextSelect, TweenSpec(600))
    val bottomBar = animateColorAsState(targetColors.bottomBar, TweenSpec(600))
    val background = animateColorAsState(targetColors.background, TweenSpec(600))
    val progressColor = animateColorAsState(targetColors.progressColor, TweenSpec(600))
    val textPrimary = animateColorAsState(targetColors.textPrimary, TweenSpec(600))
    val textSecondary = animateColorAsState(targetColors.textSecondary, TweenSpec(600))
    val icon = animateColorAsState(targetColors.icon, TweenSpec(600))
    val iconCurrent = animateColorAsState(targetColors.iconCurrent, TweenSpec(600))

    val colors = QstoolComposeColors(
        topBarBackground = topBarBackground.value,
        topBarIcon = topBarIcon.value,
        topBarText = topBarText.value,
        topBarTextSelect = topBarTextSelect.value,
        bottomBar = bottomBar.value,
        background = background.value,
        progressColor = progressColor.value,
        textPrimary = textPrimary.value,
        textSecondary = textSecondary.value,
        icon = icon.value,
        iconSelect = iconCurrent.value,
    )

    CompositionLocalProvider(
        localQstoolComposeColors provides colors, LocalOverscrollConfiguration provides null
    ) {
        MaterialTheme(
            shapes = MaterialTheme.shapes, content = content
        )
    }
}

@Stable
class QstoolComposeColors(
    topBarBackground: Color,
    topBarIcon: Color,
    topBarText: Color,
    topBarTextSelect: Color,
    bottomBar: Color,
    background: Color,
    progressColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    icon: Color,
    iconSelect: Color,
) {
    var topBarBackground: Color by mutableStateOf(topBarBackground, structuralEqualityPolicy())
        internal set
    var topBarIcon: Color by mutableStateOf(topBarIcon, structuralEqualityPolicy())
        internal set
    var topBarText: Color by mutableStateOf(topBarText, structuralEqualityPolicy())
        internal set
    var topBarTextSelect: Color by mutableStateOf(topBarTextSelect, structuralEqualityPolicy())
        internal set
    var bottomBar: Color by mutableStateOf(bottomBar, structuralEqualityPolicy())
        internal set
    var background: Color by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var progressColor: Color by mutableStateOf(progressColor, structuralEqualityPolicy())
        internal set
    var textPrimary: Color by mutableStateOf(textPrimary, structuralEqualityPolicy())
        internal set
    var textSecondary: Color by mutableStateOf(textSecondary, structuralEqualityPolicy())
        internal set
    var icon: Color by mutableStateOf(icon, structuralEqualityPolicy())
        internal set
    var iconCurrent: Color by mutableStateOf(iconSelect, structuralEqualityPolicy())
        internal set
}

private val localQstoolComposeColors by lazy {
    compositionLocalOf { LightColorPalette }
}

object QstoolComposeThem {
    val colors: QstoolComposeColors
        @Composable get() = localQstoolComposeColors.current

    enum class Theme {
        Light, Dark, Pink
    }
}
