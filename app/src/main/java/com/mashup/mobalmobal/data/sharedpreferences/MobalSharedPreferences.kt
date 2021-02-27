package com.mashup.mobalmobal.data.sharedpreferences

interface MobalSharedPreferences {

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?

    fun saveUserId(userId: String?)

    fun getUserId(): String?
}