package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfileUserBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfileUserViewHolder(
    val binding: HolderProfileUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProfileItem.User) {
        with(binding) {
            profileName.text = item.name
        }
    }
}