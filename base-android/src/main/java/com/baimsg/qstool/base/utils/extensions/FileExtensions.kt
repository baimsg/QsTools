package com.baimsg.qstool.base.utils.extensions

import java.io.File

/**
 * Create by Baimsg on 2022/12/29
 *
 **/
fun File.append(name: CharSequence) = File(absolutePath + File.separator + name)
fun File.append(file: File) = File(absolutePath + File.separator + file.absolutePath)