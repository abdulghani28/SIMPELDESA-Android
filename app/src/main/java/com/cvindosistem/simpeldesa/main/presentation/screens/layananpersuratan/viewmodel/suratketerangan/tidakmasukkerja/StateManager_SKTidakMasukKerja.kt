package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKTidakMasukKerjaStateManager {
    // Step navigation
    var currentStep by mutableIntStateOf(1)
        private set

    var useMyDataChecked by mutableStateOf(false)
        private set

    // Loading states
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set
    var isLoadingAgama by mutableStateOf(false)
        private set

    // Dialog states
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Error states
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set

    // Data states
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set

    // Step 1 - Personal Information
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set

    // Step 2 - Work Information
    var jabatanValue by mutableStateOf("")
        private set
    var namaPerusahaanValue by mutableStateOf("")
        private set
    var alasanIzinValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set
    var lamaValue by mutableIntStateOf(0)
        private set
    var terhitungDariValue by mutableStateOf("")
        private set

    // Validation errors
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // UI State
    private val _uiState = MutableStateFlow(SKTidakMasukKerjaUiState())
    val uiState = _uiState.asStateFlow()

    // State update functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun updateUseMyDataChecked(checked: Boolean) { useMyDataChecked = checked }
    fun updateLoading(loading: Boolean) { isLoading = loading }
    fun updateLoadingUserData(loading: Boolean) { isLoadingUserData = loading }
    fun updateLoadingAgama(loading: Boolean) { isLoadingAgama = loading }
    fun updateShowConfirmationDialog(show: Boolean) { showConfirmationDialog = show }
    fun updateShowPreviewDialog(show: Boolean) { showPreviewDialog = show }
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }

    // Step 1 field updates
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }

    // Step 2 field updates
    fun updateJabatan(value: String) { jabatanValue = value }
    fun updateNamaPerusahaan(value: String) { namaPerusahaanValue = value }
    fun updateAlasanIzin(value: String) { alasanIzinValue = value }
    fun updateKeperluan(value: String) { keperluanValue = value }
    fun updateLama(value: Int) { lamaValue = value }
    fun updateTerhitungDari(value: String) { terhitungDariValue = value }

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
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || agamaIdValue.isNotBlank() ||
                alamatValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                jabatanValue.isNotBlank() || namaPerusahaanValue.isNotBlank() ||
                alasanIzinValue.isNotBlank() || keperluanValue.isNotBlank() ||
                terhitungDariValue.isNotBlank()
    }

    fun populateUserData(userData: UserInfoResponse.Data) {
        // Copy semua assignment dari loadUserData() di kode asli
        nikValue = userData.nik
        namaValue = userData.nama_warga
        alamatValue = userData.alamat
        jenisKelaminValue = userData.jenis_kelamin
        agamaIdValue = userData.agama_id
        pekerjaanValue = userData.pekerjaan
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = userData.tanggal_lahir

        clearMultipleFieldErrors(listOf(
            "nik", "nama", "alamat", "jenis_kelamin", "agama_id",
            "pekerjaan", "tempat_lahir", "tanggal_lahir"
        ))
    }

    fun clearUserData() {
        // Copy semua assignment dari clearUserData() di kode asli
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        agamaIdValue = ""
        alamatValue = ""
        pekerjaanValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Step 2 - Copy dari resetForm() di kode asli
        jabatanValue = ""
        namaPerusahaanValue = ""
        alasanIzinValue = ""
        keperluanValue = ""
        lamaValue = 0
        terhitungDariValue = ""

        _validationErrors.value = emptyMap()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }
}