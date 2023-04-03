package com.baimsg.qstool.data.db.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.baimsg.qstool.data.models.entities.CookieRecord
import kotlinx.coroutines.flow.Flow

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@Dao
abstract class CookieRecordDao : BaseDao<CookieRecord>() {

    @Transaction
    @Query("DELETE FROM cookie_record WHERE uin=:id")
    abstract override suspend fun deleteById(id: String)

    @Query("DELETE FROM cookie_record")
    abstract override suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM cookie_record")
    abstract override fun observeEntries(): Flow<List<CookieRecord>>

    @Transaction
    @Query("SELECT * FROM cookie_record")
    abstract override fun pagingSourceEntries(): PagingSource<Int, CookieRecord>

    @Transaction
    @Query("SELECT * FROM cookie_record LIMIT :count OFFSET :offset")
    abstract override fun observeEntries(count: Int, offset: Int): Flow<List<CookieRecord>>

    @Transaction
    @Query("SELECT * FROM cookie_record WHERE uin = :id")
    abstract override fun observeEntriesById(id: String): Flow<CookieRecord>

    @Transaction
    @Query("SELECT * FROM cookie_record WHERE uin IN (:ids)")
    abstract override fun observeEntriesById(ids: List<String>): Flow<List<CookieRecord>>

    @Transaction
    @Query("SELECT * FROM cookie_record WHERE uin = :id")
    abstract override fun observeEntriesNullableById(id: String): Flow<CookieRecord?>

    @Query("SELECT COUNT(*) FROM cookie_record")
    abstract override suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM cookie_record")
    abstract override fun observeCount(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM cookie_record")
    abstract suspend fun entries(): List<CookieRecord>

}