package com.mashup.mobalmobal.ui.profile.domain.model

import androidx.annotation.StringRes
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.dto.UserDto

sealed class ProfileItem(
    val id: String
) {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    data class Header(
        @StringRes
        val titleId: Int
    ) : ProfileItem(titleId.toString())

    data class User(
        val userId: Int,
        val nickName: String,
        val profileUrl: String?,
        val point: Int
    ) : ProfileItem(userId.toString())

    data class DonationSummary(
        val requestCount: Int,
        val donatedCount: Int,
        val closedCount: Int
    ) : ProfileItem("profile donation summary")

    data class Donation(
        val donationId: String,
        val imageUrl: String,
        val title: String,
        val description: String,
        val goalPrice: Double,
        val donatedPrice: Double,
        val startAt: String,
        val endAt: String,
    ) : ProfileItem(donationId)
}

fun createProfileHeaderItems(requestCount: Int, donatedCount: Int, closedCount: Int): List<ProfileItem> =
    listOfNotNull(
        ProfileItem.Header(
            titleId = R.string.profile_header_donation_summary
        ),

        ProfileItem.DonationSummary(
            requestCount = requestCount,
            donatedCount = donatedCount,
            closedCount = closedCount
        )
    )
