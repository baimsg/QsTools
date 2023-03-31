package com.baimsg.qstool.base.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
/**
 * 平移退场动画
 */
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.exitSlideToStart(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    targetOffset: (offsetForFullSlide: Int) -> Int = { it }
) = exitSlideTo(AnimationDirection.Start, durationMillis, delayMillis, easing, targetOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.exitSlideToEnd(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    targetOffset: (offsetForFullSlide: Int) -> Int = { it }
) = exitSlideTo(AnimationDirection.End, durationMillis, delayMillis, easing, targetOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.exitSlideToUp(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    targetOffset: (offsetForFullSlide: Int) -> Int = { it }
) = exitSlideTo(AnimationDirection.Up, durationMillis, delayMillis, easing, targetOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.exitSlideToDown(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    targetOffset: (offsetForFullSlide: Int) -> Int = { it }
) = exitSlideTo(AnimationDirection.Down, durationMillis, delayMillis, easing, targetOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.exitSlideTo(
    retention: AnimationDirection,
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    targetOffset: (offsetForFullSlide: Int) -> Int = { it }
): ExitTransition {
    return slideOutOfContainer(
        when (retention) {
            AnimationDirection.Start -> AnimatedContentScope.SlideDirection.Start
            AnimationDirection.End -> AnimatedContentScope.SlideDirection.End
            AnimationDirection.Up -> AnimatedContentScope.SlideDirection.Up
            AnimationDirection.Down -> AnimatedContentScope.SlideDirection.Down
        },
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis, easing = easing
        ), targetOffset = targetOffset
    )
}


/**
 * 平移进场动画
 */
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.enterSlideToStart(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    initialOffset: (offsetForFullSlide: Int) -> Int = { it }
) = enterSlideTo(AnimationDirection.Start, durationMillis, delayMillis, easing, initialOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.enterSlideToEnd(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    initialOffset: (offsetForFullSlide: Int) -> Int = { it }
) = enterSlideTo(AnimationDirection.End, durationMillis, delayMillis, easing, initialOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.enterSlideToUp(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    initialOffset: (offsetForFullSlide: Int) -> Int = { it }
) = enterSlideTo(AnimationDirection.Up, durationMillis, delayMillis, easing, initialOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.enterSlideToDown(
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    initialOffset: (offsetForFullSlide: Int) -> Int = { it }
) = enterSlideTo(AnimationDirection.Down, durationMillis, delayMillis, easing, initialOffset)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.enterSlideTo(
    retention: AnimationDirection,
    durationMillis: Int = 200,
    delayMillis: Int = 0,
    easing: Easing = LinearOutSlowInEasing,
    initialOffset: (offsetForFullSlide: Int) -> Int = { it }
): EnterTransition {
    return slideIntoContainer(
        when (retention) {
            AnimationDirection.Start -> AnimatedContentScope.SlideDirection.Start
            AnimationDirection.End -> AnimatedContentScope.SlideDirection.End
            AnimationDirection.Up -> AnimatedContentScope.SlideDirection.Up
            AnimationDirection.Down -> AnimatedContentScope.SlideDirection.Down
        },
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis, easing = easing
        ),
        initialOffset = initialOffset
    )
}


@ExperimentalAnimationApi
fun openEnter() = fadeIn(
    animationSpec = tween(
        durationMillis = 50,
        delayMillis = 35,
        easing = LinearEasing
    )
) + scaleIn(initialScale = 0.85f, animationSpec = tween(300))

@ExperimentalAnimationApi
fun openExit() = fadeOut(
    animationSpec = tween(
        durationMillis = 50,
        delayMillis = 35,
        easing = LinearEasing
    )
) + scaleOut(targetScale = 1.15f, animationSpec = tween(300))

fun fadeNoChangEnter() = fadeIn(initialAlpha = 1f, animationSpec = tween(300))

fun fadeNoChangExit() = fadeOut(targetAlpha = 1f, animationSpec = tween(300))

fun fadeEnter() = fadeIn(animationSpec = tween(150))

fun fadeExit() = fadeOut(animationSpec = tween(150))