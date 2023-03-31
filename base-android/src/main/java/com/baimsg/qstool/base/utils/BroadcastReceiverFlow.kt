package com.baimsg.qstool.base.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Create by baimsg on 2022/4/5.
 * Email 1469010683@qq.com
 *
 **/

/**
 * flow广播
 */
fun Context.flowBroadcasts(intentFilter: IntentFilter): Flow<Intent> {
    val resultChannel = MutableStateFlow(Intent())

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            resultChannel.value = intent
        }
    }

    return resultChannel.onStart { registerReceiver(receiver, intentFilter) }
        .onCompletion { unregisterReceiver(receiver) }
}
