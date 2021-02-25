package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileDonationSummaryBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfileDonationSummaryViewHolder(
    private val binding: HolderProfileDonationSummaryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProfileItem.DonationSummary) {
        with(binding) {
        }
    }
}