package com.mashup.mobalmobal.ui.charge.complete

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.constant.Constants.KEY_CHARGING_PRICE
import com.mashup.mobalmobal.databinding.FragmentChargeCompleteBinding

class ChargeCompleteFragment : BaseViewBindingFragment<FragmentChargeCompleteBinding>() {

    private val chargingPrice: Int by lazy { arguments?.getInt(KEY_CHARGING_PRICE) ?: 0 }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChargeCompleteBinding =
        FragmentChargeCompleteBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setupChargingPriceTitleView()
        binding.chargeCompleteClose.setOnClickListener {
            // TODO Close ChargeCompleteShow
        }
    }

    private fun setupChargingPriceTitleView() {
        val chargingPriceText = getString(R.string.charge_price, chargingPrice)
        val chargingCompleteText = getString(R.string.charge_complete_title, chargingPriceText)

        val chargingPriceTextStartIndex = chargingCompleteText.indexOf(chargingPriceText)
        val chargingPriceTextEndIndex = chargingPriceTextStartIndex + chargingPriceText.length

        val chargingCompleteSpannableString = SpannableStringBuilder(chargingCompleteText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.white, null)),
                chargingPriceTextStartIndex,
                chargingPriceTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                chargingPriceTextStartIndex,
                chargingPriceTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.chargeCompleteTitle.setText(
            chargingCompleteSpannableString,
            TextView.BufferType.SPANNABLE
        )
    }
}