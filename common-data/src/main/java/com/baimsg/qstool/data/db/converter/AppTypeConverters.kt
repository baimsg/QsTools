package com.baimsg.qstool.data.db.converter

import androidx.room.TypeConverter
import com.baimsg.qstool.data.models.Cookies
import com.baimsg.qstool.utils.JSON

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
object AppTypeConverters {
    @TypeConverter
    fun cookiesToString(value: Cookies): String = JSON.encodeToString(Cookies.serializer(), value)

    @TypeConverter
    fun stringToCookies(value: String): Cookies = JSON.decodeFromString(Cookies.serializer(), value)
}
