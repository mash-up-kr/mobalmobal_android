package com.mashup.mobalmobal.network

import com.mashup.mobalmobal.network.NetworkErrorUtils.toErrorResponse
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport

data class Response<T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null
)

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Single<Response<T>>.onErrorResponse(tag: String? = null): Single<Response<T>> =
    onErrorResumeNext { t -> t.toErrorResponse<T>(tag)?.let { Single.just(it) } ?: Single.error(t) }
