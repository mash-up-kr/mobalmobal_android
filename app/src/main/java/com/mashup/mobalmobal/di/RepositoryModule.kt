package com.mashup.mobalmobal.di

import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepository
import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepositoryImpl
import com.mashup.mobalmobal.ui.donationdetail.data.service.DonationDetailService
import com.mashup.mobalmobal.ui.profile.data.repository.MockProfileRepositoryImpl
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepositoryImpl
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProfileRepository(profileService: ProfileService): ProfileRepository =
        ProfileRepositoryImpl(profileService)

    @Provides
    fun provideDonationDetailRepository(donationDetailService: DonationDetailService): DonationDetailRepository =
        DonationDetailRepositoryImpl(donationDetailService)

//    @Provides
//    fun provideMockProfileRepository(): ProfileRepository =
//        MockProfileRepositoryImpl()
}