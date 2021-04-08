package com.mashup.mobalmobal.ui.profile.data.repository

import com.google.gson.Gson
import com.mashup.mobalmobal.ui.profile.data.ProfileMockData
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.ProfileResponseDto
import io.reactivex.Single

class MockProfileRepositoryImpl : ProfileRepository {

    override fun getUserInfo(): Single<ProfileResponseDto> =
        Single.just(
            Gson().fromJson(
                ProfileMockData.userData,
                ProfileResponseDto::class.java
            )
        )

    override fun getMyDonations(status: String): Single<MyPostResponseDto> =
        Single.just(
            Gson().fromJson(
                ProfileMockData.donationData,
                MyPostResponseDto::class.java
            )
        )

}