package com.mashup.mobalmobal.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
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

    var goalPrice: Int? = null
        set(value) {
            field = value
            updateProgressbarSize()
        }

    var currentPrice: Int? = null
        set(value) {
            field = value
            binding.donationCurrentPrice.text = String.format("%,d", value ?: 0)
            updateProgressbarSize()
        }

    private fun updateProgressbarSize() {
        val progress = currentPrice ?: return
        val max = goalPrice ?: return
        val ratio = progress.toFloat() / max.toFloat()
        val horizontalMargin =
            resources.getDimensionPixelSize(R.dimen.donation_progress_bar_shadow_horizontal_margin)
        val actualRatio = if (ratio > 1.0f) 1.0f else ratio
        doOnPreDraw {
            binding.donationProgressbar.updateLayoutParams<LayoutParams> {
                val actualMeasuredWidth = it.measuredWidth + horizontalMargin
                val actualWidth = horizontalMargin + actualMeasuredWidth * actualRatio
                width = actualWidth.toInt()
            }
        }
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