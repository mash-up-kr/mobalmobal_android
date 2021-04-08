package com.mashup.mobalmobal.ui.donate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentDonateBinding
import com.mashup.mobalmobal.extensions.showChargeBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateFragment : BaseViewBindingFragment<FragmentDonateBinding>() {
    companion object {
        const val KEY_SELECTED_POST_ID = "key_selected_donation_id"
        private const val INVALID_ID = -1
    }

    private val donateViewModel by viewModels<DonateViewModel>()
    private val postId: Int by lazy {
        arguments?.getInt(KEY_SELECTED_POST_ID) ?: INVALID_ID
    }
    private var formatAmount = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkVerifyPostId()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun checkVerifyPostId() {
        if (postId == INVALID_ID) findNavController().popBackStack()
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateBinding = FragmentDonateBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        bindDonation()

        showChargeBottomSheet(
            "후원",
            onPriceClick = { price ->
                binding.donateAmount.setText(String.format("%,d", price.toLong()))
                true
            },
            onDirectClick = {
                binding.donateAmount.requestFocus()
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
                true
            }
        )
    }

    private fun bindDonation() = with(binding) {
        donateButton.setOnClickListener {
            val amount = formatAmount.replace(",", "")
            donateViewModel.requestDonation(postId, amount)
        }

        donateAmount.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank() && text.toString() != formatAmount) {
                val amount = text.toString().replace(",", "")
                formatAmount = String.format("%,d", amount.toLongOrNull() ?: 0L)
                donateAmount.setText(formatAmount)
                donateAmount.setSelection(formatAmount.length)
                donateButton.isEnabled = true
            } else {
                donateButton.isEnabled = false
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