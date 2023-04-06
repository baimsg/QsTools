package com.baimsg.qstool.data

/**
 * Create by Baimsg on 2023/4/5
 *
 **/
sealed class InvokeStatus

object Uninitialized : InvokeStatus()

class InvokeLading(val msg: String) : InvokeStatus()

object InvokeSuccess : InvokeStatus()

class InvokeFail(val msg: String) : InvokeStatus()