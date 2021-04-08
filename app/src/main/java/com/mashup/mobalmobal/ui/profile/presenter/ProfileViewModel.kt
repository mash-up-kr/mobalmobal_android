package com.mashup.mobalmobal.ui.profile.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.ui.profile.data.dto.toProfileItem
import com.mashup.mobalmobal.ui.profile.data.dto.toProfileItems
import com.mashup.mobalmobal.ui.profile.data.dto.toSummaryProfileItem
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val profileRepository: ProfileRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        const val STATUS_DONATION_BEFORE = "before"
        const val STATUS_DONATION_INPROGRESS = "inprogress"
        const val STATUS_DONATION_EXPIRED = "expired"
    }

    private val _toastSubject: PublishSubject<String> = PublishSubject.create()
    val toastSubject get() = _toastSubject

    private val _loadingSubject: PublishSubject<Boolean> = PublishSubject.create()
    val loadingSubject get() = _loadingSubject

    private val _profileSubject: BehaviorSubject<List<ProfileItem>> =
        BehaviorSubject.createDefault(emptyList())
    val profileSubject get() = _profileSubject

    init {
        requestProfile()
    }

    private fun requestProfile() {
        profileRepository.getUserInfo()
            .zipWith(requestDonations())
            .subscribeOnIO()
            .doOnSubscribe { _loadingSubject.onNext(true) }
            .subscribeWithErrorLogger { response ->
                val userDto = response.first.data.user
                val donations = response.second

                val profileModel: List<ProfileItem> =
                    listOfNotNull(
                        userDto.toProfileItem()
                    ) + donations

                _profileSubject.onNext(profileModel)
                _loadingSubject.onNext(false)
            }
            .addToDisposables()
    }

    private fun requestDonations() =
        profileRepository.getMyDonations(STATUS_DONATION_BEFORE)
            .zipWith(profileRepository.getMyDonations(STATUS_DONATION_INPROGRESS))
            .zipWith(profileRepository.getMyDonations(STATUS_DONATION_EXPIRED))
            .map {
                val requestDonations =
                    it.first.first.data.toProfileItems(R.string.profile_header_donation_request)
                val donatedDoantions =
                    it.first.second.data.toProfileItems(R.string.profile_header_donated)
                val closedDonations =
                    it.second.data.toProfileItems(R.string.profile_header_donation_closed)

                mutableListOf<ProfileItem>().apply {
                    addAll(
                        Triple(
                            requestDonations.size,
                            donatedDoantions.size,
                            closedDonations.size
                        ).toSummaryProfileItem()
                    )

                    addAll(requestDonations + donatedDoantions + closedDonations)
                }
            }
}