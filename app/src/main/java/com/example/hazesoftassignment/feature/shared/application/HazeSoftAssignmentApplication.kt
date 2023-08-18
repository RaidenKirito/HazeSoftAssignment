package com.example.hazesoftassignment.feature.shared.application

import android.app.Application
import com.example.hazesoftassignment.utils.util.Logger
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.plugins.RxJavaPlugins

@HiltAndroidApp
class HazeSoftAssignmentApplication : Application() {
    private val tag = "HazeSoftAssignment"

    override fun onCreate() {
        super.onCreate()
        handleRxCrash()
    }

    private fun handleRxCrash() {
        RxJavaPlugins.setErrorHandler { t -> Logger.e(tag, t.localizedMessage) }
    }
}