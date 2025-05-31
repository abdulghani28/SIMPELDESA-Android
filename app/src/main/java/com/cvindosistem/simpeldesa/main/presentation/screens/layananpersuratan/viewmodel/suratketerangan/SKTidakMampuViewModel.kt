package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKTidakMampuRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMampuResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMampuUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKTidakMampuViewModel(
    private val createSuratCatatanTidakMampuUseCase: CreateSuratTidakMampuUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKTidakMampuUiState())
    val uiState = _uiState.asStateFlow()

    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingAgama by mutableStateOf(false)
    var agamaErrorMessage by mutableStateOf<String?>(null)

    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
    var isLoadingStatusKawin by mutableStateOf(false)
    var statusKawinErrorMessage by mutableStateOf<String?>(null)

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Current step
    var currentStep by mutableIntStateOf(1)
        private set

    // Use My Data state
    var useMyDataChecked by mutableStateOf(false)
        private set

    var isLoadingUserData by mutableStateOf(false)
        private set

    // Show confirmation dialog
    var showConfirmationDialog by mutableStateOf(false)
        private set

    // Show preview dialog
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Events
    private val _catatanTidakMampuEvent = MutableSharedFlow<SKTidakMampuEvent>()
    val catatanTidakMampuEvent = _catatanTidakMampuEvent.asSharedFlow()

    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var selectedGender by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var agamaValue by mutableStateOf("")
        private set
    var statusKawinValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
            loadAgama()
            loadStatusKawin()
        } else {
            clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            isLoadingUserData = true
            try {
                when (val result = getUserInfoUseCase()) {
                    is UserInfoResult.Success -> {
                        val userData = result.data.data
                        nikValue = userData.nik
                        namaValue = userData.nama_warga
                        tempatLahirValue = userData.tempat_lahir
                        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
                        selectedGender = userData.jenis_kelamin
                        pekerjaanValue = userData.pekerjaan
                        alamatValue = userData.alamat
                        agamaValue = userData.agama_id
                        statusKawinValue = userData.status_kawin_id

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat", "status_kawin_id",
                            "agama_id"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            isLoadingAgama = true
            agamaErrorMessage = null
            try {
                when (val result = getAgamaUseCase()) {
                    is AgamaResult.Success -> {
                        agamaList = result.data.data // <- ini penting!
                    }
                    is AgamaResult.Error -> {
                        agamaErrorMessage = result.message
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.AgamaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                agamaErrorMessage = e.message ?: "Gagal memuat data agama"
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.AgamaLoadError(agamaErrorMessage!!))
            } finally {
                isLoadingAgama = false
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            isLoadingStatusKawin = true
            statusKawinErrorMessage = null
            try {
                when (val result = getStatusKawinUseCase()) {
                    is StatusKawinResult.Success -> {
                        statusKawinList = result.data.data // <- ini penting!
                    }
                    is StatusKawinResult.Error -> {
                        statusKawinErrorMessage = result.message
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.StatusKawinLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                statusKawinErrorMessage = e.message ?: "Gagal memuat data statusKawin"
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.StatusKawinLoadError(statusKawinErrorMessage!!))
            } finally {
                isLoadingStatusKawin = false
            }
        }
    }

    private fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
    }

    // Step 1 field updates
    fun updateNik(value: String) {
        nikValue = value
        clearFieldError("nik")
    }

    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        tempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        tanggalLahirValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal_lahir")
    }

    fun updateGender(value: String) {
        selectedGender = value
        clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }
    
    fun updateAgama(value: String) {
        agamaValue = value
        clearFieldError("agama_id")
    }

    fun updateStatusKawin(value: String) {
        statusKawinValue = value
        clearFieldError("status_kawin_id")
    }

    // Step 2 field updates
    fun updateKeperluan(value: String) {
        keperluanValue = value
        clearFieldError("keperluan")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()

        if (!step1Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.ValidationError)
            }
        }

        showPreviewDialog = true
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        showConfirmationDialog = false
    }

    fun confirmSubmit() {
        showConfirmationDialog = false
        submitForm()
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (namaValue.isBlank()) {
            errors["nama"] = "Nama lengkap tidak boleh kosong"
        }

        if (tempatLahirValue.isBlank()) {
            errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        }

        if (tanggalLahirValue.isBlank()) {
            errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        }

        if (selectedGender.isBlank()) {
            errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        }

        if (pekerjaanValue.isBlank()) {
            errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        if (alamatValue.isBlank()) {
            errors["alamat"] = "Alamat tidak boleh kosong"
        }

        if (agamaValue.isBlank()) {
            errors["agama_id"] = "Agama tidak boleh kosong"
        }

        if (statusKawinValue.isBlank()) {
            errors["status_kawin_id"] = "Status kawin tidak boleh kosong"
        }


        if (keperluanValue.isBlank()) {
            errors["keperluan"] = "Alamat tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validateStep1()
    }

    private fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
        _validationErrors.value = currentErrors
    }

    private fun clearMultipleFieldErrors(fieldNames: List<String>) {
        val currentErrors = _validationErrors.value.toMutableMap()
        fieldNames.forEach { fieldName ->
            currentErrors.remove(fieldName)
        }
        _validationErrors.value = currentErrors
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val request = SKTidakMampuRequest(
                    alamat = alamatValue,
                    disahkan_oleh = "", // This might be set on the backend
                    jenis_kelamin = selectedGender,
                    nama = namaValue,
                    nik = nikValue,
                    pekerjaan = pekerjaanValue,
                    tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
                    tempat_lahir = tempatLahirValue,
                    keperluan = keperluanValue,
                    agama_id = agamaValue,
                    status_kawin_id = statusKawinValue
                )

                when (val result = createSuratCatatanTidakMampuUseCase(request)) {
                    is SuratTidakMampuResult.Success -> {
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratTidakMampuResult.Error -> {
                        errorMessage = result.message
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.SubmitError(errorMessage!!))
            } finally {
                isLoading = false
            }
        }
    }

    // Get validation error for specific field
    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    // Check if field has error
    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }

    // Reset form
    private fun resetForm() {
        currentStep = 1
        useMyDataChecked = false

        // Reset Step 1 fields
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaValue = ""
        statusKawinValue = ""
        keperluanValue = ""

        _validationErrors.value = emptyMap()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    // Clear error message
    fun clearError() {
        errorMessage = null
    }

    // Check if form has data
    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    // Events
    sealed class SKTidakMampuEvent {
        data class StepChanged(val step: Int) : SKTidakMampuEvent()
        data object SubmitSuccess : SKTidakMampuEvent()
        data class SubmitError(val message: String) : SKTidakMampuEvent()
        data object ValidationError : SKTidakMampuEvent()
        data class UserDataLoadError(val message: String) : SKTidakMampuEvent()
        data class AgamaLoadError(val message: String) : SKTidakMampuEvent()
        data class StatusKawinLoadError(val message: String) : SKTidakMampuEvent()
    }
}

// UI State data class
data class SKTidakMampuUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val statusKawinList: List<StatusKawinResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)