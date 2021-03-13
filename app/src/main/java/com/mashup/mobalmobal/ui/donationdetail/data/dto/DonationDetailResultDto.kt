package com.mashup.mobalmobal.ui.donationdetail.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.dto.DonationDto
import com.mashup.mobalmobal.ui.donationdetail.domain.DonationItem
import com.mashup.mobalmobal.ui.donationdetail.domain.User
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileDetailDto

data class DonationDetailResultDto (
    @SerializedName("result")
    val result: Int,
    @SerializedName("data")
    val `data`: DonationDto
)

fun DonationDto.toDonationItem(): DonationItem =
    DonationItem(
        imageUrl = imageUrl,
        productName = product,
        description = description,
        author = User(
            userId = author.userId,
            nickName = author.nickName,
            profileUrl = author.profileUrl
        ),
        goalPrice = goalPrice,
        donatedPrice = donatedPrice,
        donators = mutableListOf<User>().also{
            it.addAll(donateUsers.map { donator ->
                User(
                    userId = donator.userId,
                    nickName = donator.nickName,
                    profileUrl = donator.profileUrl
                )
            })
        },
        startDate = startDate,
        dueDate = dueDate
    )