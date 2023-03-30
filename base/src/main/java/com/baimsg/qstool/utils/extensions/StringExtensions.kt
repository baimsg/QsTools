package com.baimsg.qstool.utils.extensions

import android.util.Base64
import java.nio.charset.Charset

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 *
 **/

/**
 * 是否以开头或者结尾
 */
fun String.edgeWith(edge: String, ignoreCase: Boolean = false): Boolean =
    startsWith(edge, ignoreCase) || endsWith(edge, ignoreCase)

/**
 * 过滤null
 */
fun String?.orBlank(): String = when (this == null) {
    false -> this
    else -> ""
}

/**
 * 正则匹配开头或结尾
 * @param charSequence 匹配内容
 */
fun Regex.edgeMatches(charSequence: CharSequence): Boolean =
    Regex("$pattern[\\s\\S]*").matches(charSequence) || Regex("[\\s\\S]*$pattern").matches(
        charSequence
    )

/**
 * 去除html格式
 */
fun String.removeHighlight() = replace(Regex("<[\\s\\S]+?>"), "")

/**
 * 不为null且不为空数据
 */
fun String?.isNotNullAndNotBlank() = this != null && this.isNotBlank()

/**
 * 显示指定长度内容
 * @param limit 指定长度
 * @param ellipsize 结尾符号
 */
fun CharSequence.truncate(limit: Int, ellipsize: String = "..."): CharSequence {
    if (length > limit) {
        return substring(0, limit) + ellipsize
    }
    return this
}

/**
 * 是否是http开头
 */
fun String?.isUrl(): Boolean =
    this?.run { startsWith("http://") || startsWith("https://") } ?: false

/**
 * 是否是中文
 */
fun Char.isChinese(): Boolean = code in 0x4E00..0x29FA5

/**
 * 字符是否是换行符
 */
fun Char.isLineFeed(): Boolean = this == '\n'

/**
 * 字符是否是英文
 */
fun Char.isEnglish(): Boolean = this in 'a'..'z' || this in 'A'..'Z'

/**
 * 转为英文
 */
fun String.toPureEnglish(): String {
    return Regex("[^A-Za-z0-9]").replace(this, "")
}

/**
 * 字符串是英语
 */
fun String.isEnglish(): Boolean {
    val re = Regex("[^A-Za-z0-9]")
    val filter = re.replace(this, "") // works

    return if (filter.isNotEmpty()) {
        filter.matches(Regex("^[a-zA-Z0-9]*"))
    } else false
}

/**
 * 转换为导航路由格式
 * 过滤route里面的 ‘/’
 */
fun String.toRoute(): String {
    return if (contains("/")) {
        replace("/", "[斜杠处理]")
    } else this
}

/**
 * 恢复导航原本路由格式
 */
fun String.recoverRoute(): String {
    return if (contains("[斜杠处理]")) {
        replace("[斜杠处理]", "/")
    } else this
}

/**
 * 转换成base64字符串
 * @param flags base64编码
 * @param charset 字符编码
 */
fun Any.toBase64String(flags: Int = Base64.DEFAULT, charset: Charset = Charsets.UTF_8): String =
    String(toBase64Bytes(flags), charset = charset)

/**
 * 转换成base64字节数组
 * @param flags base64编码
 */
fun Any.toBase64Bytes(flags: Int = Base64.DEFAULT): ByteArray = Base64.encode(
    when (this) {
        is ByteArray -> this
        is String -> toByteArray()
        else -> toString().toByteArray()
    }, flags
)

/**
 * 解码base64数据
 * @param flags base64编码
 * @param charset 字符编码
 * @return 解码后的 String
 */
fun Any.decodeBase64AsString(
    flags: Int = Base64.DEFAULT,
    charset: Charset = Charsets.UTF_8,
): String = String(decodeBase64AsBytes(flags), charset = charset)

/**
 * 解码base64数据
 * @param flags base64编码
 * @return 解码后的 byteArray
 */
fun Any.decodeBase64AsBytes(flags: Int = Base64.DEFAULT): ByteArray = Base64.decode(
    when (this) {
        is ByteArray -> this
        is String -> toByteArray()
        else -> toString().toByteArray()
    }, flags
)

/**
 * 16进制转字符串
 */
fun Any.decodeHexAsString(charset: Charset = Charsets.UTF_8): String =
    String(decodeHexAsBytes(), charset = charset)

/**
 * 16进制转字节数组
 */
fun Any.decodeHexAsBytes(): ByteArray = when (this) {
    is String -> this
    is ByteArray -> String(this)
    else -> this.toString()
}.run {
    require(length % 2 == 0) { "Unexpected hex string: $this" }
    val result = ByteArray(length / 2)
    for (i in result.indices) {
        val d1 = decodeHexDigit(this[i * 2]) shl 4
        val d2 = decodeHexDigit(this[i * 2 + 1])
        result[i] = (d1 + d2).toByte()
    }
    result
}

private fun decodeHexDigit(c: Char): Int {
    return when (c) {
        in '0'..'9' -> c - '0'
        in 'a'..'f' -> c - 'a' + 10
        in 'A'..'F' -> c - 'A' + 10
        else -> throw IllegalArgumentException("Unexpected hex digit: $c")
    }
}

/**
 * 格式化歌词
 */
fun String.formatLrc() = buildString {
    append(this@formatLrc.replace(Regex("\\[.+][\r\n]"), "").trim { it <= ' ' || it == '\uFEFF' })
}

/**
 * 仅显示歌词内容
 */
fun String.onlyLrc() = buildString {
    append(this@onlyLrc.replace(Regex("\\[\\d+:\\d+.+?]"), ""))
}
