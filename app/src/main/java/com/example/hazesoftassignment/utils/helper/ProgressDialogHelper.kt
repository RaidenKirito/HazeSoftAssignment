package com.example.hazesoftassignment.utils.helper

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.hazesoftassignment.databinding.LayoutProgressDialogBinding
import com.example.hazesoftassignment.utils.extensions.viewGone
import com.example.hazesoftassignment.utils.extensions.viewVisible

class ProgressDialogHelper(context: Context?) {

    private val context: Context?
    private lateinit var binding: LayoutProgressDialogBinding

    fun showProgress(message: String?) {
        try {
            setMessage(message)
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setMessage(message: String?) {
        if (message.isNullOrEmpty()) {
            binding.txvMessage.viewGone()
            return
        }
        binding.txvMessage.text = message
        binding.txvMessage.viewVisible()
    }

    private val isShowing get() = dialog?.isShowing

    fun dismissDialog() {
        if (dialog != null && isShowing == true) {
            try {
                dialog?.dismiss()
            } catch (e: IllegalArgumentException) {
                dialog = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun destroyDialog() {
        if (dialog == null) {
            return
        }

        dialog?.dismiss()
        dialog = null
    }

    companion object {
        private var dialog: Dialog? = null
    }

    init {
        this.context = context
        if (this.context != null) {
            binding = LayoutProgressDialogBinding.inflate(LayoutInflater.from(context))
            dialog = Dialog(this.context)
            dialog?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(binding.root)
                setCancelable(false)
            }
        }
    }
}