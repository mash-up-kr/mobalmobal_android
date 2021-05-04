package com.mashup.mobalmobal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.mashup.mobalmobal.data.paging.PostPagingSource
import com.mashup.mobalmobal.network.onErrorResponse
import com.mashup.mobalmobal.network.service.PostService
import com.mashup.mobalmobal.ui.main.MainAdapterItem
import com.mashup.mobalmobal.ui.main.createEmptyMyDonationItem
import com.mashup.mobalmobal.ui.main.toMyDonationItem
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(private val service: PostService) {

    @ExperimentalCoroutinesApi
    fun getPosts(
        coroutineScope: CoroutineScope,
        pageSize: Int,
        myDonationItem: MainAdapterItem.MyDonation
    ): Flowable<PagingData<MainAdapterItem>> {
        val config = PagingConfig(
            pageSize = pageSize
        )
        return Pager(config) {
            PostPagingSource(service = service, myDonationItem = myDonationItem)
        }.flowable.cachedIn(coroutineScope)
    }

    fun getMyPosts(): Single<MainAdapterItem.MyDonation> = service.getMyPosts()
        .onErrorResponse()
        .map { response ->
            response.data?.toMyDonationItem() ?: createEmptyMyDonationItem()
        }
}