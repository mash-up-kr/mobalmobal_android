package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.data.dto.PostDto

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
