package com.mashup.mobalmobal.ui.profile.data.repository

import com.funin.base.funinbase.extension.JsonObject
import com.funin.base.funinbase.extension.requestBodyOf
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
        val reqeustBody = requestBodyOf {
            put("filter", JsonObject().apply {
                put("status", status)
            })
        }
        return profileService.getMyPosts(reqeustBody)
    }


    override fun getMyDonations(): Single<MyDonateResponseDto> =
        profileService.getMyDonates()
}

