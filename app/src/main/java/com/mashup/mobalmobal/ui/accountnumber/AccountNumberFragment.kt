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
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.showToast
import com.mashup.base.extensions.clipData
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentAccountNumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountNumberFragment : BaseViewBindingFragment<FragmentAccountNumberBinding>() {

    override fun onSetupViews(view: View) {
        setupAccountNumberText()
    }

    private fun setupAccountNumberText() {
        val accountNumber = getString(R.string.account_number_account_number)
        val accountNumberText =
            getString(R.string.account_number_account_number_text, accountNumber)

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

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountNumberBinding =
        FragmentAccountNumberBinding.inflate(inflater, container, false)
}