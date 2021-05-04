package com.mashup.mobalmobal.network

import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val preferences: MobalSharedPreferences
) : Interceptor {

    companion object {
        private const val KEY_ACCESS_TOKEN = "authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                runBlocking(Dispatchers.IO) {
                    val accessToken = preferences.getAccessToken() ?: return@runBlocking
                    header(KEY_ACCESS_TOKEN, accessToken)
                }
            }
            .build()
    )
}