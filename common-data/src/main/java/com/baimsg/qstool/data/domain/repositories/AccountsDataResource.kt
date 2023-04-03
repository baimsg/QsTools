package com.baimsg.qstool.data.domain.repositories

import com.baimsg.qstool.base.CoroutineDispatchers
import com.baimsg.qstool.data.api.AppEndpoints
import com.baimsg.qstool.data.models.Cookies
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
    suspend fun chkRisk(cookies: Cookies) = resultApiCall(dispatchers.network) {
        appEndpoints.chkRisk(
            bkn = getBkn(cookies.pSKey), requestBody = buildJsonObject {
                put("com", buildJsonObject {
                    put("src", 2)
                    put("scene", 1104)
                    put("platform", 6)
                    put("version", "100")
                })
            }.toRequestBody(), headers = mapOf(
                "qname-service" to "1935233:65536",
                "qname-space" to "Production",
                "origin" to "https://accounts.qq.com",
                "referer" to "https://accounts.qq.com/phone/verify",
                "Cookie" to cookies.cookie
            )
        )
    }

    suspend fun queryVerifyMethod(riskTicket: String, cookies: Cookies): Result<String> =
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
                }.toRequestBody(), headers = mapOf(
                    "qname-service" to "1935233:65536",
                    "qname-space" to "Production",
                    "origin" to "https://accounts.qq.com",
                    "referer" to "https://accounts.qq.com/phone/verify",
                    "Cookie" to cookies.cookie
                )
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