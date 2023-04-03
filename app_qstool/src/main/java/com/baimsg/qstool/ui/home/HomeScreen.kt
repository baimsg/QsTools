package com.baimsg.qstool.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.baimsg.qstool.ui.components.TopBar
import com.baimsg.qstool.ui.layout.PressWithAlphaBox
import com.baimsg.qstool.ui.modal.*
import com.baimsg.qstool.ui.theme.QstoolComposeThem

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun HomeScreen(openLoginScreen: () -> Unit) {
    val view = LocalView.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QstoolComposeThem.colors.background),
    ) {
        TopBar(title = "首页")
        HomeContent()
    }
}

@Composable
fun HomeContent() {
    Column {
        PressWithAlphaBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(QstoolComposeThem.colors.bottomBar)
                .padding(vertical = 8.dp)
        ) {
            Text(text = "手机号码换绑", color = QstoolComposeThem.colors.textPrimary)
        }
        PressWithAlphaBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(QstoolComposeThem.colors.bottomBar)
                .padding(vertical = 8.dp)
        ) {
            Text(text = "账号密码修改", color = QstoolComposeThem.colors.textPrimary)
        }

    }

}