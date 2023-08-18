package com.example.hazesoftassignment.feature.shared.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hazesoftassignment.feature.shared.model.customMessage.DialogMessage
import com.example.hazesoftassignment.feature.shared.model.customMessage.DialogMessage.DynamicMessage
import com.example.hazesoftassignment.feature.shared.model.customMessage.DialogMessage.ResourceMessage
import com.example.hazesoftassignment.feature.shared.model.customMessage.LoadingMessage
import com.example.hazesoftassignment.feature.shared.enums.ErrorEnum
import com.example.hazesoftassignment.feature.shared.enums.Status
import com.example.hazesoftassignment.feature.shared.model.response.base.Resource
import com.example.hazesoftassignment.utils.constants.ErrorConstants
import kotlinx.coroutines.launch
import okio.IOException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

    private val _errorResponse = MutableLiveData<DialogMessage>()
    val errorResponse: LiveData<DialogMessage> get() = _errorResponse

    private val _loadingMessage = MutableLiveData<LoadingMessage>()
    val loadingMessage: LiveData<LoadingMessage> get() = _loadingMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _okAction = MutableLiveData<() -> Unit?>()
    val okAction: LiveData<() -> Unit?> get() = _okAction

    private val _errorEnumResponse = MutableLiveData<ErrorEnum?>()
    val errorEnumResponse: LiveData<ErrorEnum?> get() = _errorEnumResponse

    protected fun <T> executeRequest(
        block: suspend () -> Resource<T>?,
        onSuccess: (T?) -> Unit = {},
        onError: (Throwable?) -> Unit = {}
    ) {
        viewModelScope.launch {
            val response = block()
            when (response?.status) {
                Status.SUCCESS -> {
                    onSuccess(response.data)
                }

                Status.ERROR -> {
                    onError(response.throwable)
                }
                else -> {
                    null
                }
            }
        }
    }

    protected fun performActionOnException(throwable: Throwable?, okAction: () -> Unit?) {
        when (throwable) {
            is UnknownHostException -> {
                handleError(ErrorEnum.NoWifi) {
                    okAction()
                }
            }

            is IOException -> {
                handleError(ErrorEnum.NoWifi) {
                    okAction()
                }
            }

            else -> {
                handleError(
                    throwable?.localizedMessage ?: ErrorConstants.defaultErrorMessage
                ) {
                    okAction()
                }
            }
        }
    }

    protected fun showLoading(message: String?) {
        showLoading(LoadingMessage.DynamicString(message))
    }

    protected fun showLoading(message: Int?) {
        showLoading(LoadingMessage.ResourceString(message))
    }

    protected fun hideLoading() {
        _isLoading.postValue(false)
    }

    protected fun handleError(message: String?, okAction: () -> Unit?) {
        _okAction.postValue(okAction)
        _errorResponse.postValue(DynamicMessage(message))
    }

    protected fun handleError(message: Int, okAction: () -> Unit?) {
        _okAction.postValue(okAction)
        _errorResponse.postValue(ResourceMessage(message))
    }

    protected fun handleError(errorEnum: ErrorEnum?, okAction: () -> Unit?) {
        _okAction.postValue(okAction)
        _errorEnumResponse.postValue(errorEnum)
    }

    private fun showLoading(loadingMessage: LoadingMessage) {
        _loadingMessage.postValue(loadingMessage)
        _isLoading.postValue(true)
    }
}