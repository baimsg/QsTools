package com.baimsg.qstool.base.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.baimsg.qstool.base.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
@InstallIn(SingletonComponent::class)
@Module
object BaseModule {

    @Provides
    fun appContext(app: Application): Context = app.applicationContext

    @Provides
    fun appResources(app: Application): Resources = app.resources

    @Provides
    fun appAssetManager(app: Application): AssetManager = app.assets

    @ApplicationId
    @Provides
    fun provideApplicationId(application: Application): String = application.packageName

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheDir(
        @ApplicationContext context: Context
    ): File = context.cacheDir

    @Provides
    @Singleton
    @Named("files")
    fun provideFilesDir(
        @ApplicationContext context: Context
    ): File = context.filesDir

    @Singleton
    @Provides
    fun coroutineDispatchers() = CoroutineDispatchers(
        network = Dispatchers.IO,
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}