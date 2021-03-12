package com.mashup.mobalmobal.ui.donationdetail.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.dto.DonationDto
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileDetailDto

data class DonationDetailResultDto (
    @SerializedName("result")
    val result: Int,
    @SerializedName("data")
    val `data`: DonationDto
)
