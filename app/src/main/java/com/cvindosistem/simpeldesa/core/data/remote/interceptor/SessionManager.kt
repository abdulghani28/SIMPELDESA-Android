package com.cvindosistem.simpeldesa.core.data.remote.interceptor

import android.content.Context
import android.content.Intent
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SessionManager(
    private val userPreferences: UserPreferences,
    private val context: Context
) {
    private val _sessionExpired = MutableSharedFlow<Unit>()
    val sessionExpired = _sessionExpired.asSharedFlow()

    @OptIn(DelicateCoroutinesApi::class)
    fun handleUnauthorized() {
        // Clear all user data
        userPreferences.clearAllUserData()

        // Emit session expired event
        GlobalScope.launch {
            _sessionExpired.emit(Unit)
        }

        // Send broadcast untuk update UI
        val intent = Intent("com.cvindosistem.simpeldesa.SESSION_EXPIRED")
        context.sendBroadcast(intent)
    }

    fun isSessionValid(): Boolean {
        return userPreferences.isLoggedIn()
    }
}