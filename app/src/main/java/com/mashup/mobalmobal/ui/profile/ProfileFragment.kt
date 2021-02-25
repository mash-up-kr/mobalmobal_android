package com.mashup.mobalmobal.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 프로필 변경
 */
@AndroidEntryPoint
class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    private fun navigateProfileToMyDonations() =
        findNavController().navigate(R.id.action_profileFragment_to_myDonationsFragment)

    private fun navigateProfileToSettings() =
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
}