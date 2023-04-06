package com.baimsg.qstool.data.domain.repositories

import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.data.api.AppEndpoints
import com.baimsg.qstool.data.models.*
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
                        put("version", "100")
                    })
                }.toRequestBody(), headers = cookies.defaultHeaders
            )
        }

    suspend fun queryMbPhone(cookies: Cookies): Result<MbPhoneInfo> =
        resultApiCall(dispatchers.network) {
            appEndpoints.queryMbPhone(
                bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                    put("com", buildJsonObject {
                        put("src", 2)
                        put("scene", 551)
                        put("platform", 6)
                        put("version", "100")
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
                    put("src", 2)
                    put("scene", 1104)
                    put("platform", 6)
                    put("version", "100")
                })
            }.toRequestBody(), headers = cookies.defaultHeaders
        )
    }

    /**
     * 获取验证方式
     */
    suspend fun queryVerifyMethod(riskTicket: String, cookies: Cookies): Result<VerifyMethod> =
        resultApiCall(dispatchers.network) {
            appEndpoints.queryVerifyMethod(
                bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                    put("com", buildJsonObject {
                        put("src", 2)
                        put("scene", 751)
                        put("platform", 6)
                        put("version", "100")
                    })
                    put("nonce", Random(9999999).nextInt())
                    put("faceAppid", 101966366)
                    put("riskTicket", riskTicket)
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
                    put("src", 2)
                    put("scene", 351)
                    put("platform", 6)
                    put("version", "100")
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
                    put("src", 2)
                    put("scene", 351)
                    put("platform", 6)
                    put("version", "100")
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
                    put("src", 2)
                    put("scene", 951)
                    put("platform", 6)
                    put("version", "100")
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
                    put("src", 2)
                    put("scene", 251)
                    put("platform", 6)
                    put("version", "100")
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