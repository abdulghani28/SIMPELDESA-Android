package com.cvindosistem.simpeldesa.auth.presentation.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LoginUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LogoutUseCase
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var licenseCode by mutableStateOf(userPreferences.getLicenseCode() ?: "")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasLicenseCode = userPreferences.hasLicenseCode()
        private set

    // Validation states
    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var licenseCodeError by mutableStateOf<String?>(null)
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun onEmailChanged(value: String) {
        email = value
        // Clear email error when user starts typing
        if (emailError != null) {
            emailError = null
        }
        // Clear general login error when user modifies input
        if (loginError != null) {
            loginError = null
        }
    }

    fun onPasswordChanged(value: String) {
        password = value
        // Clear password error when user starts typing
        if (passwordError != null) {
            passwordError = null
        }
        // Clear general login error when user modifies input
        if (loginError != null) {
            loginError = null
        }
    }

    fun onLicenseCodeChanged(value: String) {
        licenseCode = value

        if (licenseCodeError != null) {
            licenseCodeError = null
        }

        if (loginError != null) {
            loginError = null
        }
    }

    private fun validateEmail(): Boolean {
        return when {
            email.isEmpty() -> {
                emailError = "Email tidak boleh kosong"
                false
            }
            !isValidEmail(email) -> {
                emailError = "Format email tidak valid"
                false
            }
            else -> {
                emailError = null
                true
            }
        }
    }

    private fun validatePassword(): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Password tidak boleh kosong"
                false
            }
            password.length < 6 -> {
                passwordError = "Password minimal 6 karakter"
                false
            }
            else -> {
                passwordError = null
                true
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // Simple email validation pattern
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex()) || isValidPhoneNumber(email)
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        // Indonesian phone number validation (starts with 08 or +62)
        val phonePattern = "^(\\+62|62|0)[0-9]{9,13}$"
        return phone.matches(phonePattern.toRegex())
    }

    fun login() {
        viewModelScope.launch {
            // Clear previous errors
            loginError = null

            // Validate inputs
            val isEmailValid = validateEmail()
            val isPasswordValid = validatePassword()

            if (!isEmailValid || !isPasswordValid) {
                return@launch
            }

            isLoading = true

            // Use stored license code if available, otherwise use the one entered
            val codeToUse = userPreferences.getLicenseCode() ?: licenseCode

            // Validate license code is provided
            if (codeToUse.isEmpty()) {
                loginError = "Kode lisensi harus diisi"
                _loginEvent.emit(LoginEvent.Error("Kode lisensi harus diisi"))
                isLoading = false
                return@launch
            }

            when (val result = loginUseCase(email, password, codeToUse)) {
                is LoginResult.Success -> {
                    Log.d("LoginViewModel", "Login successful: ${result.token}")

                    // Save auth token
                    userPreferences.saveAuthToken(result.token)

                    // Save license code only after successful login
                    // This ensures wrong license codes are not saved
                    if (licenseCode.isNotEmpty() && !userPreferences.hasLicenseCode()) {
                        userPreferences.saveLicenseCode(licenseCode)
                        hasLicenseCode = true
                    }

                    _loginEvent.emit(LoginEvent.Success)
                }
                is LoginResult.Error -> {
                    Log.e("LoginViewModel", "Login failed: ${result.message}")
                    loginError = result.message
                    _loginEvent.emit(LoginEvent.Error(result.message))
                }
            }

            isLoading = false
        }
    }

    // Function to manually trigger validation (useful for submit button state)
    fun isFormValid(): Boolean {
        return email.isNotEmpty() &&
                password.isNotEmpty() &&
                isValidEmail(email) &&
                password.length >= 6
    }

    sealed class LoginEvent {
        data object Success : LoginEvent()
        data class Error(val message: String) : LoginEvent()
    }
}