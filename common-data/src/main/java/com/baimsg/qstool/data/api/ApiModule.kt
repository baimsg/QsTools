package com.baimsg.qstool.data.api

import android.app.Application
import android.content.Context
import com.baimsg.qstool.base.android.BuildConfig
import com.baimsg.qstool.data.api.converter.ToStringConverterFactory
import com.baimsg.qstool.data.api.interceptor.CommonParamsInterceptor
import com.baimsg.qstool.data.api.interceptor.DynamicURLInterceptor
import com.baimsg.qstool.utils.DEFAULT_JSON_FORMAT
import com.baimsg.qstool.utils.extensions.MEDIA_TYPE_JSON
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    private fun getBaseBuilder(cache: Cache): OkHttpClient.Builder {
        return OkHttpClient.Builder().dns(DnsServer())
            .eventListenerFactory(HttpRequestEventListener.factory).sslSocketFactory(
                sslSocketFactory = SSLSocketClient.getSSLSocketFactory(),
                trustManager = SSLSocketClient.X509TrustManager
            ).hostnameVerifier { _, _ -> true }.cache(cache)
            .connectTimeout(NetConstant.API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetConstant.API_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NetConstant.API_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES)).dispatcher(Dispatcher().apply {
                // 允许在同一主机上增加并发图像获取的数量
                maxRequestsPerHost = 10
            }).retryOnConnectionFailure(true)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun createRetrofit(baseUrl: String, client: OkHttpClient, json: Json) =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ToStringConverterFactory())
            .addConverterFactory(json.asConverterFactory(MEDIA_TYPE_JSON)).client(client).build()

    @Provides
    @Singleton
    fun okHttpCache(app: Application): Cache =
        Cache(File(app.cacheDir, "api_cache"), (30 * 1024 * 1024).toLong())

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }


    @Provides
    @Singleton
    @Named("DynamicURLInterceptor")
    fun dynamicURLInterceptor(@ApplicationContext context: Context): Interceptor =
        DynamicURLInterceptor(context)

    @Provides
    @Singleton
    @Named("CommonParamsInterceptor")
    fun commonParamsInterceptor(@ApplicationContext context: Context): Interceptor =
        CommonParamsInterceptor(context)

    @Provides
    @Singleton
    fun okHttp(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("DynamicURLInterceptor") dynamicURLInterceptor: Interceptor,
        @Named("CommonParamsInterceptor") commonParamsInterceptor: Interceptor
    ): OkHttpClient = getBaseBuilder(cache).addInterceptor(dynamicURLInterceptor)
        .addInterceptor(commonParamsInterceptor).addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun jsonConfigured(): Json = DEFAULT_JSON_FORMAT

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    @Named("base")
    fun retrofitBase(client: OkHttpClient, json: Json): Retrofit =
        createRetrofit(NetConstant.BASE_URL, client, json)

    @Provides
    @Singleton
    fun providesAppEndpoints(@Named("base") retrofit: Retrofit): AppEndpoints =
        retrofit.create(AppEndpoints::class.java)
}