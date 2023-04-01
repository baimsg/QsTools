package com.baimsg.qstool

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore

/**
 * Create by Baimsg on 2023/4/1
 * 这个类用来缓存 app 的 viewModel
 **/
class AppViewModelStore private constructor() {
    companion object {
        private var instance: AppViewModelStore? = null

        val store: ViewModelStore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ViewModelStore() }

        const val DEFAULT_KEY = "app_viewModel_default_key"

        @Synchronized
        fun instance(): AppViewModelStore {
            return instance ?: AppViewModelStore().also { instance = it }
        }
    }

    /**
     * 通过这个方法获取 viewModel 会自动缓存viewModel
     */
    @Composable
    inline fun <reified VM : ViewModel> viewModels(): VM {
        val key = "$DEFAULT_KEY${VM::class.java.canonicalName}"
        return store[key] as? VM ?: hiltViewModel<VM>().also { store.put(key, it) }
    }

}