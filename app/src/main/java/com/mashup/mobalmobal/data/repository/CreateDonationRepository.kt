package com.mashup.mobalmobal.data.repository

import com.funin.base.funinbase.extension.requestBodyOf
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.service.CreateDonationService
import io.reactivex.Single
import javax.inject.Inject

class CreateDonationRepository @Inject constructor(private val service: CreateDonationService) {
    fun createDonation(
        title: String,
        description: String?,
        postImage: String?,
        goal: Int,
        startedAt: String?,
        endAt: String?
    ): Single<Response<PostDto>> {
         return service.createDonation(
            requestBodyOf {
                "title" to title
                description?.let { "post_description" to it }
                postImage?.let { "post_image" to it }
                "goal" to goal
                startedAt?.let { "startedAt" to it }
                endAt?.let { "endAt" to it }
            }
        )
    }
}