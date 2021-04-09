package com.mashup.mobalmobal.data.repository

import com.funin.base.funinbase.extension.requestBodyOf
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.service.CreateDonationService
import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailDto
import io.reactivex.Single
import javax.inject.Inject

class CreateDonationRepository @Inject constructor(private val service: CreateDonationService) {
    fun createDonation(
        title: String,
        description: String?,
        goal: Int,
        startedAt: String,
        endAt: String
    ): Single<Response<DonationDetailDto>> {
        return service.createDonation(
            requestBodyOf {
                "title" to title
                description?.let { "description" to it }
                "goal" to goal
                "started_at" to startedAt
                "end_at" to endAt
            }
        )
    }
}