package com.mashup.mobalmobal.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentProfileBinding
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 프로필 변경
 */
@AndroidEntryPoint
class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>() {
    companion object {
        private const val KEY_USER_ID = "key_user_id"
    }

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    val userId: String by lazy {
        arguments?.getString(KEY_USER_ID)
            ?: throw IllegalArgumentException("ProfileFragment must have user's id")
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onBindViewModels() {
        super.onBindViewModels()
        observeViewModel()
    }

    private fun observeViewModel() = with(profileViewModel) {
        toastSubject.observeOnMain()
            .subscribeWithErrorLogger { context?.showToast(it, Toast.LENGTH_SHORT) }

        profileSubject.observeOnMain()
            .subscribeWithErrorLogger {}
    }

    private fun navigateProfileToMyDonations() =
        findNavController().navigate(R.id.action_profileFragment_to_myDonationsFragment)

    private fun navigateProfileToSettings() =
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
}