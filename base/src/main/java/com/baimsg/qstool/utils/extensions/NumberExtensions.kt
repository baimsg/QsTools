package com.baimsg.qstool.utils.extensions

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by Baimsg on 2022/9/5
 *
 **/

/**
 * 转换(分:秒)格式
 * @param isSecond 是否是秒为单位
 */
fun Long.translateTimeToMin(isSecond: Boolean = true): String {
    val time = if (isSecond) this else this / 1000
    val hh = time / 3600
    val mm = time % 3600 / 60
    val ss = time % 3600 % 60
    val sb = StringBuilder()
    val s = "%02d"
    if (hh != 0L) sb.append("$s:".format(hh))
    if (mm != 0L) sb.append("$s:".format(mm))
    sb.append(s.format(ss))
    return sb.toString()
}

/**
 * 转换天为单位
 * @param isSecond 是否是秒为单位
 */
fun Long.translateTime(isSecond: Boolean = false): String {
    val time = if (isSecond) this / 60 else this
    val day = time / (60 * 24)
    val hour = time / (60) % 24
    val minute = time % 60
    val sb = StringBuilder()
    if (day != 0L) sb.append("${day}天")
    if (hour != 0L) sb.append("${hour}小时")
    if (minute != 0L) sb.append("${minute}分钟")
    return sb.toString()
}

/**
 * 格式化时间
 * @param pattern 格式
 * @param isMillisecond 是毫秒单位
 */
fun Long.formatData(
    pattern: String = "yyyy年MM月dd日 HH:mm:ss",
    isMillisecond: Boolean = false,
): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(if (isMillisecond) this else this * 1000)


/**
 * 转换文件大小/bit
 */
fun Long.toFileSize(): String {
    val bytes = StringBuffer()
    val format = DecimalFormat("###.0")
    when {
        this <= 0 -> bytes.append("0B")
        this >= 1024 * 1024 * 1024 -> bytes.append(
            format.format(this / (1024.0 * 1024.0 * 1024.0))
        ).append("GB")
        this >= 1024 * 1024 -> bytes.append(
            format.format(this / (1024.0 * 1024.0))
        ).append("MB")
        this >= 1024 -> bytes.append(format.format(this / 1024.0)).append("KB")
        else -> bytes.append(this).append("B")
    }
    return bytes.toString()
}

/**
 * 计算分钟
 * @param unit 原单位
 */
fun Long.toMinuteByLong(unit: String? = null): Long = when (unit) {
    "天" -> this * 24 * 60
    "小时" -> this * 60
    else -> this
}

