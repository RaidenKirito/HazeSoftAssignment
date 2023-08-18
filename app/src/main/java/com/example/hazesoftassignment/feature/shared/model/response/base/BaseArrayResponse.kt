package com.example.hazesoftassignment.feature.shared.model.response.base

import com.example.hazesoftassignment.feature.shared.model.response.meta.Meta
import com.example.hazesoftassignment.feature.shared.model.response.pagination.Pagination
import com.google.gson.annotations.SerializedName

class BaseArrayResponse<T>(
    @SerializedName("data") val data: List<T>? = null,
    var meta: Meta? = Meta(),
    var pagination: Pagination? = Pagination()
)

