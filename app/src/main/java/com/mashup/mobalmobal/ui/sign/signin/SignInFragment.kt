package com.mashup.mobalmobal.ui.sign.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.activityViewModels
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.funin.base.funinbase.extension.rx.observeOnMain
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.extension.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentSignInBinding
import com.mashup.mobalmobal.ui.sign.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseViewBindingFragment<FragmentSignInBinding>() {

    companion object {
        private const val FACEBOOK_PERMISSION_EMAIL = "email"
        private const val FACEBOOK_PERMISSION_PUBLIC_PROFILE = "public_profile"
    }

    private val viewModel by activityViewModels<SignViewModel>()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleLoginLauncher: ActivityResultLauncher<Intent>

    private lateinit var callbackManager: CallbackManager

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)

    override fun onSetupViews(view: View) {
        setGoogleLogin()
        setFacebookLogin()

        binding.signInGoogleLayout.signInGoogle.setOnClickListener { onGoogleClicked() }
        binding.signInFacebookLayout.signInFacebook.setOnClickListener { onFacebookClicked() }
    }

    override fun onBindViewModels() {
        viewModel.signToastMessage
            .observeOnMain()
            .subscribeWithErrorLogger(::showToast)
            .addToDisposables()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleLoginLauncher = registerForActivityResult(GoogleLoginResultContract()) { idToken ->
            idToken?.let { viewModel.handleGoogleAccessToken(it) }
        }
    }

    private fun setFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    result?.accessToken?.let {
                        viewModel.handleFacebookAccessToken(it)
                    }
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                    error?.message?.let { showToast(it) }
                }
            })
    }

    private fun onGoogleClicked() {
        googleLoginLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun onFacebookClicked() {
        LoginManager.getInstance().logInWithReadPermissions(
            this, listOf(
                FACEBOOK_PERMISSION_EMAIL,
                FACEBOOK_PERMISSION_PUBLIC_PROFILE
            )
        )
    }

    private class GoogleLoginResultContract : ActivityResultContract<Intent, String>() {
        override fun createIntent(context: Context, input: Intent): Intent = input

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            return if (resultCode == Activity.RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(intent)
                    .getResult(ApiException::class.java)?.idToken
            } else {
                null
            }
        }
    }
}
