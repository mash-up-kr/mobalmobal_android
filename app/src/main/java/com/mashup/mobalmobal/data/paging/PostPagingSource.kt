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
) : RxPagingSource<String, PostDto>() {

    override fun getRefreshKey(state: PagingState<String, PostDto>): String? {
        /** TODO ItemKeyed의 기준이 되는 created_at이 현재 String이라 증감연산자로 이전 key를 알아 낼
         * 수 없어서 우선 무조건 null를 반환하도록 합니다.
         *
         *        val anchorPosition = state.anchorPosition ?: return null
         *        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
         *        anchorPage.prevKey?.let { return it + 1 }
         *        anchorPage.nextKey?.let { return it - 1 }
         **/
        return null
    }

    override fun loadSingle(params: LoadParams<String>): Single<LoadResult<String, PostDto>> {
        // TODO 이것도 위와 같은 이유로 증감을 하지 않습니다
        val nextPageNumber = params.key
        return service.getPosts(limit = params.loadSize, order = order, after = nextPageNumber)
            .subscribeOn(SchedulerProvider.io())
            .map { response ->
                response.data?.posts?.let { LoadResult.Page(it, null, it.lastOrNull()?.createdAt) }
                    ?: LoadResult.Page(emptyList(), null, null)
            }
    }

}