package com.mashup.mobalmobal.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object MobalSharedPreferencesImpl : MobalSharedPreferences {

    private const val PREFERENCE_NAME = "mobal_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    private const val KEY_ACCESS_TOKEN = "access_token"

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
}