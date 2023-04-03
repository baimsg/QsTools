package com.baimsg.qstool.data.api.interceptor

import android.content.Context
import com.baimsg.qstool.data.api.NetConstant
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
class DynamicURLInterceptor(context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder = request.newBuilder()
        val newUrl = request.url.newBuilder()
        newUrl.preventURLEncode(request.url.query)
        val routerHosts = request.headers(NetConstant.DYNAMIC_KEY)
        //获得新的 URL
        when (val url = routerHosts.firstOrNull()?.toHttpUrlOrNull()) {
            null -> builder.url(newUrl.build())
            else -> {
                //如果有这个header，先将配置的header删除
                builder.removeHeader(NetConstant.DYNAMIC_KEY)
                builder.url(newUrl.spliceNewUrl(url).build())
            }
        }
        return chain.proceed(builder.build())
    }

    /**
     * 拼接新host地址
     * @param newUrl 新的url
     */
    private fun HttpUrl.Builder.spliceNewUrl(newUrl: HttpUrl) = run {
        scheme(newUrl.scheme).host(newUrl.host).port(newUrl.port)
    }

    /**
     * 防止酷我音乐参数q url编码
     * @param srcQuery 没有编码的Query
     */
    private fun HttpUrl.Builder.preventURLEncode(srcQuery: String?) = apply {
        srcQuery ?: return@apply
        if (srcQuery.matches(Regex("f=[\\s\\S]+q=[\\s\\S]+"))) {
            encodedQuery(srcQuery)
            query(srcQuery)
        }
    }
}