package com.baimsg.ui.coil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import com.baimsg.qstool.utils.extensions.logE

/**
 * Create by Baimsg on 2023/3/31
 *
 **/
suspend fun Context.getBitmap(
    data: Any?,
    size: Int = Int.MAX_VALUE,
    allowHardware: Boolean = true
): Bitmap? {
    val request = ImageRequest.Builder(this)
        .data(data)
        .size(size)
        .precision(Precision.INEXACT)
        .allowHardware(allowHardware)
        .build()

    return when (val result = imageLoader.execute(request)) {
        is SuccessResult -> (result.drawable as BitmapDrawable).bitmap
        is ErrorResult -> {
            logE(result.throwable)
            null
        }
    }
}
