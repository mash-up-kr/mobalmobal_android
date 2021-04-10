package com.mashup.mobalmobal.ui.donationdetail.domain

import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.util.DateTimeUtils

data class DonationItem(
    val imageUrl: String?,
    val productName: String,
    val description: String,
    val author: User,
    val goalPrice: Int,
    val currentPrice: Int,
    val donators: List<User>,
    val dueDateText: String,
    val endAt: String
) {
    val donatePercent: Double
        get() = currentPrice.toDouble().div(goalPrice.toDouble()) * 100
}

fun PostDto.toDonationItem(): DonationItem =
    DonationItem(
        imageUrl = postImage,
        productName = title,
        description = description ?: "",
        author = User(
            userId = user?.userId ?: "2",
            nickName = user?.nickName ?: "리액션마스터",
            profileUrl = user?.profileUrl ?: "http://pht.qoo-static.com/VHB9bVB8cTcnqwnu0nJqKYbiutRclnbGxTpwnayKB4vMxZj8pk1220Rg-6oQ68DwAkqO=w300"
        ),
        goalPrice = goalPrice,
        currentPrice = currentAmount,
        donators = mutableListOf<User>().also {
            it.addAll(donatedUsers?.map { donator ->
                User(
                    userId = donator.userId,
                    nickName = donator.nickName,
                    profileUrl = donator.profileUrl
                )
            } ?: emptyList())
        },
        dueDateText = DateTimeUtils.calculateDecimalDayText(startedAt, endAt) ?: "",
        endAt = endAt ?: ""
    )

data class User(
    val userId: String,
    val nickName: String,
    val profileUrl: String?
)