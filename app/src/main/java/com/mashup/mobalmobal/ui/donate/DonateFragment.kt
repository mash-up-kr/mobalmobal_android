package com.mashup.mobalmobal.ui.donate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentDonateBinding

class DonateFragment : BaseViewBindingFragment<FragmentDonateBinding>() {
    private val donateViewModel: DonateViewModel by viewModels()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateBinding = FragmentDonateBinding.inflate(inflater, container, false)
    
    private fun navigateDonateToCharge() =
        findNavController().navigate(R.id.action_donateFragment_to_chargeFragment)
}