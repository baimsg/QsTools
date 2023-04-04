package com.baimsg.qstool.ui.home

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baimsg.qstool.base.utils.rememberFlowWithLifecycle
import com.baimsg.qstool.base.utils.toast
import com.baimsg.qstool.data.models.entities.CookieRecord
import com.baimsg.qstool.ui.components.CoverImage
import com.baimsg.qstool.ui.components.Item
import com.baimsg.qstool.ui.components.TopBar
import com.baimsg.qstool.ui.layout.PressWithAlphaBox
import com.baimsg.qstool.ui.modal.*
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.baimsg.qstool.ui.theme.Shapes

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@Composable
internal fun HomeScreen(openLoginScreen: () -> Unit, openChangeScreen: (Long) -> Unit) {
    HomeScreen(
        openLoginScreen = openLoginScreen,
        openChangeScreen = openChangeScreen,
        viewModel = hiltViewModel()
    )
}

@Composable
internal fun HomeScreen(
    openLoginScreen: () -> Unit,
    openChangeScreen: (Long) -> Unit,
    viewModel: HomeViewModel,
) {
    HomeScreen(
        openLoginScreen = openLoginScreen,
        openChangeScreen = openChangeScreen,
        viewModel = viewModel
    ) { action ->
        viewModel.submitAction(action)
    }

}

@Composable
internal fun HomeScreen(
    openLoginScreen: () -> Unit,
    openChangeScreen: (Long) -> Unit,
    viewModel: HomeViewModel,
    executor: (HomeAction) -> Unit,
) {
    val view = LocalView.current
    val viewState by rememberFlowWithLifecycle(viewModel.viewState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QstoolComposeThem.colors.background),
    ) {
        TopBar(title = "首页")
        HomeContent(view, viewState.cookieRecords, openLoginScreen, openChangeScreen)
    }
}

@Composable
internal fun HomeContent(
    view: View,
    cookieRecords: List<CookieRecord>,
    openLoginScreen: () -> Unit,
    openChangeScreen: (Long) -> Unit,
) {
    Column {
        PressWithAlphaBox(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(QstoolComposeThem.colors.bottomBar)
            .padding(vertical = 12.dp), onClick = {
            showCookieRecord(view, cookieRecords, openLoginScreen, openChangeScreen)
        }) {
            Text(text = "手机号码换绑", color = QstoolComposeThem.colors.textPrimary)
        }
        PressWithAlphaBox(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(QstoolComposeThem.colors.bottomBar)
            .padding(vertical = 12.dp), onClick = {
            view.context.toast("暂时未开发")
        }) {
            Text(text = "账号密码修改", color = QstoolComposeThem.colors.textPrimary)
        }

    }

}

fun showCookieRecord(
    view: View,
    cookieRecords: List<CookieRecord>,
    openLoginScreen: () -> Unit,
    openChangeScreen: (Long) -> Unit,
) {
    view.bottomSheet {
        Column {
            DialogTitle(
                text = if (cookieRecords.isEmpty()) "暂时没有登录账号" else "当前已登陆账号",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = QstoolComposeThem.colors.iconCurrent
            )
            BottomSheetList(
                it, modifier = Modifier.heightIn(0.dp, 600.dp)
            ) {
                itemsIndexed(cookieRecords) { index, record ->
                    Item(title = "[${index + 1}] ${record.uin}", paddingVer = 18.dp, accessory = {
                        CoverImage(
                            data = "https://q.qlogo.cn/g?b=qq&nk=${record.uin}&s=100",
                            size = 28.dp,
                            shape = Shapes.search
                        )
                    }) {
                        it.dismiss()
                        openChangeScreen(record.uin)
                    }
                }
            }
            DialogActions(
                modal = it, actions = listOf(ModalAction("添加账号") {
                    it.dismiss()
                    openLoginScreen.invoke()
                })
            )
        }

    }.show()
}