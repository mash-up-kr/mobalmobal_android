package com.mashup.mobalmobal.ui.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.funin.base.funinbase.base.BaseActivity
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ActivitySignBinding
import com.mashup.mobalmobal.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity() {

    private lateinit var binding: ActivitySignBinding

    private val signViewModel by viewModels<SignViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViewModels()
    }

    private fun bindViewModels() {
        signViewModel.signStep
            .observeOnMain()
            .subscribeWithErrorLogger(::navigate)
            .addToDisposables()

        signViewModel.mainTrigger
            .observeOnMain()
            .subscribeWithErrorLogger { navigateToMain() }
            .addToDisposables()

        signViewModel.signUpErrorMessage
            .observeOnMain()
            .subscribeWithErrorLogger(::showToast)
            .addToDisposables()

        signViewModel.signInErrorMessage
            .observeOnMain()
            .subscribeWithErrorLogger(::showToast)
            .addToDisposables()
    }

    private fun navigate(signStep: SignStep) {
        when (signStep) {
            SignStep.SIGN_IN -> findNavController(R.id.fragment_nav_host).navigate(R.id.signInFragment)
            SignStep.SIGN_UP -> findNavController(R.id.fragment_nav_host).navigate(R.id.signUpFragment)
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}