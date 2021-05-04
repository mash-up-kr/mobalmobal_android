package com.mashup.base.extensions

import io.reactivex.Observable
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.BiFunction

@CheckReturnValue
@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any, R : Any> Observable<T>.combineLatest(observable: Observable<R>): Observable<Pair<T, R>> =
    Observable.combineLatest(this, observable, BiFunction(::Pair))