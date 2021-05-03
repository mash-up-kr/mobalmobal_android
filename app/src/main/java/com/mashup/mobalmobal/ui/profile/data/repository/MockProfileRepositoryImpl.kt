package com.mashup.mobalmobal.ui.profile.data.repository

import com.google.gson.Gson
import com.mashup.mobalmobal.ui.profile.data.ProfileMockData
import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateResponseDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostResponseDto
import com.mashup.mobalmobal.ui.profile.presenter.ProfileViewModel
import io.reactivex.Single

class MockProfileRepositoryImpl : ProfileRepository {

    override fun getMyPosts(status: String): Single<MyPostResponseDto> =
        when (status) {
            ProfileViewModel.STATUS_DONATION_BEFORE -> {
                Single.just(
                    Gson().fromJson(
                        ProfileMockData.postData,
                        MyPostResponseDto::class.java
                    )
                )
            }
            ProfileViewModel.STATUS_DONATION_INPROGRESS -> {
                Single.just(
                    Gson().fromJson(
                        ProfileMockData.postDataInPregress,
                        MyPostResponseDto::class.java
                    )
                )
            }
            ProfileViewModel.STATUS_DONATION_EXPIRED -> {
                Single.just(
                    Gson().fromJson(
                        ProfileMockData.postDataExpired,
                        MyPostResponseDto::class.java
                    )
                )
            }
            else -> {
                Single.just(
                    Gson().fromJson(
                        ProfileMockData.postDataExpired,
                        MyPostResponseDto::class.java
                    )
                )
            }
        }


    override fun getMyDonations(): Single<MyDonateResponseDto> =
        Single.just(
            Gson().fromJson(
                ProfileMockData.donateData,
                MyDonateResponseDto::class.java
            )
        )

}