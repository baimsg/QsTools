package com.baimsg.qstool.utils.extensions

import java.util.*

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 * 集合工具类
 **/

/**
 * 符合value值的key集合
 * @param target value
 */
fun <K, V> Map<K, V>.findKeys(target: V) = filterValues { it == target }.keys

/**
 * 符合value值的第一个key值
 * @param target value
 */
fun <K, V> Map<K, V>.findFirstKey(target: V) = findKeys(target).first()

/**
 * 获取数据或空集合
 * @param key value
 */
fun <K, V> Map<K, Set<V>>.getOrEmpty(key: K): Set<V> = getOrElse(key) { emptySet() }

/**
 * 随机获取值组成string
 * @param length 长度
 * @param selector
 */
fun <K> List<K>.randomByString(
    length: Int = 6, selector: (K?) -> Any? = ::defaultSelector
): String = buildString {
    repeat(length) {
        val value = selector(this@randomByString.randomOrNull())
        if (value != null) {
            append(value)
        }
    }
}

private fun <T> defaultSelector(t: T) = t

/**
 * 计算总和
 * @param selector
 */
inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0.0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * 全部插入前面
 */
fun <T> List<T>.toStack(): Stack<T> = Stack<T>().also { stack ->
    forEach { stack.push(it) }
}

/**
 * 删除并返回列表的第一个元素
 */
fun <T> Stack<T>.popOrNull(): T? = when (isEmpty()) {
    true -> null
    else -> pop()
}

/**
 * 交换两个值的位置
 * @param fromIdx 开始位置
 * @param toIdx 结束位置
 */
fun <T> List<T>.swap(fromIdx: Int, toIdx: Int): List<T> {
    val copy = toMutableList()
    Collections.swap(copy, fromIdx, toIdx)
    return copy
}
