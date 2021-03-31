package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.dto.UserDto
import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SignService {

    @POST("users")
    fun signUp(@Body body: RequestBody): Single<Response<UserDto>>
}