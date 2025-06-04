package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.resiktpsementara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKResiKTPSementaraStateManager {

    // UI State
    private val _uiState = MutableStateFlow(SKResiKTPSementaraUiState())
    val uiState = _uiState.asStateFlow()

    // Loading states
    var isLoading by mutableStateOf(false)
    var isLoadingUserData by mutableStateOf(false)
    var isLoadingAgama by mutableStateOf(false)

    // Error states
    var errorMessage by mutableStateOf<String?>(null)
    var agamaErrorMessage by mutableStateOf<String?>(null)

    // Form states
    var currentStep by mutableIntStateOf(1)
    var useMyDataChecked by mutableStateOf(false)
    var showConfirmationDialog by mutableStateOf(false)
    var showPreviewDialog by mutableStateOf(false)

    // Data states
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())

    // Form fields - Step 1
    var nikValue by mutableStateOf("")
    var namaValue by mutableStateOf("")
    var tempatLahirValue by mutableStateOf("")
    var tanggalLahirValue by mutableStateOf("")
    var selectedGender by mutableStateOf("")
    var pekerjaanValue by mutableStateOf("")
    var alamatValue by mutableStateOf("")
    var agamaValue by mutableStateOf("")
    var keperluanValue by mutableStateOf("")

    // Validation state
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // State update functions - hanya untuk operasi yang membutuhkan logic khusus
    fun updateValidationErrors(errors: Map<String, String>) {
        _validationErrors.value = errors
    }

    fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
        _validationErrors.value = currentErrors
    }

    fun clearMultipleFieldErrors(fieldNames: List<String>) {
        val currentErrors = _validationErrors.value.toMutableMap()
        fieldNames.forEach { fieldName -> currentErrors.remove(fieldName) }
        _validationErrors.value = currentErrors
    }

    fun getFieldError(fieldName: String): String? = _validationErrors.value[fieldName]
    fun hasFieldError(fieldName: String): Boolean = _validationErrors.value.containsKey(fieldName)

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaValue = ""
        keperluanValue = ""
        _validationErrors.value = emptyMap()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        selectedGender = userData.jenis_kelamin
        pekerjaanValue = userData.pekerjaan
        alamatValue = userData.alamat
        agamaValue = userData.agama_id
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaValue = ""
    }
}