package com.baimsg.qstool.utils.extensions

import android.os.Parcel
import android.os.Parcelable

/**
 * Create by baimsg on 2022/3/17.
 * Email 1469010683@qq.com
 * 读取list数据
 **/
inline fun <reified T> Parcel.readMutableList(): MutableList<T> {
    @Suppress("UNCHECKED_CAST")
    return readArrayList(T::class.java.classLoader) as MutableList<T>
}

inline fun <reified T : Parcelable> parcelableCreatorOf(): Parcelable.Creator<T> =
    object : Parcelable.Creator<T> {
        override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)

        override fun createFromParcel(source: Parcel?): T =
            T::class.java.getDeclaredConstructor(Parcel::class.java).newInstance(source)
    }