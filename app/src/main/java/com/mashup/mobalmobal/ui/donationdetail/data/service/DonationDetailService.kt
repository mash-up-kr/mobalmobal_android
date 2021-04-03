package com.mashup.mobalmobal.ui.donationdetail.data.service

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DonationDetailService {

    @GET("donation")
    fun getDonation(
        @Query("donation_id")
        donationId: String
    ): Single<DonationDetailResultDto>
}