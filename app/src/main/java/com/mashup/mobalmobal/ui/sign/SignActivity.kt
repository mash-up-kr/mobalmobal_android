package com.mashup.mobalmobal.ui.sign

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.funin.base.funinbase.base.BaseActivity
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity() {

    private lateinit var binding: ActivitySignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setStatusBarColor()
    }

    private fun setStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }

}