package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("token")
    val token: TokenDto
)