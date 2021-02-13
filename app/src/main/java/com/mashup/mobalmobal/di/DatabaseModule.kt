package com.mashup.mobalmobal.di

import android.content.Context
import com.mashup.mobalmobal.data.MobalDatabase
import com.mashup.mobalmobal.data.dao.DonationDao
import com.mashup.mobalmobal.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun getDatabase(@ApplicationContext context: Context): MobalDatabase =
        MobalDatabase.getInstance(context)

    @Provides
    fun getUserDao(@ApplicationContext context: Context): UserDao =
        MobalDatabase.getInstance(context).userDao()

    @Provides
    fun getDonationDao(@ApplicationContext context: Context): DonationDao =
        MobalDatabase.getInstance(context).donationDao()
}