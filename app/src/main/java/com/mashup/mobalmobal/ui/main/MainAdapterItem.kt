package com.mashup.mobalmobal.ui.main

import androidx.paging.PagingData
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
        val donations: PagingData<MainDonationAdapterItem>
    ) : MainAdapterItem("Main-Progress_Donations")
}

fun PostsDto.toMainAdapterItem(): MainAdapterItem.MyDonation =
    MainAdapterItem.MyDonation(donations = toMyDonationAdapterItems())

fun createEmptyMainAdapterItem(): MainAdapterItem.MyDonation =
    MainAdapterItem.MyDonation(donations = listOf(MyDonationAdapterItem.Addition))