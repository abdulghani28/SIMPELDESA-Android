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
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratGhaibResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratGhaibUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliViewModel.SKDomisiliEvent.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKGhaibViewModel(
    private val createSKGhaibUseCase: CreateSuratGhaibUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKGhaibUiState())
    val uiState = _uiState.asStateFlow()

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
    private val _skGhaibEvent = MutableSharedFlow<SKGhaibEvent>()
    val skGhaibEvent = _skGhaibEvent.asSharedFlow()

    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var hubunganIdValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Orang Yang Hilang
    var namaOrangHilangValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var usiaValue by mutableIntStateOf(0)
        private set
    var alamatValue by mutableStateOf("")
        private set
    var hilangSejakValue by mutableStateOf("")
        private set



    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
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

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf("nik", "nama"))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skGhaibEvent.emit(SKGhaibEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skGhaibEvent.emit(SKGhaibEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        nikValue = ""
        namaValue = ""
    }

    // Step 1 - Informasi Pelapor Update Functions
    fun updateNik(value: String) {
        nikValue = value
        clearFieldError("nik")
    }

    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateHubunganId(value: String) {
        hubunganIdValue = value
        clearFieldError("hubungan_id")
    }

    // Step 2 - Informasi Orang Yang Hilang Update Functions
    fun updateNamaOrangHilang(value: String) {
        namaOrangHilangValue = value
        clearFieldError("nama_orang_hilang")
    }

    fun updateJenisKelamin(value: String) {
        jenisKelaminValue = value
        clearFieldError("jenis_kelamin")
    }

    fun updateUsia(value: Int) {
        usiaValue = value
        clearFieldError("usia")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    fun updateHilangSejak(value: String) {
        hilangSejakValue = value
        clearFieldError("hilang_sejak")
    }



    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
            }
        }

        showPreviewDialog = true
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    // Step navigation
    fun nextStep() {
        when (currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    currentStep = 2
                    viewModelScope.launch {
                        _skGhaibEvent.emit(SKGhaibEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    showConfirmationDialog = true
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            currentStep -= 1
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
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

    private fun validateStep1WithEvent(): Boolean {
        val isValid = validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (namaValue.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (hubunganIdValue.isBlank()) errors["hubungan_id"] = "Hubungan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (namaOrangHilangValue.isBlank()) errors["nama_orang_hilang"] = "Nama orang hilang tidak boleh kosong"
        if (jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (usiaValue <= 0) errors["usia"] = "Usia harus lebih dari 0"
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (hilangSejakValue.isBlank()) errors["hilang_sejak"] = "Tanggal hilang tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2()
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
                val request = SKGhaibRequest(
                    alamat = alamatValue,
                    disahkan_oleh = "",
                    hilang_sejak = hilangSejakValue,
                    hubungan_id = hubunganIdValue,
                    jenis_kelamin = jenisKelaminValue,
                    keperluan = "", // Send empty string as requested
                    nama = namaValue,
                    nama_orang_hilang = namaOrangHilangValue,
                    nik = nikValue,
                    usia = usiaValue
                )

                when (val result = createSKGhaibUseCase(request)) {
                    is SuratGhaibResult.Success -> {
                        _skGhaibEvent.emit(SKGhaibEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratGhaibResult.Error -> {
                        errorMessage = result.message
                        _skGhaibEvent.emit(SKGhaibEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skGhaibEvent.emit(SKGhaibEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Informasi Pelapor
        nikValue = ""
        namaValue = ""
        hubunganIdValue = ""

        // Step 2 - Informasi Orang Yang Hilang
        namaOrangHilangValue = ""
        jenisKelaminValue = ""
        usiaValue = 0
        alamatValue = ""
        hilangSejakValue = ""

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
                hubunganIdValue.isNotBlank() || namaOrangHilangValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || usiaValue > 0 ||
                alamatValue.isNotBlank() || hilangSejakValue.isNotBlank()
    }

    // Events
    sealed class SKGhaibEvent {
        data class StepChanged(val step: Int) : SKGhaibEvent()
        data object SubmitSuccess : SKGhaibEvent()
        data class SubmitError(val message: String) : SKGhaibEvent()
        data object ValidationError : SKGhaibEvent()
        data class UserDataLoadError(val message: String) : SKGhaibEvent()
    }
}

// UI State data class
data class SKGhaibUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)