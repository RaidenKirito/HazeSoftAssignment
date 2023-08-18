package com.example.hazesoftassignment.utils.extensions

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View?.viewVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.viewInVisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.viewGone() {
    this?.visibility = View.GONE
}

fun View?.disableView() {
    this?.isEnabled = false
}

fun View?.enableView() {
    this?.isEnabled = true
}

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit).isSuccess
    }
    awaitClose { setOnClickListener(null) }
}