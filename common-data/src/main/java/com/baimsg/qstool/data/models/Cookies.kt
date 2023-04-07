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
            "user-agent" to "Mozilla/5.0 (Linux; Android 11; Mi9 Pro 5G Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/98.0.4758.102 MQQBrowser/6.2 TBS/046403 Mobile Safari/537.36 V1_AND_SQ_8.9.38_3856_YYB_D QQ/8.9.38.10545 NetType/4G WebP/0.3.0 AppId/537154738 Pixel/1080 StatusBarHeight/76 SimpleUISwitch/0 QQTheme/1000 StudyMode/0 CurrentMode/0 CurrentFontScale/1.0 GlobalDensityScale/0.9818182 AllowLandscape/false InMagicWin/0",
            "qname-service" to "1935233:65536",
            "qname-space" to "Production",
            "sec-fetch-site" to "same-origin",
            "sec-fetch-mode" to "cors",
            "sec-fetch-dest" to "empty",
            "origin" to "https://accounts.qq.com",
            "referer" to "https://accounts.qq.com/safe/securityphone?from=setting",
            "Cookie" to cookie
        )
}