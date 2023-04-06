package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/6
 *
 **/
@kotlinx.serialization.Serializable
class PhoneInfo(
    val way: Int = 0,
    val areaCode: String = "",
    val phoneNum: String = "",
) {
}