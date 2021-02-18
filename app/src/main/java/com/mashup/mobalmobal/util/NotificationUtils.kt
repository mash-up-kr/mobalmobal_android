package com.mashup.mobalmobal.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.ui.MainActivity

object NotificationUtils {
    private const val NOTIFICATION_ID = 0

    fun NotificationManager.sendNotification(applicationContext: Context, messageBody: String) {

        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        contentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_channel_id)
        )
        builder.apply {
            setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            setContentTitle(applicationContext.getString(R.string.notification_title))
            setContentText(messageBody)
            setContentIntent(contentPendingIntent)
        }
        notify(NOTIFICATION_ID, builder.build())
    }

    fun NotificationManager.cancelAllNotifications() {
        cancelAll()
    }


}
