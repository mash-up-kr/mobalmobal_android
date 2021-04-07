package com.mashup.mobalmobal.ui.main

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val sharedPreferences: MobalSharedPreferences
) : BaseViewModel(schedulerProvider) {

    private fun isSignedIn(): Flowable<Boolean> = sharedPreferences.getAccessTokenFlowable()
        .map { it.isNotBlank() }

    val isSignedInSingle: Single<Boolean>
        get() = isSignedIn().firstOrError()
}