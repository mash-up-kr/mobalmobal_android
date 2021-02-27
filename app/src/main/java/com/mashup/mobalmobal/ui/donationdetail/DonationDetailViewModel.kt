package com.mashup.mobalmobal.ui.donationdetail

import androidx.lifecycle.ViewModel
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepository
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DonationDetailViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val donationDetailRepository: DonationDetailRepository
) : BaseViewModel(schedulerProvider){

    fun requestDonationDetail(donationId: String) {
        donationDetailRepository.getDonationDetail(donationId)
            .toFlowable()
            .subscribeOnIO()
            .doOnSubscribe{}
            .subscribeWithErrorLogger {  }
            .addToDisposables()
    }
}