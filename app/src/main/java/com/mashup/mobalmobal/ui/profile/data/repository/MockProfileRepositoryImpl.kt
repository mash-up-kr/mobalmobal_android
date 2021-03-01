package com.mashup.mobalmobal.ui.profile.data.repository

import com.google.gson.Gson
import com.mashup.mobalmobal.ui.profile.data.ProfileMockData
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResultDto
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MockProfileRepositoryImpl : ProfileRepository {

    override fun getProfile(userId: String): Single<ProfileResultDto> =
        Single.just(
            Gson().fromJson(
                ProfileMockData.profileData,
                ProfileResultDto::class.java)
        )
}