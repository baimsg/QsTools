package com.baimsg.qstool.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun appDatabase(context: Context): AppDatabase {
        val builder = Room.databaseBuilder(context, AppDatabase::class.java, "qstool_data")
            .addMigrations(*DatabaseMigrations.APP_MIGRATIONS)
            .fallbackToDestructiveMigrationOnDowngrade()
        return builder.build()
    }

}