package com.baimsg.qstool.data.db.daos

import com.baimsg.qstool.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    @Provides
    fun cookieRecord(appDatabase: AppDatabase): CookieRecordDao =
        appDatabase.cookieRecordDao()
}