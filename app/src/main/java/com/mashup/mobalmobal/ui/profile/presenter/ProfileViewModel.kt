package com.mashup.mobalmobal.ui.profile.presenter

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.base.extensions.combineLatest
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.dto.UserDto
import com.mashup.mobalmobal.data.repository.UserRepository
import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostDto
import com.mashup.mobalmobal.ui.profile.data.repository.ProfileRepository
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.domain.model.createProfileHeaderItems
import com.mashup.mobalmobal.ui.profile.domain.model.toProfileItem
import com.mashup.mobalmobal.ui.profile.domain.model.toProfileItems
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
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
            }
            .addToDisposables()
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

    private val _itemsSubject: BehaviorSubject<List<ProfileItem>> = BehaviorSubject.create()
    val itemsSubject: Observable<List<ProfileItem>> = _itemsSubject.distinctUntilChanged()

    init {
        _userDtoSubject.combineLatest(_donationsSubject)
            .distinctUntilChanged()
            .map { listOf(it.first.toProfileItem()) + it.second }
            .subscribeWithErrorLogger {
                _itemsSubject.onNext(it)
            }
            .addToDisposables()
    }

    private fun requestDonations(): Single<List<ProfileItem>> =
        requestMyCreateDonations()
            .zipWith(requestMyDonated())
            .zipWith(requestMyClosed())
            .map {
                val createDonations =
                    it.first.first.toProfileItems(R.string.profile_header_donation_request)
                val donatedDoantions =
                    it.first.second.toProfileItems(R.string.profile_header_donated)
                val closedDonations =
                    it.second.toProfileItems(R.string.profile_header_donation_closed)

                mutableListOf<ProfileItem>().apply {
                    addAll(
                        createProfileHeaderItems(
                            createDonations.getDonationCount(),
                            donatedDoantions.getDonationCount(),
                            closedDonations.getDonationCount()
                        )
                    )

                    addAll(createDonations + donatedDoantions + closedDonations)
                }
            }

    private fun requestMyCreateDonations(): Single<MyPostDto> =
        profileRepository.getMyPosts(STATUS_DONATION_BEFORE)
            .zipWith(profileRepository.getMyPosts(STATUS_DONATION_INPROGRESS))
            .map { (myBeforePosts, myInProgressPosts) ->
                val beforeDonation = myBeforePosts.data.posts
                val inProgressDonation = myInProgressPosts.data.posts
                val list = mutableListOf<PostDto>().also {
                    if (!beforeDonation.isNullOrEmpty()) it.addAll(beforeDonation)
                    if (!inProgressDonation.isNullOrEmpty()) it.addAll(inProgressDonation)
                }
                MyPostDto(list)
            }

    private fun requestMyDonated(): Single<MyDonateDto> =
        profileRepository.getMyDonations()
            .map {
                if (it.data.donates.isNullOrEmpty()) {
                    MyDonateDto(emptyList())
                } else {
                    it.data
                }
            }

    private fun requestMyClosed(): Single<MyPostDto> =
        profileRepository.getMyPosts(STATUS_DONATION_EXPIRED)
            .map {
                if (it.data.posts.isNullOrEmpty()) {
                    MyPostDto(emptyList())
                } else {
                    it.data
                }
            }

    private fun List<ProfileItem>.getDonationCount(): Int =
        filterIsInstance(ProfileItem.Donation::class.java).size
}