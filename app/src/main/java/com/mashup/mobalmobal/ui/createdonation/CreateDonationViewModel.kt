package com.mashup.mobalmobal.ui.createdonation

import android.net.Uri
import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import com.mashup.mobalmobal.data.repository.CreateDonationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateDonationViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val createDonationRepository: CreateDonationRepository
) : BaseViewModel(schedulerProvider) {

    private val _createDonationInputSubject: BehaviorSubject<CreateDonation> =
        BehaviorSubject.createDefault(CreateDonation())

    private val _isCreateDonationInputEnableSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val isCreateDonationEnabled: Observable<Boolean> = _isCreateDonationInputEnableSubject

    private val _createDonationErrorMessageSubject: PublishSubject<String> = PublishSubject.create()
    val createDonationErrorMessage: Observable<String> = _createDonationErrorMessageSubject

    private val _navigateToComplete: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val completeTrigger: Observable<Boolean> = _navigateToComplete

    private val _createCompleteInputSubject: BehaviorSubject<CreateCompleteDonation> =
        BehaviorSubject.createDefault(CreateCompleteDonation())
    val createCompleteInputSubject = _createCompleteInputSubject

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

    fun setCreateDonationUrl(postImage: Uri?) {
        _createDonationInputSubject.onNext(
            _createDonationInputSubject.value?.copy(postImage = postImage) ?: CreateDonation(postImage = postImage)
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
                    createDonationRepository.createDonation(
                        title = createDonationInput.productName,
                        description = createDonationInput.description,
                        post_image = createDonationInput.postImage.toString(),
                        goal = createDonationInput.fundAmount,
                        started_at = createDonationInput.startDate,
                        end_at = createDonationInput.dueDate
                    )
                } else {
                    when {
                        createDonationInput.description.isNullOrBlank() ->
                            _createDonationErrorMessageSubject.onNext("description이 비었습니다.")
                        createDonationInput.productName.isNullOrBlank() ->
                            _createDonationErrorMessageSubject.onNext("productName이 비었습니다.")
                        createDonationInput.postImage == null ->
                            _createDonationErrorMessageSubject.onNext("이미지 uri가 비었습니다.")
                        createDonationInput.fundAmount == null ->
                            _createDonationErrorMessageSubject.onNext("fundAmount가 비었습니다.")
                        createDonationInput.startDate == null ->
                            _createDonationErrorMessageSubject.onNext("startDate가 비었습니다.")
                        else ->
                            _createDonationErrorMessageSubject.onNext("dueDate가 비었습니다.")
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
                    navigateToComplete()

                    _createCompleteInputSubject.onNext(
                        CreateCompleteDonation(
                            title = response.data.title,
                            description = response.data.description,
                            goal = response.data.goalPrice,
                            postImage = response.data.postImage,
                            dday = getDDay(response.data.startedAt, response.data.endAt)
                        )
                    )
                } else {
                    response.message?.let { _createDonationErrorMessageSubject.onNext(it) }
                }

            }
            .addToDisposables()
    }

    private fun getDDay(startAt:Long, endAt: Long): String {
        val dday = getIgnoredTimeDays(endAt) - getIgnoredTimeDays(startAt)

        return (dday / (24*60*60*1000)).toString()
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
        _navigateToComplete.onNext(true)
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