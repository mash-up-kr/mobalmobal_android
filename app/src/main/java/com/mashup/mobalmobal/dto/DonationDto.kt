package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName

data class DonationDto(
    @SerializedName("donation_id")
    val donationId: String,
    @SerializedName("author")
    val author: UserDto,
    @SerializedName("product")
    val product: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("goal_price")
    val goalPrice: Double,
    @SerializedName("donated_price")
    val donatedPrice: Double,
    @SerializedName("start_date")
    val startDate: Long,
    @SerializedName("due_date")
    val dueDate: Long,
    @SerializedName("donate_users")
    val donateUsers: List<UserDto>
)