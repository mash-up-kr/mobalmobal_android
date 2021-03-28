package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseViewBindingFragment<FragmentMainBinding>() {
    companion object {
        private const val TAG = "MainFragment"
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    private fun navigateMainToDetail(donationId: Int) =
        MainFragmentDirections.actionMainFragmentToDetailFragment(donationId)
            .also { action -> findNavController().navigate(action) }

    private fun navigateMainToProfile() =
        findNavController().navigate(R.id.action_mainFragment_to_profileFragment)

    private fun navigateMainToCreateDonation() =
        findNavController().navigate(R.id.create_donation_fragment)
}