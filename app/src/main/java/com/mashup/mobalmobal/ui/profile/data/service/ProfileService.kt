package com.mashup.mobalmobal.ui.profile.data.service

import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileService {
    @POST("posts/my")
    fun getMyPosts(
        @Body
        body: RequestBody
    ): Single<MyPostResponseDto>

    @GET("donate/my")
    fun getMyDonates(): Single<MyDonateResponseDto>
}