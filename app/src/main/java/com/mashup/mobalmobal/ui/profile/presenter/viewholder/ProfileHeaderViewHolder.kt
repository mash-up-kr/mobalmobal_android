package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileHeaderBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfileHeaderViewHolder(
    private val binding: HolderProfileHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileItem.Header) {
        with(binding) {
            tvTitle.text = root.context.getString(item.titleId)
        }
    }
}