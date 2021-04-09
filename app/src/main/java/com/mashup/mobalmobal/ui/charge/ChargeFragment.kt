package com.mashup.mobalmobal.ui.charge

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.showSoftInput
import com.mashup.mobalmobal.databinding.FragmentChargeBinding
import com.mashup.mobalmobal.extensions.showChargeBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargeFragment : BaseViewBindingFragment<FragmentChargeBinding>() {

    private val chargeViewModel by viewModels<ChargeViewModel>()

    private var chargePriceWatcher: TextWatcher? = null
    private var formatAmount = ""

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChargeBinding = FragmentChargeBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        bindCharge()

        showChargeBottomSheet(
            "충전",
            onPriceClick = { price ->
                binding.chargeAmount.setText(String.format("%,d", price.toLong()))
                true
            },
            onDirectClick = {
                binding.chargeAmount.showSoftInput()
                true
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chargePriceWatcher = null
    }

    private fun bindCharge() = with(binding) {
        chargeToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        chargePriceWatcher = chargeAmount.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank() && text.toString() != formatAmount) {
                val amount = text.toString().replace(",", "")
                formatAmount = String.format("%,d", amount.toLongOrNull() ?: 0L)
                chargeAmount.setText(formatAmount)
                chargeAmount.setSelection(formatAmount.length)
                chargeViewModel.setChargeAmount(formatAmount)
                chargeButton.isEnabled = true
            } else {
                chargeButton.isEnabled = false
            }
        }
    }

}