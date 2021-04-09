package com.mashup.mobalmobal.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.funin.base.funinbase.rx.schedulers.SchedulerProvider
import com.mashup.mobalmobal.network.service.PostService
import com.mashup.mobalmobal.ui.main.MainAdapterItem
import com.mashup.mobalmobal.ui.main.createEmptyMainAdapterItem
import com.mashup.mobalmobal.ui.main.toMainAdapterItems
import io.reactivex.Single

class PostPagingSource(
    private val service: PostService,
    private val order: String = "DESC",
    private val myDonationItem: MainAdapterItem.MyDonation
) : RxPagingSource<Int, MainAdapterItem>() {

    override fun getRefreshKey(state: PagingState<Int, MainAdapterItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        anchorPage.prevKey?.let { return it + 1 }
        anchorPage.nextKey?.let { return it - 1 }
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MainAdapterItem>> {
        val nextPageNumber = params.key?.let { it - 1 }
        return service.getPosts(
            limit = params.loadSize,
            order = order,
            after = nextPageNumber
        ).subscribeOn(SchedulerProvider.io())
            .map { response ->
                response.data?.let {
                    LoadResult.Page(
                        if (nextPageNumber == null) {
                            listOf(
                                myDonationItem,
                                MainAdapterItem.Header("진행중")
                            ) + it.toMainAdapterItems()
                        } else {
                            it.toMainAdapterItems()
                        },
                        null,
                        it.posts.lastOrNull()?.postId
                    )
                } ?: LoadResult.Page(createEmptyMainAdapterItem(), null, null)
            }
    }
}