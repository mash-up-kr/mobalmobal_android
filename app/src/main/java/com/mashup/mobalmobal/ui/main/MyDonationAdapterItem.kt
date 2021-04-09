package com.mashup.mobalmobal.ui.main

import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.dto.PostsDto

sealed class MyDonationAdapterItem(val id: String) {
    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean = false

    data class Donation(
        val donationId: Int,
        val title: String,
        val currentPrice: Int,
        val donationRatio: Float
    ) : MyDonationAdapterItem("MyDonation-$donationId")

    object Addition : MyDonationAdapterItem("My-Donation-Addition")
}

fun PostsDto.toMyDonationAdapterItems(): List<MyDonationAdapterItem> =
    listOf(MyDonationAdapterItem.Addition) + posts.map { it.toMyDonationAdapterItem() }

fun PostDto.toMyDonationAdapterItem(): MyDonationAdapterItem {
    return MyDonationAdapterItem.Donation(
        donationId = postId,
        title = title,
        currentPrice = currentAmount,
        donationRatio = currentAmount.toFloat() / goalPrice
    )
}