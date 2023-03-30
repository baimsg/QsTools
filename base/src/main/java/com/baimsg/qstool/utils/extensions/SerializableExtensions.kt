package com.baimsg.qstool.utils.extensions

import android.util.Base64
import java.io.*

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 * 序列化和反序列化工具类
 **/

/**
 * base64转对象
 */
inline fun <reified T> String.decodeAsBase64Object(): T? {
    val data = Base64.decode(toByteArray(), Base64.DEFAULT)
    val objectInputStream = ObjectInputStream(
        ByteArrayInputStream(data)
    )
    val parsedObject = objectInputStream.readObject()
    objectInputStream.close()
    return parsedObject as? T
}

/**
 * 对象转base64
 */
fun Serializable.encodeAsBase64String(): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(this)
    objectOutputStream.flush()
    objectOutputStream.close()
    return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
}
