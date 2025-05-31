package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratTugasRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratTugasResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTugasUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SuratTugasViewModel(
    private val createSuratTugasUseCase: CreateSuratTugasUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SuratTugasUiState())
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
    private val _tugasEvent = MutableSharedFlow<SuratTugasEvent>()
    val tugasEvent = _tugasEvent.asSharedFlow()

    // Step 1 - Informasi Penerima Tugas
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var jabatanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Tugas
    var ditugaskanUntukValue by mutableStateOf("")
        private set
    var deskripsiValue by mutableStateOf("")
        private set
    var disahkanOlehValue by mutableStateOf("")
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
                        _tugasEvent.emit(SuratTugasEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _tugasEvent.emit(SuratTugasEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        nikValue = ""
        namaValue = ""
    }

    // Step 1 - Penerima Tugas
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

    // Step 2 - Informasi Tugas
    fun updateDitugaskanUntuk(value: String) {
        ditugaskanUntukValue = value
        clearFieldError("ditugaskan_untuk")
    }

    fun updateDeskripsi(value: String) {
        deskripsiValue = value
        clearFieldError("deskripsi")
    }

    fun updateDisahkanOleh(value: String) {
        disahkanOlehValue = value
        clearFieldError("disahkan_oleh")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
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
                        _tugasEvent.emit(SuratTugasEvent.StepChanged(currentStep))
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
                _tugasEvent.emit(SuratTugasEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
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
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
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

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (ditugaskanUntukValue.isBlank()) errors["ditugaskan_untuk"] = "Ditugaskan untuk tidak boleh kosong"
        if (deskripsiValue.isBlank()) errors["deskripsi"] = "Deskripsi tidak boleh kosong"

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
                val penerima = SuratTugasRequest.Penerima(
                    jabatan = jabatanValue,
                    nama = namaValue,
                    nik = nikValue
                )

                val request = SuratTugasRequest(
                    deskripsi = deskripsiValue,
                    disahkan_oleh = disahkanOlehValue,
                    ditugaskan_untuk = ditugaskanUntukValue,
                    penerima = listOf(penerima)
                )

                when (val result = createSuratTugasUseCase(request)) {
                    is SuratTugasResult.Success -> {
                        _tugasEvent.emit(SuratTugasEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratTugasResult.Error -> {
                        errorMessage = result.message
                        _tugasEvent.emit(SuratTugasEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _tugasEvent.emit(SuratTugasEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Penerima Tugas
        nikValue = ""
        namaValue = ""
        jabatanValue = ""

        // Step 2 - Informasi Tugas
        ditugaskanUntukValue = ""
        deskripsiValue = ""
        disahkanOlehValue = ""

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
                ditugaskanUntukValue.isNotBlank() || deskripsiValue.isNotBlank()
    }

    // Events
    sealed class SuratTugasEvent {
        data class StepChanged(val step: Int) : SuratTugasEvent()
        data object SubmitSuccess : SuratTugasEvent()
        data class SubmitError(val message: String) : SuratTugasEvent()
        data object ValidationError : SuratTugasEvent()
        data class UserDataLoadError(val message: String) : SuratTugasEvent()
    }
}

// UI State data class
data class SuratTugasUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)