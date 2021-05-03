package com.mashup.mobalmobal.ui.profile.presenter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.HolderProfileUserBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ProfileUserViewHolder(
    private val binding: HolderProfileUserBinding,
    private val listener: ProfileAdapter.ProfileClickListener?,
    private val glideRequests: GlideRequests
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.tvProfilePoint.setOnClickListener {
            listener?.onProfileItemClick(it, bindingAdapterPosition)
        }
    }

    private val profileUserRadius = binding.root.context.resources
        .getDimensionPixelSize(R.dimen.profile_user_radius)

    fun bind(item: ProfileItem.User) {
        with(binding) {
            tvProfileName.text = item.nickName
            tvProfilePoint.text = root.context.getString(R.string.profile_user_point, item.point)

            glideRequests.load(item.profileUrl)
                .transform(
                    RoundedCornersTransformation(
                        profileUserRadius,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
                .placeholder(R.drawable.img_profile_default)
                .error(R.drawable.img_profile_default)
                .into(ivProfile)
        }
    }
}