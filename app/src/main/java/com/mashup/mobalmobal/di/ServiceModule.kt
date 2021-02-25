package com.mashup.mobalmobal.di

import com.mashup.mobalmobal.network.MobalRetrofit
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
}