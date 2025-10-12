package com.cvindosistem.simpeldesa.auth.presentation.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LoginUseCase
import com.cvindosistem.simpeldesa.core.data.fcm.FcmManager
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk menangani logika otentikasi pengguna.
 *
 * Mengelola input login (email, password, dan kode lisensi), validasi form,
 * serta menangani alur login dan penyimpanan token ke `UserPreferences`.
 *
 * @property loginUseCase Use case untuk menjalankan login API call.
 * @property userPreferences Penyimpanan lokal untuk token, kode lisensi, dll.
 * @property fcmManager Manager untuk inisialisasi token FCM setelah login.
 */
class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val userPreferences: UserPreferences,
    private val fcmManager: FcmManager
) : ViewModel() {

    // === Input States ===
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

//    var licenseCode by mutableStateOf(userPreferences.getLicenseCode() ?: "")
//        private set

    // === UI States ===
    var isLoading by mutableStateOf(false)
        private set

//    var hasLicenseCode = userPreferences.hasLicenseCode()
//        private set

    // === Validation Errors ===
    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

//    var licenseCodeError by mutableStateOf<String?>(null)
//        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    // === One-time Event (Login Result) ===
    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    // === Input Updaters ===

    /** Memperbarui input email dan menghapus error terkait jika ada */
    fun onEmailChanged(value: String) {
        email = value
        emailError = null
        loginError = null
    }

    /** Memperbarui input password dan menghapus error terkait jika ada */
    fun onPasswordChanged(value: String) {
        password = value
        passwordError = null
        loginError = null
    }

    /** Memperbarui input kode lisensi dan menghapus error terkait jika ada */
//    fun onLicenseCodeChanged(value: String) {
//        licenseCode = value
//        licenseCodeError = null
//        loginError = null
//    }

    // === Validation ===

    /** Validasi format dan kekosongan email */
    private fun validateEmail(): Boolean {
        return when {
            email.isEmpty() -> {
                emailError = "Email tidak boleh kosong"
                false
            }
            else -> true
        }
    }

    /** Validasi panjang dan kekosongan password */
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
            else -> true
        }
    }

    /** Memastikan format email valid atau merupakan nomor telepon */
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        return email.matches(emailPattern.toRegex()) || isValidPhoneNumber(email)
    }

    /** Validasi nomor telepon Indonesia */
    private fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = "^(\\+62|62|0)[0-9]{9,13}$"
        return phone.matches(phonePattern.toRegex())
    }

    // === Login Action ===

    /**
     * Mengeksekusi proses login:
     * - Validasi input
     * - Kirim request login
     * - Simpan token dan kode lisensi jika berhasil
     * - Emit event [LoginEvent]
     */
    fun login() {
        viewModelScope.launch {
            loginError = null

            val isEmailValid = validateEmail()
            val isPasswordValid = validatePassword()

            if (!isEmailValid || !isPasswordValid) return@launch

            isLoading = true
//            val codeToUse = userPreferences.getLicenseCode() ?: licenseCode

//            if (codeToUse.isEmpty()) {
//                loginError = "Kode lisensi harus diisi"
//                _loginEvent.emit(LoginEvent.Error("Kode lisensi harus diisi"))
//                isLoading = false
//                return@launch
//            }

            when (val result = loginUseCase(email, password)) {
                is LoginResult.Success -> {
                    Log.d("LoginViewModel", "Login successful: ${result.token}")
                    userPreferences.saveAuthToken(result.token)

//                    if (licenseCode.isNotEmpty() && !userPreferences.hasLicenseCode()) {
//                        userPreferences.saveLicenseCode(licenseCode)
//                        hasLicenseCode = true
//                    }

                    try {
                        fcmManager.initializeFcm()
                    } catch (e: Exception) {
                        Log.e("LoginViewModel", "Failed to initialize FCM after login", e)
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

    /** Menentukan apakah form saat ini valid, berguna untuk enable tombol submit */
    fun isFormValid(): Boolean {
        return email.isNotEmpty() &&
                password.isNotEmpty() &&
                password.length >= 6
    }

    // === Login Events ===

    /**
     * Event satu arah yang menunjukkan hasil dari proses login.
     */
    sealed class LoginEvent {
        data object Success : LoginEvent()
        data class Error(val message: String) : LoginEvent()
    }
}