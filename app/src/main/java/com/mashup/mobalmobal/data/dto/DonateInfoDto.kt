package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class DonateInfoDto(
    @SerializedName("donate_id") val donateId: Int,
    @SerializedName("post_id") val postId: Int,
    @SerializedName("amount") val amount: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("createdAt") val createdAt: String,
)
