package com.example.hazesoftassignment.feature.shared.model.response.base

import com.example.hazesoftassignment.feature.shared.model.response.meta.Meta
import com.example.hazesoftassignment.feature.shared.model.response.pagination.Pagination
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data") val data: T? = null,
    var meta: Meta? = Meta(),
    var pagination: Pagination? = Pagination()
)