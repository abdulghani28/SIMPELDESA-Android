package com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.auth.domain.usecases.auth.LogoutUseCase
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val userPreferences: UserPreferences,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _profileEvent = MutableSharedFlow<ProfileEvent>()
    val profileEvent = _profileEvent.asSharedFlow()

    private val _logoutEvent = MutableSharedFlow<LogoutEvent>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    init {
        loadUserData()
    }

    fun loadUserData() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    _uiState.value = _uiState.value.copy(
                        userName = userData.nama_warga,
                        nik = userData.nik,
                        foto = userData.photo,
                        tempatTanggalLahir = "${userData.tempat_lahir}, ${formatTanggalLahir(userData.tanggal_lahir)}",
                        jenisKelamin = userData.jenis_kelamin,
                        agama = userData.agama,
                        pekerjaan = userData.pekerjaan,
                        alamatLengkap = userData.alamat,
                        village = extractVillageName(userData.alamat),
                        email = userData.email,
                        noTelp = userData.no_telp,
                        isDataLoaded = true
                    )
                    _profileEvent.emit(ProfileEvent.DataLoaded)
                }
                is UserInfoResult.Error -> {
                    errorMessage = result.message
                    _profileEvent.emit(ProfileEvent.Error(result.message))
                }
            }
            isLoading = false
        }
    }

    fun refreshData() {
        loadUserData()
    }

    fun clearError() {
        errorMessage = null
    }

    fun logout() {
        viewModelScope.launch {
            isLoading = true

            when (val result = logoutUseCase()) {
                is LogoutResult.Success -> {
                    userPreferences.clearAuthToken()
                    _logoutEvent.emit(LogoutEvent.Logout)
                    Log.d("ProfileViewModel", "Logout successful")
                }
                is LogoutResult.Error -> {
                    Log.e("ProfileViewModel", "Logout failed: ${result.message}")
                    _logoutEvent.emit(LogoutEvent.Error(result.message))
                }
            }

            isLoading = false
        }
    }

    // Helper function to extract village name from address
    private fun extractVillageName(alamat: String): String {
        return when {
            alamat.contains("Desa", ignoreCase = true) -> {
                val parts = alamat.split(",", " ")
                val desaIndex = parts.indexOfFirst { it.contains("Desa", ignoreCase = true) }
                if (desaIndex != -1 && desaIndex + 1 < parts.size) {
                    "Desa ${parts[desaIndex + 1].trim()}"
                } else {
                    "Desa Sukaramai Baru" // fallback
                }
            }
            else -> "Desa Sukaramai Baru" // fallback default
        }
    }

    // Helper function to format birth date
    private fun formatTanggalLahir(tanggalLahir: String): String {
        // Assuming tanggalLahir is in format "YYYY-MM-DD" or similar
        // You can adjust this logic based on your actual date format
        return try {
            // Simple formatting logic - adjust as needed
            val parts = tanggalLahir.split("-")
            if (parts.size >= 3) {
                val day = parts[2]
                val month = when (parts[1]) {
                    "01" -> "Januari"
                    "02" -> "Februari"
                    "03" -> "Maret"
                    "04" -> "April"
                    "05" -> "Mei"
                    "06" -> "Juni"
                    "07" -> "Juli"
                    "08" -> "Agustus"
                    "09" -> "September"
                    "10" -> "Oktober"
                    "11" -> "November"
                    "12" -> "Desember"
                    else -> parts[1]
                }
                val year = parts[0]
                "$day $month $year"
            } else {
                tanggalLahir
            }
        } catch (e: Exception) {
            tanggalLahir
        }
    }

    sealed class ProfileEvent {
        data object DataLoaded : ProfileEvent()
        data object LogoutSuccess : ProfileEvent()
        data class Error(val message: String) : ProfileEvent()
    }

    sealed class LogoutEvent {
        data object Logout : LogoutEvent()
        data class Error(val message: String) : LogoutEvent()
    }
}

// Data class untuk UI State
data class ProfileUiState(
    val userName: String = "",
    val nik: String = "",
    val tempatTanggalLahir: String = "",
    val foto: String? = "",
    val jenisKelamin: String = "",
    val agama: String = "",
    val pekerjaan: String = "",
    val alamatLengkap: String = "",
    val village: String = "Desa Sukaramai Baru",
    val email: String = "",
    val noTelp: String = "",
    val isDataLoaded: Boolean = false
)