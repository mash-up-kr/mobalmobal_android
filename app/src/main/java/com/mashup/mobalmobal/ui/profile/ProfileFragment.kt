package com.mashup.mobalmobal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseFragment
import com.mashup.mobalmobal.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 프로필 변경
 */
@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun goProfileToMyDonations() =
        findNavController().navigate(R.id.action_profileFragment_to_myDonationsFragment)

    private fun goProfileToSettings() =
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
}