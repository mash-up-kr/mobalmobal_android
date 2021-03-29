package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseViewBindingFragment<FragmentMainBinding>(), MainAdapter.OnClickListener {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    @Inject
    lateinit var mainAdapter: MainAdapter

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onSetupViews(view: View) {
        binding.mainRecycler.adapter = mainAdapter
        binding.mainProfile.setOnClickListener {
            // TODO go to ProfileShow
        }
        binding.mainAlarm.setOnClickListener {
            // TODO go to AlarmShow
        }
    }

    override fun onBindViewModels() {
        viewModel.items
            .observeOnMain()
            .subscribeWithErrorLogger {
                mainAdapter.submitList(it)
            }
            .addToDisposables()
    }

    private fun navigateMainToDetail(donationId: Int) =
        MainFragmentDirections.actionMainFragmentToDetailFragment(donationId)
            .also { action -> findNavController().navigate(action) }

    override fun onDonationClick(donationId: Int) {
        navigateMainToDetail(donationId)
    }

    private fun navigateMainToCreateDonation() =
        findNavController().navigate(R.id.create_donation_fragment)

    override fun onAddDonationClick() {
        navigateMainToCreateDonation()
    }
}

