package com.mashup.mobalmobal.ui.donationdetail.data.service

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DonationDetailService {

    @GET("donation")
    fun getProfile(
        @Query("donation_id")
        donationId: Int
    ): Single<DonationDetailResultDto>
}