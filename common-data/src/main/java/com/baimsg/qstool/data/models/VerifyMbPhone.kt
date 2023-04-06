package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/6
 *
 **/
@kotlinx.serialization.Serializable
data class VerifyMbPhone(
    val retcode: Int = 0,
    val retmsg: String = "",
    val needChange: Boolean = false,
) {}