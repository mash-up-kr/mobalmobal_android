package com.mashup.mobalmobal.ui.sign.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.funin.base.funinbase.base.BaseFragment
import com.mashup.mobalmobal.R

/**
 * 로그인
 */
class SignInFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    fun navigateSignInToSignUp() =
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
}