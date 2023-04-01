package com.baimsg.qstool.ui.coil

import android.app.Application
import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.base.inititializer.AppInitializer
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
class CoilAppInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: CoroutineDispatchers
) : AppInitializer {

    override fun init(application: Application) {
        Coil.setImageLoader(
            imageLoader = ImageLoader.Builder(context).dispatcher(dispatchers.io)
                .fetcherDispatcher(dispatchers.network).apply {
                    memoryCache {
                        MemoryCache.Builder(context).maxSizePercent(0.35).build()
                    }
                    diskCache {
                        DiskCache.Builder().directory(context.cacheDir.resolve("image_cache"))
                            .maxSizePercent(0.35).build()
                    }
                    components {

                    }
                }.build()
        )
    }
}