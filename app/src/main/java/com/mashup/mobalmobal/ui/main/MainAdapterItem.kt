package com.mashup.mobalmobal.ui.main

import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.dto.PostsDto

sealed class MainAdapterItem(val id: String) {
    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean = false

    data class MyDonation(
        val donations: List<MyDonationAdapterItem>,
    ) : MainAdapterItem("Main-My-Donations")

    data class Header(
        val title: String
    ) : MainAdapterItem("Main-Header-$title")

    data class ProgressDonation(
        val postId: Int,
        val dueDateText: String,
        val currentPrice: Int,
        val goalPrice: Int,
        val title: String,
        val donationImageUrl: String?
    ) : MainAdapterItem(postId.toString())
}

fun PostsDto.toMyDonationItem(): MainAdapterItem.MyDonation =
    MainAdapterItem.MyDonation(donations = toMyDonationAdapterItems().distinct())

fun createEmptyMyDonationItem(): MainAdapterItem.MyDonation =
    MainAdapterItem.MyDonation(donations = listOf(MyDonationAdapterItem.Addition))

fun createEmptyMainAdapterItem(): List<MainAdapterItem> =
    listOf(createEmptyMyDonationItem(), MainAdapterItem.Header("진행중"))

fun PostsDto.toMainAdapterItems(): List<MainAdapterItem.ProgressDonation> =
    posts.map { it.toMainAdapterItem() }

fun PostDto.toMainAdapterItem(): MainAdapterItem.ProgressDonation {
    return MainAdapterItem.ProgressDonation(
        postId = postId,
        dueDateText = "TODO $endAt - $startedAt",
        currentPrice = currentAmount,
        goalPrice = goalPrice,
        title = title,
        donationImageUrl = postImage
    )
}