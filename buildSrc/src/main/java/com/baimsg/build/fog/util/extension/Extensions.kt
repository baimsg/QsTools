package com.baimsg.build.fog.util.extension

/**
 * Create by Baimsg on 2022/7/28
 *
 **/

/**
 * 判断当前类是否是需要进行字符串加密的
 * @param className 当前 className
 */
fun <T> List<T>.isInFogPackages(className: String): Boolean {
    return when {
        className.isBlank() -> false
        className.contains("LiveLiterals\$") -> false
        isEmpty() -> false
        else -> {
            forEach {
                if (className.replace('/', '.').startsWith("$it")) {
                    return true
                }
            }
            false
        }
    }

}