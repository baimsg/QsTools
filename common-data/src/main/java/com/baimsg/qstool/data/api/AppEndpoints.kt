package com.baimsg.qstool.data.api

import com.baimsg.qstool.data.models.AppConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
interface AppEndpoints {
    /**
     * 解析github文件
     * @param user 用户名
     * @param repo 仓库名@版本
     * @param file 文件路径
     */
    @GET("gh/{user}/{repo}/{file}")
    suspend fun loadGithub(
        @Path("user") user: String = "baimsg",
        @Path("repo") repo: String = "nut-repository@latest",
        @Path("file") file: String = "config/app-info.json",
    ): AppConfig

    /**
     * 有道云笔记获取配置信息
     * @param fileId 文件id
     * @param shareKey 分享密钥
     * @param method 请求方法
     * @param read 是否可读性
     * @param urlName 请求地址
     */
    @GET("yws/api/personal/file/{fd}")
    suspend fun loadConfig(
        @Path("fd") fileId: String = "WEBbb3926b80614cd9cebc9feae2d979152",
        @Query("shareKey") shareKey: String = "738d158be7474282a334eb63ffd67a8c",
        @Query("method") method: String = "download",
        @Query("read") read: Boolean = true,
        @Header(NetConstant.DYNAMIC_KEY) urlName: String = NetConstant.YOUDAO_NOTE_URL,
    ): AppConfig


    /**
     * 获取有道云笔记文件id
     * @param shareKey 分享密钥
     * @param sev 默认即可
     * @param editorType 默认即可
     * @param editorVersion 默认即可
     * @param urlName 请求地址
     */
    @GET("yws/api/note/{shareKey}")
    suspend fun getNoteFileId(
        @Path("shareKey") shareKey: String = "738d158be7474282a334eb63ffd67a8c",
        @Query("sev") sev: String = "j1",
        @Query("editorType") editorType: Int = 1,
        @Query("editorVersion") editorVersion: String = "new-json-editor",
        @Header(NetConstant.DYNAMIC_KEY) urlName: String = NetConstant.YOUDAO_NOTE_URL,
    ): String

}