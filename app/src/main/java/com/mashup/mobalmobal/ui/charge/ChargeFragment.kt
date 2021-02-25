package com.mashup.mobalmobal.ui.charge

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentChargeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 충전 페이지
 */
@AndroidEntryPoint
class ChargeFragment : BaseViewBindingFragment<FragmentChargeBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChargeBinding = FragmentChargeBinding.inflate(inflater, container, false)
}