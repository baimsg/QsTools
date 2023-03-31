package com.baimsg.qstool.base.utils.extensions

/**
 * Create by Baimsg on 2022/3/16
 *
 **/
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry

@Composable
fun getNavArgument(key: String): Any? {
    val owner = LocalViewModelStoreOwner.current
    return if (owner is NavBackStackEntry) owner.arguments?.get(key)
    else null
}
