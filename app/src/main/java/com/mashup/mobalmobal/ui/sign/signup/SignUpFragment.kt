package com.mashup.mobalmobal.ui.sign.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentSignUpBinding

/**
 * 회원가입
 */
class SignUpFragment : BaseViewBindingFragment<FragmentSignUpBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
}