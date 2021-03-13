package com.mashup.mobalmobal.ui.donationdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.startGalleryIntent
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentDetailBinding
import com.mashup.mobalmobal.ui.donationdetail.domain.DonationItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 도네 상세페이지
 */
@AndroidEntryPoint
class DonationDetailFragment : BaseViewBindingFragment<FragmentDetailBinding>() {
    companion object {
        private const val TAG = "DetailFragment"
        private const val KEY_SELECTED_DONATION_ID = "key_selected_donation_id"
    }
    @Inject
    lateinit var glideRequests: GlideRequests
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
                bindDonation(it)
            }.addToDisposables()
        requestDonationDetail(donationId)
    }

    fun bindDonation(donation: DonationItem) = with(binding) {
        glideRequests.load(donation.imageUrl)
            .centerCrop()
            .into(ivProduct)

        glideRequests.load(donation.author.profileUrl)
            .centerCrop()
            .into(ivProfile)

        tvDonationTitle.text = getString(R.string.donation_detail_title, donation.author.nickName, donation.productName)
        tvDonationDescription.text = donation.description
        tvGoalPrice.text = getString(R.string.donation_price, donation.goalPrice)
        tvDonationCurrnetPrice.text = getString(R.string.donation_price, donation.donatedPrice)
        tvDonationEndDate.text = donation.dueDate.toString()
    }

    private fun requestDonationDetail(donationId: String) =
        donationDetailViewModel.requestDonationDetail(donationId)

    private fun navigateDetailToDonate() =
        findNavController().navigate(R.id.action_detailFragment_to_donateFragment)
}