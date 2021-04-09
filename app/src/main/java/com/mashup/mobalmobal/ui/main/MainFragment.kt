package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentMainBinding
import com.mashup.mobalmobal.ui.donationdetail.presenter.DonationDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseViewBindingFragment<FragmentMainBinding>(), MainAdapter.OnClickListener {

    private val viewModel by viewModels<MainViewModel>()
    private val meViewModel by activityViewModels<MeViewModel>()

    @Inject
    lateinit var mainAdapter: MainAdapter

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onSetupViews(view: View) {
        binding.mainRecycler.setup()
        binding.mainProfile.setOnClickListener {
            showToast("TODO Main Profile!!")
        }
        binding.mainSwipeRefreshLayout.setOnRefreshListener {
            mainAdapter.refresh()
            viewModel.refresh()
        }
    }

    private fun RecyclerView.setup() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                if (position == 0 || position == 1) 2 else 1
        }
        layoutManager = gridLayoutManager
        adapter = mainAdapter
    }

    override fun onBindViewModels() {
        viewModel.items
            .observeOnMain()
            .subscribeWithErrorLogger {
                lifecycleScope.launchWhenCreated { mainAdapter.submitData(it) }
            }
            .addToDisposables()

        meViewModel.meName
            .observeOnMain()
            .subscribeWithErrorLogger {
                binding.mainUserName.text = getString(R.string.main_user_name, it)
            }
            .addToDisposables()

        lifecycleScope.launchWhenCreated {
            mainAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh !is LoadState.Loading) {
                    binding.mainSwipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun navigateMainToDetail(donationId: Int) =
        findNavController().navigate(
            R.id.action_mainFragment_to_detailFragment,
            bundleOf(DonationDetailFragment.KEY_SELECTED_POST_ID to donationId)
        )

    override fun onDonationClick(donationId: Int) {
        navigateMainToDetail(donationId)
    }

    private fun navigateMainToCreateDonation() =
        findNavController().navigate(R.id.create_donation_fragment)

    override fun onAddDonationClick() {
        navigateMainToCreateDonation()
    }
}