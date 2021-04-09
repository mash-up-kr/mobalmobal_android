package com.mashup.mobalmobal.ui.donationdetail.presenter

import androidx.lifecycle.SavedStateHandle
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.constant.Constants.KEY_POST_ID
import com.mashup.mobalmobal.ui.donationdetail.data.repository.DonationDetailRepository
import com.mashup.mobalmobal.ui.donationdetail.domain.DonationItem
import com.mashup.mobalmobal.ui.donationdetail.domain.toDonationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DonationDetailViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val donationDetailRepository: DonationDetailRepository
) : BaseViewModel(schedulerProvider) {

    private val _backTriggerSubject: PublishSubject<Unit> = PublishSubject.create()
    val backTriggerSubject get() = _backTriggerSubject

    private var postId: Int? = savedStateHandle[KEY_POST_ID]

    private val _postIdSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    init {
        val postId = savedStateHandle.get<Int>(KEY_POST_ID)
        if (postId != null) {
            _postIdSubject.onNext(postId)
        } else {
            _backTriggerSubject.onNext(Unit)
        }
    }

    private val _donationSubject: BehaviorSubject<DonationItem> = BehaviorSubject.create()
    val donationSubject get() = _donationSubject

    init {
        requestDonationDetail()
    }

    fun requestDonationDetail() {
        _postIdSubject.firstOrError()
            .flatMap { donationDetailRepository.getDonationDetail(it.toString()) }
            .subscribeOnIO()
            .subscribeWithErrorLogger { _donationSubject.onNext(it.data.post.toDonationItem()) }
            .addToDisposables()
    }
}