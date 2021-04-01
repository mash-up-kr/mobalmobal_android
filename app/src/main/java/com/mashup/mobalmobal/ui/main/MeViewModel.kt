package com.mashup.mobalmobal.ui.main

import android.util.Log
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val sharedPreferences: MobalSharedPreferences
) : BaseViewModel(schedulerProvider) {

    fun checkSignedIn(): Single<Boolean> = sharedPreferences.getAccessTokenFlowable().firstOrError()
        .map { it.isNotBlank() }

}