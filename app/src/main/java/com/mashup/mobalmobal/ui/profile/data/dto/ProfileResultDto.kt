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
    @SerializedName("user_id")
    val userId: String,
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
            userId = userId,
            name = name,
            nickName = nickName,
            profileUrl = profileUrl
        ),

        ProfileItem.Point(point),

        ProfileItem.DonationSummary(
            requestCount = donationSummary.requestCount,
            donatedCount = donationSummary.donatedCount,
            closedCount = donationSummary.closedCount
        ),
    ).toMutableList()
        .also {
            it.addAll(requestDonations.map { donation ->
                ProfileItem.RequestDonation(
                    donationId = donation.donationId,
                    author = donation.author,
                    title = donation.title,
                    description = donation.description,
                    goalPrice = donation.goalPrice,
                    donatedPrice = donation.donatedPrice,
                    startDate = donation.startDate,
                    dueDate = donation.dueDate
                )
            })

            it.addAll(donations.map { donation ->
                ProfileItem.Donated(
                    donationId = donation.donationId,
                    author = donation.author,
                    title = donation.title,
                    description = donation.description,
                    goalPrice = donation.goalPrice,
                    donatedPrice = donation.donatedPrice,
                    startDate = donation.startDate,
                    dueDate = donation.dueDate
                )
            })
        }


