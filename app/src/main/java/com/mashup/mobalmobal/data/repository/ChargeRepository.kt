package com.mashup.mobalmobal.data.repository

import com.funin.base.funinbase.extension.requestBodyOf
import com.mashup.mobalmobal.data.dto.ChargeDto
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.onErrorResponse
import com.mashup.mobalmobal.network.service.ChargeService
import io.reactivex.Single
import javax.inject.Inject

class ChargeRepository @Inject constructor(
    private val service: ChargeService
) {
    fun charge(amount: String, userName: String, chargedAt: String): Single<Response<ChargeDto>> =
        service.charge(
            requestBodyOf {
                "amount" to amount
                "user_name" to userName
                "charged_at" to chargedAt
            }
        ).onErrorResponse()
}