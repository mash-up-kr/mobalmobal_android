package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.databinding.HolderProfileDonationBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter
import com.mashup.mobalmobal.util.DateTimeUtils

class ProfileDonationViewHolder(
    private val binding: HolderProfileDonationBinding,
    private val listener: ProfileAdapter.ProfileClickListener?,
    private val glideRequests: GlideRequests
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            root.setOnClickListener {
                listener?.onDonationClicked(bindingAdapterPosition)
            }
        }
    }

    fun bind(item: ProfileItem.Donation) {
        bindTitle(item)

        binding.donation.apply {
            goalPrice = item.goalPrice.toInt()
            dueDate = DateTimeUtils.calculateDecimalDayText(item.startAt, item.endAt) ?: ""
            currentPrice = item.donatedPrice.toInt()
            setDonationImage(glideRequests, item.imageUrl)
        }
    }

    fun bindTitle(item: ProfileItem.Donation) {
        binding.donation.title = item.title
    }
}