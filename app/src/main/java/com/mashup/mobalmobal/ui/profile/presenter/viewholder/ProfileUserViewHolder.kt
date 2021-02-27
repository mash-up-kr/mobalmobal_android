package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.databinding.HolderProfileUserBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter
import javax.inject.Inject

class ProfileUserViewHolder(
    private val binding: HolderProfileUserBinding,
    private val listener: ProfileAdapter.ProfileClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    @Inject
    lateinit var glideRequest: GlideRequests

    init {
        with(binding) {
            tvModify.setOnClickListener {
                listener?.onProfileItemClick(it, absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: ProfileItem.User) {
        with(binding) {
            tvProfileName.text = item.name
            tvProfileNickName.text = item.nickName

            glideRequest.load(item.profileUrl)
                .centerCrop()
                .into(ivProfile)
        }
    }
}