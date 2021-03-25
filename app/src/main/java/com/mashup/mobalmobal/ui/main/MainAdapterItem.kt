package com.mashup.mobalmobal.ui.main

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
        val donations: List<MainDonationAdapterItem>
    ) : MainAdapterItem("Main-Progress_Donations")
}