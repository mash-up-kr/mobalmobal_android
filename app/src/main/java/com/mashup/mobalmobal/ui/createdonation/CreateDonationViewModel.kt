package com.mashup.mobalmobal.ui.createdonation

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.extension.rx.subscribeWithErrorLogger
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateDonationViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val DAY_DIFF = 7
        private const val HOUR_DIFF = 1
    }

    private val _productNameSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val productName get() = _productNameSubject

    private val _titleSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val title get() = _titleSubject

    private val _descriptionSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val description get() = _descriptionSubject

    private val _urlSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val url get() = _urlSubject

    private val _fundAmountSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val fundAmount get() = _fundAmountSubject

    private val startDateCalendar = Calendar.getInstance()

    private val _startDateSubject: BehaviorSubject<Triple<Int, Int, Int>> =
        BehaviorSubject.createDefault(
            createDefaultStartDate()
        )
    val startDateSubject get() = _startDateSubject

    private val _startTimeSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(
        startDateCalendar.get(Calendar.HOUR_OF_DAY)
    )
    val startHourSubject get() = _startTimeSubject

    private val endDateCalendar = Calendar.getInstance().also {
        it.add(Calendar.DAY_OF_MONTH, DAY_DIFF)
        it.add(Calendar.HOUR_OF_DAY, HOUR_DIFF)
    }

    private val _endDateSubject: BehaviorSubject<Triple<Int, Int, Int>> =
        BehaviorSubject.createDefault(
            createDefaultEndDate()
        )
    val endDateSubject get() = _endDateSubject

    private val _endTimeSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(
        endDateCalendar.get(Calendar.HOUR_OF_DAY)
    )
    val endHourSubject get() = _endTimeSubject

    private val _startDateTimeSubject: BehaviorSubject<Long> = BehaviorSubject.createDefault(
        startDateCalendar.time.time
    )
    val startDatetimeSubject get() = _startDateTimeSubject

    private val _endDateTimeSubject: BehaviorSubject<Long> = BehaviorSubject.createDefault(
        endDateCalendar.time.time
    )
    val endDateTimeSubject get() = _endDateTimeSubject

    private val _startDateTimeTextSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val startDateTimeTextSubject get() = _startDateTimeTextSubject

    private val _endDateTimeTextSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val endDateTimeTextSubject get() = _endDateTimeTextSubject

    init {
        _startDateTimeSubject
            .subscribeOnIO()
            .subscribeWithErrorLogger {
                _startDateTimeTextSubject.onNext(
                    getDate(it)
                )
            }
            .addToDisposables()

        _endDateTimeSubject
            .subscribeOnIO()
            .subscribeWithErrorLogger {
                _endDateTimeTextSubject.onNext(
                    getDate(it)
                )
            }
            .addToDisposables()
    }

    private fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 HH시")

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds

        return formatter.format(calendar.time)
    }

    private fun createDefaultStartDate(): Triple<Int, Int, Int> {
        return Triple(
            startDateCalendar.get(Calendar.YEAR),
            startDateCalendar.get(Calendar.MONTH),
            startDateCalendar.get(
                Calendar.DAY_OF_MONTH
            )
        )
    }

    private fun createDefaultEndDate(): Triple<Int, Int, Int> {
        return Triple(
            endDateCalendar.get(Calendar.YEAR),
            endDateCalendar.get(Calendar.MONTH),
            endDateCalendar.get(
                Calendar.DAY_OF_MONTH
            )
        )
    }

    fun createDateTime(year: Int, month: Int, day: Int){

    }

    fun setStartDateTime(triple: Triple<Int, Int, Int>) {
        _startDateSubject.onNext(triple)
    }
}