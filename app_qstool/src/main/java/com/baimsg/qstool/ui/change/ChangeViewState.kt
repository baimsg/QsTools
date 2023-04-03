package com.baimsg.qstool.ui.change

import com.baimsg.qstool.data.models.CheckRisk
import com.baimsg.qstool.data.models.entities.CookieRecord

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
data class ChangeViewState(
    val uin: Long = 0,
    val cookieRecord: CookieRecord? = null,
    val checkRisk: CheckRisk = CheckRisk(),
    val test: String = "",
) {
    companion object {
        val Empty = ChangeViewState()
    }
}