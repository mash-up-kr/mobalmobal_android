package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class PostsDto(@SerializedName("posts") val posts: List<PostDto>)