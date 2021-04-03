package com.mashup.mobalmobal.ui.createdonation

import android.content.Context
import android.net.Uri
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.data.repository.CreateDonationRepository
import com.mashup.mobalmobal.data.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateDonationViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val createDonationRepository: CreateDonationRepository,
    private val fileRepository: FileRepository
) : BaseViewModel(schedulerProvider) {

    private val _createDonationInputSubject: BehaviorSubject<CreateDonation> =
        BehaviorSubject.createDefault(CreateDonation())

    private val _isCreateDonationInputEnableSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isCreateDonationEnabled: Observable<Boolean> = _isCreateDonationInputEnableSubject

    private val _createDonationErrorIdSubject: PublishSubject<Int> = PublishSubject.create()
    val createDonationErrorIdSubject: Observable<Int> = _createDonationErrorIdSubject

    private val _createDonationErrorMessageSubject: PublishSubject<String> = PublishSubject.create()
    val createDonationErrorMessage: Observable<String> = _createDonationErrorMessageSubject

    private val _navigateToCompleteSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val completeTrigger: Observable<Boolean> = _navigateToCompleteSubject

    private val _createCompleteInputSubject: BehaviorSubject<CreateCompleteDonation> =
        BehaviorSubject.createDefault(CreateCompleteDonation())
    val createCompleteInput = _createCompleteInputSubject.distinctUntilChanged()

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
            _createDonationInputSubject.value?.copy(productName = productName)
                ?: CreateDonation(productName = productName)
        )
    }

    fun setCreateDonationDescription(description: String?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(description = description)
                ?: CreateDonation(description = description)
        )
    }

    fun setCreateDonationFundAmount(fundAmount: Int?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(fundAmount = fundAmount)
                ?: CreateDonation(fundAmount = fundAmount)
        )
    }

    fun setCreateDonationUrl(postImage: Uri?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(postImage = postImage)
                ?: CreateDonation(postImage = postImage)
        )
    }

    fun setCreateDonationStartDate(startDate: Long?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(startDate = startDate)
                ?: CreateDonation(startDate = startDate)
        )
    }

    fun setCreateDonationDueDate(dueDate: Long?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(dueDate = dueDate)
                ?: CreateDonation(dueDate = dueDate)
        )
    }

    fun createDonation(context: Context) {
        _createDonationInputSubject.firstOrError()
            .subscribeOnIO()
            .flatMap { createDonationInput ->
                if (!createDonationInput.description.isNullOrBlank() &&
                    !createDonationInput.productName.isNullOrBlank() &&
                    createDonationInput.fundAmount != null &&
                    createDonationInput.postImage != null &&
                    createDonationInput.startDate != null &&
                    createDonationInput.dueDate != null
                ) {
                    fileRepository.uploadImage(context, createDonationInput.postImage).flatMap {
                        createDonationRepository.createDonation(
                            title = createDonationInput.productName,
                            description = createDonationInput.description,
                            postImage = it,
                            goal = createDonationInput.fundAmount,
                            startedAt = createDonationInput.startDate,
                            endAt = createDonationInput.dueDate
                        )
                    }

                } else {
                    when {
                        createDonationInput.description.isNullOrBlank() ->
                            _createDonationErrorIdSubject.onNext(R.string.void_description)
                        createDonationInput.productName.isNullOrBlank() ->
                            _createDonationErrorIdSubject.onNext(R.string.void_product_name)
                        createDonationInput.postImage == null ->
                            _createDonationErrorIdSubject.onNext(R.string.void_post_image)
                        createDonationInput.fundAmount == null ->
                            _createDonationErrorIdSubject.onNext(R.string.void_fund_amount)
                        createDonationInput.startDate == null ->
                            _createDonationErrorIdSubject.onNext(R.string.void_start_date)
                        else ->
                            _createDonationErrorIdSubject.onNext(R.string.void_due_date)
                    }
                    Single.error(
                        IllegalArgumentException(
                            "create donation failed: createDonationInput: $createDonationInput"
                        )
                    )
                }
            }
            .subscribeWithErrorLogger { response ->
                if (response.data != null) {
                    _createCompleteInputSubject.onNext(
                        CreateCompleteDonation(
                            title = response.data.title,
                            description = response.data.description,
                            goal = response.data.goalPrice,
                            postImage = response.data.postImage,
                            dday = getDDay(response.data.startedAt, response.data.endAt)
                        )
                    )
                    navigateToComplete()
                } else {
                    response.message?.let { _createDonationErrorMessageSubject.onNext(it) }
                }

            }
            .addToDisposables()
    }

    private fun getDDay(startAt: Long, endAt: Long): String {
        val dday = getIgnoredTimeDays(endAt) - getIgnoredTimeDays(startAt)

        return (dday / (24 * 60 * 60 * 1000)).toString()
    }

    private fun getIgnoredTimeDays(time: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun navigateToComplete() {
        _navigateToCompleteSubject.onNext(true)
    }

    data class CreateDonation(
        val productName: String? = null,
        val description: String? = null,
        val fundAmount: Int? = null,
        val postImage: Uri? = null,
        val startDate: Long? = null,
        val dueDate: Long? = null
    )

    data class CreateCompleteDonation(
        val title: String? = null,
        val description: String? = null,
        val goal: Int? = null,
        val postImage: String? = null,
        val dday: String? = null
    )

    private fun CreateDonation.isValidate(): Boolean =
        !productName.isNullOrBlank() && !description.isNullOrBlank() && postImage != null
                && !fundAmount.toString().isNullOrBlank() && !startDate.toString()
            .isNullOrBlank() && !dueDate.toString().isNullOrBlank()


}