package com.example.hazesoftassignment.utils.extensions

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

fun <T : Any> Observable<T>?.throttle(timeInMilliSeconds: Long = 1000L): Observable<T>? =
    this?.throttleFirst(timeInMilliSeconds, TimeUnit.MILLISECONDS)