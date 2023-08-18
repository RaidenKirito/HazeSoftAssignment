package com.example.hazesoftassignment.feature.shared.base

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.hazesoftassignment.R
import com.example.hazesoftassignment.feature.shared.enums.ErrorEnum
import com.example.hazesoftassignment.utils.helper.ProgressDialogHelper
import com.example.hazesoftassignment.utils.util.DialogUtils

abstract class BaseFragment<BD : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var binding: BD? = null
    private var viewModel: VM? = null
    private var progressDialogHelper: ProgressDialogHelper? = null

    abstract fun getViewModel(): VM
    abstract fun getViewBinding(): BD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        initObservers()
    }

    protected fun showMessageDialog(message: String?) {
        DialogUtils.showAlertDialog(context, "", message, {}, {})
    }

    protected fun showMessageDialog(title: String?, message: String?) {
        DialogUtils.showAlertDialog(context, title, message, {}, {})
    }

    protected fun showMessageDialog(message: String?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(context, "", message, { okAction?.invoke() }, {})
    }

    protected fun showMessageDialog(title: String?, message: String?, okAction: () -> Unit?) {
        DialogUtils.showAlertDialog(context, title, message, { okAction() }, {})
    }

    protected fun showMessageDialog(title: Int?, message: Int?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(context,
            getString(title ?: 0),
            getString(message ?: 0),
            { okAction?.invoke() },
            {})
    }

    protected fun showLoading(message: String?) {
        if (progressDialogHelper == null)
            progressDialogHelper = ProgressDialogHelper(requireContext())
        progressDialogHelper?.showProgress(message)
    }

    protected fun hideLoading() {
        if (progressDialogHelper == null)
            progressDialogHelper = ProgressDialogHelper(requireContext())
        progressDialogHelper?.dismissDialog()
    }

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

    private fun showOrHideLoading(isShow: Boolean?, message: String?) {
        if (isShow == true) {
            showLoading(message)
        } else {
            hideLoading()
        }
    }

    private fun initObservers() {
        with(viewModel) {
            this?.errorResponse?.observe(viewLifecycleOwner) { dialogMessage ->
                showMessageDialog(dialogMessage.asString(context), okAction.value)
            }

            this?.errorEnumResponse?.observe(viewLifecycleOwner) { errorEnum ->
                showMessageDialog(errorEnum, okAction.value)
            }

            this?.isLoading?.observe(viewLifecycleOwner) { isLoading ->
                showOrHideLoading(isLoading, loadingMessage.value?.asString(context))
            }
        }
    }

    protected fun addTextChangeListener(
        edittext: EditText?,
        beforeTextChange: () -> Unit = {},
        onTextChange: () -> Unit={},
        afterTextChange: () -> Unit = {}
    ) {
        edittext?.tag = false
        edittext?.onFocusChangeListener = View.OnFocusChangeListener{_,_ ->
            edittext?.tag = true
        }
        edittext?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(edittext.tag == true) {
                    beforeTextChange()
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(edittext.tag == true){
                    onTextChange()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if(edittext.tag == true){
                    afterTextChange()
                }
            }
        })
    }
}