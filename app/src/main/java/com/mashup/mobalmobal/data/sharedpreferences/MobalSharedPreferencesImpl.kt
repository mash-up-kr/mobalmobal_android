package com.mashup.mobalmobal.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

object MobalSharedPreferencesImpl : MobalSharedPreferences {

    private const val PREFERENCE_NAME = "mobal_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_USER_ID = "user_id"

    private val _accessTokenProcessor: BehaviorProcessor<String> = BehaviorProcessor.create()

    fun init(applicationContext: Context) {
        sharedPreferences =
            applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        emitInitialValue()
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

    private fun emitInitialValue() {
        emitValue(sharedPreferences, KEY_ACCESS_TOKEN)
    }

    private fun emitValue(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            KEY_ACCESS_TOKEN -> {
                val accessToken = sharedPreferences.getString(key, "") ?: ""
                _accessTokenProcessor.offer(accessToken)
            }
        }
    }

    override fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    override fun getAccessTokenFlowable(): Flowable<String> =
        _accessTokenProcessor.distinctUntilChanged()

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