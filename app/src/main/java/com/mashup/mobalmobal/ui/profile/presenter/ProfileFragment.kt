package com.mashup.mobalmobal.ui.profile.presenter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferencesImpl
import com.mashup.mobalmobal.databinding.FragmentProfileBinding
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>(),
    ProfileAdapter.ProfileClickListener {

    private val profileViewModel: ProfileViewModel by viewModels()

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

                    when(profileAdapter.currentList[position]){
                        is ProfileItem.Header -> {
                            context?.let {
                                outRect.top = it.resources.getDimensionPixelOffset(R.dimen.profile_post_header_top_margin)
                                outRect.bottom = it.resources.getDimensionPixelOffset(R.dimen.profile_post_item_bottom_margin)
                            }
                        }
                        is ProfileItem.Donation -> {
                            context?.let {
                                outRect.bottom = it.resources.getDimensionPixelOffset(R.dimen.profile_post_item_bottom_margin)
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

            profileSubject.observeOnMain()
                .subscribeWithErrorLogger { profileAdapter.submitList(it) }
                .addToDisposables()

            userNickSubject.observeOnMain()
                .subscribeWithErrorLogger { binding.tvTitle.text = it }
                .addToDisposables()

            backTriggerSubject.observeOnMain()
                .subscribeWithErrorLogger { findNavController().popBackStack() }
                .addToDisposables()
        }
    }

    override fun onProfileItemClick(view: View, position: Int) {

    }

    private fun navigateToBack() =
        findNavController().popBackStack()

    private fun navigateToEditProfile() =
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)

    private fun navigateProfileToSettings() =
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)

}