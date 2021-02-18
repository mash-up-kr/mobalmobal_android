package com.mashup.mobalmobal.ui

import android.annotation.TargetApi
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.facebook.stetho.Stetho
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferencesImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeStetho()
        initializeSharedPreferences()
        createNotificationChannel()
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeSharedPreferences() {
        MobalSharedPreferencesImpl.init(this)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                applicationContext.getString(R.string.notification_channel_id),
                applicationContext.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}