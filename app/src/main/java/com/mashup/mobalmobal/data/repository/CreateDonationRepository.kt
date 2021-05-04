package com.mashup.mobalmobal.data.repository

import android.content.Context
import android.net.Uri
import com.funin.base.funinbase.extension.toMultipartBody
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.service.CreateDonationService
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailDto
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CreateDonationRepository @Inject constructor(private val service: CreateDonationService) {
    fun createDonation(
        context: Context,
        title: String,
        description: String,
        goal: Int,
        postImage: Uri,
        startedAt: String,
        endAt: String
    ): Single<Response<DonationDetailDto>> {
        val multipartBody = postImage.toMultipartBody(context, "post_image") ?: return Single.error(
            IllegalArgumentException("createDonation failed postImage: $postImage")
        )
        return service.createDonation(
            mapOf(
                "title" to title.toTextPlainRequestBody(),
                "description" to description.toTextPlainRequestBody(),
                "goal" to goal.toString().toTextPlainRequestBody(),
                "started_at" to startedAt.toTextPlainRequestBody(),
                "end_at" to endAt.toTextPlainRequestBody()
            ),
            multipartBody
        )
    }

    fun String.toTextPlainRequestBody() = this.toRequestBody("text/plain".toMediaTypeOrNull())
}