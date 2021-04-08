package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface FileService {
    @Multipart
    @PUT("https://mobal-image-bucket.s3.ap-northeast-2.amazonaws.com/")
    fun uploadImage(
        @Part("photo") photo: RequestBody
    ): Single<Response<String>>

}