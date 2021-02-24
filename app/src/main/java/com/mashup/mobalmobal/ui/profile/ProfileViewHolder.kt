package com.mashup.mobalmobal.ui.profile

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import javax.inject.Inject

class ProfileViewHolder(
    @Inject
    var schedulerProvider: BaseSchedulerProvider,
    @Inject
    var profileRepository: ProfileRepository
) : BaseViewModel(schedulerProvider){


}