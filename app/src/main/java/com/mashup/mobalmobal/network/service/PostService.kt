package com.mashup.mobalmobal.network.service

import com.mashup.mobalmobal.data.dto.PostsDto
import com.mashup.mobalmobal.network.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    fun getPosts(
        @Query("item") after: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("order") order: String? = "ASC"
    ): Single<Response<PostsDto>>

    @GET("posts/my")
    fun getMyPosts(): Single<Response<PostsDto>>
}