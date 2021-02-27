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
import com.mashup.mobalmobal.databinding.FragmentCreateDonationInformationBinding
import java.util.*


class CreateDonationInformationFragment :
    BaseViewBindingFragment<FragmentCreateDonationInformationBinding>() {

    companion object {
        private const val DAY_DIFF = 7
        private const val HOUR_DIFF = 1
    }

    private val createDonationViewModel: CreateDonationViewModel by activityViewModels()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationInformationBinding =
        FragmentCreateDonationInformationBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        initializeDateAndTime()
        binding.startDateTime.setOnClickListener {
            val c = Calendar.getInstance()
            showDatePickerDialog(c)
        }
        binding.endDateTime.setOnClickListener {
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_MONTH, DAY_DIFF)
            c.add(Calendar.HOUR_OF_DAY, HOUR_DIFF)
            showDatePickerDialog(c)
        }
    }

    private fun initializeDateAndTime() {
        val c = Calendar.getInstance()
        val startYear = c.get(Calendar.YEAR)
        val startMonth = c.get(Calendar.MONTH) + 1
        val startDay = c.get(Calendar.DAY_OF_MONTH)
        val startHour = c.get(Calendar.HOUR_OF_DAY)

        c.add(Calendar.DAY_OF_MONTH, DAY_DIFF)
        c.add(Calendar.HOUR_OF_DAY, HOUR_DIFF)
        val endYear = c.get(Calendar.YEAR)
        val endMonth = c.get(Calendar.MONTH) + 1
        val endDate = c.get(Calendar.DAY_OF_MONTH)
        val endHour = c.get(Calendar.HOUR_OF_DAY)
        val startDateTime = "$startYear 년 $startMonth 월 $startDay 일 $startHour 시"
        val endDateTime = "$endYear 년 $endMonth 월 $endDate 일 $endHour 시"

        binding.startDateTime.text = startDateTime
        binding.endDateTime.text = endDateTime
    }

    private fun showDatePickerDialog(c: Calendar) {
        DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                var realMonth = monthOfYear + 1
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
                Toast.makeText(context, "$hourOfDay, $minute", Toast.LENGTH_SHORT).show()
            },
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(activity)
        ).show()
    }

}