package com.mashup.mobalmobal.ui.donationdetail.data.repository

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import io.reactivex.Single

interface DonationDetailRepository {
    fun getDonationDetail(donationId: String): Single<DonationDetailResultDto>
}