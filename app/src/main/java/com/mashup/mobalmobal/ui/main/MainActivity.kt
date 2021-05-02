package com.mashup.mobalmobal.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.funin.base.funinbase.base.BaseActivity
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.mashup.mobalmobal.databinding.ActivityMainBinding
import com.mashup.mobalmobal.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private val meViewModel by viewModels<MeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        checkSignedId()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun checkSignedId() {
        meViewModel.isSignedInSingle
            .observeOnMain()
            .subscribeWithErrorLogger { isSignedId ->
                if (!isSignedId) {
                    startSignActivity()
                }
            }
            .addToDisposables()
    }

    private fun startSignActivity() {
        startActivity(Intent(this, SignActivity::class.java))
        finish()
    }
}