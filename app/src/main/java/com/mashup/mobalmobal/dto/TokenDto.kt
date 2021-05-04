package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("token")
    val userToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String? = null
)