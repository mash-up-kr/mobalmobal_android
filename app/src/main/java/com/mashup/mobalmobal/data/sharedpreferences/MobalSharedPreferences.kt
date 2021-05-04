package com.mashup.mobalmobal.data.sharedpreferences

import io.reactivex.Flowable

interface MobalSharedPreferences {

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?

    fun saveUserId(userId: String?)

    fun getAccessTokenFlowable(): Flowable<String>

    fun getUserId(): String?
}