package com.baimsg.qstool.data.models

import com.baimsg.qstool.utils.JSON
import kotlinx.serialization.SerialName
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import java.io.Serializable

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@kotlinx.serialization.Serializable
data class Cookies(
    val pgv_pvi: String = "",
    val pgv_si: String = "",
    val RK: String = "",
    val uin: String = "",
    val p_uin: String = "",
    @SerialName("skey") val sKey: String = "",
    @SerialName("p_skey") val pSKey: String = "",
    val ptcz: String = "",
    val pt4_token: String = "",
) : Serializable {

    val qq: Long get() = Regex("\\d+").find(uin)?.value?.toLong() ?: 0


    val cookies: Map<String, String>
        get() = JSON.decodeFromString(
            MapSerializer(String.serializer(), String.serializer()), JSON.encodeToString(this)
        )

    val cookie: String
        get() = buildString {
            cookies.forEach { (key, value) ->
                if (isNotEmpty()) append("; ")
                append("$key=$value")
            }
            append("; domainid=761")
        }

    val defaultHeaders: Map<String, String>
        get() = mapOf(
            "qname-service" to "1935233:65536",
            "qname-space" to "Production",
            "origin" to "https://accounts.qq.com",
            "referer" to "https://accounts.qq.com/phone/verify",
            "Cookie" to cookie
        )
}