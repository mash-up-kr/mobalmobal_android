package com.mashup.mobalmobal.network

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MobalRetrofit {

    const val API_END_POINT = "http://13.125.168.51:3000/"

    fun <T> create(
        service: Class<T>,
        client: OkHttpClient,
        httpUrl: String = API_END_POINT
    ): T = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
        .create(service)

    inline fun <reified T : Any> create(
        client: OkHttpClient,
        httpUrl: String = API_END_POINT
    ): T {
        require(httpUrl.isNotBlank()) { "Parameter httpUrl cannot be blank." }
        return create(service = T::class.java, httpUrl = httpUrl, client = client)
    }
}