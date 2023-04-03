package com.baimsg.qstool.data.db.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.baimsg.qstool.data.models.entities.BaseEntity
import com.baimsg.qstool.data.models.entities.PaginatedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
abstract class BaseDao<E : BaseEntity> {

    /**
     * 插入一个实体 不需要重写
     * @param entity 实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: E): Long

    /**
     * 插入多个实体 不需要重写
     * @param entity 实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: E)

    /**
     * 插入实体集合 不需要重写
     * @param entities 实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: List<E>): List<Long>

    /**
     * 更新实体 不需要重写
     * @param entity 实体
     */
    @Update
    abstract suspend fun update(entity: E)

    /**
     * 执行事务
     * @param tx 事务函数
     */
    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    /**
     * 更新或插入实体  不需要重写
     * @param entity 实体
     */
    @Update
    suspend fun updateOrInsert(entity: E) {
        val entry = observeEntriesById(entity.getIdentifier()).firstOrNull()
        if (entry == null) {
            insert(entity)
        } else update(entity)
    }

    /**
     * 更新或者插入多个实体  不需要重写
     * @param entities 实体集合
     */
    @Transaction
    open suspend fun updateOrInsert(entities: List<E>) {
        entities.forEach {
            updateOrInsert(it)
        }
    }

    /**
     * 删除实体 不需要重写
     * @param entity 实体
     */
    @Delete
    abstract suspend fun delete(entity: E): Int

    /**
     * 根据id删除实体 需要子类实现
     * @param id id
     */
    abstract suspend fun deleteById(id: String)

    /**
     * 删除所有实体 需要子类实现
     */
    abstract suspend fun deleteAll()

    /**
     * 获取所有实体 需要子类实现
     * @return Flow数据
     */
    @Transaction
    abstract fun observeEntries(): Flow<List<E>>

    /**
     * 获取数据 需要子类实现
     * @return paging数据
     */
    @Transaction
    abstract fun pagingSourceEntries(): PagingSource<Int, E>

    /**
     * 根据条件获取数据 需要子类实现
     * @param count 总数
     * @param offset 偏移位置
     */
    @Transaction
    abstract fun observeEntries(count: Int, offset: Int): Flow<List<E>>

    /**
     * 根据id获取数据 需要子类实现
     * @param id id
     */
    @Transaction
    abstract fun observeEntriesById(id: String): Flow<E>

    /**
     * 根据id列表获取数据 需要子类实现
     * @param ids id列表
     */
    @Transaction
    abstract fun observeEntriesById(ids: List<String>): Flow<List<E>>

    /**
     * 根据id获取可空数据 需要子类实现
     * @param id id
     */
    abstract fun observeEntriesNullableById(id: String): Flow<E?>

    /**
     * 获取数据数量 需要子类实现
     */
    abstract suspend fun count(): Int

    /**
     * 获取数据数量 需要子类实现
     * @return flow
     */
    abstract fun observeCount(): Flow<Int>


}

/**
 * 统一参数数据卡dao
 */
abstract class EntityDao<Params : Any, E : BaseEntity> : BaseDao<E>() {

    /**
     * 根据参数获取数据
     * @param params 参数
     * @return flow
     */
    abstract fun observeEntries(params: Params): Flow<List<E>>

    /**
     * 根据参数获取数据
     * @param params 参数
     * @return PagingSource
     */
    abstract fun pagingSourceEntries(params: Params): PagingSource<Int, E>

    /**
     * 参数对应数据数量
     * @param params 参数
     */
    abstract suspend fun count(params: Params): Int

    /**
     * 根据参数删除对应数据
     * @param params
     */
    abstract suspend fun delete(params: Params): Int

    /**
     * 更新数据参数
     * @param params 删除参数
     * @param entity 插入实体
     */
    @Transaction
    open suspend fun update(params: Params, entity: E) {
        delete(params)
        insert(entity)
    }
}

/**
 * 分页数据库管理dao
 */
abstract class PaginatedEntryDao<Params : Any, E : PaginatedEntity> : EntityDao<Params, E>() {

    /**
     * 根据参数和页码获取数据
     * @param params 参数
     * @param page 页码
     * @return Flow
     */
    @Transaction
    abstract fun observeEntries(params: Params, page: Int): Flow<List<E>>

    /**
     * 根据参数和页码获取数据
     * @param params 参数
     * @param page 页码
     * @return PagingSource
     */
    @Transaction
    abstract fun pagingSourceEntries(params: Params, page: Int): PagingSource<Int, E>

    /**
     * 删除数据
     * @param params 参数
     * @param page 页码
     */
    @Transaction
    abstract suspend fun delete(params: Params, page: Int): Int

    /**
     * 更新数据
     */
    @Transaction
    open suspend fun update(id: String, entity: E) {
        deleteById(id)
        insert(entity)
    }

    /**
     * 更新数据
     */
    @Transaction
    open suspend fun update(params: Params, page: Int, entities: List<E>) {
        delete(params, page)
        insertAll(entities)
    }

    /**
     * Inserts given entities if it doesn't exist already in database.
     * This is little dirty because entity ids are not actually primary keys.
     */
    @Transaction
    open suspend fun insertMissing(entities: List<E>) {
        val existingIds =
            observeEntriesById(entities.map { it.getIdentifier() }).first()
                .map { it.getIdentifier() }
                .toSet()
        insertAll(entities.filterNot { entity -> existingIds.contains(entity.getIdentifier()) })
    }

}
