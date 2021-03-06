package com.mashup.mobalmobal.ui.profile.presenter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.constant.Constants
import com.mashup.mobalmobal.databinding.FragmentProfileBinding
import com.mashup.mobalmobal.ui.charge.ChargeViewModel
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>(),
    ProfileAdapter.ProfileClickListener {

    private val profileViewModel: ProfileViewModel by viewModels()
    private val chargeViewModel: ChargeViewModel by viewModels()

    @Inject
    lateinit var profileAdapter: ProfileAdapter

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        with(binding) {
            ivBack.setOnClickListener { navigateToBack() }
            ivEdit.setOnClickListener { navigateToEditProfile() }
            ivSetting.setOnClickListener { navigateProfileToSettings() }
            recyclerViewProfile.adapter = profileAdapter

            recyclerViewProfile.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val position = parent.getChildAdapterPosition(view)

                    when (profileAdapter.currentList[position]) {
                        is ProfileItem.Header -> {
                            context?.let {
                                outRect.top =
                                    it.resources.getDimensionPixelOffset(R.dimen.profile_post_header_top_margin)
                                outRect.bottom =
                                    it.resources.getDimensionPixelOffset(R.dimen.profile_post_item_bottom_margin)
                            }
                        }
                        is ProfileItem.Donation -> {
                            context?.let {
                                outRect.bottom =
                                    it.resources.getDimensionPixelOffset(R.dimen.profile_post_item_bottom_margin)
                            }
                        }
                    }
                }
            })
        }
    }

    override fun onBindViewModels() {
        with(profileViewModel) {
            toastSubject.observeOnMain()
                .subscribeWithErrorLogger(::showToast)
                .addToDisposables()

            itemsSubject.observeOnMain()
                .subscribeWithErrorLogger { profileAdapter.submitList(it) }
                .addToDisposables()

            userNickSubject.observeOnMain()
                .subscribeWithErrorLogger { binding.tvTitle.text = it }
                .addToDisposables()

            backTriggerSubject.observeOnMain()
                .subscribeWithErrorLogger { findNavController().popBackStack() }
                .addToDisposables()
        }

        chargeViewModel.chargeCompleteTriggerSubject
            .observeOnMain()
            .subscribeWithErrorLogger {
                if (it) {
                    profileViewModel.fetchUser()
                }
            }
            .addToDisposables()
    }

    override fun onProfilePointClicked() {
        navigateProfileToCharging()
    }

    override fun onDonationClicked(position: Int) {
        val donation = profileAdapter.currentList[position] as? ProfileItem.Donation
        donation?.let { navigateProfileToDetail(it.postId) }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    private fun navigateToEditProfile() =
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)

    private fun navigateProfileToSettings() =
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)

    private fun navigateProfileToCharging() =
        findNavController().navigate(R.id.action_profileFragment_to_chargeFragment)

    private fun navigateProfileToDetail(postId: Int) = findNavController().navigate(
        R.id.action_profileFragment_to_detailFragment,
        bundleOf(Constants.KEY_POST_ID to postId)
    )
}