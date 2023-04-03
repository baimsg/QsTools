package com.baimsg.qstool.data.models

import kotlinx.serialization.SerialName
import java.io.Serializable

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@kotlinx.serialization.Serializable
data class Cookies(
    @SerialName("pgv_pvi") val pgvPvi: String = "",
    @SerialName("pgvSi") val pgv_si: String = "",
    val RK: String = "",
    val uin: String = "",
    @SerialName("pUin") val p_uin: String = "",
    @SerialName("skey") val sKey: String = "",
    @SerialName("p_skey") val pSKey: String = "",
    @SerialName("ptcz") val ptcZ: String = "",
    @SerialName("pt4_token") val pt4Token: String = "",
) : Serializable