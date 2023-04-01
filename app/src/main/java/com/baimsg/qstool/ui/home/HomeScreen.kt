package com.baimsg.qstool.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.baimsg.qstool.AppViewModelStore
import com.baimsg.qstool.ui.main.MainActivityViewModel

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@Composable
internal fun HomeScreen(openLoginScreen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val message = remember {
            mutableStateOf("开启全屏")
        }
        val viewModel: MainActivityViewModel = AppViewModelStore.instance().viewModels()

        Button(onClick = {
            viewModel.isFullScreen = !viewModel.isFullScreen
            if (viewModel.isFullScreen) {
                message.value = "关闭全屏"
            } else {
                message.value = "开启全屏"
            }
        }) {
            Text(text = message.value)
        }
        Button(onClick = openLoginScreen) {
            Text(text = "去登录")
        }
    }
}