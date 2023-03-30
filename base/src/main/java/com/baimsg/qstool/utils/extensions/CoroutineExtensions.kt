@file:OptIn(ExperimentalCoroutinesApi::class)

package com.baimsg.qstool.utils.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 * 协程工具类
 **/
fun <T, R> Flow<T?>.flatMapLatestNullable(transform: suspend (value: T) -> Flow<R>): Flow<R?> {
    return flatMapLatest { if (it != null) transform(it) else flowOf(null) }
}

fun <T, R> Flow<T?>.mapNullable(transform: suspend (value: T) -> R): Flow<R?> {
    return map { if (it != null) transform(it) else null }
}

/***
 * 延时发送数据
 * @param timeout 延时时间
 * @param value 值
 */
fun <T> delayFlow(timeout: Long, value: T): Flow<T> = flow {
    delay(timeout)
    emit(value)
}

/**
 * 保持间隔产生数据
 * @param interval 间隔值
 * @param timeUnit 间隔单位
 */
fun flowInterval(interval: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Flow<Int> {
    val delayMillis = timeUnit.toMillis(interval)
    return channelFlow {
        var tick = 0
        send(tick)
        while (true) {
            delay(delayMillis)
            send(++tick)
        }
    }
}

/**
 * 延迟异步执行
 */
fun <T> CoroutineScope.lazyAsync(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> = lazy {
    async(start = CoroutineStart.LAZY) {
        block.invoke(this)
    }
}

/**
 * 具有默认值的 stateIn 的别名
 */
fun <T> Flow<T>.stateInDefault(
    scope: CoroutineScope,
    initialValue: T,
    started: SharingStarted = SharingStarted.WhileSubscribed(5000),
) = stateIn(scope, started, initialValue)

/**
 * 给定 [target] 的 [timeMillis] 发射延迟
 * 即如果在 [timeMillis] 之前发出了其他内容，则跳过 [target] 的发射
 */
fun <T> Flow<T>.delayItem(timeMillis: Long, target: T) = mapLatest {
    if (it == target) {
        delay(timeMillis)
        it
    } else it
}
