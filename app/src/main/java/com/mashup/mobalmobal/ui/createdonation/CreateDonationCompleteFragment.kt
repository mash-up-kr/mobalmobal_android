package com.mashup.mobalmobal.ui.createdonation

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.funin.base.funinbase.base.BaseFragment
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentCreateDonationCompleteBinding

class CreateDonationCompleteFragment :
    BaseViewBindingFragment<FragmentCreateDonationCompleteBinding>() {
    private val productName: String by lazy {
        arguments?.getString("KEY_DONATION_PRODUCT") ?: "something"
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationCompleteBinding =
        FragmentCreateDonationCompleteBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setupCreatingDonationCompleteTitleView()
    }

    private fun setupCreatingDonationCompleteTitleView() {
        val productText = getString(R.string.create_donation_complete_product, productName)
        val donationCompleteText = getString(R.string.create_donation_complete_title, productText)

        val productTextStartIndex = donationCompleteText.indexOf(productText)
        val productTextEndIndex = productTextStartIndex + productText.length

        val creatingCompleteSpannableText = SpannableStringBuilder(donationCompleteText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.white, null)),
                productTextStartIndex,
                productTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                productTextStartIndex,
                productTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.donationCompleteTitle.setText(
            creatingCompleteSpannableText,
            TextView.BufferType.SPANNABLE
        )


    }
}