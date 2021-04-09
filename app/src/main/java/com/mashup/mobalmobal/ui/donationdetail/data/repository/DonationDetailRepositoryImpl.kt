package com.mashup.mobalmobal.ui.donationdetail.data.repository

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import com.mashup.mobalmobal.ui.donationdetail.data.service.DonationDetailService
import io.reactivex.Single

class DonationDetailRepositoryImpl(
    private val donationDetailService: DonationDetailService
) : DonationDetailRepository {

    override fun getDonationDetail(donationId: String): Single<DonationDetailResultDto> =
        donationDetailService.getDonation(donationId)
}