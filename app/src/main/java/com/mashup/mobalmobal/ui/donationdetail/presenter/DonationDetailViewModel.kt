package com.mashup.mobalmobal.ui.donationdetail.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.ui.donationdetail.data.dto.toDonationItem
import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepository
import com.mashup.mobalmobal.ui.donationdetail.domain.DonationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DonationDetailViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val donationDetailRepository: DonationDetailRepository
) : BaseViewModel(schedulerProvider) {

    private val _donationSubject: PublishSubject<DonationItem> = PublishSubject.create()
    val donatinSubject get() = _donationSubject

    fun requestDonationDetail(donationId: Int) {
        donationDetailRepository.getDonationDetail(donationId.toString())
            .toFlowable()
            .subscribeOnIO()
            .doOnSubscribe {}
            .subscribeWithErrorLogger { _donationSubject.onNext(it.data.toDonationItem()) }
            .addToDisposables()
    }
}