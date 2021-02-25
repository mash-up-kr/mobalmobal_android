package com.mashup.mobalmobal.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentSettingsBinding

/**
 * 설정
 */
class SettingsFragment : BaseViewBindingFragment<FragmentSettingsBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)
}