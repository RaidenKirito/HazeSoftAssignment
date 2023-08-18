package com.example.hazesoftassignment.utils.util

import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object GlobalUtils {
    fun buildGson(any: Any?): RequestBody {
        val builder = GsonBuilder()
        val gson = builder.create()
        val json = gson.toJson(any)

        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun buildGson(keyName: String?, fileName: String?, file: File?): MultipartBody.Part? {
        val requestBody = file?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return requestBody?.let {
            MultipartBody.Part.createFormData(keyName ?: "", fileName, it)
        }
    }
}