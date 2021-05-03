package com.mashup.mobalmobal.ui.profile.data.dto

import com.google.gson.annotations.SerializedName
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.dto.PostDto
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

data class MyPostResponseDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: MyPostDto
)

data class MyPostDto(
    @SerializedName("post")
    val posts: List<PostDto>?
)

fun MyPostDto.toProfileItems(headerStringId: Int): List<ProfileItem> {
    return mutableListOf<ProfileItem>().also {
        if (posts.isNullOrEmpty()) return emptyList()

        it.add(
            ProfileItem.Header(
                titleId = headerStringId
            )
        )

        it.addAll(
            posts.map { post ->
                ProfileItem.Donation(
                    donationId = post.postId.toString(),
                    imageUrl = post.postImage ?: "",
                    title = post.title,
                    description = post.description ?: "",
                    goalPrice = post.goalPrice.toDouble(),
                    donatedPrice = post.goalPrice.toDouble(),
                    startAt = post.startedAt ?: "",
                    endAt = post.endAt ?: ""
                )
            }
        )
    }
}

fun Triple<Int, Int, Int>.toSummaryProfileItem() =
    listOfNotNull(
        ProfileItem.Header(
            titleId = R.string.profile_header_donation_summary
        ),

        ProfileItem.DonationSummary(
            requestCount = first,
            donatedCount = second,
            closedCount = third
        )
    )

