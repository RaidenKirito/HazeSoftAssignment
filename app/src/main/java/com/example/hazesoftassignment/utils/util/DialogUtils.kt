package com.example.hazesoftassignment.utils.util

import android.content.Context
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.example.hazesoftassignment.R

object DialogUtils {
    fun showAlertDialog(
        context: Context?,
        title: String?,
        message: String?,
        positiveBlock: () -> Unit,
        negativeBlock: () -> Unit,
        isCancelable: Boolean = false,
        showNegativeBtn: Boolean = false,
        positiveBtnName: String? = context?.getString(R.string.ok),
        negativeBtnName: String? = context?.getString(R.string.cancel),
    ) {
        if (context == null) {
            return
        }
        AlertDialog.Builder(context).create().apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(isCancelable)
            setTitle(title)
            setMessage(message)
            setButton(AlertDialog.BUTTON_POSITIVE, positiveBtnName) { dialogInterface, _ ->
                dialogInterface.dismiss()
                positiveBlock()
            }
            if (showNegativeBtn) setButton(
                AlertDialog.BUTTON_NEUTRAL,
                negativeBtnName
            ) { dialogInterface, _ ->
                dialogInterface.dismiss()
                negativeBlock()
            }
            show()
        }
    }
}