package com.baimsg.qstool.ui.change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baimsg.qstool.base.utils.rememberFlowWithLifecycle
import com.baimsg.qstool.ui.components.CoverImage
import com.baimsg.qstool.ui.components.TopBar
import com.baimsg.qstool.ui.components.TopBarBackIconItem
import com.baimsg.qstool.ui.modal.DialogAction
import com.baimsg.qstool.ui.theme.QstoolComposeThem

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@Composable
fun ChangeScreen(onBack: () -> Unit) {
    ChangeScreen(onBack = onBack, viewModel = hiltViewModel())
}

@Composable
fun ChangeScreen(onBack: () -> Unit, viewModel: ChangeViewModel) {
    ChangeScreen(onBack = onBack, viewModel = viewModel) { action ->
        viewModel.submitAction(action)
    }
}

@Composable
fun ChangeScreen(onBack: () -> Unit, viewModel: ChangeViewModel, executor: (ChangeAction) -> Unit) {
    val state by rememberFlowWithLifecycle(viewModel.viewState)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(QstoolComposeThem.colors.background)
    ) {
        TopBar(title = "手机换绑", leftItems = listOf(TopBarBackIconItem(onClick = onBack)))
        Row(
            Modifier
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .fillMaxWidth()
                .background(QstoolComposeThem.colors.bottomBar)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            CoverImage(
                data = "https://q.qlogo.cn/g?b=qq&nk=${state.uin}&s=100",
                size = 48.dp,
                shape = RoundedCornerShape(24.dp)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${state.uin}",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                color = QstoolComposeThem.colors.textPrimary
            )
        }

        Row(
            Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .background(QstoolComposeThem.colors.bottomBar)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                modifier = Modifier
                    .width(0.dp)
                    .weight(3f),
                onValueChange = {},
                maxLines = 1,
                singleLine = true
            )
            DialogAction(text = "获取验证码") {

            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.test,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = QstoolComposeThem.colors.textPrimary
        )
    }
}