package com.mashup.mobalmobal.ui.profile

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewHolder @Inject constructor(
    var schedulerProvider: BaseSchedulerProvider,
    var profileRepository: ProfileRepository
) : BaseViewModel(schedulerProvider){

    fun getProfile(userId: String) =
        profileRepository.getProfile(userId)
            .subscribeOnIO()
}