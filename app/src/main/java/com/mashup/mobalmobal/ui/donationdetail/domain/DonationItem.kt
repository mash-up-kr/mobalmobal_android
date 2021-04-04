package com.mashup.mobalmobal.ui.donationdetail.domain

data class DonationItem(
    val imageUrl: String,
    val productName: String,
    val description: String,
    val author: User,
    val goalPrice: Long,
    val donatedPrice: Long,
    val donators: List<User>,
    val startDate: Long,
    val dueDate: Long
) {
    val donatePercent: Double
        get() = donatedPrice.toDouble().div(goalPrice.toDouble()) * 100
}

data class User(
    val userId: String,
    val nickName: String,
    val profileUrl: String
)