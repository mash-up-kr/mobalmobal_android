package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailDto
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateDonationService {

    @POST("posts")
    fun createDonation(@Body body: RequestBody): Single<Response<DonationDetailDto>>
}