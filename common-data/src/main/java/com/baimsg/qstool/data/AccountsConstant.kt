package com.baimsg.qstool.data

/**
 * Create by Baimsg on 2023/4/4
 *
 **/
object AccountsConstant {

    const val APP_ID = 101867630
    const val FACE_APP_ID = 101867630
    const val GUID = "3c037f3a986169fd86dfa8f4dcd257af"
    const val STYLE = "9"
    const val DOMAIN_ID = "761"

    const val ACCOUNTS_HOME_URL = "https://accounts.qq.com/#/"
    const val SAFE_URL = "https://accounts.qq.com/safe/securityphone?from=setting"

    const val COOKIE_PATH = "https://ptlogin2.accounts.qq.com"

    @JvmOverloads
    fun createLoginUrl(
        style: String = STYLE,
        appId: Int = APP_ID,
        s_url: String = SAFE_URL,
        domainId: String = DOMAIN_ID,
    ) = buildString {
        append("https://ui.ptlogin2.qq.com/cgi-bin/login?")
        append("style=$style&appid=$appId&s_url=$s_url&daid=$domainId$&from=setting&pt_no_onekey=1")
    }
}