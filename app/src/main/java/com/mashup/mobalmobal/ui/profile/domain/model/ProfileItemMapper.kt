package com.mashup.mobalmobal.ui.profile.domain.model

import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.ui.profile.data.dto.MyDonateDto
import com.mashup.mobalmobal.ui.profile.data.dto.MyPostDto

fun MyDonateDto.toProfileItems(headerStringId: Int): List<ProfileItem> {
    return mutableListOf<ProfileItem>().also {
        if (donates.isNullOrEmpty()) return emptyList()

        it.add(
            ProfileItem.Header(
                titleId = headerStringId
            )
        )

        it.addAll(
            donates.map { donate ->
                ProfileItem.Donation(
                    donationId = donate.postId.toString(),
                    imageUrl = donate.post.postImage ?: "",
                    title = donate.post.title,
                    description = donate.post.description ?: "",
                    goalPrice = donate.post.goalPrice.toDouble(),
                    donatedPrice = donate.post.currentAmount.toDouble(),
                    startAt = donate.post.startedAt ?: "",
                    endAt = donate.post.endAt ?: ""
                )
            }
        )
    }
}

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