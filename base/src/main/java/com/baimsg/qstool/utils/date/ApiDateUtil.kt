package com.baimsg.qstool.utils.date

import com.baimsg.qstool.utils.extensions.logE
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * 中国时区
 */
val API_ZONE_ID: ZoneId = ZoneId.of("Asia/Shanghai")

/**
 * 格式 -> 时:分
 *
 */
val HOUR_MINUTES_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

/**
 * 当前时间
 */
fun apiNow(): ZonedDateTime = ZonedDateTime.now(API_ZONE_ID)

/**
 * 将其他其他时区时间专换上海时区
 * @param fallback 回调时间
 */
fun String?.apiDate(fallback: ZonedDateTime = ZonedDateTime.now()): ZonedDateTime {
    return try {
        OffsetDateTime.parse(this).atZoneSameInstant(API_ZONE_ID)
    } catch (e: Exception) {
        this?.logE(e)
        return fallback
    }
}

/**
 *
 */
fun String?.toFormattedDateFromApi(dateFormat: DateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE): String =
    dateFormat.format(apiDate())

fun String?.toTimeFromApi(): String = HOUR_MINUTES_FORMAT.format(apiDate())

/**
 * 当前时间
 */
fun serverTime(): ZonedDateTime = ZonedDateTime.now(API_ZONE_ID)

fun formattedDateFromNow(dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")): String =
    dateFormat.format(apiNow())




