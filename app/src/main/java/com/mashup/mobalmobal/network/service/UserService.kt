package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.data.dto.UserWrappingDto
import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import retrofit2.http.GET

interface UserService {
    @GET("users")
    fun fetchUser(): Single<Response<UserWrappingDto>>
}