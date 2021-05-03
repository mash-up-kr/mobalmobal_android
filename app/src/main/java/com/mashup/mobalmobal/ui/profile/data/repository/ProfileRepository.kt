package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import io.reactivex.Single

interface ProfileRepository {
    fun getMyPosts(status: String): Single<MyPostResponseDto>

    fun getMyDonations(): Single<MyDonateResponseDto>
}