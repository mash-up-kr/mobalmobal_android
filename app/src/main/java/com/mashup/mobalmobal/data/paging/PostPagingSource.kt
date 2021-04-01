package com.mashup.mobalmobal.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.funin.base.funinbase.rx.schedulers.SchedulerProvider
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.network.service.PostService
import io.reactivex.Single

class PostPagingSource(
    private val service: PostService,
    private val order: String = "ASC"
) : RxPagingSource<Int, PostDto>() {

    override fun getRefreshKey(state: PagingState<Int, PostDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null

        anchorPage.prevKey?.let { return it + 1 }
        anchorPage.nextKey?.let { return it - 1 }
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PostDto>> {
        val nextPageNumber = params.key ?: 1
        return service.getPosts(limit = params.loadSize, order = order, after = nextPageNumber)
            .subscribeOn(SchedulerProvider.io())
            .map { response ->
                response.data?.let { LoadResult.Page(it, null, it.lastOrNull()?.userId) }
                    ?: LoadResult.Page(emptyList(), null, null)
            }
    }

}