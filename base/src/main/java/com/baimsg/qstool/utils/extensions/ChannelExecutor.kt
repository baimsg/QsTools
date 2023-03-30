package com.baimsg.qstool.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Create by Baimsg on 2022/9/26
 * Channel 实现线程池效果
 **/
fun Channel<Any>.runBlock(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.Unconfined).launch {
        send(0)
        CoroutineScope(Dispatchers.IO).launch {
            block()
            receive()
        }
    }
}


class ChannelExecutor(capacity: Int) : Executor {
    private val channel = Channel<Any>(capacity)

    override fun execute(command: Runnable) {
        channel.runBlock {
            command.run()
        }
    }
}


class ChannelExecutorService(capacity: Int) : AbstractExecutorService() {
    private val channel = Channel<Any>(capacity)

    override fun execute(command: Runnable) {
        channel.runBlock {
            command.run()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun isEmpty(): Boolean {
        return channel.isEmpty || channel.isClosedForReceive
    }

    override fun shutdown() {
        channel.close()
    }

    override fun shutdownNow(): MutableList<Runnable> {
        shutdown()
        return mutableListOf()
    }

    @ExperimentalCoroutinesApi
    override fun isShutdown(): Boolean {
        return channel.isClosedForSend
    }

    @ExperimentalCoroutinesApi
    override fun isTerminated(): Boolean {
        return channel.isClosedForReceive
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
        var millis = unit.toMillis(timeout)
        while (!isTerminated && millis > 0) {
            try {
                Thread.sleep(200L)
                millis -= 200L
            } catch (ignore: Exception) {
            }
        }
        return isTerminated
    }
}