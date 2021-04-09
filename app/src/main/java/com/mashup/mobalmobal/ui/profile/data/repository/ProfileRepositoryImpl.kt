package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResponseDto
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import io.reactivex.Single

class ProfileRepositoryImpl(
    private val profileService: ProfileService
) : ProfileRepository {

    override fun getUserInfo(): Single<ProfileResponseDto> =
        profileService.getUserInfo()

    override fun getMyPosts(status: String): Single<MyPostResponseDto> {
        TODO("API 정의되고 나면 추가할 예정입니다.")
    }

    override fun getMyDonations(): Single<MyDonateResponseDto> {
        TODO("Not yet implemented")
    }
}