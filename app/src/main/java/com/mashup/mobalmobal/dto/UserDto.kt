package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName

class UserDto (
    @SerializedName("id")
    val userId: String,
    @SerializedName("nick_name")
    val nickName: String,
    @SerializedName("profile_url")
    val profileUrl: String,
)