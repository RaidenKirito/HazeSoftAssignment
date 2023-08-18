package com.example.hazesoftassignment.feature.shared.model.response.pagination

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("total_count") val totalCount: Int? = null,
    val count: Int? = null,
    val offset: Int? = null
)
