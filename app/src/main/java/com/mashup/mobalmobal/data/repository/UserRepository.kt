package com.mashup.mobalmobal.data.repository

import com.mashup.mobalmobal.data.dto.UserDto
import com.mashup.mobalmobal.network.Response
import com.mashup.mobalmobal.network.onErrorResponse
import com.mashup.mobalmobal.network.service.UserService
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserService) {

    fun fetchUser(): Single<Response<UserDto>> = service.fetchUser().onErrorResponse()
        .map {
            Response(
                code = it.code,
                data = it.data?.user,
                message = it.message
            )
        }
}