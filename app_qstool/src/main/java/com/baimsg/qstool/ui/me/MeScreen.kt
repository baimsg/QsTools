package com.baimsg.qstool.ui.me

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@Composable
internal fun MeScreen() {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .height(0.dp)
                .weight(15f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(100) {
                item {
                    Text(text = "数据数据数据数据数据数据数据数据数据$it")
                }
            }
        }
        Button(modifier = Modifier
            .height(0.dp)
            .weight(0.1f), onClick = { /*T\ODO*/ }) {
            Text(text = "取消")
        }
    }
}