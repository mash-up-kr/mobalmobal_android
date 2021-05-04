package com.mashup.mobalmobal.ui.profile.data.service

import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {
    @GET("posts/my")
    fun getMyPosts(
        @Query("status")
        status: String? = null
    ): Single<MyPostResponseDto>

    @GET("donate/my")
    fun getMyDonates(): Single<MyDonateResponseDto>
}