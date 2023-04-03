package com.baimsg.qstool.data.models

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@kotlinx.serialization.Serializable
data class AppConfig(
    val sign: SignData? = null,
) {

}

@kotlinx.serialization.Serializable
data class SignData(
    val sign: String = ""
)

