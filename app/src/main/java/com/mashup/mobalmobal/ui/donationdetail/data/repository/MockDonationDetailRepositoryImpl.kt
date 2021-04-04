package com.mashup.mobalmobal.ui.donationdetail.data.repository

import com.google.gson.Gson
import com.mashup.mobalmobal.ui.donationdetail.data.MockDonationData
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import io.reactivex.Single

class MockDonationDetailRepositoryImpl : DonationDetailRepository {
    override fun getDonationDetail(donationId: String): Single<DonationDetailResultDto> =
        Single.just(
            Gson().fromJson(
                MockDonationData.donationData,
                DonationDetailResultDto::class.java
            )
        )
}