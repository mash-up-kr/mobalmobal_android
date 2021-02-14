package com.mashup.mobalmobal.ui.mydonations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseFragment
import com.mashup.mobalmobal.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 나의 도네이션 페이지
 */
@AndroidEntryPoint
class MyDonationsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_donations, container, false)
    }
}