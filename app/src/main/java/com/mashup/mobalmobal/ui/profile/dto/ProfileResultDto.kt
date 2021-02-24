package com.mashup.mobalmobal.ui.profile.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.dto.DonationDto

data class ProfileResultDto (
    @SerializedName("result")
    val result: Int,
    @SerializedName("data")
    val `data`: ProfileDetailDto
)

data class ProfileDetailDto (
    @SerializedName("name")
    val name: String,
    @SerializedName("nick_name")
    val nickName: String,
    @SerializedName("profile_url")
    val profileUrl: String,
    @SerializedName("point")
    val point: Double,
    @SerializedName("my_donation_summary")
    val donationSummary: DonationSummaryDto,
    @SerializedName("my_request_donation")
    val requestDonations: List<DonationDto>,
    @SerializedName("my_donatings")
    val donations: List<DonationDto>
)

data class DonationSummaryDto(
    @SerializedName("request_count")
    val requestCount: Int,
    @SerializedName("donate_count")
    val donateCount: Int,
    @SerializedName("closed_count")
    val closedCount: Int
)
