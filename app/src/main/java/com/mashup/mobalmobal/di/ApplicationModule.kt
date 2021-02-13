package com.mashup.mobalmobal.di

import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideMobalSharedPreferences(): MobalSharedPreferences = MobalSharedPreferencesImpl
}