package com.example.hazesoftassignment.feature.shared.model.response.meta

import com.google.gson.annotations.SerializedName

data class Meta(
    val status: Int? = null,
    val msg: String? = null,
    @SerializedName("response_id")
    val responseId: String? = null
)
