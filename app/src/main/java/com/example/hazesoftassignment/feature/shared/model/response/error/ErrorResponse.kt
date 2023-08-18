package com.example.hazesoftassignment.feature.shared.model.response.error

data class ErrorResponse(
    val errors: List<Error>? = null,
)

data class Error(
    var title: String? = null,
    var detail: String? = null,
    var source: ArrayList<String>? = null,
)