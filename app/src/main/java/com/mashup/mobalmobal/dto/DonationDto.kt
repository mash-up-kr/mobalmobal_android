package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.data.dto.UserDto

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
    val goalPrice: Long,
    @SerializedName("donated_price")
    val donatedPrice: Long,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("due_date")
    val dueDate: String,
    @SerializedName("donate_users")
    val donateUsers: List<UserDto>
)