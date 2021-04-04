package com.mashup.mobalmobal.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ViewDonationBinding
import com.mashup.mobalmobal.databinding.ViewDonatorBinding

class DonatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){

    private var binding: ViewDonatorBinding=
        ViewDonatorBinding.inflate(LayoutInflater.from(context), this)


    private val profileImageViews: List<ImageView> by lazy {
        listOf(
            binding.ivProfileFirst,
            binding.ivProfileSecond,
            binding.ivProfileThird
        )
    }

    fun setDonatorProfiles(glideRequests: GlideRequests, profiles: List<String>) {
        if(profiles.isNullOrEmpty()) return

        profileImageViews.forEachIndexed { i, imageView ->
            if(profiles.lastIndex >= i){
                if(profiles.size >= profileImageViews.size
                    && i == 0) {
                    imageView.setImageResource(R.drawable.ic_profile_dot)
                }else {
                    glideRequests.setImageUrl(imageView, profiles[i])
                }
            }
        }
    }

    private fun GlideRequests.setImageUrl(imageView: ImageView, url: String){
        this.load(url)
            .circleCrop()
            .into(imageView)
    }
}