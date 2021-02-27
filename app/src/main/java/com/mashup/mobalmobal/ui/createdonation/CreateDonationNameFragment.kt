package com.mashup.mobalmobal.ui.createdonation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.funin.base.funinbase.base.BaseFragment
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentCreateDonationNameBinding

class CreateDonationNameFragment : BaseViewBindingFragment<FragmentCreateDonationNameBinding>() {
    private val createDonationViewModel: CreateDonationViewModel by activityViewModels()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationNameBinding = FragmentCreateDonationNameBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        observeViewModel()
    }

    private fun observeViewModel() {
        createDonationViewModel.productName
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.createDonationNameNameInput.setText(it)
            }
            .addToDisposables()
    }

}