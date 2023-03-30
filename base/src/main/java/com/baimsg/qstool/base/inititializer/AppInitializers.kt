package com.baimsg.qstool.base.inititializer

import android.app.Application

/**
 * Create by Baimsg on 2023/3/30
 * 初始化数据
 **/
class AppInitializers(private vararg val initializers: AppInitializer) : AppInitializer {
    override fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}

interface AppInitializer {
    fun init(application: Application)
}