package com.mashup.mobalmobal.ui.sign.signup

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentSignUpBinding
import com.mashup.mobalmobal.ui.sign.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseViewBindingFragment<FragmentSignUpBinding>() {

    private val signUpViewModel by activityViewModels<SignViewModel>()

    private var signUpNicknameWatcher: TextWatcher? = null
    private var signUpEmailWatcher: TextWatcher? = null
    private var signUpCellPhoneWatcher: TextWatcher? = null

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setupPolicyTextView()
        signUpNicknameWatcher = binding.signUpNicknameTextInput.doOnTextChanged { text, _, _, _ ->
            signUpViewModel.setSignUpNickname(text?.toString())
        }
        signUpEmailWatcher = binding.signUpEmailTextInput.doOnTextChanged { text, _, _, _ ->
            signUpViewModel.setSignUpEmail(text?.toString())
        }
        signUpCellPhoneWatcher = binding.signUpCellPhoneTextInput.doOnTextChanged { text, _, _, _ ->
            signUpViewModel.setSignUpCellPhone(text?.toString())
        }
        binding.signUpPolicyCheckBox.setOnCheckedChangeListener { _, isChecked ->
            signUpViewModel.setIsSignUpPolicyAgreeChecked(isChecked)
        }
        binding.signUpSignUpButton.setOnClickListener {
            signUpViewModel.signUp()
        }
    }

    override fun onBindViewModels() {
        signUpViewModel.isSignUpEnabled
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.signUpSignUpButton.isEnabled = it
            }
            .addToDisposables()

        signUpViewModel.signUpErrorMessage
            .observeOnMain()
            .subscribeWithErrorLogger(::showToast)
            .addToDisposables()
    }

    private fun setupPolicyTextView() {
        val usagePolicyText = getString(R.string.sign_up_usage_policy)
        val personalPolicyText = getString(R.string.sign_up_personal_information_policy)
        val policyText = getString(R.string.sign_up_policy, usagePolicyText, personalPolicyText)

        val usagePolicyStartIndex = policyText.indexOf(usagePolicyText)
        val personalPolicyStartIndex = policyText.indexOf(personalPolicyText)

        SpannableStringBuilder(policyText).apply {
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onWebLinkClick(getString(R.string.usage_policy_url))
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        val whiteColor = requireContext().getColor(R.color.white)
                        ds.color = whiteColor
                    }
                },
                usagePolicyStartIndex,
                usagePolicyStartIndex + usagePolicyText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onWebLinkClick(getString(R.string.personal_information_policy_url))
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        val whiteColor = requireContext().getColor(R.color.white)
                        ds.color = whiteColor
                    }
                },
                personalPolicyStartIndex,
                personalPolicyStartIndex + personalPolicyText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }.also {
            binding.signUpPolicyText.movementMethod = LinkMovementMethod.getInstance()
            binding.signUpPolicyText.setText(it, TextView.BufferType.SPANNABLE)
        }
    }

    private fun onWebLinkClick(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
    }
}