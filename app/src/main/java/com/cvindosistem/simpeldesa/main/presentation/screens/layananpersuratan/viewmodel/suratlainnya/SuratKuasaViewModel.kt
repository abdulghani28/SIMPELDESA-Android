package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKuasaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKuasaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SuratKuasaViewModel(
    private val createSuratKuasaUseCase: CreateSuratKuasaUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SuratKuasaUiState())
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
    private val _kuasaEvent = MutableSharedFlow<SuratKuasaEvent>()
    val kuasaEvent = _kuasaEvent.asSharedFlow()

    // Step 1 - Informasi Pemberi Kuasa
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var jabatanValue by mutableStateOf("")
        private set
    var kuasaSebagaiValue by mutableStateOf("")
        private set
    var kuasaUntukValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Penerima Kuasa
    var nikPenerimaValue by mutableStateOf("")
        private set
    var namaPenerimaValue by mutableStateOf("")
        private set
    var jabatanPenerima by mutableStateOf("")
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
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _kuasaEvent.emit(SuratKuasaEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _kuasaEvent.emit(SuratKuasaEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        // Step 1 - Calon 
        nikValue = ""
        namaValue = ""
    }

    // Step 1 - Pelapor
    fun updateNik(value: String) {
        nikValue = value
        clearFieldError("nik")
    }

    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateJabatan(value: String) {
        jabatanValue = value
        clearFieldError("jabatan")
    }

    fun updateKuasaSebagai(value: String) {
        kuasaSebagaiValue = value
        clearFieldError("kuasa_sebagai")
    }

    fun updateKuasaUntuk(value: String) {
        kuasaUntukValue = value
        clearFieldError("kuasa_untuk")
    }

    // Step 2 - Untuk
    fun updateNikPenerima(value: String) {
        nikPenerimaValue = value
        clearFieldError("nik_penerima")
    }

    fun updateNamaPenerima(value: String) {
        namaPenerimaValue = value
        clearFieldError("nama_penerima")
    }

    fun updateJabatanPenerima(value: String) {
        jabatanPenerima = value
        clearFieldError("jabatan_penerima")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
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
                        _kuasaEvent.emit(SuratKuasaEvent.StepChanged(currentStep))
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
                _kuasaEvent.emit(SuratKuasaEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
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
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
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
        if (jabatanValue.isBlank()) errors["jabatan"] = "Jabatan tidak boleh kosong"
        if (kuasaUntukValue.isBlank()) errors["kuasa_untuk"] = "Kolom tidak boleh kosong"
        if (kuasaSebagaiValue.isBlank()) errors["kuasa_sebagai"] = "Kolom tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (nikValue.isBlank()) {
            errors["nik_penerima"] = "NIK tidak boleh kosong"
        } else if (nikValue.length != 16) {
            errors["nik_penerima"] = "NIK harus 16 digit"
        }
        if (namaPenerimaValue.isBlank()) errors["nama_penerima"] = "Nama penerima tidak boleh kosong"
        if (jabatanPenerima.isBlank()) errors["jabatan_penerima"] = "Jabatan penerima tidak boleh kosong"

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
                val request = SuratKuasaRequest(
                    disahkan_oleh = "",
                    disposisi_kuasa_sebagai = kuasaSebagaiValue,
                    disposisi_kuasa_untuk = kuasaUntukValue,
                    jabatan_pemberi = jabatanValue,
                    jabatan_penerima = jabatanPenerima,
                    nama_pemberi = namaValue,
                    nama_penerima = namaPenerimaValue,
                    nik_pemberi = nikValue,
                    nik_penerima = nikPenerimaValue,
                )

                when (val result = createSuratKuasaUseCase(request)) {
                    is SuratKuasaResult.Success -> {
                        _kuasaEvent.emit(SuratKuasaEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratKuasaResult.Error -> {
                        errorMessage = result.message
                        _kuasaEvent.emit(SuratKuasaEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _kuasaEvent.emit(SuratKuasaEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Pemberi
        nikValue = ""
        namaValue = ""
        jabatanValue = ""
        kuasaUntukValue = ""
        kuasaSebagaiValue = ""

        // Step 2 - Orang Tua
        nikPenerimaValue = ""
        namaPenerimaValue = ""
        jabatanPenerima = ""

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
        return nikValue.isNotBlank() || namaValue.isNotBlank()
    }

    // Events
    sealed class SuratKuasaEvent {
        data class StepChanged(val step: Int) : SuratKuasaEvent()
        data object SubmitSuccess : SuratKuasaEvent()
        data class SubmitError(val message: String) : SuratKuasaEvent()
        data object ValidationError : SuratKuasaEvent()
        data class UserDataLoadError(val message: String) : SuratKuasaEvent()
    }
}

// UI State data class
data class SuratKuasaUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)