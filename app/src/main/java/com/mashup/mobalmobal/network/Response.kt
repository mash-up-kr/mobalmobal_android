package com.mashup.mobalmobal.network

data class Response<T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null
)