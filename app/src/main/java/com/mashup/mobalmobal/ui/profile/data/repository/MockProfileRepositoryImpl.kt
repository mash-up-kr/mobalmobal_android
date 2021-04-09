package com.mashup.mobalmobal.ui.profile.data.repository

import com.google.gson.Gson
import com.mashup.mobalmobal.ui.profile.data.ProfileMockData
import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
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

    override fun getMyPosts(status: String): Single<MyPostResponseDto> =
        Single.just(
            Gson().fromJson(
                ProfileMockData.postData,
                MyPostResponseDto::class.java
            )
        )

    override fun getMyDonations(): Single<MyDonateResponseDto> =
        Single.just(
            Gson().fromJson(
                ProfileMockData.donateData,
                MyDonateResponseDto::class.java
            )
        )

}