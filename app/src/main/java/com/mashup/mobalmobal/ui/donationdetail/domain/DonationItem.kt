package com.mashup.mobalmobal.ui.donationdetail.domain

import com.mashup.mobalmobal.data.dto.PostDto

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
            userId = user?.userId ?: "",
            nickName = user?.nickName ?: "",
            profileUrl = user?.profileUrl ?: ""
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
        dueDateText = "endAt - startedAt",
        endAt = endAt ?: ""
    )

data class User(
    val userId: String,
    val nickName: String,
    val profileUrl: String
)