package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfilePointBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfilePointViewHolder(
    private val binding: HolderProfilePointBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileItem.Point) {
        with(binding) {
            tvPointCount.text = item.point.toString()
        }
    }
}