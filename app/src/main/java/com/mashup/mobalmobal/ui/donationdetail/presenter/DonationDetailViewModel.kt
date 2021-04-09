package com.mashup.mobalmobal.ui.donationdetail.presenter

import androidx.lifecycle.SavedStateHandle
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepository
import com.mashup.mobalmobal.ui.donationdetail.domain.DonationItem
import com.mashup.mobalmobal.ui.donationdetail.domain.toDonationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DonationDetailViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val donationDetailRepository: DonationDetailRepository
) : BaseViewModel(schedulerProvider) {

    private val _donationSubject: PublishSubject<DonationItem> = PublishSubject.create()
    val donatinSubject get() = _donationSubject

    private val _backTriggerSubject: PublishSubject<Unit> = PublishSubject.create()
    val backTriggerSubject get() = _backTriggerSubject

    private var postId: Int? = savedStateHandle[DonationDetailFragment.KEY_SELECTED_POST_ID]

    init {
        requestDonationDetail(postId)
    }

    private fun requestDonationDetail(donationId: Int?) {
        if (postId == null) {
            backTriggerSubject.onNext(Unit)
            return
        }

        donationDetailRepository.getDonationDetail(donationId.toString())
            .subscribeOnIO()
            .subscribeWithErrorLogger { _donationSubject.onNext(it.data.post.toDonationItem()) }
            .addToDisposables()
    }
}