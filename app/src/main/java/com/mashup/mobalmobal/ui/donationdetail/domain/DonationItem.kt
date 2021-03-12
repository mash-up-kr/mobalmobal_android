package com.mashup.mobalmobal.ui.donationdetail.domain

sealed class DonationItem {
    data class Info(
        val imageUrl: String,
        val productName: String,
        val description: String,
        val author: User,
        val goalPrice: Double,
        val donatedPrice: Double,
        val donators: List<User>,
        val startDate: Long,
        val dueDate: Long
    ): DonationItem()
}

data class User(
    val userId: String,
    val nickName: String,
    val profileUrl: String
)