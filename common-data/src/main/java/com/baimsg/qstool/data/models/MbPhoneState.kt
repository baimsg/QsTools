package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/5
 *
 **/
@kotlinx.serialization.Serializable
data class MbPhoneState(
    val retcode: Int = 0,
    val retmsg: String = "",
    val jump: Int = 1,
    val review: Boolean = false,
    val hasSet: Boolean = true,
) {

}