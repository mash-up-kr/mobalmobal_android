package com.mashup.mobalmobal.ui.main

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.dto.UserDto
import com.mashup.mobalmobal.data.repository.UserRepository
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val userRepository: UserRepository,
    private val sharedPreferences: MobalSharedPreferences
) : BaseViewModel(schedulerProvider) {

    private fun isSignedIn(): Flowable<Boolean> = sharedPreferences.getAccessTokenFlowable()
        .map { it.isNotBlank() }

    val isSignedInSingle: Single<Boolean>
        get() = isSignedIn().firstOrError()

    init {
        isSignedIn()
            .distinctUntilChanged()
            .filter { it }
            .flatMapCompletable { fetchMe() }
            .subscribeOnIO()
            .subscribeWithErrorLogger()
            .addToDisposables()
    }

    private val _meSubject: BehaviorSubject<UserDto> = BehaviorSubject.create()

    val meName: Observable<String> = _meSubject.distinctUntilChanged().map { it.nickname }

    private fun fetchMe(): Completable = userRepository.fetchUser()
        .doOnSuccess { response -> response.data?.let { _meSubject.onNext(it) } }
        .ignoreElement()
}