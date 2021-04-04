package com.mashup.mobalmobal.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val postRepository: PostRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val INITIAL_LOAD_SIZE = 30
        private const val PAGE_SIZE = 30
    }

    @ExperimentalCoroutinesApi
    private val donationItems: Flowable<PagingData<MainDonationAdapterItem>> =
        postRepository.getPosts(viewModelScope, INITIAL_LOAD_SIZE, PAGE_SIZE)
            .map { pagingData -> pagingData.map { it.toMainDonationAdapterItem() } }

    private val _myDonationSubject: BehaviorSubject<MainAdapterItem.MyDonation> =
        BehaviorSubject.createDefault(MainAdapterItem.MyDonation(listOf(MyDonationAdapterItem.Addition)))

    init {
        fetchMyDonation()
    }

    private val _itemsSubject: BehaviorSubject<List<MainAdapterItem>> = BehaviorSubject.create()
    val items: Observable<List<MainAdapterItem>> = _itemsSubject

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        donationItems.combineLatest(_myDonationSubject.toFlowable(BackpressureStrategy.LATEST))
            .subscribeOnIO()
            .subscribeWithErrorLogger { (donationItems, myDonation) ->
                _itemsSubject.onNext(
                    listOf(
                        myDonation,
                        MainAdapterItem.Header("진행중"),
                        MainAdapterItem.ProgressDonation(donations = donationItems)
                    )
                )
            }
            .addToDisposables()
    }

    // TODO Add refreshing donation Items
    fun refresh() {
        fetchMyDonation()
    }

    private fun fetchMyDonation() {
        postRepository.getMyPosts()
            .subscribeOnIO()
            .subscribeWithErrorLogger {
                _myDonationSubject.onNext(it)
            }
            .addToDisposables()
    }
}