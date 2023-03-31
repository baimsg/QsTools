package com.baimsg.qstool.base.utils.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.os.Environment
import java.io.BufferedReader
import java.io.FileReader

/**
 * Create by Baimsg on 2022/12/29
 *
 **/
// 检查包含外部存储的卷是否可用
// 用于读写。
fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

// 检查包含外部存储的卷是否可用于至少读取.
fun isExternalStorageReadable(): Boolean {
    return Environment.getExternalStorageState() in setOf(
        Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY
    )
}

fun procVersion() = runCatching {
    val reader = FileReader("/proc/version")
    BufferedReader(reader).readLine().also {
        reader.close()
    }
}.getOrNull() ?: ""

fun bootId() = runCatching {
    BufferedReader(FileReader("/proc/sys/kernel/random/boot_id")).readLine()
}.getOrNull() ?: ""

@SuppressLint("PrivateApi")
fun getBaseBand(): String? {
    return try {
        val cls = Class.forName("android.os.SystemProperties")
        cls.getMethod("get", String::class.java, String::class.java)
            .invoke(cls.newInstance(), "gsm.version.baseband", "no message") as String
    } catch (e: Exception) {
        ""
    }
}

fun getInnerVersion(): String {
    return if (Build.DISPLAY.contains(Build.VERSION.INCREMENTAL)) {
        Build.DISPLAY
    } else Build.VERSION.INCREMENTAL
}