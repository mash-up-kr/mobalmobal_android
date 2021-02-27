package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.HolderProfilePointBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter

class ProfilePointViewHolder(
    private val binding: HolderProfilePointBinding,
    private val listener: ProfileAdapter.ProfileClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            root.setOnClickListener {
                listener?.onProfileItemClick(it, absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: ProfileItem.Point) {
        with(binding) {
            tvPointCount.text = item.point.toString()
        }
    }
}