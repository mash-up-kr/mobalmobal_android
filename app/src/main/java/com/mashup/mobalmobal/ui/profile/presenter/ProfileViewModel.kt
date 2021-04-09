package com.mashup.mobalmobal.ui.profile.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferencesImpl
import com.mashup.mobalmobal.ui.profile.data.dto.*
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

    private val _backTriggerSubject: PublishSubject<Unit> = PublishSubject.create()
    val backTriggerSubject get() = _backTriggerSubject

    init {
        requestProfile("34")
    }

    private fun requestProfile(userId: String?) {
        if(userId.isNullOrBlank()) {
            backTriggerSubject.onNext(Unit)
            return
        }

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
                            createDonations.getDonationCount() ,
                            donatedDoantions.getDonationCount(),
                            closedDonations.getDonationCount()
                        ).toSummaryProfileItem()
                    )

                    addAll(createDonations + donatedDoantions + closedDonations)
                }
            }

    private fun requestMyCreateDonations() =
        profileRepository.getMyPosts(STATUS_DONATION_BEFORE)
            .zipWith(profileRepository.getMyPosts(STATUS_DONATION_INPROGRESS))
            .map{pair ->
                val beforeDonation = pair.first.data.posts
                val inProgressDonation = pair.second.data.posts
                val list = mutableListOf<PostDto>().also {
                    if(!beforeDonation.isNullOrEmpty()) it.addAll(beforeDonation)
                    if(!inProgressDonation.isNullOrEmpty()) it.addAll(inProgressDonation)
                }
                MyPostDto(list)
            }

    private fun requestMyDonated() =
        profileRepository.getMyDonations()
            .map {
                if(it.data.donates.isNullOrEmpty()){
                    MyDonateDto(emptyList())
                }else{
                    it.data
                }
            }

    private fun reuqestMyClosed() =
        profileRepository.getMyPosts(STATUS_DONATION_EXPIRED)
            .map {
                if(it.data.posts.isNullOrEmpty()){
                    MyPostDto(emptyList())
                }else{
                    it.data
                }
            }

    private fun List<ProfileItem>.getDonationCount() =
        if(isNullOrEmpty()) {
            0
        }else{
            size - 1
        }
}