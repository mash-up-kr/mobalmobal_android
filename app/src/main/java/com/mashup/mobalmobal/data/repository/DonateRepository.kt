package com.mashup.mobalmobal.data.repository

import com.funin.base.funinbase.extension.requestBodyOf
import com.mashup.mobalmobal.network.onErrorResponse
import com.mashup.mobalmobal.network.service.DonateService
import io.reactivex.Single
import javax.inject.Inject

class DonateRepository @Inject constructor(
    private val service: DonateService
) {
    fun donate(postId: String, amount: String): Single<Boolean> = service.donate(
        requestBodyOf {
            "post_id" to postId
            "amount" to amount
        }
    ).onErrorResponse().map { it.data != null }
}