package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.berpergian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBerpergianUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKBerpergianViewModel(
    createSKBerpergianUseCase: CreateSuratBerpergianUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // Composed components
    private val stateManager = SKBerpergianStateManager()
    private val validator = SKBerpergianValidator()
    private val dataLoader = SKBerpergianDataLoader(getUserInfoUseCase)
    private val formSubmitter = SKBerpergianFormSubmitter(createSKBerpergianUseCase)

    // UI State for the form
    private val _uiState = MutableStateFlow(SKBerpergianUiState())
    val uiState = _uiState.asStateFlow()

    // Loading and error states
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Delegate properties to state manager
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog

    // Form field properties (delegated to state manager)
    val nikValue: String get() = stateManager.formState.value.nik
    val namaValue: String get() = stateManager.formState.value.nama
    val tempatLahirValue: String get() = stateManager.formState.value.tempatLahir
    val tanggalLahirValue: String get() = stateManager.formState.value.tanggalLahir
    val jenisKelaminValue: String get() = stateManager.formState.value.jenisKelamin
    val pekerjaanValue: String get() = stateManager.formState.value.pekerjaan
    val alamatValue: String get() = stateManager.formState.value.alamat
    val tempatTujuanValue: String get() = stateManager.formState.value.tempatTujuan
    val maksudTujuanValue: String get() = stateManager.formState.value.maksudTujuan
    val tanggalKeberangkatanValue: String get() = stateManager.formState.value.tanggalKeberangkatan
    val lamaValue: Int get() = stateManager.formState.value.lama
    val satuanLamaValue: String get() = stateManager.formState.value.satuanLama
    val jumlahPengikutValue: String get() = stateManager.formState.value.jumlahPengikut
    val keperluanValue: String get() = stateManager.formState.value.keperluan

    // Validation errors
    val validationErrors = validator.validationErrors

    // Events
    private val _skBerpergianEvent = MutableSharedFlow<SKBerpergianEvent>()
    val skBerpergianEvent = _skBerpergianEvent.asSharedFlow()

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        stateManager.setUseMyDataChecked(checked)
        if (checked) {
            loadUserData()
        } else {
            clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            stateManager.setLoadingUserData(true)
            try {
                dataLoader.loadUserData()
                    .onSuccess { userData ->
                        stateManager.updatePersonalInfo(
                            nik = userData.nik,
                            nama = userData.nama,
                            tempatLahir = userData.tempatLahir,
                            tanggalLahir = userData.tanggalLahir,
                            jenisKelamin = userData.jenisKelamin,
                            pekerjaan = userData.pekerjaan,
                            alamat = userData.alamat
                        )

                        // Clear validation errors for filled fields
                        validator.clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat"
                        ))
                    }
                    .onFailure { exception ->
                        errorMessage = exception.message ?: "Gagal memuat data pengguna"
                        stateManager.setUseMyDataChecked(false)
                        _skBerpergianEvent.emit(SKBerpergianEvent.UserDataLoadError(errorMessage!!))
                    }
            } finally {
                stateManager.setLoadingUserData(false)
            }
        }
    }

    private fun clearUserData() {
        stateManager.updatePersonalInfo(
            nik = "",
            nama = "",
            tempatLahir = "",
            tanggalLahir = "",
            jenisKelamin = "",
            pekerjaan = "",
            alamat = ""
        )
    }

    // Step 1 - Personal Information Update Functions
    fun updateNik(value: String) {
        stateManager.updatePersonalInfo(nik = value)
        validator.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updatePersonalInfo(nama = value)
        validator.clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updatePersonalInfo(tempatLahir = value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updatePersonalInfo(tanggalLahir = value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updatePersonalInfo(jenisKelamin = value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePersonalInfo(pekerjaan = value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.updatePersonalInfo(alamat = value)
        validator.clearFieldError("alamat")
    }

    // Step 2 - Travel Information Update Functions
    fun updateTempatTujuan(value: String) {
        stateManager.updateTravelInfo(tempatTujuan = value)
        validator.clearFieldError("tempat_tujuan")
    }

    fun updateMaksudTujuan(value: String) {
        stateManager.updateTravelInfo(maksudTujuan = value)
        validator.clearFieldError("maksud_tujuan")
    }

    fun updateTanggalKeberangkatan(value: String) {
        stateManager.updateTravelInfo(tanggalKeberangkatan = value)
        validator.clearFieldError("tanggal_keberangkatan")
    }

    fun updateLama(value: Int) {
        stateManager.updateTravelInfo(lama = value)
        validator.clearFieldError("lama")
    }

    fun updateSatuanLama(value: String) {
        stateManager.updateTravelInfo(satuanLama = value)
        validator.clearFieldError("satuan_lama")
    }

    fun updateJumlahPengikut(value: String) {
        stateManager.updateTravelInfo(jumlahPengikut = value)
        validator.clearFieldError("jumlah_pengikut")
    }

    // Step 3 - Additional Information Update Functions
    fun updateKeperluan(value: String) {
        stateManager.updateAdditionalInfo(value)
        validator.clearFieldError("keperluan")
    }

    // Preview dialog functions
    fun showPreview() {
        val formData = stateManager.formState.value
        val step1Valid = validator.validateStep1(formData)
        val step2Valid = validator.validateStep2(formData)
        val step3Valid = validator.validateStep3(formData)

        if (!step1Valid || !step2Valid || !step3Valid) {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }

        stateManager.setShowPreviewDialog(true)
    }

    fun dismissPreview() {
        stateManager.setShowPreviewDialog(false)
    }

    // Step navigation
    fun nextStep() {
        val formData = stateManager.formState.value
        when (currentStep) {
            1 -> {
                if (validateStep1WithEvent(formData)) {
                    stateManager.nextStep()
                    viewModelScope.launch {
                        _skBerpergianEvent.emit(SKBerpergianEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent(formData)) {
                    stateManager.nextStep()
                    viewModelScope.launch {
                        _skBerpergianEvent.emit(SKBerpergianEvent.StepChanged(currentStep))
                    }
                }
            }
            3 -> {
                if (validateStep3WithEvent(formData)) {
                    stateManager.setShowConfirmationDialog(true)
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        val formData = stateManager.formState.value
        if (validator.validateAllSteps(formData)) {
            stateManager.setShowConfirmationDialog(true)
        } else {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        stateManager.setShowConfirmationDialog(false)
    }

    fun confirmSubmit() {
        stateManager.setShowConfirmationDialog(false)
        submitForm()
    }

    private fun validateStep1WithEvent(formData: SKBerpergianStateManager.FormData): Boolean {
        val isValid = validator.validateStep1(formData)
        if (!isValid) {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(formData: SKBerpergianStateManager.FormData): Boolean {
        val isValid = validator.validateStep2(formData)
        if (!isValid) {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(formData: SKBerpergianStateManager.FormData): Boolean {
        val isValid = validator.validateStep3(formData)
        if (!isValid) {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validator.validateAllSteps(stateManager.formState.value)
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                formSubmitter.submitForm(stateManager.formState.value)
                    .onSuccess {
                        _skBerpergianEvent.emit(SKBerpergianEvent.SubmitSuccess)
                        resetForm()
                    }
                    .onFailure { exception ->
                        errorMessage = exception.message ?: "Terjadi kesalahan"
                        _skBerpergianEvent.emit(SKBerpergianEvent.SubmitError(errorMessage!!))
                    }
            } finally {
                isLoading = false
            }
        }
    }

    // Get validation error for specific field
    fun getFieldError(fieldName: String): String? {
        return validator.getFieldError(fieldName)
    }

    // Check if field has error
    fun hasFieldError(fieldName: String): Boolean {
        return validator.hasFieldError(fieldName)
    }

    // Reset form
    private fun resetForm() {
        stateManager.resetForm()
        validator.clearAllErrors()
        errorMessage = null
    }

    // Clear error message
    fun clearError() {
        errorMessage = null
    }

    // Check if form has data
    fun hasFormData(): Boolean {
        return stateManager.hasFormData()
    }

    // Events (kept same as original)
    sealed class SKBerpergianEvent {
        data class StepChanged(val step: Int) : SKBerpergianEvent()
        data object SubmitSuccess : SKBerpergianEvent()
        data class SubmitError(val message: String) : SKBerpergianEvent()
        data object ValidationError : SKBerpergianEvent()
        data class UserDataLoadError(val message: String) : SKBerpergianEvent()
    }
}