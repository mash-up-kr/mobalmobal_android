package com.mashup.mobalmobal.ui.charge

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ChargeViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _chargeAmountSubject: BehaviorSubject<String> = BehaviorSubject.create()

    fun setChargeAmount(amount: String) {
        _chargeAmountSubject.onNext(amount)
    }

}