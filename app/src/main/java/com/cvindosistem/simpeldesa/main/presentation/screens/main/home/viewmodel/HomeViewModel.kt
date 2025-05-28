package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel

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
                        dusun = userData.dusun,
                        greeting = getCurrentGreeting(),
                        rt = userData.rt,
                        rw = userData.rw,
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
            dusun = _uiState.value.dusun,
            rt = _uiState.value.rt,
            rw = _uiState.value.rw
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
    val dusun: String = "Desa . . .",
    val rt: String = "XX",
    val rw: String = "YY",
    val greeting: String = "Halo 👋",
    val isDataLoaded: Boolean = false
)

// Data class untuk informasi desa
data class VillageInfo(
    val dusun: String,
    val rt: String,
    val rw: String
)