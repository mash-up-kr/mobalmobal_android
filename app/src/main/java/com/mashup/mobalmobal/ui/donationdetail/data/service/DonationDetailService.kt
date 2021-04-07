package com.mashup.mobalmobal.ui.donationdetail.data.service

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DonationDetailService {
    @GET("posts/{post_id}}")
    fun getDonation(
        @Path("post_id")
        donationId: String
    ): Single<DonationDetailResultDto>
}