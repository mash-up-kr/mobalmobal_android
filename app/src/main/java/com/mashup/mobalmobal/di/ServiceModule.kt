package com.mashup.mobalmobal.di

import com.mashup.mobalmobal.network.MobalRetrofit
import com.mashup.mobalmobal.network.service.*
import com.mashup.mobalmobal.ui.donationdetail.data.service.DonationDetailService
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideProfileService(okHttpClient: OkHttpClient): ProfileService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun provideSignService(okHttpClient: OkHttpClient): SignService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun providePostService(okHttpClient: OkHttpClient): PostService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun provideDonationDetailService(okHttpClient: OkHttpClient): DonationDetailService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun provideUserService(okHttpClient: OkHttpClient): UserService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun provideCreateDonationService(okHttpClient: OkHttpClient): CreateDonationService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun provideDonateService(okHttpClient: OkHttpClient): DonateService =
        MobalRetrofit.create(okHttpClient)

    @Provides
    fun provideChargeService(okHttpClient: OkHttpClient): ChargeService =
        MobalRetrofit.create(okHttpClient)

}