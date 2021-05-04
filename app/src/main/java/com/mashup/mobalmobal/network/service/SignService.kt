package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.dto.LoginDto
import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SignService {
    @POST("users/login")
    fun login(@Body body: RequestBody): Single<Response<LoginDto>>

    @POST("users")
    fun signUp(@Body body: RequestBody): Single<Response<LoginDto>>
}