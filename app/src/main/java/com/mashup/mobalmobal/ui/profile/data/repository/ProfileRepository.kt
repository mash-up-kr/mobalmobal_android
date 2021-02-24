package com.mashup.mobalmobal.ui.profile.data.repository

import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResultDto
import io.reactivex.Single

interface ProfileRepository {
    fun getProfile(userId: String): Single<ProfileResultDto>
}
