package com.example.hazesoftassignment.feature.shared.model.response.base

import com.example.hazesoftassignment.feature.shared.enums.Status

sealed class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?) {
    data class Success<T>(val response: T?) : Resource<T>(Status.SUCCESS, response, null)
    data class Error<T>(val error: Throwable?) : Resource<T>(Status.ERROR, null, error)
}