package com.baimsg.qstool.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baimsg.qstool.data.db.converter.AppTypeConverters
import com.baimsg.qstool.data.db.converter.BaseTypeConverters
import com.baimsg.qstool.data.models.entities.CookieRecord

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@Database(
    version = DatabaseMigrations.APP_DB_VERSION,
    entities = [CookieRecord::class],
    exportSchema = true
)
@TypeConverters(
    BaseTypeConverters::class, AppTypeConverters::class
)
abstract class AppDatabase : RoomDatabase() {

}