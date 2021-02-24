package com.mashup.mobalmobal.ui.profile

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import javax.inject.Inject

class ProfileViewHolder(
    @Inject
    var schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider){


}