package com.mashup.mobalmobal.ui.editprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseViewBindingFragment<FragmentEditProfileBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false)
}