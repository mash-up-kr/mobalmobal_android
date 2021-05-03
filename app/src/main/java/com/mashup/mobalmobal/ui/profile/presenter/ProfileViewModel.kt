package com.mashup.mobalmobal.ui.profile.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.base.extensions.combineLatest
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.dto.UserDto
import com.mashup.mobalmobal.data.repository.UserRepository
import com.mashup.mobalmobal.ui.profile.data.dto.*
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    userRepository: UserRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        const val STATUS_DONATION_BEFORE = "BEFORE"
        const val STATUS_DONATION_INPROGRESS = "IN_PROGRESS"
        const val STATUS_DONATION_EXPIRED = "EXPIRED"
    }

    private val _toastSubject: PublishSubject<String> = PublishSubject.create()
    val toastSubject get() = _toastSubject

    val userNickSubject: Observable<String> get() = _userDtoSubject.map { it.nickname }

    private val _backTriggerSubject: PublishSubject<Unit> = PublishSubject.create()
    val backTriggerSubject get() = _backTriggerSubject

    private val _userDtoSubject: BehaviorSubject<UserDto> = BehaviorSubject.create()

    init {
        userRepository.fetchUser()
            .subscribeOnIO()
            .subscribeWithErrorLogger { userDto ->
                userDto.data?.let { _userDtoSubject.onNext(it) }
            }.addToDisposables()
    }

    private val _donationsSubject: BehaviorSubject<List<ProfileItem>> =
        BehaviorSubject.createDefault(emptyList())

    init {
        requestDonations()
            .subscribeOnIO()
            .subscribeWithErrorLogger {
                _donationsSubject.onNext(it)
            }
            .addToDisposables()
    }

    private val _itemSubject: BehaviorSubject<List<ProfileItem>> = BehaviorSubject.create()
    val itemSubject: Observable<List<ProfileItem>> = _itemSubject.distinctUntilChanged()

    init {
        _userDtoSubject.combineLatest(_donationsSubject)
            .distinctUntilChanged()
            .map { listOf(it.first.toProfileItem()) + it.second }
            .subscribeWithErrorLogger {
                _itemSubject.onNext(it)
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