package com.example.hazesoftassignment.utils.extensions

import com.example.hazesoftassignment.utils.constants.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T : Any> Single<T>.getSubscription(): Single<T> =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.setDelay(): Single<T> =
    this.delay(Constants.apiDelayTime, TimeUnit.MILLISECONDS)