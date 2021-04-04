package com.mashup.mobalmobal.ui.donate

import androidx.lifecycle.SavedStateHandle
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.repository.DonateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val savedStateHandle: SavedStateHandle,
    private val donateRepository: DonateRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val KEY_POST_ID = "postId"
    }

    private val _donationDetailTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val donationDetailTrigger: Observable<Boolean> = _donationDetailTriggerSubject

    private val _donateErrorMessageSubject: PublishSubject<Int> = PublishSubject.create()
    val donateErrorMessage: Observable<Int> = _donateErrorMessageSubject

    private val postId = savedStateHandle.get<String>(KEY_POST_ID)

    init {
        if (postId == null) {
            _donateErrorMessageSubject.onNext(R.string.donate_error_post_id)
            navigateToDonationDetail()
        }
    }

    fun requestDonation(amount: String) {
        postId?.let {
            donateRepository.donate(postId, amount)
                .subscribeWithErrorLogger { isSuccess ->
                    if (isSuccess) {
                        navigateToDonationDetail()
                    } else {
                        _donateErrorMessageSubject.onNext(
                            R.string.donate_error_message
                        )
                    }
                }.addToDisposables()
        }
    }

    private fun navigateToDonationDetail() {
        _donationDetailTriggerSubject.onNext(true)
    }
}