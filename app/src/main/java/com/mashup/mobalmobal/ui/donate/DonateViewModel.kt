package com.mashup.mobalmobal.ui.donate

import androidx.lifecycle.SavedStateHandle
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.constant.Constants.KEY_POST_ID
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
    savedStateHandle: SavedStateHandle,
    private val donateRepository: DonateRepository
) : BaseViewModel(schedulerProvider) {

    private val _donateSuccessTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val donateSuccessTrigger: Observable<Boolean> = _donateSuccessTriggerSubject

    private val _donateErrorMessageSubject: PublishSubject<Int> = PublishSubject.create()
    val donateErrorMessage: Observable<Int> = _donateErrorMessageSubject

    private val _donateToastMessageSubject: PublishSubject<Int> = PublishSubject.create()
    val donateToastMessage: Observable<Int> = _donateToastMessageSubject

    private val postIdProcessor: BehaviorSubject<Int> = BehaviorSubject.create()

    init {
        val initialPostId = savedStateHandle.get<Int>(KEY_POST_ID)
        if (initialPostId != null) {
            postIdProcessor.onNext(initialPostId)
        } else {
            _donateErrorMessageSubject.onNext(R.string.donate_error_post_id)
            navigateToDonationDetail()
        }
    }

    private val postIdSingle: Single<Int> = postIdProcessor.firstOrError()

    private val _donateAmountSubject: BehaviorSubject<String> = BehaviorSubject.create()

    fun setDonateAmount(amount: String) {
        _donateAmountSubject.onNext(amount)
    }

    fun requestDonation() {
        postIdSingle.zipWith(_donateAmountSubject.firstOrError())
            .flatMap { (postId, donateAmount) ->
                val formattedAmount = donateAmount.replace(",", "").trim()
                donateRepository.donate(postId.toString(), formattedAmount)
            }
            .subscribeWithErrorLogger { isSuccess ->
                if (isSuccess) {
                    _donateToastMessageSubject.onNext(R.string.donate_success_message)
                    navigateToDonationDetail()
                } else {
                    _donateErrorMessageSubject.onNext(
                        R.string.donate_error_message
                    )
                }
            }.addToDisposables()
    }

    fun donate(amount: String) {
        postIdSingle
            .flatMap { donateRepository.donate(it.toString(), amount) }
            .subscribeWithErrorLogger { isSuccess ->
                if (isSuccess) {
                    _donateToastMessageSubject.onNext(R.string.donate_success_message)
                    _donateSuccessTriggerSubject.onNext(isSuccess)
                } else {
                    _donateErrorMessageSubject.onNext(
                        R.string.donate_error_message
                    )
                }
            }
            .addToDisposables()
    }

    private fun navigateToDonationDetail() {
        _donateSuccessTriggerSubject.onNext(true)
    }
}