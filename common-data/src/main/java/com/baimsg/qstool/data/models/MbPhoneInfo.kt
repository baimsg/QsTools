package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/5
 *
 **/
@kotlinx.serialization.Serializable
data class MbPhoneInfo(
    val retcode: Int = 0,
    val retmsg: String = "",
    val needCheck: Boolean = false,
    val areaCode: String = "",
    val phoneNum: String = "",
)

fun MbPhoneInfo.toPhoneInfo() = PhoneInfo(way = 3, areaCode = areaCode, phoneNum = phoneNum)