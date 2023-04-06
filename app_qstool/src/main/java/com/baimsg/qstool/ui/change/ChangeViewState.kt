package com.baimsg.qstool.ui.change

import com.baimsg.qstool.data.InvokeStatus
import com.baimsg.qstool.data.Uninitialized
import com.baimsg.qstool.data.models.CheckRisk
import com.baimsg.qstool.data.models.Cookies
import com.baimsg.qstool.data.models.PhoneInfo

/**
 * Create by Baimsg on 2023/4/3
 * @param uin 账号
 * @param cookie cookie数据
 * @param checkRisk 账号安全
 * @param verifyCode 验证码
 * @param invokeStatus 加载信息
 **/
internal data class ChangeViewState(
    val uin: Long = 0,
    val cookie: Cookies? = null,
    val checkRisk: CheckRisk = CheckRisk(),
    val phoneInfo: PhoneInfo = PhoneInfo(),
    val timeOut: Int = 0,
    val verifyCode: String = "",
    val invokeStatus: InvokeStatus = Uninitialized,
) {
    companion object {
        val Empty = ChangeViewState()
    }
}
