package com.baimsg.qstool.data.models.entities

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
interface BaseEntity {
    var params: String

    fun getIdentifier(): String
}

interface PaginatedEntity : BaseEntity {
    var page: Int
}
