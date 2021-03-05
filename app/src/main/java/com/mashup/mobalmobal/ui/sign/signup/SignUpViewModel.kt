package com.mashup.mobalmobal.ui.sign.signup

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.base.extensions.combineLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _signUpInputSubject: BehaviorSubject<SignUp> =
        BehaviorSubject.createDefault(SignUp())

    private val _isSignUpPolicyAgreeCheckedSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)

    private val _isSignUpErrorMessageSubject: PublishSubject<String> = PublishSubject.create()
    val isSignUpErrorMessage: Observable<String> = _isSignUpErrorMessageSubject

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

    // TODO Implement SignUp
    fun signUp() {
        _signUpInputSubject.firstOrError()
            .zipWith(_isSignUpPolicyAgreeCheckedSubject.firstOrError())
            .subscribeOnIO()
            .subscribeWithErrorLogger { (signUpInput, isPolicyAgreeChecked) ->
                if (!isPolicyAgreeChecked) {
                    _isSignUpErrorMessageSubject.onNext("개인 정보 수집 및 동의를 체크해 주세요.")
                }
            }
            .addToDisposables()
    }

    data class SignUp(
        val nickname: String? = null,
        val cellPhone: String? = null,
        val email: String? = null
    )

    private fun SignUp.isValidate(): Boolean =
        !nickname.isNullOrBlank() && !cellPhone.isNullOrBlank() && !email.isNullOrBlank()
}