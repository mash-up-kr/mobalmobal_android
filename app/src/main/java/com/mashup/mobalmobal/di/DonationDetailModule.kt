package com.mashup.mobalmobal.di

import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepository
import com.mashup.mobalmobal.ui.donationdetail.data.repository.MockDonationDetailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DonationDetailModule {

    @Provides
    fun provideDonationDetailRepository(): DonationDetailRepository =
        MockDonationDetailRepositoryImpl()
}