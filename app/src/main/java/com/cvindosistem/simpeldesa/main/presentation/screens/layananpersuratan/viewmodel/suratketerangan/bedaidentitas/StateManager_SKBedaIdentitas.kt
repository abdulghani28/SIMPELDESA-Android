package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PerbedaanIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.TercantumIdentitasResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKBedaIdentitasStateManager {
    // UI State for the form
    private val _uiState = MutableStateFlow(SKBedaIdentitasUiState())
    val uiState = _uiState.asStateFlow()

    // External data states
    var tercantumIdentitasList by mutableStateOf<List<TercantumIdentitasResponse.Data>>(emptyList())
    var isLoadingTercantumIdentitas by mutableStateOf(false)
    var tercantumIdentitasErrorMessage by mutableStateOf<String?>(null)

    var perbedaanIdentitasList by mutableStateOf<List<PerbedaanIdentitasResponse.Data>>(emptyList())
    var isLoadingPerbedaanIdentitas by mutableStateOf(false)
    var perbedaanIdentitasErrorMessage by mutableStateOf<String?>(null)

    // Loading & Error states
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Navigation state
    var currentStep by mutableIntStateOf(1)

    // Use My Data state
    var useMyDataChecked by mutableStateOf(false)
    var isLoadingUserData by mutableStateOf(false)

    // Dialog states
    var showConfirmationDialog by mutableStateOf(false)
    var showPreviewDialog by mutableStateOf(false)

    // Step 1 - Identitas Pertama
    var perbedaanIdValue by mutableStateOf("")
    var nama1Value by mutableStateOf("")
    var nomor1Value by mutableStateOf("")
    var tempatLahir1Value by mutableStateOf("")
    var tanggalLahir1Value by mutableStateOf("")
    var alamat1Value by mutableStateOf("")
    var tercantumId1Value by mutableStateOf("")

    // Step 2 - Identitas Kedua
    var nama2Value by mutableStateOf("")
    var nomor2Value by mutableStateOf("")
    var tempatLahir2Value by mutableStateOf("")
    var tanggalLahir2Value by mutableStateOf("")
    var alamat2Value by mutableStateOf("")
    var tercantumId2Value by mutableStateOf("")

    // Step 3 - Perbedaan dan Keperluan
    var keperluanValue by mutableStateOf("")

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

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
        fieldNames.forEach { fieldName ->
            currentErrors.remove(fieldName)
        }
        _validationErrors.value = currentErrors
    }

    fun getFieldError(fieldName: String): String? = _validationErrors.value[fieldName]
    fun hasFieldError(fieldName: String): Boolean = _validationErrors.value.containsKey(fieldName)

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false

        // Reset all form values
        perbedaanIdValue = ""
        nama1Value = ""
        nomor1Value = ""
        tempatLahir1Value = ""
        tanggalLahir1Value = ""
        alamat1Value = ""
        tercantumId1Value = ""

        nama2Value = ""
        nomor2Value = ""
        tempatLahir2Value = ""
        tanggalLahir2Value = ""
        alamat2Value = ""
        tercantumId2Value = ""

        keperluanValue = ""

        _validationErrors.value = emptyMap()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nama1Value.isNotBlank() || nomor1Value.isNotBlank() ||
                tempatLahir1Value.isNotBlank() || tanggalLahir1Value.isNotBlank() ||
                alamat1Value.isNotBlank() || tercantumId1Value.isNotBlank() ||
                nama2Value.isNotBlank() || nomor2Value.isNotBlank() ||
                tempatLahir2Value.isNotBlank() || tanggalLahir2Value.isNotBlank() ||
                alamat2Value.isNotBlank() || tercantumId2Value.isNotBlank() ||
                perbedaanIdValue.isNotBlank() || keperluanValue.isNotBlank()
    }
}