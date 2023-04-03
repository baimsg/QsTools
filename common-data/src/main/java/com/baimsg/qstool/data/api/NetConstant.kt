package com.baimsg.qstool.data.api

import java.time.Duration

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
object NetConstant {
    /**
     * 路由到指定域名
     */
    const val DYNAMIC_KEY = "router_host"

    val API_TIMEOUT = Duration.ofSeconds(40).toMillis()

    /**
     * jsdelivr
     */
    const val BASE_URL = "https://cdn.jsdelivr.net/"

    /**
     * 有道云笔记
     */
    const val YOUDAO_NOTE_URL = "https://note.youdao.com/"

}