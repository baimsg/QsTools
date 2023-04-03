package com.baimsg.qstool.data.api

import com.baimsg.qstool.utils.extensions.logD
import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
class HttpRequestEventListener : EventListener() {
    companion object {
        val factory = Factory {
            HttpRequestEventListener()
        }
    }

    private var callStartTime = 0L
    override fun callStart(call: Call) {
        logD("HttpRequestCallBack callStart")
        callStartTime = System.currentTimeMillis()
        super.callStart(call)
    }

    override fun callEnd(call: Call) {
        logD("HttpRequestCallBack callEnd")
        logD("HttpRequestCallBack callTime ${System.currentTimeMillis() - callStartTime}")
        super.callEnd(call)
    }

    override fun callFailed(call: Call, ioe: IOException) {
        logD("HttpRequestCallBack callFailed")
        logD("HttpRequestCallBack callTime ${System.currentTimeMillis() - callStartTime}")
        super.callFailed(call, ioe)
    }

    private var connectStartTime = 0L
    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        connectStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack connectStart")
        super.connectStart(call, inetSocketAddress, proxy)
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        logD("HttpRequestCallBack connectionReleased")
        logD("HttpRequestCallBack connectTime ${System.currentTimeMillis() - connectStartTime}")
        super.connectionReleased(call, connection)
    }

    override fun connectFailed(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?,
        ioe: IOException
    ) {
        logD("HttpRequestCallBack connectFailed")
        logD("HttpRequestCallBack connectTime ${System.currentTimeMillis() - connectStartTime}")
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
    }

    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        logD("HttpRequestCallBack connectEnd")
        logD("HttpRequestCallBack connectTime ${System.currentTimeMillis() - connectStartTime}")
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        logD("HttpRequestCallBack connectionAcquired")
        super.connectionAcquired(call, connection)
    }

    private var requestBodyStartTime = 0L
    override fun requestBodyStart(call: Call) {
        requestBodyStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack requestBodyStart")
        super.requestBodyStart(call)
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        logD("HttpRequestCallBack requestBodyEnd")
        logD("HttpRequestCallBack requestBodyTime ${System.currentTimeMillis() - requestBodyStartTime}")
        super.requestBodyEnd(call, byteCount)
    }

    private var responseBodyStartTime = 0L
    override fun responseBodyStart(call: Call) {
        responseBodyStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack responseBodyStart")
        super.responseBodyStart(call)
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        logD("HttpRequestCallBack responseBodyEnd")
        logD("HttpRequestCallBack responseBodyTime ${System.currentTimeMillis() - responseBodyStartTime}")
        super.responseBodyEnd(call, byteCount)
    }

    private var secureConnectStartTime = 0L
    override fun secureConnectStart(call: Call) {
        secureConnectStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack secureConnectStart")
        super.secureConnectStart(call)
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        logD("HttpRequestCallBack secureConnectEnd")
        logD("HttpRequestCallBack secureConnectTime ${System.currentTimeMillis() - secureConnectStartTime}")
        super.secureConnectEnd(call, handshake)
    }

    private var requestHeadersStartTime = 0L
    override fun requestHeadersStart(call: Call) {
        requestHeadersStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack requestHeadersStart")
        super.requestHeadersStart(call)
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        logD("HttpRequestCallBack requestHeadersEnd")
        logD("HttpRequestCallBack requestHeadersTime ${System.currentTimeMillis() - requestHeadersStartTime}")
        for (head in request.headers.names()) {
            logD("HttpHeaders name: $head value ${request.headers[head]}")
        }
        super.requestHeadersEnd(call, request)
    }

    private var responseHeadersStartTime = 0L
    override fun responseHeadersStart(call: Call) {
        responseHeadersStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack responseHeadersStart")
        super.responseHeadersStart(call)
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        logD("HttpRequestCallBack responseHeadersEnd")
        logD("HttpRequestCallBack responseHeadersTime ${System.currentTimeMillis() - responseHeadersStartTime}")
        super.responseHeadersEnd(call, response)
    }

    private var dnsStartTime = 0L
    override fun dnsStart(call: Call, domainName: String) {
        dnsStartTime = System.currentTimeMillis()
        logD("HttpRequestCallBack dnsStart")
        super.dnsStart(call, domainName)
    }

    override fun dnsEnd(
        call: Call,
        domainName: String,
        inetAddressList: List<@JvmSuppressWildcards InetAddress>
    ) {
        logD("HttpRequestCallBack dnsEnd")
        logD("HttpRequestCallBack dnsTime ${System.currentTimeMillis() - dnsStartTime}")
        super.dnsEnd(call, domainName, inetAddressList)
    }
}