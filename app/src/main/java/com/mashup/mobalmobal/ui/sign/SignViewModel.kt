package com.mashup.mobalmobal.ui.sign

import com.facebook.AccessToken
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mashup.base.extensions.combineLatest
import com.mashup.mobalmobal.data.repository.SignRepository
import com.mashup.mobalmobal.data.sharedpreferences.MobalSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val signRepository: SignRepository,
    private val sharedPreferences: MobalSharedPreferences
) : BaseViewModel(schedulerProvider) {

    private val auth: FirebaseAuth = Firebase.auth

    private val _signToastMessageSubject: PublishSubject<String> = PublishSubject.create()
    val signToastMessage: Observable<String> = _signToastMessageSubject

    private val _signStepSubject: BehaviorSubject<SignStep> =
        BehaviorSubject.createDefault(SignStep.SIGN_IN)
    val signStep: Observable<SignStep> = _signStepSubject

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
                    signRepository.login(
                        fireStoreId = it.uid
                    )
                        .flatMap {
                            Single.just(it.data != null)
                        }
                        .subscribeWithErrorLogger {
                            if (it) {
                                navigateToMain()
                            } else {
                                task.result?.user?.uid?.let { uid ->
                                    _signUpInputSubject.onNext(
                                        _signUpInputSubject.value?.copy(
                                            provider = credential.provider,
                                            fireStoreId = uid
                                        ) ?: SignUp(
                                            provider = credential.provider,
                                            fireStoreId = uid
                                        )
                                    )
                                    sharedPreferences.saveAccessToken(uid)
                                    navigateToSignUp()
                                } ?: _signUpErrorMessageSubject.onNext("로그인에 실패했습니다. 다시 시도해 주세요.")
                            }
                        }
                        .addToDisposables()
                }
            }
        }
    }

    private fun navigateToMain() {
        _mainTriggerSubject.onNext(true)
    }

    private fun navigateToSignUp() {
        _signStepSubject.onNext(SignStep.SIGN_UP)
    }

    private val _signUpInputSubject: BehaviorSubject<SignUp> =
        BehaviorSubject.createDefault(SignUp())

    private val _isSignUpPolicyAgreeCheckedSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)

    private val _signUpErrorMessageSubject: PublishSubject<String> = PublishSubject.create()
    val signUpErrorMessage: Observable<String> = _signUpErrorMessageSubject

    private val _isSignUpEnabledSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isSignUpEnabled: Observable<Boolean> = _isSignUpEnabledSubject

    init {
        _signUpInputSubject.combineLatest(_isSignUpPolicyAgreeCheckedSubject)
            .subscribeOnIO()
            .subscribeWithErrorLogger { (signUpInput, isPolicyAgreeChecked) ->
                _isSignUpEnabledSubject.onNext(signUpInput.isValidate() && isPolicyAgreeChecked)
            }
            .addToDisposables()
    }

    fun setSignUpNickname(nickname: String?) {
        _signUpInputSubject.onNext(
            _signUpInputSubject.value?.copy(nickname = nickname) ?: SignUp(nickname = nickname)
        )
    }

    fun setSignUpCellPhone(phone: String?) {
        _signUpInputSubject.onNext(
            _signUpInputSubject.value?.copy(cellPhone = phone) ?: SignUp(cellPhone = phone)
        )
    }

    fun setSignUpEmail(email: String?) {
        _signUpInputSubject.onNext(
            _signUpInputSubject.value?.copy(email = email) ?: SignUp(email = email)
        )
    }

    fun setIsSignUpPolicyAgreeChecked(isChecked: Boolean) {
        _isSignUpPolicyAgreeCheckedSubject.onNext(isChecked)
    }

    private val _mainTriggerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val mainTrigger: Observable<Boolean> = _mainTriggerSubject

    fun signUp() {
        _signUpInputSubject.firstOrError()
            .zipWith(_isSignUpPolicyAgreeCheckedSubject.firstOrError())
            .subscribeOnIO()
            .flatMap { (signUpInput, isPolicyAgreeChecked) ->
                if (!isPolicyAgreeChecked) {
                    _signUpErrorMessageSubject.onNext("개인 정보 수집 및 동의를 체크해 주세요.")
                    Single.error(IllegalArgumentException("sign up failed isPolicyAgreeChecked: $isPolicyAgreeChecked"))
                } else {
                    Single.just(signUpInput)
                }
            }
            .flatMap { signUpInput ->
                if (!signUpInput.provider.isNullOrBlank() &&
                    !signUpInput.fireStoreId.isNullOrBlank() &&
                    !signUpInput.nickname.isNullOrBlank()
                ) {
                    signRepository.signUp(
                        provider = signUpInput.provider,
                        fireStoreId = signUpInput.fireStoreId,
                        nickname = signUpInput.nickname,
                        cellPhone = signUpInput.cellPhone,
                        email = signUpInput.email
                    )
                } else {
                    when {
                        signUpInput.provider.isNullOrBlank() ->
                            _signUpErrorMessageSubject.onNext("provider가 비어있어요")
                        signUpInput.fireStoreId.isNullOrBlank() ->
                            _signUpErrorMessageSubject.onNext("fireStoreId가 비어있어요")
                        else ->
                            _signUpErrorMessageSubject.onNext("nickname이 비어있어요")
                    }
                    Single.error(
                        IllegalArgumentException("signup failed: signUpInput: $signUpInput")
                    )
                }
            }
            .subscribeWithErrorLogger { response ->
                if (response.data != null) {
                    // Main 으로 이동
                } else {
                    response.message?.let { _signUpErrorMessageSubject.onNext(it) }
                }
            }
            .addToDisposables()
    }

    data class SignUp(
        val provider: String? = null,
        val fireStoreId: String? = null,
        val nickname: String? = null,
        val cellPhone: String? = null,
        val email: String? = null
    )

    private fun SignUp.isValidate(): Boolean = !provider.isNullOrBlank() &&
            !fireStoreId.isNullOrBlank() && !nickname.isNullOrBlank() &&
            !cellPhone.isNullOrBlank() && !email.isNullOrBlank()
}