package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("post_id") val postId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("post_image") val postImage: String? = null,
    @SerializedName("title") val title: String,
    @SerializedName("post_description") val description: String? = null,
    @SerializedName("goal") val goalPrice: Int,
    @SerializedName("current_amount") val currentAmount: Int,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("started_at") val startedAt: String? = null,
    @SerializedName("end_at") val endAt: String? = null
)