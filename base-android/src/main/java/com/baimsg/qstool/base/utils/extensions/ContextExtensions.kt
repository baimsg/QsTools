package com.baimsg.qstool.base.utils.extensions

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.baimsg.qstool.utils.KvUtils
import java.io.InputStreamReader
import java.util.*

/**
 * Create by baimsg on 2022/3/5.
 * Email 1469010683@qq.com
 *
 * 查找baseContext
 *
 **/
inline fun <reified T : Context> Context.findBaseContext(): T? {
    var ctx: Context? = this
    do {
        if (ctx is T) {
            return ctx
        }
        if (ctx is ContextWrapper) {
            ctx = ctx.baseContext
        }
    } while (ctx != null)
    // 如果我们到达这里，在我们的 Context 层次结构中没有 T 类型的 Context
    return null
}

/**
 * 隐藏键盘
 */
fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

/**
 * 隐藏键盘
 */
fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

fun Context.copy(message: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("simple text", message)
    clipboard.setPrimaryClip(clip)
}

/**
 * 判断权限
 * @param permissionName 权限名称
 */
fun Context.checkPermission(permissionName: String): Boolean =
    checkCallingOrSelfPermission(permissionName) == PackageManager.PERMISSION_GRANTED

/**
 * 判断权限
 * @param permissionNames 权限名称集合
 */
fun Context.checkPermission(permissionNames: List<String>): Boolean = run {
    var hasPermission = true
    permissionNames.forEach {
        hasPermission = hasPermission and checkPermission(it)
    }
    hasPermission
}


/**
 * 获取本地 IMSI
 */
fun imsi(): String = KvUtils.getString("imsi")

/**
 * 更新 IMSI
 */
fun Context.updateIMSI(): String = this.imsi().also { KvUtils.put("imsi", it) }

/**
 * 获取最新 设备IMSI
 */
@SuppressLint("MissingPermission", "HardwareIds")
fun Context.imsi(): String = buildString {
    if (checkPermission(Manifest.permission.READ_PHONE_STATE)) {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            append(tm.subscriberId)
        } catch (_: Exception) {
        }
    }
}

/**
 * 获取本地 IMEI
 */
fun imei(): String = KvUtils.getString("imei")

/**
 * 更新 IMEI
 */
fun Context.updateIMEI(): String = this.imei().also { KvUtils.put("imei", it) }

/**
 * 获取最新 IMEI
 */
@SuppressLint("MissingPermission", "NewApi")
fun Context.imei(): String = buildString {
    if (checkPermission(Manifest.permission.READ_PHONE_STATE) && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            append(tm.imei)
        } catch (_: Exception) {
        }
    }
}

/**
 * 获取本地 meid
 */
fun meid(): String = KvUtils.getString("meid")

/**
 * 更新 meid
 */
fun Context.updateMeid(): String = this.meid().also { KvUtils.put("meid", it) }

/**
 * 获取最新 meid
 */
@SuppressLint("MissingPermission")
fun Context.meid(): String = buildString {
    if (checkPermission(Manifest.permission.READ_PHONE_STATE) && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            append(tm.meid)
        } catch (_: Exception) {
        }
    }
}

/**
 * 获取本地 iccid
 */
fun iccId(): String = KvUtils.getString("icc_id")

/**
 * 更新 iccid
 */
fun Context.updateIccId(): String = this.iccId().also { KvUtils.put("icc_id", it) }

/**
 * 获取最新 iccid
 */
@SuppressLint("MissingPermission", "HardwareIds")
fun Context.iccId(): String = buildString {
    if (checkPermission(Manifest.permission.READ_PHONE_STATE)) {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            append(tm.simSerialNumber)
        } catch (_: Exception) {
        }
    }
}

/**
 * 获取本地 设备id
 */
fun androidId(): String = KvUtils.getString("android_id")

/**
 * 更新 设备id
 */
fun Context.updateAndroidId(): String = this.androidId().also { KvUtils.put("android_id", it) }

/**
 * 获取最新 设备id
 */
@SuppressLint("HardwareIds")
fun Context.androidId(): String =
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

/**
 * 获取设备唯一id
 */
//@SuppressLint("HardwareIds", "PrivateApi")
//fun Context.updateAndroidId(): String {
//    val localId = KvUtils.getString("android_id", "")
//    if (localId.isNotBlank()) {
//        return localId
//    }
//    val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
//    val tmDevice = imei()
//    val macAd = mac()
//    var serialNum = ""
//    try {
//        val c = Class.forName("android.os.SystemProperties")
//        val get = c.getMethod("get", String::class.java, String::class.java)
//        serialNum = get.invoke(c, "ro.serialno", "unknown") as String
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//    }
//    if (androidId.isNullOrBlank() && tmDevice.isBlank() && macAd.isBlank() && serialNum.isBlank()) {
//        val uuid = UUID.randomUUID().toString().replace("-", "")
//        KvUtils.put("android_id", uuid)
//        return uuid
//    }
//    val deviceUuid = UUID(
//        androidId.hashCode().toLong(),
//        (tmDevice.hashCode() or macAd.hashCode() or serialNum.hashCode()).toLong()
//    ).toString().replace("-", "")
//    KvUtils.put("android_id", deviceUuid)
//    return deviceUuid
//}


@SuppressLint("MissingPermission")
fun Context.mac(): String {
    return try {
        val manager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val macAddress = manager.connectionInfo.macAddress
        if (macAddress.isNullOrBlank()) throw NullPointerException()
        else macAddress
    } catch (e: Exception) {
        ""
    }
}

/**
 * 写出到文件
 * @param data 写出数据
 * @param output 输出Uri
 */
fun Context.writeToFile(data: ByteArray, output: Uri) {
    // 在写入之前尝试重置文件
    // 不允许使用 write-truncate (wt) 模式打开非本地文件，因此在写入数据之前请安全操作
    runCatching {
        contentResolver.openOutputStream(output, "wt")?.apply {
            write(byteArrayOf())
            close()
        }
    }
    runCatching {
        val outputStream =
            contentResolver.openOutputStream(output) ?: error("Failed to open output file stream")
        outputStream.write(data)
        outputStream.close()
    }.onFailure {
        error("Failed to write to file: $output")
    }
}

/**
 * 读取文件
 * @param input 文件uri
 */
fun Context.readFromFile(input: Uri): String {
    val inputStream =
        contentResolver.openInputStream(input) ?: error("Failed to open input file stream")
    return inputStream.bufferedReader().readText()
}

/**
 * 判断文件是否真是有效
 * @param input 文件uri
 */
fun Context.isFile(input: Uri): Boolean = try {
    contentResolver.openFileDescriptor(input, "r")?.use { descriptor ->
        descriptor.close()
    }
    true
} catch (_: Exception) {
    false
}


fun Context.readFromAssets(input: String): String? = runCatching {
    val inp = assets.open(input)
    val isr = InputStreamReader(inp)
    isr.readText().also {
        inp.close()
        isr.close()
    }
}.getOrNull()
