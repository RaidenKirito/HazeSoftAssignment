package com.example.hazesoftassignment.feature.shared.model.customMessage

import android.content.Context
import androidx.annotation.StringRes

sealed class DialogMessage {
    data class DynamicMessage(val message: String?) : DialogMessage()
    data class ResourceMessage(@StringRes val resId: Int?) : DialogMessage()

    fun asString(context: Context?): String? {
        return when (this) {
            is DynamicMessage -> message
            is ResourceMessage -> context?.getString(resId ?: 0)
        }
    }
}