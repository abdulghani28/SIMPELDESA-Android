package com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.RequestOtpResult
import com.cvindosistem.simpeldesa.auth.domain.model.ResetPasswordResult
import com.cvindosistem.simpeldesa.auth.domain.model.ValidateOtpResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.RequestOtpUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ResetPasswordUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.ValidateOtpUseCase
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PasswordResetViewModel(
    private val requestOtpUseCase: RequestOtpUseCase,
    private val validateOtpUseCase: ValidateOtpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val userPreferences: UserPreferences
) : ViewModel(), Parcelable {

    var email by mutableStateOf("")
        private set

    var otp by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Counter for OTP resend functionality
    var countdownTime by mutableIntStateOf(0)
        private set

    private var countdownJob: Job? = null

    private val _passwordResetEvent = MutableSharedFlow<PasswordResetEvent>()
    val passwordResetEvent = _passwordResetEvent.asSharedFlow()

    constructor(parcel: Parcel) : this(
        TODO("requestOtpUseCase"),
        TODO("validateOtpUseCase"),
        TODO("resetPasswordUseCase"),
        TODO("userPreferences")
    )

    fun onEmailChanged(value: String) {
        email = value
    }

    fun onOtpChanged(value: String) {
        otp = value
    }

    fun onNewPasswordChanged(value: String) {
        newPassword = value
    }

    fun onConfirmPasswordChanged(value: String) {
        confirmPassword = value
    }

    fun requestOtp() {
        viewModelScope.launch {
            if (email.isEmpty()) {
                _passwordResetEvent.emit(PasswordResetEvent.Error("Email harus diisi"))
                return@launch
            }

            isLoading = true

//            val licenseCode = userPreferences.getLicenseCode() ?: ""
//            if (licenseCode.isEmpty()) {
//                _passwordResetEvent.emit(PasswordResetEvent.Error("Kode lisensi tidak ditemukan"))
//                isLoading = false
//                return@launch
//            }

            when (val result = requestOtpUseCase(email)) {
                is RequestOtpResult.Success -> {
                    Log.d("PasswordResetViewModel", "OTP request successful")
                    _passwordResetEvent.emit(PasswordResetEvent.OtpSent)
                    startCountdown()
                }
                is RequestOtpResult.Error -> {
                    Log.e("PasswordResetViewModel", "OTP request failed: ${result.message}")
                    _passwordResetEvent.emit(PasswordResetEvent.Error(result.message))
                }
            }

            isLoading = false
        }
    }

    fun validateOtp() {
        viewModelScope.launch {
            if (otp.isEmpty()) {
                _passwordResetEvent.emit(PasswordResetEvent.Error("Kode OTP harus diisi"))
                return@launch
            }

            isLoading = true

//            val licenseCode = userPreferences.getLicenseCode() ?: ""
//            if (licenseCode.isEmpty()) {
//                _passwordResetEvent.emit(PasswordResetEvent.Error("Kode lisensi tidak ditemukan"))
//                isLoading = false
//                return@launch
//            }

            when (val result = validateOtpUseCase(email, otp)) {
                is ValidateOtpResult.Success -> {
                    if (result.isValid) {
                        Log.d("PasswordResetViewModel", "OTP validation successful")
                        _passwordResetEvent.emit(PasswordResetEvent.OtpValid)
                    } else {
                        _passwordResetEvent.emit(PasswordResetEvent.Error("Kode OTP tidak valid"))
                    }
                }
                is ValidateOtpResult.Error -> {
                    Log.e("PasswordResetViewModel", "OTP validation failed: ${result.message}")
                    _passwordResetEvent.emit(PasswordResetEvent.Error(result.message))
                }
            }

            isLoading = false
        }
    }

    fun resetPassword() {
        viewModelScope.launch {
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                _passwordResetEvent.emit(PasswordResetEvent.Error("Password harus diisi"))
                return@launch
            }

            if (newPassword != confirmPassword) {
                _passwordResetEvent.emit(PasswordResetEvent.Error("Password tidak sama"))
                return@launch
            }

            if (newPassword.length < 6) {
                _passwordResetEvent.emit(PasswordResetEvent.Error("Password minimal 6 karakter"))
                return@launch
            }

            isLoading = true

//            val licenseCode = userPreferences.getLicenseCode() ?: ""
//            if (licenseCode.isEmpty()) {
//                _passwordResetEvent.emit(PasswordResetEvent.Error("Kode lisensi tidak ditemukan"))
//                isLoading = false
//                return@launch
//            }

            when (val result = resetPasswordUseCase(email, otp, newPassword)) {
                is ResetPasswordResult.Success -> {
                    Log.d("PasswordResetViewModel", "Password reset successful")
                    _passwordResetEvent.emit(PasswordResetEvent.PasswordResetSuccess)
                }
                is ResetPasswordResult.Error -> {
                    Log.e("PasswordResetViewModel", "Password reset failed: ${result.message}")
                    _passwordResetEvent.emit(PasswordResetEvent.Error(result.message))
                }
            }

            isLoading = false
        }
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownTime = 60 // 60 seconds countdown

        countdownJob = viewModelScope.launch {
            while (countdownTime > 0) {
                delay(1000)
                countdownTime--
            }
        }
    }

    fun canResendOtp(): Boolean = countdownTime == 0

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }

    sealed class PasswordResetEvent {
        data object OtpSent : PasswordResetEvent()
        data object OtpValid : PasswordResetEvent()
        data object PasswordResetSuccess : PasswordResetEvent()
        data class Error(val message: String) : PasswordResetEvent()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PasswordResetViewModel> {
        override fun createFromParcel(parcel: Parcel): PasswordResetViewModel {
            return PasswordResetViewModel(parcel)
        }

        override fun newArray(size: Int): Array<PasswordResetViewModel?> {
            return arrayOfNulls(size)
        }
    }
}