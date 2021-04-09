package com.mashup.mobalmobal.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.mashup.base.extensions.forEach
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ViewDonationBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class DonationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: ViewDonationBinding =
        ViewDonationBinding.inflate(LayoutInflater.from(context), this)

    init {
        setBackgroundResource(R.drawable.background_view_donation)
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.DonationView).use {
            it.forEach { attr ->
                when (attr) {
                    R.styleable.DonationView_donationTitle -> title = it.getString(attr)
                    R.styleable.DonationView_donationDueDate -> dueDate = it.getString(attr)
                    R.styleable.DonationView_donationGoalPrice -> goalPrice = it.getInteger(attr, 0)
                    R.styleable.DonationView_donationCurrentPrice ->
                        currentPrice = it.getInteger(attr, 0)
                }
            }
        }
    }

    var title: String?
        get() = binding.donationTitle.text.toString()
        set(value) {
            binding.donationTitle.text = value
        }

    var dueDate: String?
        get() = binding.donationDueDate.text.toString()
        set(value) {
            binding.donationDueDate.text = value
        }

    var goalPrice: Int
        get() = binding.donationProgressbar.max
        set(value) {
            binding.donationProgressbar.max = value
        }

    var currentPrice: Int
        get() = binding.donationProgressbar.progress
        set(value) {
            binding.donationProgressbar.progress = value
            binding.donationCurrentPrice.text = String.format("%,d", value)
        }

    init {
        initializeAttributes(attrs)
    }

    fun setDonationImage(glideRequests: GlideRequests, imageUrl: String?) {
        val donationRadius = resources.getDimensionPixelSize(R.dimen.donation_radius)
        glideRequests.load(imageUrl)
            .transform(
                RoundedCornersTransformation(
                    donationRadius,
                    0,
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
            .into(binding.donationImage)
    }
}