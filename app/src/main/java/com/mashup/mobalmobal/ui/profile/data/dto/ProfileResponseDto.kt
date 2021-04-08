package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.dto.UserDto
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

data class ProfileResponseDto (
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: ProfileDto
)

data class ProfileDto (
    @SerializedName("user")
    val user: UserDto
)

fun UserDto.toProfileItem(): ProfileItem =
    ProfileItem.User(
        userId = userId,
        nickName = nickName,
        profileUrl = profileUrl ?: "",
        point = cash ?: 0
    )



