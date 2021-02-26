package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mashup.mobalmobal.databinding.HolderProfileDonatedBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfileDonatedViewHolder(
    private val binding: HolderProfileDonatedBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileItem.Donated) {
        with(binding) {
            tvTitle.text = item.title
        }
    }
}