package com.mashup.mobalmobal.ui.main

sealed class MyDonationAdapterItem(val id: String) {
    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean = false

    data class Donation(
        val donationId: Int,
        val title: String,
        val currentPriceText: String
    ) : MyDonationAdapterItem("MyDonation-$donationId")

    object Addition : MyDonationAdapterItem("My-Donation-Addition")
}