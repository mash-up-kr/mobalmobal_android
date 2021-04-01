package com.mashup.mobalmobal.ui.createdonation

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showSoftInput
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedRxImagePicker
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class CreateDonationFragment : BaseViewBindingFragment<FragmentCreateDonationBinding>() {

    companion object {
        private const val START_PICKER_ID = 1
        private const val END_PICKER_ID = 2
        private const val DAY_DIFF = 7
        private const val HOUR_DIFF = 1
        private const val START_DATE_TIME_PICKER = 1
        private const val END_DATE_TIME_PICKER = 2
        private const val TRANSLATION_Y = "translationY"
        private const val PRICE_INPUT_VIEW_INDEX = 2
    }

    @Inject
    lateinit var glideRequest: GlideRequests
    private val createDonationViewModel by activityViewModels<CreateDonationViewModel>()

    private var createDonationProductNameWatcher: TextWatcher? = null
    private var createDonationDescriptionWatcher: TextWatcher? = null
    private var createDonationPriceWatcher: TextWatcher? = null
    private var result = ""


    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationBinding =
        FragmentCreateDonationBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setActionListenerAtCreateDonationInputs()
        setImageFromGallery()
        setInputTextWatcher()
        setDateTimePickerDialog()
        binding.createDonationCompleteButton.setOnClickListener {
            createDonationViewModel.createDonation()
        }
        binding.toolbar.setNavigationOnClickListener {
            navigateToBack()
        }
    }

    override fun onBindViewModels() {
        createDonationViewModel.isCreateDonationEnabled
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.createDonationCompleteButton.isVisible = it
            }
            .addToDisposables()

        createDonationViewModel.completeTrigger
            .observeOnMain()
            .subscribeWithErrorLogger {
                navigateCreateDonationToComplete()
            }
            .addToDisposables()
    }

    private fun navigateCreateDonationToComplete() =
        findNavController().navigate(R.id.create_donation_complete_fragment)

    private fun navigateToBack() =
        findNavController().popBackStack()

    private fun setInputTextWatcher() {
        createDonationProductNameWatcher =
            binding.createDonationNameInput.doOnTextChanged { text, _, _, _ ->
                createDonationViewModel.setCreateDonationProductName(text.toString())
            }

        createDonationDescriptionWatcher =
            binding.createDonationDescriptionInput.doOnTextChanged { text, _, _, _ ->
                createDonationViewModel.setCreateDonationDescription(text.toString())
            }

        createDonationPriceWatcher =
            binding.createDonationPriceInput.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotBlank() && text.toString() != result) {
                    val price = text.toString().replace(",", "")
                    createDonationViewModel.setCreateDonationFundAmount(price.toIntOrNull())
                    result = String.format("%,d", price.toLongOrNull() ?: 0L)
                    binding.createDonationPriceInput.setText(result)
                    binding.createDonationPriceInput.setSelection(result.length)
                }
            }
    }

    private fun setImageFromGallery() {
        binding.createDonationProductImageView.setOnClickListener {
            TedRxImagePicker.with(requireActivity())
                .start()
                .subscribe({ uri ->
                    createDonationViewModel.setCreateDonationUrl(uri)
                    glideRequest.load(uri)
                        .centerCrop()
                        .into(binding.createDonationProductImageView)
                }, { error ->
                    Log.e("CreateDonationShow", "Set image from gallery", error)
                })
        }
    }

    private fun setActionListenerAtCreateDonationInputs() {
        binding.createDonationNameInput.setAnimationListener(
            EditorInfo.IME_ACTION_DONE,
            0,
            binding.createDonationDescriptionTextInputLayout,
            binding.createDonationDescriptionInput
        )

        binding.createDonationDescriptionInput.setAnimationListener(
            EditorInfo.IME_ACTION_DONE,
            1,
            binding.createDonationPriceTextInputLayout,
            binding.createDonationPriceInput
        )

        binding.createDonationPriceInput.setAnimationListener(
            EditorInfo.IME_ACTION_DONE,
            2,
            binding.createDonationStartDateInputLayout,
            binding.createDonationStartDateInput
        )

    }

    private fun EditText.setAnimationListener(
        imeActionId: Int,
        viewIndex: Int,
        nextViewLayout: View,
        nextView: View?
    ) {
        setOnEditorActionListener { v, actionId, event ->
            if (actionId == imeActionId && !binding.createDonationProductImageView.isVisible) {
                goDownAnimation(viewIndex, nextViewLayout, nextView)
            }
            false
        }
    }

    private fun goDownAnimation(
        viewIndex: Int,
        nextViewLayout: View,
        nextView: View?
    ) {
        ObjectAnimator.ofFloat(
            binding.createDonationInputWrapperLayout,
            TRANSLATION_Y,
            binding.createDonationNameTextInputLayout.height.toFloat() * (viewIndex + 1)
        ).apply {
            duration = 300
            start()
        }.doOnEnd {
            nextViewLayout.visibility = View.VISIBLE
            nextView?.requestFocus()
            if (viewIndex < PRICE_INPUT_VIEW_INDEX) {
                (nextView as? EditText)?.showSoftInput()
            }
        }
    }

    private fun setDateTimePickerDialog() {
        binding.createDonationStartDateInput.setOnClickListener {
            showDatePickerDialog(START_PICKER_ID)
        }
        binding.createDonationDueDateInput.setOnClickListener {
            showDatePickerDialog(END_PICKER_ID)
        }
    }

    private fun showDatePickerDialog(viewId: Int) {
        val calendar = Calendar.getInstance()
        if (viewId == END_DATE_TIME_PICKER) {
            calendar.add(Calendar.DAY_OF_MONTH, DAY_DIFF)
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            showTimePickerDialog(viewId, year, month, dayOfMonth)
        }

        DatePickerDialog(requireActivity(), dateSetListener, year, month, day).show()
    }

    private fun showTimePickerDialog(viewId: Int, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        if (viewId == END_DATE_TIME_PICKER) {
            calendar.add(Calendar.DAY_OF_MONTH, DAY_DIFF)
            calendar.add(Calendar.HOUR_OF_DAY, HOUR_DIFF)
        }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance().also {
                it.set(Calendar.YEAR, year)
                it.set(Calendar.MONTH, month)
                it.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                it.set(Calendar.HOUR_OF_DAY, hourOfDay)
                it.set(Calendar.MINUTE, minute)
            }
            val formattedDateTime = getFormattedDateTime(calendar.timeInMillis)

            if (viewId == START_DATE_TIME_PICKER) {
                createDonationViewModel.setCreateDonationStartDate(calendar.timeInMillis)
                binding.createDonationStartDateInput.setText(formattedDateTime)
                if (!binding.createDonationProductImageView.isVisible) {
                    goDownAnimation(
                        3,
                        binding.createDonationDueDateInputLayout,
                        binding.createDonationDueDateInput
                    )
                }

            } else {
                createDonationViewModel.setCreateDonationDueDate(calendar.timeInMillis)
                binding.createDonationDueDateInput.setText(formattedDateTime)

                if (!binding.createDonationProductImageView.isVisible) {
                    goDownAnimation(
                        4,
                        binding.createDonationProductImageView,
                        null
                    )
                }
                binding.createDonationDueDateInput.clearFocus()
            }
        }
        TimePickerDialog(
            activity,
            timeSetListener,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        ).show()
    }

    private fun getFormattedDateTime(milliSeconds: Long) =
        SimpleDateFormat("yyyy-MM-dd HH:mm").format(milliSeconds)
}