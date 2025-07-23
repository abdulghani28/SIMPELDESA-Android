package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.data.abstractclass.BaseViewModel
import com.cvindosistem.simpeldesa.core.data.local.preferences.NotificationUtils
import com.cvindosistem.simpeldesa.core.data.remote.interceptor.SessionManager
import com.cvindosistem.simpeldesa.core.domain.model.notification.NotificationResponse
import com.cvindosistem.simpeldesa.core.domain.model.notification.NotifikasiResult
import com.cvindosistem.simpeldesa.core.domain.usecases.GetNotifikasiUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel.HomeEvent.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getNotifikasiUseCase: GetNotifikasiUseCase,
    private val sessionManager: SessionManager
) : BaseViewModel(sessionManager) {

    // UI State
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Notification state
    private val _hasUnreadNotifications = MutableStateFlow(false)
    val hasUnreadNotifications = _hasUnreadNotifications.asStateFlow()

    // Notification data
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    // Events
    private val _homeEvent = MutableSharedFlow<HomeEvent>()
    val homeEvent = _homeEvent.asSharedFlow()

    init {
        loadUserData()
        loadNotifications()
    }

    fun loadUserData() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
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
            } catch (e: Exception) {
                handleApiError(e) // Handle token expiry
            }
            isLoading = false
        }
    }

    fun loadNotifications() {
        viewModelScope.launch {
            try {
                when (val result = getNotifikasiUseCase()) {
                    is NotifikasiResult.Success -> {
                        val notificationItems = result.data.data.map { notification ->
                            NotificationItem(
                                id = notification.id,
                                category = mapJenisSuratToCategory(notification.jenis_surat),
                                title = notification.title,
                                message = notification.message,
                                timestamp = formatTimestamp(notification.created_at),
                                jenisSurat = notification.jenis_surat,
                                suratId = notification.surat_id,
                                wargaId = notification.warga_id
                            )
                        }

                        _notifications.value = notificationItems

                        _homeEvent.emit(HomeEvent.NotificationsLoaded)
                    }
                    is NotifikasiResult.Error -> {
                        Log.e("HomeViewModel", "Error loading notifications: ${result.message}")
                        _homeEvent.emit(Error(result.message))
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Exception loading notifications", e)
                _homeEvent.emit(HomeEvent.Error("Gagal memuat notifikasi"))
            }
        }
    }

    fun refreshData() {
        loadUserData()
        loadNotifications()
    }

    fun clearError() {
        errorMessage = null
    }

    fun setHasUnreadNotifications(hasUnread: Boolean) {
        _hasUnreadNotifications.value = hasUnread
    }

    // Function to be called when receiving new notification (called from broadcast receiver)
    fun onNewNotificationReceived() {
        _hasUnreadNotifications.value = true
        // Reload notifications to get the latest data
        loadNotifications()
    }

    // Function to check initial notification state from SharedPreferences
    fun checkInitialNotificationState(context: Context) {
        viewModelScope.launch {
            val hasUnread = NotificationUtils.hasUnreadNotifications(context)
            _hasUnreadNotifications.value = hasUnread
        }
    }

    // Get notifications filtered by category
    fun getNotificationsByCategory(category: NotificationCategory): List<NotificationItem> {
        return when (category) {
            NotificationCategory.FITUR_LAYANAN -> {
                _notifications.value.filter {
                    it.category in listOf("Layanan Persuratan", "Layanan Administrasi", "Layanan Publik")
                }
            }
            NotificationCategory.PENGUMUMAN -> {
                _notifications.value.filter {
                    it.category in listOf("Lapor Pemdes", "Pengumuman Desa", "Informasi Umum")
                }
            }
        }
    }

    // Helper function to map jenis_surat to category
    private fun mapJenisSuratToCategory(jenisSurat: String): String {
        return when (jenisSurat.lowercase()) {
            "surat_keterangan_bepergian", "surat_keterangan_domisili",
            "surat_keterangan_usaha", "surat_pengantar_nikah" -> "Layanan Persuratan"
            "lapor_pemdes" -> "Lapor Pemdes"
            "pengumuman" -> "Pengumuman Desa"
            else -> "Informasi Umum"
        }
    }

    // Helper function to format timestamp
    private fun formatTimestamp(timestamp: String): String {
        return try {
            // Parse ISO timestamp and format to Indonesian format
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy | HH:mm", Locale("id", "ID"))
            val date = inputFormat.parse(timestamp)
            date?.let { outputFormat.format(it) + " WIB" } ?: timestamp
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error formatting timestamp: $timestamp", e)
            timestamp
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
            dusun = _uiState.value.dusun,
            rt = _uiState.value.rt,
            rw = _uiState.value.rw
        )
    }

    sealed class HomeEvent {
        data object DataLoaded : HomeEvent()
        data object NotificationsLoaded : HomeEvent()
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

// Updated NotificationItem data class
data class NotificationItem(
    val id: String,
    val category: String,
    val title: String,
    val message: String = "",
    val timestamp: String,
    val jenisSurat: String = "",
    val suratId: String = "",
    val wargaId: String = ""
)

// Enum for notification categories
enum class NotificationCategory {
    FITUR_LAYANAN,
    PENGUMUMAN
}