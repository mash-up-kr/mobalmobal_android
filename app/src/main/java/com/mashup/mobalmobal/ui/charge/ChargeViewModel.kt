package com.mashup.mobalmobal.ui.charge

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.dto.UserDto
import com.mashup.mobalmobal.data.repository.ChargeRepository
import com.mashup.mobalmobal.data.repository.UserRepository
import com.mashup.mobalmobal.util.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChargeViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    userRepository: UserRepository,
    private val chargeRepository: ChargeRepository
) : BaseViewModel(schedulerProvider) {

    private val _meSubject: BehaviorSubject<UserDto> = BehaviorSubject.create()
    private val meName: Observable<String> = _meSubject.map { it.nickname }.distinctUntilChanged()

    init {
        userRepository.fetchUser()
            .subscribeOnIO()
            .subscribeWithErrorLogger { response -> response.data?.let { _meSubject.onNext(it) } }
            .addToDisposables()
    }

    private val _donateTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val donateTrigger: Observable<Boolean> = _donateTriggerSubject

    private val _chargeCompleteTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val chargeCompleteTriggerSubject: Observable<Boolean> = _chargeCompleteTriggerSubject

    private val _requestedPriceSubject: PublishSubject<Int> = PublishSubject.create()
    val requestedPrice: Observable<Int> = _requestedPriceSubject

    private val _chargeErrorMessageSubject: PublishSubject<Int> = PublishSubject.create()
    val chargeErrorMessage: Observable<Int> = _chargeErrorMessageSubject

    private val _chargeAmountSubject: BehaviorSubject<String> = BehaviorSubject.create()

    fun setChargeAmount(amount: String) {
        _chargeAmountSubject.onNext(amount)
    }

    fun requestCharge() {
        _chargeAmountSubject.firstOrError().zipWith(meName.firstOrError())
            .flatMap { (amount, meName) ->
                val actualAmount = amount.replace(",", "").trim()
                if (actualAmount.isBlank()) {
                    Single.error(IllegalArgumentException("request charge failed charge amount is blank"))
                } else {
                    chargeRepository.charge(
                        amount = actualAmount,
                        userName = meName,
                        chargedAt = DateTimeUtils.createDateByMobalDateFormat(Date(System.currentTimeMillis()))
                    )
                }
            }
            .subscribeOnIO()
            .subscribeWithErrorLogger {
                if (it.data != null) {
                    _chargeCompleteTriggerSubject.onNext(true)
                    _requestedPriceSubject.onNext(it.data.charge.amount)
                }
            }
            .addToDisposables()
    }
}