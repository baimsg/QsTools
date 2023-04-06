package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/6
 *
 **/
@kotlinx.serialization.Serializable
data class SmsInfo(
    val retcode: Int = 0,
    val retmsg: String = "",
    val way: Int = 0,
    val resendInterval: Int = 0,
    val sms: String = "",
    val sendTo: String = "",
    val faceUrl: String = "",
) {}