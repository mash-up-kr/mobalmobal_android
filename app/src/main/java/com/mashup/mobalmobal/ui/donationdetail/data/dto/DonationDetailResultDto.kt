package com.mashup.mobalmobal.ui.donationdetail.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.dto.PostsDto

data class DonationDetailResultDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DonationDetailDto
)

data class DonationDetailDto(@SerializedName("post") val post: PostDto)

