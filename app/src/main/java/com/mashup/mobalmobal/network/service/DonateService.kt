package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.data.dto.DonateDto
import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface DonateService {
    @POST("donate")
    fun donate(@Body body: RequestBody): Single<Response<DonateDto>>
}