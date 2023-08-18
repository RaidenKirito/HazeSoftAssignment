package com.example.hazesoftassignment.feature.shared.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.hazesoftassignment.R
import com.example.hazesoftassignment.feature.shared.enums.ErrorEnum
import com.example.hazesoftassignment.utils.extensions.showToastMessage
import com.example.hazesoftassignment.utils.helper.ProgressDialogHelper
import com.example.hazesoftassignment.utils.util.DialogUtils

abstract class BaseActivity<BD : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected var binding: BD? = null
    private var viewModel: VM? = null
    private var progressDialogHelper: ProgressDialogHelper? = null

    abstract fun getViewModel(): VM
    abstract fun getViewBinding(): BD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        binding = getViewBinding()
        setContentView(binding?.root)
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun showMessageDialog(message: String?) {
        DialogUtils.showAlertDialog(this, "", message, {}, {})
    }

    protected fun showMessageDialog(message: Int?) {
        DialogUtils.showAlertDialog(this, "", getString(message ?: 0), {}, {})
    }

    protected fun showMessageDialog(title: String?, message: String?) {
        DialogUtils.showAlertDialog(this, title, message, {}, {})
    }

    protected fun showMessageDialog(message: String?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(this, "", message, { okAction?.invoke() }, {})
    }

    protected fun showMessageDialog(title: String?, message: String?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(this, title, message, { okAction?.invoke() }, {})
    }

    protected fun showMessageDialog(title: Int?, message: Int?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(this,
            getString(title ?: 0),
            getString(message ?: 0),
            { okAction?.invoke() },
            {})
    }

    protected fun showLoading(message: String?) {
        if(progressDialogHelper==null)
            progressDialogHelper= ProgressDialogHelper(this)
        progressDialogHelper?.showProgress(message)
    }

    protected fun showLoading(message: Int) {
        if(progressDialogHelper==null)
            progressDialogHelper= ProgressDialogHelper(this)
        progressDialogHelper?.showProgress(applicationContext.getString(message))
    }

    protected fun hideLoading() {
        progressDialogHelper?.dismissDialog()
    }

    // you can also create a custom error message view rather if you do not want to show alert dialog
    protected fun showMessageDialog(errorEnum: ErrorEnum?, okAction: (() -> Unit?)?) {
        when (errorEnum) {
            ErrorEnum.NoWifi -> {
                showMessageDialog(
                    R.string.error_no_wifi, R.string.error_no_wifi_description, okAction
                )
            }
            ErrorEnum.DefaultError -> {
                showMessageDialog(R.string.error, R.string.error_default, okAction)
            }
            else -> {
                showMessageDialog(
                    R.string.error, R.string.error_default, okAction
                )
            }
        }
    }

    protected fun showToast(message: String) {
        this.showToastMessage(message)
    }

    private fun showOrHideLoading(isShow: Boolean?, message: String?) {
        if (isShow == true) {
            showLoading(message)
        } else {
            hideLoading()
        }
    }

    private fun initObservers() {
        with(viewModel) {
            this?.errorResponse?.observe(this@BaseActivity) { dialogMessage ->
                showMessageDialog(dialogMessage.asString(this@BaseActivity), okAction.value)
            }

            this?.errorEnumResponse?.observe(this@BaseActivity) { errorEnum ->
                showMessageDialog(errorEnum, okAction.value)
            }

            this?.isLoading?.observe(this@BaseActivity) { isLoading ->
                showOrHideLoading(isLoading, loadingMessage.value?.asString(this@BaseActivity))
            }
        }
    }
}