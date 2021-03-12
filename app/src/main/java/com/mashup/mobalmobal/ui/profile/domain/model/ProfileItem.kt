package com.mashup.mobalmobal.ui.profile.domain.model

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

    data class User(
        val userId: String,
        val name: String,
        val nickName: String,
        val profileUrl: String
    ) : ProfileItem(userId)

    data class Point(
        val point: Double
    ) : ProfileItem("profile item point : $point")

    data class DonationSummary(
        val requestCount: Int,
        val donatedCount: Int,
        val closedCount: Int
    ) : ProfileItem("profile donation summary")

    data class RequestDonation(
        val donationId: String,
        val author: UserDto,
        val title: String,
        val description: String,
        val goalPrice: Double,
        val donatedPrice: Double,
        val startDate: Long,
        val dueDate: Long,
    ) : ProfileItem(donationId)

    data class Donated(
        val donationId: String,
        val author: UserDto,
        val title: String,
        val description: String,
        val goalPrice: Double,
        val donatedPrice: Double,
        val startDate: Long,
        val dueDate: Long,
    ) : ProfileItem(donationId)
}