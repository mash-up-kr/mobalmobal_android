package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResponseDto
import io.reactivex.Single

interface ProfileRepository {
    fun getUserInfo(): Single<ProfileResponseDto>

    fun getMyPosts(status: String): Single<MyPostResponseDto>

    fun getMyDonations(): Single<MyDonateResponseDto>
}