package com.mashup.mobalmobal.ui.sign.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 로그인
 */
@AndroidEntryPoint
class SignInFragment : BaseViewBindingFragment<FragmentSignInBinding>() {

    companion object {
        private const val TAG = "SignInFragment"
    }

    private val viewModel by viewModels<SignInViewModel>()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleLoginLauncher: ActivityResultLauncher<Intent>

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGoogleLogin()

        binding.signInGoogle.setOnClickListener {
            onGoogleClicked()
        }

        viewModel.showToast.observe(viewLifecycleOwner, { message ->
            showToastMessage(message)
        })
    }

    private fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleLoginLauncher = registerForActivityResult(GoogleLoginResultContract()) { idToken ->
            idToken?.let {
                viewModel.signInFirebase(it)
            }
        }
    }

    private fun onGoogleClicked() {
        googleLoginLauncher.launch(googleSignInClient.signInIntent)
    }

    fun navigateSignInToSignUp() =
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

    inner class GoogleLoginResultContract : ActivityResultContract<Intent, String>() {
        override fun createIntent(context: Context, input: Intent): Intent = input

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)

            try {
                val account = task.getResult(ApiException::class.java)

                account?.let {
                    return account.idToken!!
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }

            return ""
        }
    }
}
