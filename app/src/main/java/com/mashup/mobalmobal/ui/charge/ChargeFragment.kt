package com.mashup.mobalmobal.ui.charge

import android.text.TextWatcher
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
import com.mashup.mobalmobal.databinding.FragmentChargeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChargeFragment : BaseViewBindingFragment<FragmentChargeBinding>() {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"
    }

    private val chargeViewModel by viewModels<ChargeViewModel>()

    private var chargePriceWatcher: TextWatcher? = null
    private var formatAmount = ""

    private val dateFormat = SimpleDateFormat(DATE_FORMAT)

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
                val date = Date(System.currentTimeMillis())
                chargeViewModel.setChargedAt(dateFormat.format(date))
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

        chargeViewModel.chargeCompleteTriggerSubject
            .observeOnMain()
            .subscribeWithErrorLogger { navigateChargeComplete() }
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

    private fun navigateDonate() =
        findNavController().popBackStack()

    private fun navigateChargeComplete() =
        findNavController().navigate(R.id.action_donateFragment_to_chargeFragment)

}