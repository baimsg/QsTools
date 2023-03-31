package com.baimsg.qstool.base.utils.extensions

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 *
 **/
fun <T> SavedStateHandle.getStateFlow(
    key: String,
    scope: CoroutineScope,
    initialValue: T = get(key) ?: error("No initial value for key $key")
): MutableStateFlow<T> = this.let { handle ->
    val liveData = handle.getLiveData(key, initialValue).also { liveData ->
        if (liveData.value === initialValue) {
            liveData.value = initialValue
        }
    }
    val mutableStateFlow = MutableStateFlow(liveData.value ?: initialValue)

    val observer: Observer<T> = Observer { value ->
        if (value != mutableStateFlow.value) {
            mutableStateFlow.value = value
        }
    }
    liveData.observeForever(observer)

    scope.launch {
        mutableStateFlow.also { flow ->
            flow.onCompletion {
                withContext(Dispatchers.Main.immediate) {
                    liveData.removeObserver(observer)
                }
            }.collectLatest { value ->
                withContext(Dispatchers.Main.immediate) {
                    if (liveData.value != value) {
                        liveData.value = value
                    }
                }
            }
        }
    }
    mutableStateFlow
}
