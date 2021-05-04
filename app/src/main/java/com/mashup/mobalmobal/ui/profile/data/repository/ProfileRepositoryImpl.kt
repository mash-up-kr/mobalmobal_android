package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import io.reactivex.Single

class ProfileRepositoryImpl(
    private val profileService: ProfileService
) : ProfileRepository {

    override fun getMyPosts(status: String): Single<MyPostResponseDto> =
        profileService.getMyPosts(status)

    override fun getMyDonations(): Single<MyDonateResponseDto> =
        profileService.getMyDonates()
}

