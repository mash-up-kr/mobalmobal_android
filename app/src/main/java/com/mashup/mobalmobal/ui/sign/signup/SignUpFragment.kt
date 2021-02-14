package com.mashup.mobalmobal.ui.sign.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseFragment
import com.mashup.mobalmobal.R

/**
 * 회원가입
 */
class SignUpFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }
}