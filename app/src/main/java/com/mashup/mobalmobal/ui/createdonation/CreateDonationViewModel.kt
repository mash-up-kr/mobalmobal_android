package com.mashup.mobalmobal.ui.createdonation

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.SingleSubject

class CreateDonationViewModel: ViewModel() {

    private val _productNameSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val productName get() = _productNameSubject

    private val _titleSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val title get() = _titleSubject

    private val _descriptionSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val description get() = _descriptionSubject

    private val _urlSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val url get() = _urlSubject

    private val _startDateTimeSubject: SingleSubject<String> = SingleSubject.create()
    val startDateTimeSubject get() = _startDateTimeSubject

    private val _endDateTimeSubject: SingleSubject<String> = SingleSubject.create()
    val endDateTimeSubject get() = _endDateTimeSubject

    private val _fundAmountSubject: BehaviorSubject<String> = BehaviorSubject.create()
    val fundAmount get() = _fundAmountSubject
}