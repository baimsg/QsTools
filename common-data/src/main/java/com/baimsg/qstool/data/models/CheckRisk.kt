package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/4
 *
 **/
@kotlinx.serialization.Serializable
data class CheckRisk(
    val retcode: Int = 0,
    val retmsg: String = "",
    val safe: Boolean = false,
    val ticket: String = "",
) {

}