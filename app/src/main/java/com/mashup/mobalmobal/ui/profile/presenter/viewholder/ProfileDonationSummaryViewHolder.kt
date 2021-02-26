package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileDonationSummaryBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter

class ProfileDonationSummaryViewHolder(
    private val binding: HolderProfileDonationSummaryBinding,
    private val listener: ProfileAdapter.ProfileClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            root.setOnClickListener {
                listener.onProfileItemClick(it, absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: ProfileItem.DonationSummary) {
        with(binding) {
            tvRequestDonationCount.text = item.requestCount.toString()
            tvDonatedCount.text = item.donatedCount.toString()
            tvClosedDonationCount.text = item.closedCount.toString()
        }
    }
}