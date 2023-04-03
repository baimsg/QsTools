package com.baimsg.qstool.data.api.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
class CommonParamsInterceptor(context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}