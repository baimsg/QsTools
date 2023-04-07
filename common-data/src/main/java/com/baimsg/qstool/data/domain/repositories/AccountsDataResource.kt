package com.baimsg.qstool.data.domain.repositories

import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.data.AccountsConstant
import com.baimsg.qstool.data.api.AppEndpoints
import com.baimsg.qstool.data.models.*
import com.baimsg.qstool.utils.extensions.now
import com.baimsg.qstool.utils.extensions.resultApiCall
import com.baimsg.qstool.utils.extensions.toRequestBody
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import kotlin.random.Random

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
class AccountsDataResource @Inject constructor(
    private val appEndpoints: AppEndpoints,
    private val dispatchers: CoroutineDispatchers,
) {

    /**
     * 获取手机号状态
     * @param cookies 登录信息
     */
    suspend fun queryMbPhoneStat(cookies: Cookies): Result<MbPhoneState> =
        resultApiCall(dispatchers.network) {
            appEndpoints.queryMbPhoneStat(
                bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                    put("com", buildJsonObject {
                        put("src", 2)
                        put("scene", 251)
                        put("platform", 6)
                        put("version", "8938")
                    })
                }.toRequestBody(), headers = cookies.defaultHeaders
            )
        }

    /**
     * 检查账号是否安全
     * @param cookies 登录信息
     */
    suspend fun chkRisk(cookies: Cookies): Result<CheckRisk> = resultApiCall(dispatchers.network) {
        appEndpoints.chkRisk(
            bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                put("com", buildJsonObject {
                    put("src", 1)
                    put("scene", 704)
                    put("platform", 2)
                    put("version", "8938")
                })
                put("nonce", Random(999999).nextInt())
                put("faceAppid", AccountsConstant.FACE_APP_ID)
            }.toRequestBody(), headers = cookies.defaultHeaders
        )
    }

    /**
     * 获取验证方式
     */
    suspend fun queryVerifyMethod(cookies: Cookies): Result<VerifyMethod> =
        resultApiCall(dispatchers.network) {
            appEndpoints.queryVerifyMethod(
                bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                    put("com", buildJsonObject {
                        put("src", 1)
                        put("scene", 704)
                        put("platform", 2)
                        put("version", "8938")
                    })
                    put("nonce", Random(999999).nextInt())
                    put("faceAppid", AccountsConstant.FACE_APP_ID)
                }.toRequestBody(), headers = cookies.defaultHeaders
            )
        }

    /**
     * 查找绑定手机
     */
    suspend fun queryMbPhone(cookies: Cookies): Result<MbPhoneInfo> =
        resultApiCall(dispatchers.network) {
            appEndpoints.queryMbPhone(
                bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                    put("com", buildJsonObject {
                        put("src", 1)
                        put("scene", 501)
                        put("platform", 2)
                        put("version", "8938")
                    })
                    put("appid", AccountsConstant.APP_ID)
                    put("check", true)
                    put("guid", AccountsConstant.GUID)
                    put("loginTime", now() / 1000)
                }.toRequestBody(), headers = cookies.defaultHeaders
            )
        }


    /**
     * 获取验证码
     */
    suspend fun getSms(
        way: Int,
        areaCode: String,
        mobile: String,
        cookies: Cookies,
    ): Result<SmsInfo> = resultApiCall(dispatchers.network) {
        appEndpoints.getSms(
            bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                put("com", buildJsonObject {
                    put("src", 1)
                    put("scene", 301)
                    put("platform", 2)
                    put("version", "8938")
                })
                put("way", way)
                put("areaCode", areaCode)
                put("mobile", mobile)
            }.toRequestBody(), headers = cookies.defaultHeaders
        )
    }

    /**
     * 验证短信
     */
    suspend fun checkSms(
        phoneInfo: PhoneInfo,
        sms: String,
        cookies: Cookies,
    ): Result<CheckSms> = resultApiCall(dispatchers.network) {
        appEndpoints.chkSms(
            bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                put("com", buildJsonObject {
                    put("src", 1)
                    put("scene", 301)
                    put("platform", 2)
                    put("version", "8938")
                })
                put("way", phoneInfo.way)
                put("areaCode", phoneInfo.areaCode)
                put("mobile", phoneInfo.phoneNum)
                put("sms", sms)
            }.toRequestBody(), headers = cookies.defaultHeaders
        )
    }

    /**
     * 验证手机号
     */
    suspend fun verifyMbPhone(
        areaCode: String,
        mobile: String,
        cookies: Cookies,
    ): Result<VerifyMbPhone> = resultApiCall(dispatchers.network) {
        appEndpoints.verifyMbPhone(
            bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                put("com", buildJsonObject {
                    put("src", 1)
                    put("scene", 901)
                    put("platform", 2)
                    put("version", "8938")
                })
                put("areaCode", areaCode)
                put("mobile", mobile)
            }.toRequestBody(), headers = cookies.defaultHeaders
        )
    }

    /**
     * 换绑
     */
    suspend fun changeMbPhone(
        phoneInfo: PhoneInfo,
        type: Int = 0,
        checkSmsList: List<CheckSms>,
        cookies: Cookies,
    ): Result<ChangeMbPhone> = resultApiCall(dispatchers.network) {
        appEndpoints.changeMbPhone(
            bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                put("com", buildJsonObject {
                    put("src", 1)
                    put("scene", 202)
                    put("platform", 2)
                    put("version", "8938")
                    put("device", buildJsonObject {
                        put("guid", "3c037f3a986169fd86dfa8f4dcd257af")
                        put("qimei", "9d4ce4080fb4ea84750245bb100014016503")
                        put("qimei36", "9d4ce4080fb4ea84750245bb100014016503")
                        put("subappid", "537154738")
                        put("platform", "Android")
                        put("brand", "Xiaomi")
                        put("model", "Mi9 Pro 5G")
                        put("bssid", "")
                        put("devInfo", "Xiaomi Mi9 Pro 5G")
                    })
                })
                put("type", type)
                put("ticket", buildJsonObject {
                    put("ticket0", buildJsonObject {
                        val checkSms = checkSmsList.first { it.way == 3 }
                        put("way", checkSms.way)
                        put("keyType", checkSms.keyType)
                        put("key", checkSms.key)
                    })
                })
                put("areaCode", phoneInfo.areaCode)
                put("mobile", phoneInfo.phoneNum)
                checkSmsList.first { it.way == 2 }.let { checkSms ->
                    put("way", checkSms.way)
                    put("keyType", checkSms.keyType)
                    put("key", checkSms.key)
                }

            }.toRequestBody(), headers = cookies.defaultHeaders
        )
    }

    private fun getBkn(sKey: String): Int {
        var base = 5381
        for (element in sKey) {
            base += (base shl 5) + element.code
        }
        return base and 2147483647
    }

    private fun qqZone(sKey: String): Int {
        var hash = 5381
        sKey.forEachIndexed { _, c ->
            hash += (hash shl 5) + c.code
        }
        return hash
    }

}