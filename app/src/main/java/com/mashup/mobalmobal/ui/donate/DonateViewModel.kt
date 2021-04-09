package com.mashup.mobalmobal.ui.donate

import androidx.lifecycle.SavedStateHandle
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.repository.DonateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
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

    private val postIdProcessor: BehaviorSubject<Int> = BehaviorSubject.create()

    private val postIdSingle: Single<Int> = postIdProcessor.firstOrError()

    init {
        val initialPostId = savedStateHandle.get<Int>(KEY_POST_ID)
        if (initialPostId != null) {
            postIdProcessor.onNext(initialPostId)
        } else {
            _donateErrorMessageSubject.onNext(R.string.donate_error_post_id)
            navigateToDonationDetail()
        }
    }

    private val _donateAmountSubject: BehaviorSubject<String> = BehaviorSubject.create()

    fun setDonateAmount(amount: String) {
        _donateAmountSubject.onNext(amount)
    }

    fun requestDonation() {
        postIdSingle.zipWith(_donateAmountSubject.firstOrError())
            .flatMap { (postId, donateAmount) ->
                donateRepository.donate(postId.toString(), donateAmount)
            }
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

    private fun navigateToDonationDetail() {
        _donationDetailTriggerSubject.onNext(true)
    }

}