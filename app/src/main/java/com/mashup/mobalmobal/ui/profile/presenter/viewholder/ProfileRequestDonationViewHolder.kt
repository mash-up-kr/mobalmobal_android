package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileRequestDonationBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter

class ProfileRequestDonationViewHolder(
    private val binding: HolderProfileRequestDonationBinding,
    private val listener: ProfileAdapter.ProfileClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            root.setOnClickListener {
                listener?.onProfileItemClick(it, absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: ProfileItem.RequestDonation) {
        bindTitle(item)
    }

    fun bindTitle(item: ProfileItem.RequestDonation) {
        binding.tvTitle.text = item.title
    }
}