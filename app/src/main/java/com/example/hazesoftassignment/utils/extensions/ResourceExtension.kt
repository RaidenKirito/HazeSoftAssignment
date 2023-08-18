package com.example.hazesoftassignment.utils.extensions

import com.example.hazesoftassignment.feature.shared.model.response.base.Resource

fun <T> Resource<T>.onSuccess(action: (T?) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        action(response)
    }
    return this
}

fun <T> Resource<T>.onFailure(action: (Throwable?) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        action(error)
    }
    return this
}