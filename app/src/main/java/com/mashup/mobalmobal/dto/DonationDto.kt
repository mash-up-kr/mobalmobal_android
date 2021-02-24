package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName

class DonationDto (
    @SerializedName("author")
    val author: UserDto,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("goal_price")
    val goalPrice: Double,
    @SerializedName("donated_price")
    val donatedPrice: Double,
    @SerializedName("start_date")
    val startDate: Double,
    @SerializedName("due_date")
    val dueDate: Double,
    @SerializedName("donate_users")
    val donateUsers: List<UserDto>
)