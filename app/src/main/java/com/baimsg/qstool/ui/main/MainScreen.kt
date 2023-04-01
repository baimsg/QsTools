package com.baimsg.qstool.ui.main

import android.annotation.SuppressLint
import android.webkit.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.baimsg.qstool.AppNavigation
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/**
 * Create by Baimsg on 2023/3/31
 *
 **/


@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun MainScreen(viewModel: MainActivityViewModel) {
    val navController = rememberAnimatedNavController()
    val configuration = LocalConfiguration.current
    val useBottomNavigation by remember {
        derivedStateOf { configuration.smallestScreenWidthDp < 600 }
    }
    val currentSelectedItem by navController.currentScreenAsState(viewModel = viewModel)
    val hasBottomBar = rememberUpdatedState(viewModel.hasBottomBar)

    Scaffold(backgroundColor = QstoolComposeThem.colors.background, bottomBar = {
        if (useBottomNavigation) {
            if (hasBottomBar.value) {
                MainBottomNavigation(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selectedScreen ->
                        navController.navigate(selectedScreen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }) { paddingValues ->
        Row(
            Modifier
                .fillMaxSize()
                .background(QstoolComposeThem.colors.background)
                .padding(paddingValues)
        ) {
            if (!useBottomNavigation) {
                MainNavigationRail(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selected ->
                        navController.navigate(selected.route) {
                            launchSingleTop = true
                            restoreState = true

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxHeight(),
                )
                Divider(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
            }
            AppNavigation(
                navController = navController, modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

        }
    }
}