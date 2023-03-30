package com.baimsg.qstool.utils.extensions

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import android.os.Bundle
import kotlinx.serialization.json.JsonElement
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import java.util.zip.DeflaterOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.Inflater
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 *
 **/
typealias Callback = () -> Unit

typealias Toggle = (Boolean) -> Unit

inline val <T : Any> T.simpleName: String get() = this.javaClass.kotlin.simpleName ?: "Unknown"
inline val <T : Any> T.name: String get() = this.javaClass.name

/**
 * 当前时间戳
 */
fun now() = System.currentTimeMillis()

/**
 * 当前时间戳
 */
fun nowNano() = System.nanoTime()

/**
 * 转字符串
 */
fun Array<out Any>.asString(): String {
    return joinToString { it.toString() }
}


val pass: Unit = Unit

fun randomUUID(): String = UUID.randomUUID().toString()

/**
 * zip加密数据
 */
fun ByteArray.zip(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream(size)
    val deflaterOutputStream = DeflaterOutputStream(byteArrayOutputStream)
    try {
        deflaterOutputStream.write(this, 0, size)
        deflaterOutputStream.finish()
        deflaterOutputStream.flush()
        val bytes: ByteArray = byteArrayOutputStream.toByteArray()
        deflaterOutputStream.close()
        byteArrayOutputStream.close()
        return bytes
    } catch (_: Exception) {
    } finally {
        deflaterOutputStream.close()
        byteArrayOutputStream.close()
    }
    return byteArrayOf()
}

/**
 * zip解压数据
 */
fun ByteArray?.unZip(): ByteArray {
    this ?: return byteArrayOf()
    var stream: ByteArrayOutputStream? = null
    var decPresser: Inflater? = null
    try {
        stream = ByteArrayOutputStream(size)
        decPresser = Inflater()
        decPresser.setInput(this)
        val bytes = ByteArray(1024)
        while (!decPresser.finished()) {
            stream.write(bytes, 0, decPresser.inflate(bytes))
        }
        val data = stream.toByteArray()
        stream.close()
        decPresser.end()
        return data
    } catch (_: Throwable) {
    } finally {
        stream?.close()
        decPresser?.end()
    }
    return byteArrayOf()
}

/**
 * Gzip解压数据
 */
fun ByteArray?.unGzip(): ByteArray {
    if (this == null || isEmpty()) {
        return byteArrayOf()
    }
    val out = ByteArrayOutputStream()
    val inputStream = ByteArrayInputStream(this)
    try {
        val unGzip = GZIPInputStream(inputStream)
        val buffer = ByteArray(1024)
        var n: Int
        while (unGzip.read(buffer).also { n = it } >= 0) {
            out.write(buffer, 0, n)
        }
    } catch (_: IOException) {
    }
    return out.toByteArray()
}

/**
 * 常见数据类型
 */
@JvmField
val GBK: Charset = Charset.forName("GB18030")

val MEDIA_TYPE_JSON: MediaType = "application/json".toMediaType()
val MEDIA_TYPE_OCTET_STREAM: MediaType = "application/octet-stream".toMediaType()
val MEDIA_TYPE_FORM_ENCODE: MediaType = "application/x-www-form-urlencoded".toMediaType()

/**
 * JSON转请求参数
 * @param mediaType 参数类型
 */
fun JsonElement.toRequestBody(mediaType: MediaType = MEDIA_TYPE_JSON) =
    toString().toRequestBody(contentType = mediaType)

/**
 * 挂起的apply函数
 */
@OptIn(ExperimentalContracts::class)
suspend fun <T> T.applyAsync(block: suspend T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}

/**
 * 仅当 [api] 大于等于设备的 SDK 版本时才运行 [block].
 */
fun whenApiLevel(api: Int, block: () -> Unit) {
    if (api >= SDK_INT) {
        block()
    }
}

fun Boolean.toFloat(): Float = if (this) 1f else 0f

fun isOreo(): Boolean = SDK_INT >= VERSION_CODES.O

operator fun Bundle?.plus(other: Bundle?) = apply { (this ?: Bundle()).putAll(other ?: Bundle()) }

fun Bundle.readable() = buildList {
    keySet().forEach {
        add("key=$it, value=${get(it)}")
    }
}.joinToString()
