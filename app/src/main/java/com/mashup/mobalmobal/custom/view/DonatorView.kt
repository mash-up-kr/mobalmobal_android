package com.mashup.mobalmobal.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ViewDonatorBinding

class DonatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: ViewDonatorBinding =
        ViewDonatorBinding.inflate(LayoutInflater.from(context), this)

    private val profileImageViews: List<ImageView> by lazy {
        listOf(
            binding.ivProfileFirst,
            binding.ivProfileSecond,
            binding.ivProfileThird
        )
    }

    fun setDonatorProfiles(glideRequests: GlideRequests, profiles: List<String>) {
        if (profiles.isEmpty()) return

        profileImageViews.forEachIndexed { index, imageView ->
            if (profiles.lastIndex >= index) {
                if (profiles.size >= profileImageViews.size && index == 0) {
                    imageView.setImageResource(R.drawable.ic_profile_dot)
                } else {
                    glideRequests.setImageUrl(imageView, profiles[index])
                }
            }
        }
    }

    private fun GlideRequests.setImageUrl(imageView: ImageView, url: String) {
        this.load(url)
            .circleCrop()
            .into(imageView)
    }
}