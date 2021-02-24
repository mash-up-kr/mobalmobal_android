package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResultDto
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import io.reactivex.Single

class ProfileRepositoryImpl:
    ProfileRepository {
    override fun getProfile(userId: String): Single<ProfileResultDto> {
        TODO("Not yet implemented")
    }
}