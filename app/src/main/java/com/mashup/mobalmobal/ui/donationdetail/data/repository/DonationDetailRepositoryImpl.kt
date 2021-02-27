package com.mashup.mobalmobal.ui.donationdetail.data.repository

import com.mashup.mobalmobal.ui.donationdetail.data.dto.DonationDetailResultDto
import com.mashup.mobalmobal.ui.donationdetail.data.service.DonationDetailService
import com.mashup.mobalmobal.ui.profile.data.service.ProfileService
import io.reactivex.Single
import javax.inject.Inject

class DonationDetailRepositoryImpl @Inject constructor(
    private val donationDetailService: DonationDetailService
): DonationDetailRepository{

    override fun getDonationDetail(donationId: String): Single<DonationDetailResultDto> =
        donationDetailService.getProfile(donationId)
}