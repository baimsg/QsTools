@file:Suppress("NOTHING_TO_INLINE")

package com.baimsg.qstool.utils.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Create by baimsg on 2022/3/5.
 * Email 1469010683@qq.com
 * retrofit工具类
 *
 **/

/**
 * 获取body或异常
 */
inline fun <T> Response<T>.bodyOrThrow(): T =
    if (isSuccessful) body()!! else throw HttpException(this)

/**
 * 重试请求
 * @param defaultDelay 默认延时时间
 * @param maxAttempts 最大重试次数
 * @param shouldRetry 是否重试
 * @param block 请求代码体
 */
suspend fun <T> withRetry(
    defaultDelay: Long = 100,
    maxAttempts: Int = 3,
    shouldRetry: (Throwable) -> Boolean = ::defaultShouldRetry,
    block: suspend () -> T
): T {
    repeat(maxAttempts) { attempt ->
        val response = runCatching { block() }
        when {
            response.isSuccess -> return response.getOrThrow()
            response.isFailure -> {
                val exception = response.exceptionOrNull()!!
                // 响应失败，让我们看看我们是否应该重试
                if (attempt == maxAttempts - 1 || !shouldRetry(exception)) {
                    throw exception
                }
                var nextDelay = attempt * attempt * defaultDelay
                if (exception is HttpException) {
                    // 如果有 HttpException，检查是否有 Retry-After
                    // header 决定延迟多长时间
                    exception.retryAfter?.let {
                        nextDelay = it.coerceAtLeast(defaultDelay)
                    }
                }
                delay(nextDelay)
            }
        }
    }
    // 我们不应该在这里打
    throw IllegalStateException("Unknown exception from executeWithRetry")
}

/**
 * 获取响应头里面的重试时间
 */
private val HttpException.retryAfter: Long?
    get() {
        val retryAfterHeader = response()?.headers()?.get("Retry-After")
        if (retryAfterHeader != null && retryAfterHeader.isNotEmpty()) {
            //得到一个 Retry-After 值，尝试将其解析为 long
            try {
                return retryAfterHeader.toLong() + 10
            } catch (nfe: NumberFormatException) {
                // 可能不会发生，忽略该值并使用上面生成的默认值
            }
        }
        return null
    }

/**
 * 默认重试策略
 * @param throwable
 */
private fun defaultShouldRetry(throwable: Throwable) = when (throwable) {
    is HttpException -> throwable.code() == 429
    is IOException -> true
    else -> false
}

/**
 * 是否是从网络获取的最新内容
 */
private val Response<*>.isFromNetwork: Boolean
    get() = raw().cacheResponse == null

/**
 * 在指定线程请求数据（带重试机制）
 * @param dispatcher 工作线程
 * @param apiCall 请求代码逻辑
 */
suspend fun <T> resultApiCall(
    dispatcher: CoroutineDispatcher, apiCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.success(withRetry {
                apiCall()
            })
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}