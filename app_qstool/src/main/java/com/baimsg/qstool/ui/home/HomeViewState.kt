package com.baimsg.qstool.ui.home

import com.baimsg.qstool.data.models.entities.CookieRecord

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
data class HomeViewState(
    val showCookieRecord: Boolean = false,
    val cookieRecords: List<CookieRecord> = emptyList(),
) {
    companion object {
        val EMPTY: HomeViewState = HomeViewState()
    }
}