package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

data class MyDonateResponseDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: MyDonateDto
)

data class MyDonateDto(
    @SerializedName("donate")
    val donates: List<DonateDto>?
)

data class DonateDto(
    @SerializedName("donate_id")
    val donateId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("createdAt")
    val createAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: String? = null,
    @SerializedName("post")
    val post: PostDto
)