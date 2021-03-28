package com.mashup.mobalmobal.ui.createdonation

import android.net.Uri
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class CreateDonationViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _createDonationInputSubject: BehaviorSubject<CreateDonation> =
        BehaviorSubject.createDefault(CreateDonation())

    private val _isCreateDonationInputEnableSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isCreateDonationEnabled: Observable<Boolean> = _isCreateDonationInputEnableSubject

    init {
        _createDonationInputSubject
            .subscribeOnIO()
            .subscribeWithErrorLogger { createDonationInput ->
                _isCreateDonationInputEnableSubject.onNext(createDonationInput.isValidate())
            }
            .addToDisposables()
    }

    fun setCreateDonationProductName(productName: String?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(productName = productName) ?: CreateDonation(
                productName = productName
            )
        )
    }

    fun setCreateDonationDescription(description: String?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(description = description) ?: CreateDonation(
                description = description
            )
        )
    }

    fun setCreateDonationFundAmount(fundAmount: Int?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(fundAmount = fundAmount) ?: CreateDonation(
                fundAmount = fundAmount
            )
        )
    }

    fun setCreateDonationFile(uri: Uri?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(uri = uri) ?: CreateDonation(uri = uri)
        )
    }

    fun setCreateDonationStartDate(startDate: Long?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(startDate = startDate) ?: CreateDonation(
                startDate = startDate
            )
        )
    }

    fun setCreateDonationDueDate(dueDate: Long?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(dueDate = dueDate)
                ?: CreateDonation(dueDate = dueDate)
        )
    }

    fun createDonation() {
        // TODO Implement CreateDonation API
    }

    data class CreateDonation(
        val productName: String? = null,
        val description: String? = null,
        val fundAmount: Int? = null,
        val uri: Uri? = null,
        val startDate: Long? = null,
        val dueDate: Long? = null
    )

    private fun CreateDonation.isValidate(): Boolean =
        !productName.isNullOrBlank() && !description.isNullOrBlank() && uri != null
                && !fundAmount.toString().isNullOrBlank() && !startDate.toString()
            .isNullOrBlank() && !dueDate.toString().isNullOrBlank()


}