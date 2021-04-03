package com.mashup.mobalmobal.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    postRepository: PostRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val INITIAL_LOAD_SIZE = 30
        private const val PAGE_SIZE = 30
    }

    @ExperimentalCoroutinesApi
    private val donationItems: Flowable<PagingData<MainDonationAdapterItem>> =
        postRepository.getPosts(viewModelScope, INITIAL_LOAD_SIZE, PAGE_SIZE)
            .map { pagingData -> pagingData.map { it.toMainDonationAdapterItem() } }

    private val _itemsSubject: BehaviorSubject<List<MainAdapterItem>> = BehaviorSubject.create()
    val items: Observable<List<MainAdapterItem>> = _itemsSubject

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        donationItems.subscribeOnIO()
            .subscribeWithErrorLogger {
                _itemsSubject.onNext(
                    listOf(
                        MainAdapterItem.MyDonation(
                            listOf(
                                MyDonationAdapterItem.Addition,
                                MyDonationAdapterItem.Donation(
                                    donationId = 1,
                                    title = "부탁드립니다 선생님",
                                    currentPriceText = "13,000"
                                ),
                                MyDonationAdapterItem.Donation(
                                    donationId = 2,
                                    title = "한푼만 줍쇼!",
                                    currentPriceText = "31,000"
                                ),
                                MyDonationAdapterItem.Donation(
                                    donationId = 3,
                                    title = "동냥좀 해주세요",
                                    currentPriceText = "321,000"
                                ),
                                MyDonationAdapterItem.Donation(
                                    donationId = 4,
                                    title = "나 보고 지나치게?",
                                    currentPriceText = "12,000"
                                ),
                                MyDonationAdapterItem.Donation(
                                    donationId = 5,
                                    title = "매쉬업 화이팅 !!",
                                    currentPriceText = "1,000"
                                ),
                                MyDonationAdapterItem.Donation(
                                    donationId = 6,
                                    title = "모두 행복하세요!!",
                                    currentPriceText = "390,000"
                                )
                            )
                        ),
                        MainAdapterItem.Header("진행중"),
                        MainAdapterItem.ProgressDonation(donations = it)
                    )
                )
            }
            .addToDisposables()
    }
}