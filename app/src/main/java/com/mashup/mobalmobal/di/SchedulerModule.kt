package com.mashup.mobalmobal.di

import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.funin.base.funinbase.rx.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SchedulerModule {

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}