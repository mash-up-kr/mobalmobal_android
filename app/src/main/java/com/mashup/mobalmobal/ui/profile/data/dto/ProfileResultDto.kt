package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.R
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
    val point: Long,
    @SerializedName("my_donation_summary")
    val donationSummary: DonationSummaryDto,
    @SerializedName("my_request_donation")
    val requestDonations: List<DonationDto>,
    @SerializedName("my_donatings")
    val donations: List<DonationDto>,
    @SerializedName("my_closed_donation")
    val closedDonations: List<DonationDto>,
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
            profileUrl = profileUrl,
            point = point
        ),

        ProfileItem.Header(
            titleId = R.string.profile_header_donation_summary
        ),

        ProfileItem.DonationSummary(
            requestCount = donationSummary.requestCount,
            donatedCount = donationSummary.donatedCount,
            closedCount = donationSummary.closedCount
        ),
    ).toMutableList()
        .also {
            it.add(
                ProfileItem.Header(
                    titleId = R.string.profile_header_donation_request
                )
            )

            it.addAll(requestDonations.map { donation ->
                ProfileItem.Donation(
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

            it.add(
                ProfileItem.Header(
                    titleId = R.string.profile_header_donated
                )
            )

            it.addAll(donations.map { donation ->
                ProfileItem.Donation(
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

            it.add(
                ProfileItem.Header(
                    titleId = R.string.profile_header_donation_closed
                )
            )

            it.addAll(closedDonations.map { donation ->
                ProfileItem.Donation(
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


