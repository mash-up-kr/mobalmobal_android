package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class DonateDto(
    @SerializedName("donate_id") val donateId: Int,
    @SerializedName("post_id") val postId: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("createdAt") val createdAt: String,
)