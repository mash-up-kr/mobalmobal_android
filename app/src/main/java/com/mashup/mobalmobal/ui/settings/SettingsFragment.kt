package com.mashup.mobalmobal.ui.settings

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentSettingsBinding

class SettingsFragment : BaseViewBindingFragment<FragmentSettingsBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        binding.settingsOpenSource.setOnClickListener {
            onWebLinkClick(getString(R.string.open_source_url))
        }
        binding.settingsTermsOfService.setOnClickListener {
            onWebLinkClick(getString(R.string.personal_information_policy_url))
        }
        binding.settingsInquiry.setOnClickListener {
            onInquiryClick()
        }
    }

    private fun onWebLinkClick(url: String) {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun onInquiryClick() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, getString(R.string.inquiry_email))
        }
        intent.resolveActivity(requireActivity().packageManager)?.let { startActivity(intent) }
    }
}