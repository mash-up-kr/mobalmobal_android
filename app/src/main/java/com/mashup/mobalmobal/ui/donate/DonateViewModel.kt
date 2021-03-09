package com.mashup.mobalmobal.ui.donate

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

}