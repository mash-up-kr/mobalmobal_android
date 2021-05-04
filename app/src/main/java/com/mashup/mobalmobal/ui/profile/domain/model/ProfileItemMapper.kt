package com.mashup.mobalmobal.ui.profile.domain.model

import com.mashup.mobalmobal.data.dto.UserDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostDto

fun MyDonateDto.toProfileItems(headerStringId: Int): List<ProfileItem> {
    return if(donates.isNullOrEmpty()) emptyList()
    else listOf(ProfileItem.Header(titleId = headerStringId)) +
            donates.map { donate ->
                ProfileItem.Donation(
                    postId = donate.postId,
                    imageUrl = donate.post.postImage ?: "",
                    title = donate.post.title,
                    description = donate.post.description ?: "",
                    goalPrice = donate.post.goalPrice.toDouble(),
                    donatedPrice = donate.post.currentAmount.toDouble(),
                    startAt = donate.post.startedAt ?: "",
                    endAt = donate.post.endAt ?: ""
                )
            }
}

fun MyPostDto.toProfileItems(headerStringId: Int): List<ProfileItem> {
    return if(posts.isNullOrEmpty()) emptyList()
    else listOf(ProfileItem.Header(titleId = headerStringId)) +
            posts.map { post ->
                ProfileItem.Donation(
                    postId = post.postId,
                    imageUrl = post.postImage ?: "",
                    title = post.title,
                    description = post.description ?: "",
                    goalPrice = post.goalPrice.toDouble(),
                    donatedPrice = post.currentAmount.toDouble(),
                    startAt = post.startedAt ?: "",
                    endAt = post.endAt ?: ""
                )
            }
}

fun UserDto.toProfileItem(): ProfileItem =
    ProfileItem.User(
        userId = id,
        nickName = nickname,
        profileUrl = profileImage,
        point = cash
    )