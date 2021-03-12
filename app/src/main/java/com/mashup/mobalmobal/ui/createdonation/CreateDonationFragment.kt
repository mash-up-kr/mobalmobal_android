package com.mashup.mobalmobal.ui.createdonation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDonationFragment : BaseViewBindingFragment<FragmentCreateDonationBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationBinding =
        FragmentCreateDonationBinding.inflate(inflater, container, false)

}