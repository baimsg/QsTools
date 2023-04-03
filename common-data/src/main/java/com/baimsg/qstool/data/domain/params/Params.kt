package com.baimsg.qstool.data.domain.params

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
interface Params {
}

interface PagingParams : Params {
    val page: Int
}

fun Params.toPrimaryKey(id: Any): String = "$id-${toString()}"