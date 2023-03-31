package com.baimsg.qstool.base.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri

object IntentUtils {

    /**
     * 浏览器打开
     * @param context
     * @param url
     */
    fun openUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(context, intent)
        } catch (_: ActivityNotFoundException) {
        }
    }

    /**
     * 打开文件
     * @param context
     * @param uri
     * @param mimeType 文件格式
     */
    fun openFile(context: Context, uri: Uri, mimeType: String) {
        try {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.setDataAndType(uri, mimeType)
            startActivity(context, intent)
        } catch (_: ActivityNotFoundException) {
        }
    }

    /**
     * 打开给定的意图作为。
     * 只是一个包装器，因此可以在打开新活动之前完成自定义内容。
     */
    fun startActivity(context: Context, intent: Intent, extras: Bundle? = null) {
        if (extras != null) context.startActivity(intent, extras)
        else context.startActivity(intent)
    }
}

fun Intent.clearTop() {
    flags =
        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
}
