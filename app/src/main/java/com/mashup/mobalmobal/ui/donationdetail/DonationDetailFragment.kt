package com.mashup.mobalmobal.ui.donationdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.startGalleryIntent
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
    private val donationDetailViewModel: DonationDetailViewModel by viewModels()
    private val donationId by lazy { arguments?.getString(KEY_SELECTED_DONATION_ID) ?: ""}

    init {
        checkVerifyDonationId()
    }

    private fun checkVerifyDonationId() {
        if(donationId.isEmpty()) findNavController().popBackStack()
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onBindViewModels() {
        donationDetailViewModel.donatinSubject.observeOnMain()
            .subscribeWithErrorLogger{

            }.addToDisposables()
        requestDonationDetail(donationId)
    }

    private fun requestDonationDetail(donationId: String) =
        donationDetailViewModel.requestDonationDetail(donationId)

    private fun navigateDetailToDonate() =
        findNavController().navigate(R.id.action_detailFragment_to_donateFragment)
}