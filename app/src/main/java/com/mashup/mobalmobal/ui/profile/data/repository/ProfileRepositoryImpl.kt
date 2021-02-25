package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResultDto
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService
) : ProfileRepository {

    override fun getProfile(userId: String): Single<ProfileResultDto> =
        profileService.getProfile(userId)
}