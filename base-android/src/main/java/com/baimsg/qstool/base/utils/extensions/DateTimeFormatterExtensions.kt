package com.baimsg.qstool.base.utils.extensions

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.time.format.DateTimeFormatter

/**
 * Create by baimsg on 2022/4/5.
 * Email 1469010683@qq.com
 *
 **/
fun DateTimeFormatter.withLocale(context: Context): DateTimeFormatter {
    val locales = ConfigurationCompat.getLocales(context.resources.configuration)
    return when {
        locales.isEmpty -> this
        else -> withLocale(locales[0])
    }
}
