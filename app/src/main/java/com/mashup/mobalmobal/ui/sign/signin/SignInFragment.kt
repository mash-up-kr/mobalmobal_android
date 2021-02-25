package com.mashup.mobalmobal.ui.sign.signin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentSignInBinding

/**
 * 로그인
 */
class SignInFragment : BaseViewBindingFragment<FragmentSignInBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)

    fun navigateSignInToSignUp() =
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
}