package com.mashup.mobalmobal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.data.paging.PostPagingSource
import com.mashup.mobalmobal.network.onErrorResponse
import com.mashup.mobalmobal.network.service.PostService
import com.mashup.mobalmobal.ui.main.MainAdapterItem
import com.mashup.mobalmobal.ui.main.createEmptyMainAdapterItem
import com.mashup.mobalmobal.ui.main.toMainAdapterItem
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
        initialLoadSize: Int,
        pageSize: Int
    ): Flowable<PagingData<PostDto>> {
        val config = PagingConfig(
            initialLoadSize = initialLoadSize,
            pageSize = pageSize
        )
        return Pager(config) { PostPagingSource(service) }.flowable.cachedIn(coroutineScope)
    }

    fun getMyPosts(): Single<MainAdapterItem.MyDonation> = service.getMyPosts()
        .onErrorResponse()
        .map { response ->
            response.data?.toMainAdapterItem() ?: createEmptyMainAdapterItem()
        }
}