package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileRequestDonationBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfileRequestDonationViewHolder(
    private val binding: HolderProfileRequestDonationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileItem.RequestDonation) {
        with(binding) {
            tvTitle.text = item.title
        }
    }
}