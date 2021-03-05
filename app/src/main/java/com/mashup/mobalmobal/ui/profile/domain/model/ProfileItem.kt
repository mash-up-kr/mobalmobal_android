package com.mashup.mobalmobal.ui.profile.domain.model

import androidx.annotation.StringRes
import com.mashup.mobalmobal.dto.UserDto

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
        val userId: String,
        val name: String,
        val nickName: String,
        val profileUrl: String,
        val point: Long
    ) : ProfileItem(userId)

    data class DonationSummary(
        val requestCount: Int,
        val donatedCount: Int,
        val closedCount: Int
    ) : ProfileItem("profile donation summary")

    data class Donation(
        val donationId: String,
        val author: UserDto,
        val title: String,
        val description: String,
        val goalPrice: Long,
        val donatedPrice: Long,
        val startDate: String,
        val dueDate: String,
    ) : ProfileItem(donationId)
}