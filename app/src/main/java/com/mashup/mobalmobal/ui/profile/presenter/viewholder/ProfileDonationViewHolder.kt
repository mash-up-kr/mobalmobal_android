package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.HolderProfileDonationBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter

class ProfileDonationViewHolder (
    private val binding: HolderProfileDonationBinding,
    private val listener: ProfileAdapter.ProfileClickListener?,
    private val glideRequests: GlideRequests
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            root.setOnClickListener {
                listener?.onProfileItemClick(it, absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: ProfileItem.Donation) {
        bindTitle(item)

        binding.donation.apply {
            goalPrice = item.goalPrice.toInt()
            currentPrice = item.donatedPrice.toInt()
            dueDate = item.endAt.toString()
            currentPriceText = context.getString(
                R.string.profile_user_point,
                item.donatedPrice.toInt()
            )
            setDonationImage(glideRequests, item.imageUrl)
        }
    }

    fun bindTitle(item: ProfileItem.Donation) {
        binding.donation.title = item.title
    }
}