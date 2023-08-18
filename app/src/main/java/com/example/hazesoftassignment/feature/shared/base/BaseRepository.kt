package com.example.hazesoftassignment.feature.shared.base

import com.example.hazesoftassignment.feature.shared.model.response.error.ErrorResponse
import com.example.hazesoftassignment.network.ApiService
import com.example.hazesoftassignment.utils.constants.ErrorConstants
import com.example.hazesoftassignment.utils.constants.HttpCodeConstants
import com.example.hazesoftassignment.utils.exceptions.UnAuthorizedException
import com.example.hazesoftassignment.utils.util.GlobalUtils
import com.google.gson.Gson
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    protected lateinit var apiService: ApiService

    fun getGlobalUtils(any: Any?): RequestBody = GlobalUtils.buildGson(any)

    fun getGlobalUtils(keyName: String?, fileName: String?, file: File?) =
        GlobalUtils.buildGson(keyName, fileName, file)

    fun getError(responseCode: Int?, error: String?): Throwable {
        return try {
            val gson = Gson()
            val root = gson.fromJson(error, ErrorResponse::class.java)
            val errorMessages = root?.errors

            if (!errorMessages.isNullOrEmpty()) {
                if (responseCode == HttpCodeConstants.unAuthorized) {
                    UnAuthorizedException(
                        errorMessages.getOrNull(0)?.detail ?: ErrorConstants.defaultErrorMessage
                    )
                } else {
                    Throwable(errorMessages[0].detail)
                }

            } else {
                getDefaultError()
            }
        } catch (e: Exception) {
            getDefaultError()
        }
    }

    fun getError(throwable: Throwable) =
        if (throwable is UnknownHostException || throwable is IOException) {
            throwable
        } else {
            getDefaultError()
        }

    private fun getDefaultError() = Throwable(ErrorConstants.defaultErrorMessage)
}