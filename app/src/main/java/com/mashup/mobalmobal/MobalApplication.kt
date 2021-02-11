package com.mashup.mobalmobal

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeStetho()
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this)
    }
}