package com.baimsg.qstool.ui.me

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
internal fun MeScreen() {
    val scaffoldState = rememberScaffoldState()

    var showSnack by remember {
        mutableStateOf(false)
    }

    val currentShow by rememberUpdatedState(newValue = showSnack)

    LaunchedEffect(key1 = currentShow) {
        scaffoldState.snackbarHostState.showSnackbar(
            message = "我是一个Snack", actionLabel = "哦"
        )
    }



    Column(Modifier.fillMaxSize()) {
        Scaffold(scaffoldState = scaffoldState) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { showSnack = !showSnack }) {
                    Text("LaunchedEffect")
                }
            }
        }
    }
}