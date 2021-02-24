package com.mashup.mobalmobal.ui.mydonations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentMyDonationsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 나의 도네이션 페이지
 */
@AndroidEntryPoint
class MyDonationsFragment : BaseViewBindingFragment<FragmentMyDonationsBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyDonationsBinding = FragmentMyDonationsBinding.inflate(inflater, container, false)
}