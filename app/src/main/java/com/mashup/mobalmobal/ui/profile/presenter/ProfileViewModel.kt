package com.mashup.mobalmobal.ui.profile.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferencesImpl
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostDto
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

    private val _profileSubject: BehaviorSubject<List<ProfileItem>> =
        BehaviorSubject.createDefault(emptyList())
    val profileSubject get() = _profileSubject

    private val _userNickSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val userNickSubject get() = _userNickSubject

    init {
        MobalSharedPreferencesImpl.getUserId()?.let { requestProfile() }
    }

    private fun requestProfile() {
        profileRepository.getUserInfo()
            .zipWith(requestDonations())
            .subscribeOnIO()
            .subscribeWithErrorLogger { response ->
                val userDto = response.first.data.user
                val donations = response.second

                val profileModel: List<ProfileItem> =
                    listOfNotNull(
                        userDto.toProfileItem()
                    ) + donations

                _userNickSubject.onNext(userDto.nickName)
                _profileSubject.onNext(profileModel)
            }
            .addToDisposables()
    }

    private fun requestDonations() =
        requestMyCreateDonations()
            .zipWith(requestMyDonated())
            .zipWith(reuqestMyClosed())
            .map {
                val createDonations =
                    it.first.first.toProfileItems(R.string.profile_header_donation_request)
                val donatedDoantions =
                    it.first.second.toProfileItems(R.string.profile_header_donated)
                val closedDonations =
                    it.second.toProfileItems(R.string.profile_header_donation_closed)

                mutableListOf<ProfileItem>().apply {
                    addAll(
                        Triple(
                            createDonations.lastIndex ,
                            donatedDoantions.lastIndex,
                            closedDonations.lastIndex
                        ).toSummaryProfileItem()
                    )

                    addAll(createDonations + donatedDoantions + closedDonations)
                }
            }

    private fun requestMyCreateDonations() =
        profileRepository.getMyPosts(STATUS_DONATION_BEFORE)
            .zipWith(profileRepository.getMyPosts(STATUS_DONATION_INPROGRESS))
            .map{
                MyPostDto(it.first.data.posts + it.second.data.posts)
            }

    private fun requestMyDonated() =
        profileRepository.getMyDonations()
            .map { it.data }

    private fun reuqestMyClosed() =
        profileRepository.getMyPosts(STATUS_DONATION_EXPIRED)
            .map { it.data }
}