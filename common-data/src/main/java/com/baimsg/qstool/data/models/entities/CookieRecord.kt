package com.baimsg.qstool.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baimsg.qstool.data.models.Cookies

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@Entity(tableName = "cookie_record")
@kotlinx.serialization.Serializable
class CookieRecord(
    @PrimaryKey val uin: Long = 0,
    @ColumnInfo(name = "login_time") val loginTime: Long = 0,
    val cookies: Cookies? = null,
    override var params: String = "",
    override var page: Int = 0

) : PaginatedEntity {
    override fun getIdentifier(): String = "$uin"
}