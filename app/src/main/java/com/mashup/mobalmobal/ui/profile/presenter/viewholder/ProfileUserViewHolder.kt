package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.HolderProfileUserBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter

class ProfileUserViewHolder(
    private val binding: HolderProfileUserBinding,
    private val listener: ProfileAdapter.ProfileClickListener?,
    private val glideRequests: GlideRequests
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileItem.User) {
        with(binding) {
            tvProfileName.text = item.name
            tvProfilePoint.text = root.context.getString(R.string.profile_user_point, item.point)

            glideRequests.load(item.profileUrl)
                .placeholder(R.drawable.img_profile)
                .error(R.drawable.img_profile)
                .centerCrop()
                .into(ivProfile)
        }
    }
}