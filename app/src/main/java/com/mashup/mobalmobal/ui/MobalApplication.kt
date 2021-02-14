package com.mashup.mobalmobal.ui

import android.app.Application
import android.app.NotificationChannel
import com.facebook.stetho.Stetho
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferencesImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeStetho()
        initializeSharedPreferences()
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeSharedPreferences() {
        MobalSharedPreferencesImpl.init(this)
    }
}