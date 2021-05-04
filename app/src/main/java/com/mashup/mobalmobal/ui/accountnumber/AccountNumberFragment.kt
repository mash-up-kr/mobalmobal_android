package com.mashup.mobalmobal.ui.accountnumber

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.showToast
import com.mashup.base.extensions.clipData
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentAccountNumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountNumberFragment : BaseViewBindingFragment<FragmentAccountNumberBinding>() {

    companion object {
        const val KEY_CHARGE_PRICE = "charge_price"
    }

    private val chargePrice: Int by lazy { arguments?.getInt(KEY_CHARGE_PRICE) ?: 0 }

    override fun onSetupViews(view: View) {
        setupAccountNumberText()
        binding.accountNumberClose.setOnClickListener {
            findNavController().navigate(R.id.action_account_number_fragment_to_profile_fragmnet)
        }
    }

    private fun setupAccountNumberText() {
        val accountNumber = getString(R.string.account_number_account_number)
        val accountNumberText = getString(
            R.string.account_number_account_number_text,
            accountNumber,
            chargePrice.toString()
        )

        val accountNumberStartIndex = accountNumberText.indexOf(accountNumber)
        val accountNumberEndIndex = accountNumberStartIndex + accountNumber.length

        val accountNumberSpannableString = SpannableStringBuilder(accountNumberText).apply {
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val isSuccess = widget.context.clipData(accountNumber)
                        showToast(
                            getString(
                                if (isSuccess) {
                                    R.string.clip_success_text
                                } else {
                                    R.string.clip_failure_text
                                }
                            )
                        )
                    }
                },
                accountNumberStartIndex,
                accountNumberEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                UnderlineSpan::class.java,
                accountNumberStartIndex,
                accountNumberEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.accountNumberAccountNumberText.movementMethod = LinkMovementMethod.getInstance()
        binding.accountNumberAccountNumberText.setText(
            accountNumberSpannableString,
            TextView.BufferType.SPANNABLE
        )
    }

    fun onBackPressed(): Boolean {
        findNavController().navigate(R.id.action_account_number_fragment_to_profile_fragmnet)
        return true
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountNumberBinding =
        FragmentAccountNumberBinding.inflate(inflater, container, false)
}