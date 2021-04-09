package com.mashup.mobalmobal.ui.charge

import androidx.lifecycle.SavedStateHandle
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.repository.ChargeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class ChargeViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val savedStateHandle: SavedStateHandle,
    private val chargeRepository: ChargeRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val KEY_USER_NAME = "userName"
    }

    private val _donateTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val donateTrigger: Observable<Boolean> = _donateTriggerSubject

    private val _chargeCompleteTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val chargeCompleteTriggerSubject: Observable<Boolean> = _chargeCompleteTriggerSubject

    private val _chargeErrorMessageSubject: PublishSubject<Int> = PublishSubject.create()
    val chargeErrorMessage: Observable<Int> = _chargeErrorMessageSubject

    private val _chargeInputSubject: BehaviorSubject<Charge> =
        BehaviorSubject.createDefault(Charge())

    private val userNameProcessor: BehaviorSubject<String> = BehaviorSubject.create()

    private val userNameSingle: Single<String> = userNameProcessor.firstOrError()

    init {
        val initialUserName = savedStateHandle.get<String>(KEY_USER_NAME)
        if (initialUserName != null) {
            userNameProcessor.onNext(initialUserName)
        } else {
            _chargeErrorMessageSubject.onNext(R.string.charge_error_user_name)
            navigateToDonation()
        }
    }

    fun setChargeAmount(amount: String) {
        _chargeInputSubject.onNext(
            _chargeInputSubject.value?.copy(amount = amount) ?: Charge(amount = amount)
        )
    }

    fun setChargedAt(date: String) {
        _chargeInputSubject.onNext(
            _chargeInputSubject.value?.copy(chargedAt = date) ?: Charge(chargedAt = date)
        )
    }

    fun requestCharge() {

    }

    private fun navigateToDonation() {
        _donateTriggerSubject.onNext(true)
    }

    private fun navigateToChargeComplete() {
        _chargeCompleteTriggerSubject.onNext(true)
    }

    data class Charge(
        val amount: String? = null,
        val chargedAt: String? = null
    )

}