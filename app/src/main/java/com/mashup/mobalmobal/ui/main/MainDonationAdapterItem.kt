package com.mashup.mobalmobal.ui.main

data class MainDonationAdapterItem(
    val donationId: Int,
    val dueDateText: String,
    val currentPrice: Int,
    val currentPriceText: String,
    val goalPrice: Int,
    val title: String,
    val donationImageUrl: String?
)