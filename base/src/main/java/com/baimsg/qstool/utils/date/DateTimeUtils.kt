package com.baimsg.qstool.utils.date

import java.time.*
import java.util.*

fun localDate(year: Int, month: Month, dayOfMonth: Int): LocalDate =
    LocalDate.of(year, month, dayOfMonth)

/**
 * @param month 1-12
 */
fun localDate(year: Int, month: Int, dayOfMonth: Int) = localDate(year, Month.of(month), dayOfMonth)

operator fun LocalDate.component1() = year
operator fun LocalDate.component2() = monthValue
operator fun LocalDate.component3() = dayOfMonth

operator fun LocalDateTime.component1() = year
operator fun LocalDateTime.component2() = monthValue
operator fun LocalDateTime.component3() = dayOfMonth
operator fun LocalDateTime.component4() = minute
operator fun LocalDateTime.component5() = second
operator fun LocalDateTime.component6() = nano

fun LocalDate.dayPadded() = "%01d".format(dayOfMonth)
fun LocalDateTime.dayPadded() = "%01d".format(dayOfMonth)

infix fun LocalDate.sameYear(other: LocalDate) = year == other.year
infix fun LocalDate.sameMonth(other: LocalDate) = month == other.month
infix fun LocalDate.sameDate(other: LocalDate) =
    sameYear(other) && sameMonth(other) && (dayOfYear == other.dayOfYear)

fun LocalDate.toMillis(zoneId: ZoneId = API_ZONE_ID) =
    atStartOfDay(zoneId).toInstant().toEpochMilli()

fun LocalDateTime.toMillis(zoneOffset: ZoneOffset = ZoneOffset.of("+8")) =
    toInstant(zoneOffset).toEpochMilli()

fun Date.toZonedDateTime(): ZonedDateTime =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())

fun Date.toLocalDate(): LocalDate = toZonedDateTime().toLocalDate()
fun Date.toLocalDateTime(): LocalDateTime = toZonedDateTime().toLocalDateTime()

fun Iterable<Date>.toLocalDateTimes() = map { it.toLocalDateTime() }
fun Iterable<Date>.toLocalDates() = map { it.toLocalDate() }

fun List<LocalDate>.limit(min: LocalDate? = null, max: LocalDate? = null) = map {
    when {
        min != null && it < min -> min
        max != null && it > max -> max
        else -> it
    }
}

fun List<LocalDate>.isValid(min: LocalDate? = null, max: LocalDate? = null) = all {
    when {
        min != null && it < min -> false
        max != null && it > max -> false
        else -> true
    }
}

/**
 * 这个日期的一年有多少天
 */
val LocalDate.daysInAYear
    get() = when (isLeapYear) {
        true -> 366
        else -> 365
    }

/**
 * 与给定日期的不同年份，具有日期精度.
 */
infix fun LocalDate.yearsDiff(end: LocalDate): Float {
    val years = (year - end.year).toFloat()

    val dayOfYearDiff = (dayOfYear - end.dayOfYear)
    return years - (dayOfYearDiff.toFloat() / daysInAYear.toFloat())
}

/**
 * 计算从现在到 [this] 的年数.
 *
 * @param zoneId 使用什么区域 id 来获取当前时间.
 */
fun LocalDate.yearsSince(zoneId: ZoneId = API_ZONE_ID): Float = LocalDate.now(zoneId) yearsDiff this
