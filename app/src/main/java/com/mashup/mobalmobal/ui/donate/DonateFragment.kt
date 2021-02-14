package com.mashup.mobalmobal.ui.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseFragment
import com.mashup.mobalmobal.R

/**
 * 후원하기 페이지
 */
class DonateFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_donate, container, false)
    }

    private fun goDonateToCharge() =
        findNavController().navigate(R.id.action_donateFragment_to_chargeFragment)
}