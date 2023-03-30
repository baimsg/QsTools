package com.baimsg.qstool.base

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Create by Baimsg on 2023/3/30
 *
 **/
data class CoroutineDispatchers(
    val network: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher,
)