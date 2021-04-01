package com.mashup.mobalmobal.ui.main

import com.mashup.mobalmobal.data.dto.PostDto

data class MainDonationAdapterItem(
    val postId: Int,
    val dueDateText: String,
    val currentPrice: Int,
    val currentPriceText: String,
    val goalPrice: Int,
    val title: String,
    val donationImageUrl: String?
)

fun PostDto.toMainDonationAdapterItem(): MainDonationAdapterItem =
    MainDonationAdapterItem(
        postId = postId,
        dueDateText = "endAt - startAt",
        currentPrice = 2000,
        currentPriceText = "2000원",
        goalPrice = goalPrice,
        title = title,
        donationImageUrl = postImage
    )