package com.baimsg.qstool.di

import com.baimsg.qstool.base.inititializer.AppInitializers
import com.baimsg.qstool.ui.coil.CoilAppInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun appInitializers(
        coilAppInitializer: CoilAppInitializer
    ): AppInitializers {
        return AppInitializers(coilAppInitializer)
    }

}