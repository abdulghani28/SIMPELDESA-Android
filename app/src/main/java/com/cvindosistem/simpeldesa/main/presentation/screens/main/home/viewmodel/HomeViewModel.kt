package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _homeEvent = MutableSharedFlow<HomeEvent>()
    val homeEvent = _homeEvent.asSharedFlow()

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
                        userEmail = userData.email,
                        village = extractVillageName(userData.alamat),
                        greeting = getCurrentGreeting(),
                        isDataLoaded = true
                    )
                    _homeEvent.emit(HomeEvent.DataLoaded)
                }
                is UserInfoResult.Error -> {
                    errorMessage = result.message
                    _homeEvent.emit(HomeEvent.Error(result.message))
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

    // Helper function to extract village name from address
    private fun extractVillageName(alamat: String): String {
        // Implementasi sederhana untuk ekstrak nama desa dari alamat
        // Anda bisa sesuaikan logic ini berdasarkan format alamat yang ada
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

    // Function to get current greeting based on time
    private fun getCurrentGreeting(): String {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (currentHour) {
            in 5..10 -> "Halo selamat pagi 👋"
            in 11..14 -> "Halo selamat siang 👋"
            in 15..18 -> "Halo selamat sore 👋"
            else -> "Halo selamat malam 👋"
        }
    }

    // Function to update greeting (dapat dipanggil secara periodik)
    fun updateGreeting() {
        _uiState.value = _uiState.value.copy(
            greeting = getCurrentGreeting()
        )
    }

    // Function to get formatted user display name
    fun getDisplayName(): String {
        return _uiState.value.userName.ifEmpty { "Pengguna" }
    }

    // Function to get village information
    fun getVillageInfo(): VillageInfo {
        return VillageInfo(
            village = _uiState.value.village,
            kecamatan = "Kecamatan Terong Belanda", // hardcoded as requested
            kabupaten = "Kabupaten Kebun Subur" // hardcoded as requested
        )
    }

    sealed class HomeEvent {
        data object DataLoaded : HomeEvent()
        data class Error(val message: String) : HomeEvent()
    }
}

// Data class untuk UI State
data class HomeUiState(
    val userName: String = "",
    val userEmail: String = "",
    val village: String = "Desa Sukaramai Baru",
    val greeting: String = "Halo selamat siang 👋",
    val isDataLoaded: Boolean = false
)

// Data class untuk informasi desa
data class VillageInfo(
    val village: String,
    val kecamatan: String,
    val kabupaten: String
)