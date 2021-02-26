package com.mashup.mobalmobal.ui.profile.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.ui.profile.data.dto.toProfileItems
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val schedulerProvider: BaseSchedulerProvider,
    private val profileRepository: ProfileRepository
) : BaseViewModel(schedulerProvider) {

    private val _toastSubject: PublishSubject<String> = PublishSubject.create()
    val toastSubject get() = _toastSubject

    private val _loadingSubject: PublishSubject<Boolean> = PublishSubject.create()
    val loadingSubject get() = _loadingSubject

    private val _profileSubject: BehaviorSubject<List<ProfileItem>> =
        BehaviorSubject.createDefault(emptyList())
    val profileSubject get() = _profileSubject

    fun getProfile(userId: String) {
        profileRepository.getProfile(userId)
            .toFlowable()
            .subscribeOnIO()
            .observeOnMain()
            .doOnSubscribe { _loadingSubject.onNext(true) }
            .subscribeWithErrorLogger { response ->
                with(response) {
                    if (result != 1) {
                        throw IOException(data.message)
                    }
                    _profileSubject.onNext(data.toProfileItems())
                }
                _loadingSubject.onNext(false)
            }
            .addToDisposables()
    }

}