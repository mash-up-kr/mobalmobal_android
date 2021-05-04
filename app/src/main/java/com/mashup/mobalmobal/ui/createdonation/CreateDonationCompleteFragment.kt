package com.mashup.mobalmobal.ui.createdonation

import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentCreateDonationCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateDonationCompleteFragment :
    BaseViewBindingFragment<FragmentCreateDonationCompleteBinding>() {
    @Inject
    lateinit var glideRequests: GlideRequests
    private val createDonationViewModel by activityViewModels<CreateDonationViewModel>()
    private var productName: String? = null

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationCompleteBinding =
        FragmentCreateDonationCompleteBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        binding.createDonationCompleteShareButton.setOnClickListener {
            val text = "(손 싹싹) 기부 부탁드립니다."
            startActivity(
                Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, text)
                    .setType("text/plain")
            )
        }

        binding.backButton.setOnClickListener {
            createDonationViewModel.clearData()
            navigateToMain()
        }
    }

    override fun onBindViewModels() {
        createDonationViewModel.createCompleteInput
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.createDonationCompleteCustomView.apply {
                    productName = it.title
                    title = it.description
                    dueDate = it.dday
                    goalPrice = it.goal ?: 0
                    currentPrice = 0
                    setDonationImage(
                        glideRequests,
                        it.postImage
                    )
                }
                setupCreatingDonationCompleteTitleView()
            }
            .addToDisposables()

    }

    private fun setupCreatingDonationCompleteTitleView() {
        val productText = getString(R.string.create_donation_complete_product, productName)
        val donationCompleteText = getString(R.string.create_donation_complete_title, productText)

        val productTextStartIndex = donationCompleteText.indexOf(productText)
        val productTextEndIndex = productTextStartIndex + productText.length

        val creatingCompleteSpannableText = SpannableStringBuilder(donationCompleteText).apply {
            setSpan(
                ForegroundColorSpan(requireContext().resources.getColor(R.color.white, null)),
                productTextStartIndex,
                productTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                productTextStartIndex,
                productTextEndIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.createDonationCompleteTitle.setText(
            creatingCompleteSpannableText,
            TextView.BufferType.SPANNABLE
        )
    }

    private fun navigateToMain() =
        findNavController().navigate(R.id.action_create_donation_complete_fragment_to_mainFragment)
}