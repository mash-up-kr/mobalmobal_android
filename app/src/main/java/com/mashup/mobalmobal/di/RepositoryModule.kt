package com.mashup.mobalmobal.di

import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepositoryImpl
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideProfileRepository(profileService: ProfileService): ProfileRepository =
        ProfileRepositoryImpl(profileService)
}