package com.mashup.mobalmobal.ui.createdonation

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.dpToPixelsAsFloat
import com.mashup.mobalmobal.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint
import com.funin.base.funinbase.extension.showToast

@AndroidEntryPoint
class CreateDonationFragment : BaseViewBindingFragment<FragmentCreateDonationBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationBinding =
        FragmentCreateDonationBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setActionListenerAtCreateDonationInputs()
    }

    private fun setActionListenerAtCreateDonationInputs() {
        binding.createDonationNameInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO && binding.createDonationDescriptionTextInputLayout.visibility != View.VISIBLE) {
                binding.createDonationDescriptionInput.requestFocus()
                ObjectAnimator.ofFloat(
                    binding.createDonationInputWrapperLayout,
                    "translationY",
                    binding.createDonationNameTextInputLayout.height.toFloat()
                ).apply {
                    duration = 300
                    start()
                }.doOnEnd {
                    binding.createDonationDescriptionTextInputLayout.visibility = View.VISIBLE
                }
            }
            true

            false
        }
        binding.createDonationDescriptionInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO && binding.createDonationPriceTextInputLayout.visibility != View.VISIBLE) {
                binding.createDonationPriceInput.requestFocus()
                ObjectAnimator.ofFloat(
                    binding.createDonationInputWrapperLayout,
                    "translationY",
                    binding.createDonationNameTextInputLayout.height.toFloat() * 2
                ).apply {
                    duration = 300
                    start()
                }.doOnEnd {
                    binding.createDonationPriceTextInputLayout.visibility = View.VISIBLE
                }
                true
            }
            false
        }

        binding.createDonationPriceInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.createDonationProductImageView.visibility != View.VISIBLE) {
                ObjectAnimator.ofFloat(
                    binding.createDonationInputWrapperLayout,
                    "translationY",
                    binding.createDonationNameTextInputLayout.height.toFloat() * 3
                ).apply {
                    duration = 300
                    start()
                }.doOnEnd {
                    binding.createDonationProductImageView.visibility = View.VISIBLE
                }
                true
            }
            false
        }

    }

}

