package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body

interface FileService {

    fun uploadImage(@Body body: RequestBody): Single<Response<String>>
}