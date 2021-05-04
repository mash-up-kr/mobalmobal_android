package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

data class MyPostResponseDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: MyPostDto
)

data class MyPostDto(
    @SerializedName("post")
    val posts: List<PostDto>?
)
