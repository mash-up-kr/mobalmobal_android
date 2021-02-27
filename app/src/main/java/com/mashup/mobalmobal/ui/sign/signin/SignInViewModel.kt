package com.mashup.mobalmobal.ui.sign.signin

import com.facebook.AccessToken
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val auth: FirebaseAuth = Firebase.auth

    private val _toastMessageSubject: PublishSubject<String> = PublishSubject.create()
    val toastMessage get() = _toastMessageSubject

    fun handleGoogleAccessToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInFirebase(credential)
    }

    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        signInFirebase(credential)
    }

    private fun signInFirebase(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    //서버 키 체크 Logic (우리 서비스 가입 여부 확인)
                }
            }
        }
    }

}