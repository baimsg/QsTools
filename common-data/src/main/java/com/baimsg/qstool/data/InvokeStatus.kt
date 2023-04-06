package com.baimsg.qstool.data

/**
 * Create by Baimsg on 2023/4/5
 * 执行状态
 **/
sealed class InvokeStatus

/**
 * 未初始化
 */
object Uninitialized : InvokeStatus()

/**
 * 正在加载
 * @param msg 加载提示
 */
class InvokeLoading(val msg: String) : InvokeStatus()

/**
 * 加载成功
 */
object InvokeSuccess : InvokeStatus()

/**
 * 加载失败
 * @param msg 失败提示
 */
class InvokeFail(val msg: String) : InvokeStatus()

/**
 *加载结束
 * @param msg 结束提示
 */
class InvokeFinish(val msg: String) : InvokeStatus()
