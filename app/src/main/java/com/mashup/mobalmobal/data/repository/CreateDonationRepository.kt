package com.mashup.mobalmobal.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.service.CreateDonationService
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailDto
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CreateDonationRepository @Inject constructor(private val service: CreateDonationService) {
    fun createDonation(
        title: String,
        description: String,
        goal: Int,
        postImage: Uri,
        startedAt: String,
        endAt: String
    ): Single<Response<DonationDetailDto>> {
        val postFile = postImage.toFile()
        val filePart = MultipartBody.Part.createFormData(
            "post_image",
            postFile.name,
            postFile.asRequestBody(MultipartBody.FORM)
        )
        return service.createDonation(
            mapOf(
                "title" to title.toTextPlainRequestBody(),
                "description" to description.toTextPlainRequestBody(),
                "goal" to goal.toString().toTextPlainRequestBody(),
                "started_at" to startedAt.toTextPlainRequestBody(),
                "end_at" to endAt.toTextPlainRequestBody()
            ),
            filePart
        )
    }
    fun String.toTextPlainRequestBody() = this.toRequestBody("text/plain".toMediaTypeOrNull())
}