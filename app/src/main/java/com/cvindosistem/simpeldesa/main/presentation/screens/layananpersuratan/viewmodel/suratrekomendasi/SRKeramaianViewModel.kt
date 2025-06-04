package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKeramaianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeramaianUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SRKeramaianViewModel(
    createSuratKeramaianUseCase: CreateSuratKeramaianUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Compose all components
    private val stateManager = SRKeramaianStateManager()
    private val validator = SRKeramaianValidator()
    private val dataLoader = SRKeramaianDataLoader(getUserInfoUseCase)
    private val formSubmitter = SRKeramaianFormSubmitter(createSuratKeramaianUseCase)

    // UI State for the form
    private val _uiState = MutableStateFlow(SRKeramaianUiState())
    val uiState = _uiState.asStateFlow()

    // Loading and error states
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _keramaianEvent = MutableSharedFlow<SRKeramaianEvent>()
    val keramaianEvent = _keramaianEvent.asSharedFlow()

    // Expose state manager properties through delegation
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog

    // Step 1 - Informasi Pelapor
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val selectedGender: String get() = stateManager.selectedGender
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val alamatValue: String get() = stateManager.alamatValue

    // Step 2 - Informasi Kegiatan
    val namaAcaraValue: String get() = stateManager.namaAcaraValue
    val tempatAcaraValue: String get() = stateManager.tempatAcaraValue
    val hariValue: String get() = stateManager.hariValue
    val tanggalValue: String get() = stateManager.tanggalValue
    val jamMulaiValue: String get() = stateManager.jamMulaiValue
    val jamSelesaiValue: String get() = stateManager.jamSelesaiValue
    val penanggungJawabValue: String get() = stateManager.penanggungJawabValue
    val kontakValue: String get() = stateManager.kontakValue


    // Expose validation
    val validationErrors = validator.validationErrors

    // Field update functions - delegate to state manager with validation clearing
    // Step 1 - Informasi Pelapor
    fun updateNik(value: String) {
        stateManager.updateNik(value)
        validator.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateSelectedGender(value: String) {
        stateManager.updateGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    // Step 2 - Informasi Kegiatan
    fun updateNamaAcara(value: String) {
        stateManager.updateNamaAcara(value)
        validator.clearFieldError("nama_acara")
    }

    fun updateTempatAcara(value: String) {
        stateManager.updateTempatAcara(value)
        validator.clearFieldError("tempat_acara")
    }

    fun updateHari(value: String) {
        stateManager.updateHari(value)
        validator.clearFieldError("hari")
    }

    fun updateTanggal(value: String) {
        stateManager.updateTanggal(value)
        validator.clearFieldError("tanggal")
    }

    fun updateJamMulai(value: String) {
        stateManager.updateJamMulai(value)
        validator.clearFieldError("jam_mulai")
    }

    fun updateJamSelesai(value: String) {
        stateManager.updateJamSelesai(value)
        validator.clearFieldError("jam_selesai")
    }

    fun updatePenanggungJawab(value: String) {
        stateManager.updatePenanggungJawab(value)
        validator.clearFieldError("penanggung_jawab")
    }

    fun updateKontak(value: String) {
        stateManager.updateKontak(value)
        validator.clearFieldError("kontak")
    }

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        stateManager.setUseMyData(checked)
        if (checked) {
            loadUserData()
        } else {
            stateManager.clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            stateManager.setLoadingUserData(true)
            try {
                dataLoader.loadUserData()
                    .onSuccess { userData ->
                        stateManager.updateUserData(userData)
                        validator.clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat"
                        ))
                    }
                    .onFailure { exception ->
                        errorMessage = exception.message ?: "Gagal memuat data pengguna"
                        stateManager.setUseMyData(false)
                        _keramaianEvent.emit(SRKeramaianEvent.UserDataLoadError(errorMessage!!))
                    }
            } finally {
                stateManager.setLoadingUserData(false)
            }
        }
    }

    // Step navigation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    stateManager.nextStep()
                    viewModelScope.launch {
                        _keramaianEvent.emit(SRKeramaianEvent.StepChanged(stateManager.currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    stateManager.showConfirmation()
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.StepChanged(stateManager.currentStep))
            }
        }
    }

    // Validation with events
    private fun validateStep1WithEvent(): Boolean {
        val isValid = validator.validateStep1(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validator.validateStep2(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
            }
        }
        return isValid
    }

    // Dialog functions
    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        val step2Valid = validator.validateStep2(stateManager)

        if (!step1Valid || !step2Valid) {
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.showConfirmation()
        } else {
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.dismissConfirmation()

    fun confirmSubmit() {
        stateManager.dismissConfirmation()
        submitForm()
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val request = stateManager.createSubmissionRequest()
                formSubmitter.submitForm(request)
                    .onSuccess {
                        _keramaianEvent.emit(SRKeramaianEvent.SubmitSuccess)
                        stateManager.resetForm()
                    }
                    .onFailure { exception ->
                        errorMessage = exception.message ?: "Terjadi kesalahan"
                        _keramaianEvent.emit(SRKeramaianEvent.SubmitError(errorMessage!!))
                    }
            } finally {
                isLoading = false
            }
        }
    }

    // Utility functions - delegate to appropriate components
    fun validateAllSteps(): Boolean = validator.validateAllSteps(stateManager)
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun clearError() { errorMessage = null }

    // COPY EVENTS CLASS DARI KODE ASLI
    sealed class SRKeramaianEvent {
        data class StepChanged(val step: Int) : SRKeramaianEvent()
        data object SubmitSuccess : SRKeramaianEvent()
        data class SubmitError(val message: String) : SRKeramaianEvent()
        data object ValidationError : SRKeramaianEvent()
        data class UserDataLoadError(val message: String) : SRKeramaianEvent()
    }
}
