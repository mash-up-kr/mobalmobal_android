package com.mashup.mobalmobal.ui.createdonation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.mashup.mobalmobal.databinding.FragmentCreateDonationInformationBinding
import java.text.SimpleDateFormat
import java.util.*


class CreateDonationInformationFragment :
    BaseViewBindingFragment<FragmentCreateDonationInformationBinding>() {

    private val createDonationViewModel: CreateDonationViewModel by activityViewModels()

    companion object {
        private const val DAY_DIFF = 7
        private const val HOUR_DIFF = 1
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationInformationBinding =
        FragmentCreateDonationInformationBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        observeViewModel()
        binding.createDonationInformationStartDateTime.setOnClickListener {
            val c = Calendar.getInstance()
            showDatePickerDialog(c)
        }
        binding.createDonationInformationEndDateTime.setOnClickListener {
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_MONTH, DAY_DIFF)
            c.add(Calendar.HOUR_OF_DAY, HOUR_DIFF)
            showDatePickerDialog(c)
        }
    }

    private fun observeViewModel() {
        createDonationViewModel.description
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.createDonationInformationDescription.setText(it)
            }
            .addToDisposables()

        createDonationViewModel.startDateTimeTextSubject
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.createDonationInformationStartDateTime.text = it
            }
            .addToDisposables()

        createDonationViewModel.endDateTimeTextSubject
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.createDonationInformationEndDateTime.text = it
            }
            .addToDisposables()
    }

    private fun showDatePickerDialog(c: Calendar) {
        DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                var realMonth = monthOfYear + 1
                //TODO: set data
                createDonationViewModel.setStartDateTime(Triple(year, monthOfYear, dayOfMonth))
                Toast.makeText(context, "$year 년 $realMonth 월 $dayOfMonth 일", Toast.LENGTH_SHORT)
                    .show()
                showTimePickerDialog()
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis()
        }.show()
    }

    private fun showTimePickerDialog() {
        val c = Calendar.getInstance()
        TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                //TODO: set data
                Toast.makeText(context, "$hourOfDay, $minute", Toast.LENGTH_SHORT).show()
            },
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(activity)
        ).show()
    }

}


