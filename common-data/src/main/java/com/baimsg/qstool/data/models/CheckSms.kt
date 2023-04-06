package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/6
 *
 **/
@kotlinx.serialization.Serializable
data class CheckSms(
    val retcode: Int = 0,
    val retmsg: String = "",
    val way: Int = 0,
    val keyType: Int = 0,
    val key: String = "",
) {}