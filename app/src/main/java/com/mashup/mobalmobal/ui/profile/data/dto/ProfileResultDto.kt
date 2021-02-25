package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.dto.DonationDto
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

data class ProfileResultDto(
    @SerializedName("result")
    val result: Int,
    @SerializedName("data")
    val `data`: ProfileDetailDto
)

data class ProfileDetailDto(
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
    val donations: List<DonationDto>,
    @SerializedName("message")
    val message: String
)

data class DonationSummaryDto(
    @SerializedName("request_count")
    val requestCount: Int,
    @SerializedName("donated_count")
    val donatedCount: Int,
    @SerializedName("closed_count")
    val closedCount: Int
)

fun ProfileDetailDto.toProfileItems(): List<ProfileItem> =
    listOfNotNull(
        ProfileItem.User(
            name,
            nickName,
            profileUrl
        ),

        ProfileItem.Point(point),

        ProfileItem.DonationSummary(
            donationSummary.requestCount,
            donationSummary.donatedCount,
            donationSummary.closedCount
        ),
    ).toMutableList()
        .also {
            it.addAll(requestDonations.map { donation ->
                ProfileItem.RequestDonation(
                    donation.author,
                    donation.title,
                    donation.description,
                    donation.goalPrice,
                    donation.donatedPrice,
                    donation.startDate,
                    donation.dueDate
                )
            })

            it.addAll(donations.map { donation ->
                ProfileItem.Donated(
                    donation.author,
                    donation.title,
                    donation.description,
                    donation.goalPrice,
                    donation.donatedPrice,
                    donation.startDate,
                    donation.dueDate
                )
            })
        }


