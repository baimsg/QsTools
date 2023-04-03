package com.baimsg.qstool.data.api

import com.baimsg.qstool.utils.extensions.logD
import okhttp3.Dns
import java.net.InetAddress

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
class DnsServer : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        logD("DnsServer Start hostName: $hostname")
        val inetAddress = Dns.SYSTEM.lookup(hostname)
        logD("DnsServer getIps $inetAddress")
        return inetAddress
    }
}