package com.baimsg.qstool.data.domain.repositories

import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.data.db.daos.CookieRecordDao
import com.baimsg.qstool.data.models.entities.CookieRecord
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
class CookieDataResource @Inject constructor(
    private val cookieRecordDao: CookieRecordDao, private val dispatchers: CoroutineDispatchers,
) {
    suspend fun hasUin(uin: Long): Boolean = withContext(dispatchers.io) {
        cookieRecordDao.observeEntriesById("$uin").firstOrNull() != null
    }

    suspend fun cookies(): List<CookieRecord> = withContext(dispatchers.io) {
        cookieRecordDao.entries()
    }
}
