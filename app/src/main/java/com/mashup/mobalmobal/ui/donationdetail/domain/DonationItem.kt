package com.mashup.mobalmobal.ui.donationdetail.domain

data class DonationItem(
    val imageUrl: String,
    val productName: String,
    val description: String,
    val author: User,
    val goalPrice: Double,
    val donatedPrice: Double,
    val donators: List<User>,
    val startDate: Long,
    val dueDate: Long
)

data class User(
    val userId: String,
    val nickName: String,
    val profileUrl: String
)