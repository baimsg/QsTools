package com.baimsg.qstool.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.baimsg.qstool.AppViewModelStore
import com.baimsg.qstool.ui.components.Item
import com.baimsg.qstool.ui.main.MainActivityViewModel
import com.baimsg.qstool.ui.modal.*

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun HomeScreen(openLoginScreen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val view = LocalView.current
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
        Button(onClick = {
            view.toast(
                text = "这是吐司", alignment = Alignment.TopCenter
            )
        }) {
            Text(text = "吐司")
        }
        Button(onClick = {
            view.stillDialog(
                systemCancellable = false, maskTouchBehavior = MaskTouchBehavior.penetrate
            ) { iModal ->
                DialogMsg(
                    modal = iModal, title = "标题", content = "这是内容", actions = listOf(
                        ModalAction("确定") {
                            it.dismiss()
                        },
                        ModalAction("确定") {
                            it.dismiss()
                        },
                        ModalAction("确定") {
                            it.dismiss()
                        },
                    )
                )
            }.show()
        }) {
            Text(text = "普通对话框")
        }
        Button(onClick = {
            view.dialog(maskTouchBehavior = MaskTouchBehavior.penetrate, verEdge = 80.dp) {
                Column() {
                    DialogList(
                        modal = it, maxHeight = 500.dp,
                    ) {
                        repeat(100) {
                            item {
                                Item(title = "这是选项$it") {
                                    view.stillToast("你点击了选择$it")
                                }
                            }
                        }
                    }
                    Box(Modifier.align(alignment = Alignment.End)) {

                        DialogAction(text = "知道了") {
                            it.dismiss()
                        }
                    }
                }

            }.show()
        }) {
            Text(text = "列表对话框")
        }
        Button(onClick = {
            view.dialog {
                DialogMarkList(
                    modal = it,
                    maxHeight = 500.dp,
                    list = (0..99).take(100).map { "这是数据$it" },
                    markIndex = 0
                ) { iModal, index ->
                    view.stillToast("你点击了选择$index")
                    iModal.dismiss()
                }
            }.show()
        }) {
            Text(text = "单选对话框")
        }
        Button(onClick = {
            view.dialog {
                Column {
                    DialogMultipleCheckList(
                        modal = it,
                        maxHeight = 500.dp,
                        list = (0..99).take(100).map { "这是数据$it" },
                        checked = setOf(1, 15)
                    ) { modal, index ->
                        view.stillToast("你点击了选择$index")

                    }
                    DialogActions(
                        modal = it, actions = listOf(
                            ModalAction("取消") {
                                it.dismiss()
                            },
                            ModalAction("确定") {
                                it.dismiss()
                            },
                        )
                    )
                }

            }.show()
        }) {
            Text(text = "多选对话框")
        }
        Button(onClick = {
            view.bottomSheet {
                BottomSheetList(modal = it) {
                    repeat(100) {
                        item {
                            Item(title = "这是数据$it") {
                                view.stillToast("你点击了选择$it")
                            }
                        }
                    }
                }
            }.show()
        }) {
            Text(text = "底部对话框")
        }

    }
}