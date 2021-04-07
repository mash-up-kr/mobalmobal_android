package com.mashup.mobalmobal.ui.donate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentDonateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateFragment : BaseViewBindingFragment<FragmentDonateBinding>() {
    private val donateViewModel by viewModels<DonateViewModel>()
    private var formatAmount = ""

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateBinding = FragmentDonateBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)

        binding.donateButton.setOnClickListener {
            val amount = formatAmount.replace(",", "")
            donateViewModel.requestDonation(amount)
        }

        binding.donateAmount.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank() && text.toString() != formatAmount) {
                val amount = text.toString().replace(",", "")
                formatAmount = String.format("%,d", amount.toLongOrNull() ?: 0L)
                binding.donateAmount.setText(formatAmount)
                binding.donateAmount.setSelection(formatAmount.length)
                binding.donateButton.isEnabled = true
            } else {
                binding.donateButton.isEnabled = false
            }
        }
    }

    override fun onBindViewModels() {
        donateViewModel.donationDetailTrigger
            .observeOnMain()
            .subscribeWithErrorLogger { navigateDonationDetail() }
            .addToDisposables()

        donateViewModel.donateErrorMessage
            .observeOnMain()
            .subscribeWithErrorLogger(::showToast)
            .addToDisposables()
    }

    private fun navigateDonateToCharge() =
        findNavController().navigate(R.id.action_donateFragment_to_chargeFragment)

    private fun navigateDonationDetail() =
        findNavController().popBackStack()
}