package com.baimsg.qstool

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.baimsg.qstool.base.animation.fadeEnter
import com.baimsg.qstool.base.animation.fadeExit
import com.baimsg.qstool.base.animation.openEnter
import com.baimsg.qstool.base.animation.openExit
import com.baimsg.qstool.ui.change.ChangeScreen
import com.baimsg.qstool.ui.home.HomeScreen
import com.baimsg.qstool.ui.login.LoginScreen
import com.baimsg.qstool.ui.me.MeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

/**
 * Create by Baimsg on 2023/4/1
 *
 **/

const val ROUTER_KEY_UIN = "uin"

/**
 * 主页面封装
 */
internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Me : Screen("me")
}

/**
 * 子页面封装
 */
internal sealed class LeafScreen(
    private val route: String,
) {
    /**
     * 创建路由地址
     * @param root 上级页面
     *
     */
    fun createRoute(root: Screen): String = "${root.route}/$route"


    object Home : LeafScreen("home")
    object Me : LeafScreen("me")
    object Login : LeafScreen("login")
    object Change : LeafScreen("change/{$ROUTER_KEY_UIN}") {
        fun createRoute(root: Screen, uin: Long): String = "${root.route}/change/$uin"
    }
}


/**
 * 整个app的导航代理方法
 * @param navController 导航控制器
 * @param modifier 修饰符
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { defaultNutEnterTransition(initialState, targetState) },
        exitTransition = { defaultNutExitTransition(initialState, targetState) },
        popEnterTransition = { defaultNutPopEnterTransition(initialState, targetState) },
        popExitTransition = { defaultNutPopExitTransition(initialState, targetState) },
        modifier = modifier,
    ) {
        addHomeTopLevel(navController)
        addMeTopLevel(navController)
    }
}

private fun NavGraphBuilder.addHomeTopLevel(navController: NavController) {
    val root = Screen.Home
    navigation(
        route = root.route, startDestination = LeafScreen.Home.createRoute(root)
    ) {
        addHome(navController, root)
        addLogin(navController, root)
        addChange(navController, root)
    }
}

private fun NavGraphBuilder.addMeTopLevel(navController: NavController) {
    val root = Screen.Me
    navigation(
        route = root.route, startDestination = LeafScreen.Me.createRoute(root)
    ) {
        addMe(navController, root)
    }
}


@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addHome(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Home.createRoute(root)) {
        HomeScreen(openLoginScreen = {
            navController.navigate(LeafScreen.Login.createRoute(root))
        }, openChangeScreen = {
            navController.navigate(LeafScreen.Change.createRoute(root, it))
        })
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addLogin(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Login.createRoute(root)) {
        LoginScreen(onBack = {
            navController.navigateUp()
        })
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addChange(navController: NavController, root: Screen) {
    composable(
        route = LeafScreen.Change.createRoute(root),
        arguments = listOf(navArgument(ROUTER_KEY_UIN) { type = NavType.LongType })
    ) {
        ChangeScreen(onBack = {
            navController.navigateUp()
        })
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addMe(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Me.createRoute(root)) {
        MeScreen()
    }
}

/**
 *
 * =============================================================================
 * =============================================================================
 * 过度动画
 *
 */

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultNutEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {

    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    //（底部导航图）
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeEnter()
    }
    // 否则我们在同一个导航图中，我们可以暗示一个方向
    return openEnter()
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultNutExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    //（底部导航图）
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeExit()
    }
    // 否则我们在同一个导航图中，我们可以暗示一个方向
    return openExit()
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultNutPopEnterTransition(
    initial: NavBackStackEntry, target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    //（底部导航图）
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeEnter()
    }
    return openEnter()
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultNutPopExitTransition(
    initial: NavBackStackEntry, target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    //（底部导航图）
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeExit()
    }
    return openExit()
}


