package com.example.wearableapp.presentation.base

import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.wearableapp.presentation.extensions.EMPTY_STRING
import com.example.wearableapp.presentation.extensions.gone
import com.example.wearableapp.presentation.extensions.snackbar
import com.example.wearableapp.presentation.extensions.visible
import com.example.wearableapp.presentation.navigator.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity : AppCompatActivity() {

    protected val appNavigator: AppNavigator by inject { parametersOf(this) }

    fun showError(@StringRes errorMessage: Int, rootView: View) = snackbar(errorMessage, rootView)

    fun showError(errorMessage: String?, rootView: View) = snackbar(errorMessage ?: EMPTY_STRING, rootView)

    fun showLoading(progressBar: ProgressBar) = progressBar.visible()

    fun hideLoading(progressBar: ProgressBar) = progressBar.gone()
}