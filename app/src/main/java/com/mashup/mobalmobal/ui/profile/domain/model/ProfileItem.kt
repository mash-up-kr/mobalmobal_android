package com.mashup.mobalmobal.ui.profile.domain.model

import com.mashup.mobalmobal.dto.UserDto

sealed class ProfileItem {
    data class User(
        val name: String,
        val nickName: String,
        val profileUrl: String
    ) : ProfileItem()

    data class Point(
        val point: Double
    ) : ProfileItem()

    data class DonationSummary(
        val requestCount: Int,
        val donatedCount: Int,
        val closedCount: Int
    ) : ProfileItem()

    data class RequestDonation(
        val author: UserDto,
        val title: String,
        val description: String,
        val goalPrice: Double,
        val donatedPrice: Double,
        val startDate: Double,
        val dueDate: Double,
    ) : ProfileItem()

    data class Donated(
        val author: UserDto,
        val title: String,
        val description: String,
        val goalPrice: Double,
        val donatedPrice: Double,
        val startDate: Double,
        val dueDate: Double,
    ) : ProfileItem()
}