package com.baimsg.qstool.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import com.baimsg.qstool.LeafScreen

import com.baimsg.qstool.Screen
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.baimsg.qstool.utils.extensions.logD
import com.baimsg.qstool.ui.resources.R

/**
 * Create by Baimsg on 2023/4/1
 *
 * 封装bottom的icon
 * @param screen 页面
 * @param labelResId 显示文本
 * @param contentDescriptionResId 描述文本
 **/
internal sealed class MainNavigationItems(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    /**
     * res资源
     * @param screen 屏幕
     * @param labelResId 显示文字
     * @param contentDescriptionResId 内容描述
     * @param iconResId 默认res图标
     * @param selectedIconResId 选中res图标
     */
    class ResourceIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : MainNavigationItems(screen, labelResId, contentDescriptionResId)

    /**
     * 矢量图
     * @param screen 屏幕
     * @param labelResId 显示文字
     * @param contentDescriptionResId 内容描述
     * @param iconImageVector 默认矢量图标
     * @param selectedImageVector 选中矢量图标
     */
    class ImageVectorIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : MainNavigationItems(screen, labelResId, contentDescriptionResId)
}

/**
 * 主页icon列表
 */
internal val mainNavigationItems = listOf(
    MainNavigationItems.ResourceIcon(
        screen = Screen.Home,
        labelResId = R.string.navigation_item_home,
        contentDescriptionResId = R.string.navigation_item_home,
        iconResId = R.drawable.ic_tab_home
    ), MainNavigationItems.ResourceIcon(
        screen = Screen.Me,
        labelResId = R.string.navigation_item_me,
        contentDescriptionResId = R.string.navigation_item_me,
        iconResId = R.drawable.ic_tab_me
    )
)

/**
 * 底部栏（手机显示）
 * @param selectedNavigation 当前选择的 tab
 * @param onNavigationSelected 切换 tab 回调
 * @param modifier 修饰符
 */
@Composable
internal fun MainBottomNavigation(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        backgroundColor = QstoolComposeThem.colors.bottomBar,
        elevation = BottomNavigationDefaults.Elevation,
        modifier = modifier
    ) {
        mainNavigationItems.forEachIndexed { _, resourceIcon ->
            val isSelected = selectedNavigation == resourceIcon.screen
            BottomNavigationItem(
                icon = {
                    MainNavigationItemIcon(
                        item = resourceIcon, selected = isSelected
                    )
                },
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = { onNavigationSelected(resourceIcon.screen) },
                selectedContentColor = QstoolComposeThem.colors.iconCurrent,
                unselectedContentColor = QstoolComposeThem.colors.icon
            )
        }
    }
}

/**
 * 侧边栏（平板电脑显示）
 * @param selectedNavigation 当前选择的 tab
 * @param onNavigationSelected 切换 tab 回调
 * @param modifier 修饰符
 */
@Composable
internal fun MainNavigationRail(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = QstoolComposeThem.colors.bottomBar,
        elevation = NavigationRailDefaults.Elevation,
        modifier = modifier,
    ) {
        NavigationRail(backgroundColor = Color.Transparent, elevation = 0.dp) {
            mainNavigationItems.forEach { item ->
                val isSelected = selectedNavigation == item.screen
                NavigationRailItem(
                    icon = {
                        MainNavigationItemIcon(
                            item = item, selected = isSelected
                        )
                    },
                    alwaysShowLabel = false,
                    label = { Text(text = stringResource(item.labelResId)) },
                    selected = isSelected,
                    onClick = { onNavigationSelected(item.screen) },
                    selectedContentColor = QstoolComposeThem.colors.iconCurrent,
                    unselectedContentColor = QstoolComposeThem.colors.icon
                )
            }
        }
    }
}

/**
 * 底部栏图标样式
 * @param item 当前icon信息
 * @param selected 是已选择
 */
@Composable
internal fun MainNavigationItemIcon(item: MainNavigationItems, selected: Boolean) {
    val painter = when (item) {
        is MainNavigationItems.ResourceIcon -> painterResource(item.iconResId)
        is MainNavigationItems.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }

    val selectedPainter = when (item) {
        is MainNavigationItems.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is MainNavigationItems.ImageVectorIcon -> item.selectedImageVector?.let {
            rememberVectorPainter(it)
        }
    }
    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId),
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId),
            Modifier.padding(4.dp)
        )
    }
}

/**
 * 将 [NavController.OnDestinationChangedListener] 添加到此 [NavController] 并更新
 * 返回的 [State] 随着目的地的变化而更新.
 */
@Stable
@Composable
internal fun NavController.currentScreenAsState(viewModel: MainActivityViewModel): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Home) }
    val hasBottomBar = rememberUpdatedState(viewModel.hasBottomBar)

    DisposableEffect(this) {
        val listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                when {
                    destination.hierarchy.any { it.route == Screen.Home.route } -> {
                        selectedItem.value = Screen.Home
                    }
                    destination.hierarchy.any { it.route == Screen.Me.route } -> {
                        selectedItem.value = Screen.Me
                    }
                }
                /**
                 * 不是在主页的时候隐藏bottomBar
                 */
                destination.route?.let { route ->
                    val isMain = when (route) {
                        LeafScreen.Home.createRoute(Screen.Home) -> true
                        LeafScreen.Me.createRoute(Screen.Me) -> true
                        else -> false
                    }
                    if (hasBottomBar.value != isMain) {
                        viewModel.hasBottomBar = isMain
                    }
                }
            }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}