package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/4
 *
 **/
@kotlinx.serialization.Serializable
data class VerifyMethod(
    val retcode: Int = 0,
    val retmsg: String = "",
    val phoneVerify: Boolean = false,
    val faceVerify: Boolean = false,
    val bigdataKey: String = "",
    val tmpKey: String = "",
    val appeal: Int = 0,
    val guaranteeVerify: Boolean = false,
    val openID: String = "",
    val encContext: String = "",
    val getSupportUrlRsp: String = "",
)