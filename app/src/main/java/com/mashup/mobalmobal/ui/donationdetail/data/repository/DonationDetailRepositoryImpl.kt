package com.mashup.mobalmobal.ui.donationdetail.data.repository

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import com.mashup.mobalmobal.ui.donationdetail.data.service.DonationDetailService
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import io.reactivex.Single

class DonationDetailRepositoryImpl (
    private val donationDetailService: DonationDetailService
): DonationDetailRepository{

    override fun getDonationDetail(donationId: Int): Single<DonationDetailResultDto> =
        donationDetailService.getProfile(donationId)
}