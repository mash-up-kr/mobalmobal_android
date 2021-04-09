package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.dto.UserDto

data class PostDto(
    @SerializedName("post_id") val postId: Int,
    @SerializedName("user") val user: UserDto? = null,
    @SerializedName("post_image") val postImage: String? = null,
    @SerializedName("title") val title: String,
    @SerializedName("post_description") val description: String? = null,
    @SerializedName("goal") val goalPrice: Int,
    @SerializedName("current_amount") val currentAmount: Int,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("update_at") val updatedAt: String? = null,
    @SerializedName("started_at") val startedAt: String? = null,
    @SerializedName("end_at") val endAt: String? = null,
    @SerializedName("donated_users") val donatedUsers: List<UserDto>? = null
)