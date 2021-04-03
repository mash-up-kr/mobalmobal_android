package com.mashup.mobalmobal.data.repository

import android.content.Context
import android.net.Uri
import com.funin.base.funinbase.extension.toMultipartBody
import com.mashup.mobalmobal.network.service.FileService
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class FileRepository @Inject constructor(private val service: FileService) {
    fun uploadImage(
        context: Context,
        uri: Uri
    ): Single<String> {
        val multipartBody = uri.toMultipartBody(context, "file") ?: return Single.error(IllegalStateException("upload image failed: multipartBody"))
        return service.uploadImage(
            MultipartBody.Builder().addPart(multipartBody).build()
        ).map { it.data ?: "" }
    }

}