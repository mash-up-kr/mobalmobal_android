package com.mashup.mobalmobal.ui.main

import com.funin.base.funinbase.base.BaseViewModel
import com.funin.base.funinbase.rx.schedulers.BaseSchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    companion object {
        private const val IPHONE =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYiUqUwR_79o3eun43tlCsfnFuq5gWBKp_vY8D21cMjr8HAStse6AQ1pMpMYat-W6NFrV5r3P2&usqp=CAc"
        private const val PS =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6SFwFTyA6OaOHVnsUWAkgJJZYoqlW3_7qgn4nlXAr7NjAGk5sQ1aT4ZInUf70FTBYUGX5omoo&usqp=CAc"
        private const val XBOX =
            "data:image/jpeg;base64,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"
        private const val BALENCIAGA =
            "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMREhMTEhESEBUQFRYRGRIWGRYYGRgYFRoXGBUWFhgYHCggGBolGxgaIjEhJS0rOi4vGB8zODUyNygtLisBCgoKDg0OFxAQGi4dHx0tLS0tNy03LS03NzUtLS0rLSstMi03LS0uLS0tNy0tLS0uLS4rLSs3NS01LS0tKystK//AABEIAQMAwgMBIgACEQEDEQH/xAAcAAEAAgIDAQAAAAAAAAAAAAAABQYEBwECAwj/xABDEAACAQIDBAgDBQYDCAMAAAABAgADEQQSIQUGMUEHEyJRYXGBoTKRsRQjQlLBM3KCkuHwFkPRVGJjg6KywtJEU8P/xAAZAQEBAQEBAQAAAAAAAAAAAAAAAQIDBAX/xAAmEQEAAgIBAwQBBQAAAAAAAAAAARECAyEEQVEFEjFxYRMiscHR/9oADAMBAAIRAxEAPwDeMREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEROtWoFUsxChQWJPAAakmB2ia/3h3jSnRZ8TjqmEr1RmoYTD5WrID+yDU8pLu2mYN2RfKOGY3jZzOaVI1BaoUQuOFmIGYW5a3gZERECH21vDTw1XDUCGetjGZKVMWF8gzOzMxAVQPMm+gMxcVvfRw7hMYr4ItotSqB1Lfu1lJUeTZT4Tw3+3dwWKoitjHeh9jvUTEo5R6V8pJU63N1W2hNwLazTNbf3GI5GH2hiqlH4V+0LRNRhwuewTryub9+sD6EfaVIAHrEOYBhYg3B4EEcbyHXeIrlZwHFZsiU6QLsp4qKjXsCR7gi54z582Pt3EYaq1ShpUZWW5CnKX0NQKeznFzxBtmOk27uhvJQp4HB1cRfrczYYXF2zlmy2AGt1GnnAvmCx/WcUZOYuVII8MpP8AZEzZUMVjhUpU61JmyVip6yiUY0KrDgwbQqxNiDz7rgic2RVcApVcuw5kAE+ItYEXvy04QJOIiAiIgIiICIiAiIgIiICIiAmuemfe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"
    }

    private val _itemsSubject: BehaviorSubject<List<MainAdapterItem>> = BehaviorSubject.create()
    val items: Observable<List<MainAdapterItem>> = _itemsSubject

    init {
        _itemsSubject.onNext(
            listOf(
                MainAdapterItem.MyDonation(
                    listOf(
                        MyDonationAdapterItem.Addition,
                        MyDonationAdapterItem.Donation(
                            donationId = 1,
                            title = "부탁드립니다 선생님",
                            currentPriceText = "13,000"
                        ),
                        MyDonationAdapterItem.Donation(
                            donationId = 2,
                            title = "한푼만 줍쇼!",
                            currentPriceText = "31,000"
                        ),
                        MyDonationAdapterItem.Donation(
                            donationId = 3,
                            title = "동냥좀 해주세요",
                            currentPriceText = "321,000"
                        ),
                        MyDonationAdapterItem.Donation(
                            donationId = 4,
                            title = "나 보고 지나치게?",
                            currentPriceText = "12,000"
                        ),
                        MyDonationAdapterItem.Donation(
                            donationId = 5,
                            title = "매쉬업 화이팅 !!",
                            currentPriceText = "1,000"
                        ),
                        MyDonationAdapterItem.Donation(
                            donationId = 6,
                            title = "모두 행복하세요!!",
                            currentPriceText = "390,000"
                        )
                    )
                ),
                MainAdapterItem.Header("진행중"),
                MainAdapterItem.ProgressDonation(
                    donations = listOf(
                        MainDonationAdapterItem(
                            donationId = 3,
                            dueDateText = "D-12",
                            currentPrice = 153000,
                            goalPrice = 153000,
                            title = "DONE Plz",
                            currentPriceText = "153,000",
                            donationImageUrl = IPHONE
                        ),
                        MainDonationAdapterItem(
                            donationId = 4,
                            dueDateText = "D-16",
                            currentPrice = 3000,
                            goalPrice = 153000,
                            title = "너만 오면 다모아",
                            currentPriceText = "3,000",
                            donationImageUrl = PS
                        ),
                        MainDonationAdapterItem(
                            donationId = 5,
                            dueDateText = "D-19",
                            currentPrice = 24000,
                            goalPrice = 153000,
                            title = "1원이라도 좋습니당",
                            currentPriceText = "24,000",
                            donationImageUrl = XBOX
                        ),
                        MainDonationAdapterItem(
                            donationId = 6,
                            dueDateText = "D-9",
                            currentPrice = 24000,
                            goalPrice = 153000,
                            currentPriceText = "24,000",
                            title = "해커톤 힘드롱",
                            donationImageUrl = BALENCIAGA
                        ),
                        MainDonationAdapterItem(
                            donationId = 7,
                            dueDateText = "D-20",
                            currentPrice = 120000,
                            goalPrice = 153000,
                            currentPriceText = "120,000",
                            title = "선생님들 너무너무 배고픕니당 ㅜ",
                            donationImageUrl = XBOX
                        ),
                        MainDonationAdapterItem(
                            donationId = 8,
                            dueDateText = "D-122",
                            currentPrice = 1000,
                            goalPrice = 153000,
                            currentPriceText = "1,000",
                            title = "팀원들 모두 자구 있어용,,",
                            donationImageUrl = PS
                        ),
                        MainDonationAdapterItem(
                            donationId = 9,
                            dueDateText = "D-37",
                            currentPrice = 153000,
                            goalPrice = 2400,
                            title = "DONE Plz",
                            currentPriceText = "2,400",
                            donationImageUrl = IPHONE
                        ),
                        MainDonationAdapterItem(
                            donationId = 10,
                            dueDateText = "D-92",
                            currentPrice = 3000,
                            goalPrice = 153000,
                            title = "너만 오면 다모아",
                            currentPriceText = "3,000",
                            donationImageUrl = PS
                        ),
                        MainDonationAdapterItem(
                            donationId = 11,
                            dueDateText = "D-94",
                            currentPrice = 24000,
                            goalPrice = 153000,
                            title = "1원이라도 좋습니당",
                            currentPriceText = "24,000",
                            donationImageUrl = XBOX
                        ),
                        MainDonationAdapterItem(
                            donationId = 12,
                            dueDateText = "D-72",
                            currentPrice = 24000,
                            goalPrice = 153000,
                            currentPriceText = "24,000",
                            title = "해커톤 힘드롱",
                            donationImageUrl = BALENCIAGA
                        ),
                        MainDonationAdapterItem(
                            donationId = 13,
                            dueDateText = "D-13",
                            currentPrice = 120000,
                            goalPrice = 153000,
                            currentPriceText = "120,000",
                            title = "선생님들 너무너무 배고픕니당 ㅜ",
                            donationImageUrl = XBOX
                        ),
                        MainDonationAdapterItem(
                            donationId = 14,
                            dueDateText = "D-100",
                            currentPrice = 1000,
                            goalPrice = 153000,
                            currentPriceText = "1,000",
                            title = "팀원들 모두 자구 있어용,,",
                            donationImageUrl = PS
                        )
                    )
                )
            )
        )
    }
}