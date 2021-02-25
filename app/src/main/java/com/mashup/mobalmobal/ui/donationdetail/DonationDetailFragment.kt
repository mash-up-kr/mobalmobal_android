package com.mashup.mobalmobal.ui.donationdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 도네 상세페이지
 */
@AndroidEntryPoint
class DonationDetailFragment : BaseViewBindingFragment<FragmentDetailBinding>() {
    companion object {
        private const val TAG = "DetailFragment"
        private const val KEY_SELECTED_DONATION_ID = "key_selected_donation_id"
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    private fun navigateDetailToDonate() =
        findNavController().navigate(R.id.action_detailFragment_to_donateFragment)
}