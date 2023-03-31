package com.baimsg.qstool

import android.content.Context
import androidx.multidex.MultiDex
import com.baimsg.qstool.base.BaseApp
import com.baimsg.qstool.base.inititializer.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
@HiltAndroidApp
class QstoolApp : BaseApp() {

    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}