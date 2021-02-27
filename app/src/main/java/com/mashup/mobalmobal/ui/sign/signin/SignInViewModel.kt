package com.mashup.mobalmobal.ui.sign.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    companion object {
        private const val TAG = "SignInViewModel"
    }

    private val auth: FirebaseAuth = Firebase.auth

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
        get() = _showToast

    fun handleGoogleAccessToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInFirebase(credential)
    }

    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        signInFirebase(credential)
    }

    private fun signInFirebase(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    //서버 키 체크 Logic (우리 서비스 가입 여부 확인)
                }
                _showToast.postValue("로그인에 성공하였습니다.")
            } else {
                _showToast.postValue("로그인에 실패하였습니다.")
            }
        }
    }

}