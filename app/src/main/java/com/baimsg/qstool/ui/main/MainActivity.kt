package com.baimsg.qstool.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.baimsg.qstool.base.provider.QstoolWindowInsetsProvider
import com.baimsg.qstool.base.utils.cancelFullScreen
import com.baimsg.qstool.base.utils.setFullScreen
import com.baimsg.qstool.base.utils.translucent
import com.baimsg.qstool.ui.theme.QstoolComposeThem

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 将屏幕扩展到状态栏
        translucent()
        setContent {
            QstoolContent(viewModel = hiltViewModel())
        }
    }

    @Composable
    fun QstoolContent(viewModel: MainActivityViewModel) {
        QstoolWindowInsetsProvider {
            QstoolComposeThem {
                if (viewModel.isFullScreen) {
                    setFullScreen()
                } else {
                    //取消全屏
                    cancelFullScreen()
                }
                MainScreen(viewModel)
            }
        }
    }
}


