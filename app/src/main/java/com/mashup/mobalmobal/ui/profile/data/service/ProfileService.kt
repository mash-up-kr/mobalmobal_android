package com.mashup.mobalmobal.ui.profile.data.service

import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {
    @GET("/users")
    fun getProfile(
        @Query("user_id")
        userId: String
    ): Single<ProfileResultDto>
}