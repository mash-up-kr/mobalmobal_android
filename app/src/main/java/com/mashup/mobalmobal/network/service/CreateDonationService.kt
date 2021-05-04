package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailDto
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface CreateDonationService {
    @Multipart
    @POST("posts")
    fun createDonation(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part postImage: MultipartBody.Part
    ): Single<Response<DonationDetailDto>>
}