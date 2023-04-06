package com.baimsg.qstool.ui.home

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
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
import com.baimsg.qstool.ui.ex.drawBottomSeparator
import com.baimsg.qstool.ui.ex.drawTopSeparator
import com.baimsg.qstool.ui.modal.*
import com.baimsg.qstool.ui.resources.R
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
        TopBar(title = stringResource(R.string.app_name))
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
    val background = QstoolComposeThem.colors.bottomBar
    val textColor = QstoolComposeThem.colors.textPrimary
    val iconColor = QstoolComposeThem.colors.iconCurrent

    LazyColumn(
        Modifier
            .padding(top = 12.dp)
            .background(QstoolComposeThem.colors.bottomBar)
    ) {
        item {
            Item(title = "手机号码换绑", titleColor = QstoolComposeThem.colors.textPrimary, onClick = {
                showCookieRecord(
                    view = view,
                    background = background,
                    textColor = textColor,
                    iconColor = iconColor,
                    cookieRecords = cookieRecords,
                    openLoginScreen = openLoginScreen,
                    openChangeScreen = openChangeScreen
                )
            })
        }
        item {
            Item(title = "账号密码修改", drawBehind = {
                drawTopSeparator(insetStart = 12.dp, insetEnd = 12.dp)
            }, titleColor = QstoolComposeThem.colors.textPrimary, onClick = {
                view.context.toast("暂时未开发")
            })
        }
    }
}


fun showCookieRecord(
    view: View,
    background: Color,
    textColor: Color,
    iconColor: Color,
    cookieRecords: List<CookieRecord>,
    openLoginScreen: () -> Unit,
    openChangeScreen: (Long) -> Unit,
) {
    view.bottomSheet(background = background) {
        Column {
            DialogTitle(
                text = if (cookieRecords.isEmpty()) "没有登录账号" else "已登录账号",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = textColor
            )
            BottomSheetList(it, modifier = Modifier
                .heightIn(0.dp, 600.dp)
                .drawBehind {
                    drawTopSeparator()
                    drawBottomSeparator()
                }) {
                itemsIndexed(cookieRecords) { index, record ->
                    Item(title = "[${index + 1}] ${record.uin}",
                        paddingVer = 18.dp,
                        titleColor = textColor,
                        accessory = {
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
                modal = it, actions = listOf(ModalAction(text = "添加账号", color = iconColor) {
                    it.dismiss()
                    openLoginScreen.invoke()
                })
            )
        }

    }.show()
}