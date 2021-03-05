package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileDonationBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter

class ProfileDonationViewHolder (
    private val binding: HolderProfileDonationBinding,
    private val listener: ProfileAdapter.ProfileClickListener?
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
    }

    fun bindTitle(item: ProfileItem.Donation) {
        binding.tvTitle.text = item.title
    }
}