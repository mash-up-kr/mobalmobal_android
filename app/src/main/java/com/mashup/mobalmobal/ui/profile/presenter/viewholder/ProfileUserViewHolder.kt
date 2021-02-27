package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.base.image.GlideRequest
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.databinding.HolderProfileUserBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import javax.inject.Inject

class ProfileUserViewHolder(
    private val binding: HolderProfileUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    @Inject
    lateinit var requests: GlideRequests

    fun bind(item: ProfileItem.User) {
        with(binding) {
            tvProfileName.text = item.name
            tvProfileNickName.text = item.nickName

            requests.load(item.profileUrl)
                .centerCrop()
                .into(ivProfile)
        }
    }
}