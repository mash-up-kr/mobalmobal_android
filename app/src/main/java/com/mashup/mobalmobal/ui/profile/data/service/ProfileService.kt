package com.mashup.mobalmobal.ui.profile.data.service

import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResponseDto
import io.reactivex.Single
import retrofit2.http.GET

interface ProfileService {
    @GET("users")
    fun getUserInfo(): Single<ProfileResponseDto>

    @GET("posts/my")
    fun getMyDonations(): Single<MyPostResponseDto>
}