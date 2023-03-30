package com.baimsg.qstool.utils.extensions

import android.util.Log

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 *
 **/
enum class LogLevel {
    DEBUG, INFO, ERR;
}

@JvmField
var msDebugAble = true

fun Any.logD(vararg param: Any) {
    logWithLevel(this, LogLevel.DEBUG, param.asList())
}

fun Any.logI(vararg param: Any) {
    logWithLevel(this, LogLevel.INFO, param.asList())
}

fun Any.logE(vararg param: Any) {
    logWithLevel(this, LogLevel.ERR, param.asList())
}

private fun logWithLevel(obj: Any, level: LogLevel, param: List<Any?>) {
    if (msDebugAble || level == LogLevel.ERR) {
        buildString {
            param.forEach {
                append(it.toString() + " | ")
            }
        }.apply {
            when (level) {
                LogLevel.DEBUG -> {
                    Log.d(obj.name, this)
                }
                LogLevel.INFO -> {
                    Log.i(obj.name, this)
                }
                LogLevel.ERR -> {
                    Log.e(obj.name, this)
                }
            }
        }
    }
}