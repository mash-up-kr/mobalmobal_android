package com.mashup.mobalmobal.data.repository

import com.funin.base.funinbase.extension.requestBodyOf
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import com.mashup.mobalmobal.dto.UserDto
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.onErrorResponse
import com.mashup.mobalmobal.network.service.SignService
import io.reactivex.Single
import javax.inject.Inject

class SignRepository @Inject constructor(
    private val service: SignService,
    private val sharedPreferences: MobalSharedPreferences
) {
    fun login(fireStoreId: String): Single<Boolean> =
        service.login(
            requestBodyOf {
                "fireStoreId" to fireStoreId
            }
        ).doOnSuccess {
            if (it.data != null) {
                sharedPreferences.saveAccessToken(it.data.token.userToken)
            }
        }.map { it.data != null }

    fun signUp(
        provider: String,
        fireStoreId: String,
        nickname: String,
        cellPhone: String? = null,
        email: String? = null
    ): Single<Response<UserDto>> = service.signUp(
        requestBodyOf {
            "provider" to provider
            "fireStoreId" to fireStoreId
            "nickname" to nickname
            cellPhone?.let { "cellPhone" to it }
            email?.let { "email" to it }
        }
    ).onErrorResponse()
}