package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
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

    fun bind(item: ProfileItem.User) {
        with(binding) {
            tvProfileName.text = item.name
            tvProfilePoint.text = root.context.getString(R.string.profile_user_point, item.point)

            glideRequest.load(item.profileUrl)
                .centerCrop()
                .into(ivProfile)
        }
    }
}