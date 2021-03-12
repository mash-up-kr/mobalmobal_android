package com.mashup.mobalmobal.ui.createdonation

import android.animation.ObjectAnimator
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.animation.doOnEnd
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedRxImagePicker
import javax.inject.Inject

@AndroidEntryPoint
class CreateDonationFragment : BaseViewBindingFragment<FragmentCreateDonationBinding>() {
    @Inject
    lateinit var glideRequest: GlideRequests

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationBinding =
        FragmentCreateDonationBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setActionListenerAtCreateDonationInputs()
        setImageFromGallery()
        setEditTextCommaSeparateListener()
    }

    private fun setImageFromGallery() {
        binding.createDonationProductImageView.setOnClickListener {
            TedRxImagePicker.with(requireActivity())
                .start()
                .subscribe({ uri ->
                    glideRequest.load(uri)
                        .centerCrop()
                        .into(binding.createDonationProductImageView)
                }, Throwable::printStackTrace)
        }
    }

    private fun setEditTextCommaSeparateListener() {
        binding.createDonationPriceInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString()) && s.toString() != "") {
                    binding.createDonationPriceInput.also {
                        val number = String.format("%,d", s.toString().replace(",", "").toLong())
                        it.removeTextChangedListener(this)
                        it.setText(number)
                        it.addTextChangedListener(this)
                        it.setSelection(number.length)
                    }
                }
            }
        })
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

