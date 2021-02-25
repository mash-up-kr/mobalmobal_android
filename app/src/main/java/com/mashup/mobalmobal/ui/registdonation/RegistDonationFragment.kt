package com.mashup.mobalmobal.ui.registdonation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentRegistDonationBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 후원 만들기
 */
@AndroidEntryPoint
class RegistDonationFragment : BaseViewBindingFragment<FragmentRegistDonationBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistDonationBinding =
        FragmentRegistDonationBinding.inflate(inflater, container, false)
}