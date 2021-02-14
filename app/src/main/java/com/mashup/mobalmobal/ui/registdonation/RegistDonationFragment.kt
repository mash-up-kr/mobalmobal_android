package com.mashup.mobalmobal.ui.registdonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseFragment
import com.mashup.mobalmobal.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 후원 만들기
 */
@AndroidEntryPoint
class RegistDonationFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_regist_donation, container, false)
    }
}