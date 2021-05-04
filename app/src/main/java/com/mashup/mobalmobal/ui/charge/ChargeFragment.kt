package com.mashup.mobalmobal.ui.charge

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentChargeBinding
import com.mashup.mobalmobal.ui.accountnumber.AccountNumberFragment
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
        with(binding) {
            chargeToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            chargeButton.setOnClickListener {
                chargeViewModel.requestCharge()
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

    override fun onBindViewModels() {
        chargeViewModel.donateTrigger
            .observeOnMain()
            .subscribeWithErrorLogger { navigateDonate() }
            .addToDisposables()

        chargeViewModel.requestedPrice
            .observeOnMain()
            .subscribeWithErrorLogger { navigateAccountNumber(it) }
            .addToDisposables()

        chargeViewModel.chargeErrorMessage
            .observeOnMain()
            .subscribeWithErrorLogger(::showToast)
            .addToDisposables()
    }

    override fun onDestroyView() {
        binding.chargeAmount.removeTextChangedListener(chargePriceWatcher)
        super.onDestroyView()
    }

    private fun navigateDonate() = findNavController().popBackStack()

    private fun navigateAccountNumber(chargePrice: Int) =
        findNavController().navigate(
            R.id.action_charge_fragment_to_account_number_fragment,
            bundleOf(AccountNumberFragment.KEY_CHARGE_PRICE to chargePrice)
        )

}