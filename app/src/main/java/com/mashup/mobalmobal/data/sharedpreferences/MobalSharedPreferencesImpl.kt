package com.mashup.mobalmobal.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object MobalSharedPreferencesImpl : MobalSharedPreferences {

    private const val PREFERENCE_NAME = "mobal_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_USER_ID = "user_id"

    fun init(applicationContext: Context) {
        sharedPreferences =
            applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun saveAccessToken(accessToken: String?) {
        sharedPreferences.edit {
            if (accessToken == null) {
                remove(KEY_ACCESS_TOKEN)
            } else {
                putString(KEY_ACCESS_TOKEN, accessToken)
            }
        }
    }

    override fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    override fun saveUserId(userId: String?) {
        sharedPreferences.edit {
            if (userId == null) {
                remove(KEY_USER_ID)
            } else {
                putString(KEY_USER_ID, userId)
            }
        }
    }

    override fun getUserId(): String? = sharedPreferences.getString(KEY_USER_ID, null)
}