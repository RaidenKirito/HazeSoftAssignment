package com.example.hazesoftassignment.feature.shared.model.response.gifResponse

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("original") val original: Original? = null
)