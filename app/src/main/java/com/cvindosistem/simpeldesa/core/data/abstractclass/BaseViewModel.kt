package com.cvindosistem.simpeldesa.core.data.abstractclass

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cvindosistem.simpeldesa.core.data.remote.interceptor.SessionManager

abstract class BaseViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    protected fun handleApiError(throwable: Throwable) {
        when {
            throwable.message?.contains("401") == true ||
                    throwable.message?.contains("Unauthorized") == true -> {
                sessionManager.handleUnauthorized()
            }
            else -> {
                // Handle other errors
                Log.e(this::class.simpleName, "API Error: ${throwable.message}")
            }
        }
    }
}