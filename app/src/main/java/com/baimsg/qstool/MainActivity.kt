package com.baimsg.qstool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.baimsg.qstool.base.provider.QstoolWindowInsetsProvider
import com.baimsg.qstool.ui.theme.QstoolComposeThem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QstoolContent()
        }
    }
}

@Composable
fun QstoolContent() {
    QstoolWindowInsetsProvider {
        QstoolComposeThem {
            Column {
                Text(text = "android")
            }
        }
    }
}
