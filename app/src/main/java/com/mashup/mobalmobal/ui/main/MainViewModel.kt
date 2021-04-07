package com.mashup.mobalmobal.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val postRepository: PostRepository
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val PAGE_SIZE = 30
    }

    private val _myDonationSubject: BehaviorSubject<MainAdapterItem.MyDonation> =
        BehaviorSubject.createDefault(createEmptyMyDonationItem())

    init {
        fetchMyDonation()
    }

    private val _itemsSubject: BehaviorSubject<PagingData<MainAdapterItem>> =
        BehaviorSubject.create()
    val items: Observable<PagingData<MainAdapterItem>> = _itemsSubject.distinctUntilChanged()

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        _myDonationSubject.toFlowable(BackpressureStrategy.LATEST)
            .switchMap {
                postRepository.getPosts(
                    coroutineScope = viewModelScope,
                    pageSize = PAGE_SIZE,
                    myDonationItem = it
                )
            }
            .subscribeOnIO()
            .subscribeWithErrorLogger { _itemsSubject.onNext(it) }
            .addToDisposables()
    }

    // TODO Add refreshing donation Items
    fun refresh() {
        fetchMyDonation()
    }

    private fun fetchMyDonation() {
        postRepository.getMyPosts()
            .subscribeOnIO()
            .subscribeWithErrorLogger { _myDonationSubject.onNext(it) }
            .addToDisposables()
    }
}