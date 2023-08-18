package com.example.hazesoftassignment.feature.shared.model.customMessage

import android.content.Context
import androidx.annotation.StringRes

sealed class LoadingMessage {
    data class DynamicString(
        val message: String? = null
    ) : LoadingMessage()

    data class ResourceString(
        @StringRes val messageResId: Int?
    ) : LoadingMessage()

    fun asString(context: Context?): String? {
        return when (this) {
            is DynamicString -> message
            is ResourceString -> context?.getString(messageResId ?: 0)
        }
    }
}