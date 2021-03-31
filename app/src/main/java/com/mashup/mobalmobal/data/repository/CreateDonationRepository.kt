package com.mashup.mobalmobal.data.repository

import com.funin.base.funinbase.extension.requestBodyOf
import com.mashup.mobalmobal.dto.DonationDto
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.service.CreateDonationService
import io.reactivex.Single

class CreateDonationRepository(private val service: CreateDonationService) {
    fun createDonation(
        title: String,
        description: String?,
        post_image: String?,
        goal: Int,
        started_at: Long,
        end_at: Long
    ): Single<Response<DonationDto>> {
         return service.createDonation(
            requestBodyOf {
                "title" to title
                description?.let { "description" to it }
                post_image?.let { "post_image" to it }
                "goal" to goal
                "started_at" to started_at
                "end_at" to end_at
            }
        )
    }
}