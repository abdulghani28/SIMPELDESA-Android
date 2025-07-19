package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatsuratnikah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDuplikatSuratNikahUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMDuplikatSuratNikahViewModel(
    createSPMDuplikatSuratNikahUseCase: CreateSuratDuplikatSuratNikahUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMDuplikatSuratNikahStateManager()
    private val validator = SPMDuplikatSuratNikahValidator(stateManager)
    private val dataLoader = SPMDuplikatSuratNikahDataLoader(getUserInfoUseCase, stateManager, validator)
    private val formSubmitter = SPMDuplikatSuratNikahFormSubmitter(createSPMDuplikatSuratNikahUseCase, stateManager)

    // Events
    private val _spmDuplikatSuratNikahEvent = MutableSharedFlow<SPMDuplikatSuratNikahEvent>()
    val spmDuplikatSuratNikahEvent = _spmDuplikatSuratNikahEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPMDuplikatSuratNikahUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue
    val noKkValue: String get() = stateManager.noKkValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val pendidikanIdValue: String get() = stateManager.pendidikanIdValue
    val agamaIdValue: String get() = stateManager.agamaIdValue

    // Form field properties - Step 2
    val namaPasanganValue: String get() = stateManager.namaPasanganValue
    val tanggalNikahValue: String get() = stateManager.tanggalNikahValue
    val kepalaKeluargaValue: String get() = stateManager.kepalaKeluargaValue
    val kecamatanKuaValue: String get() = stateManager.kecamatanKuaValue

    // Form field properties - Step 3
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 update functions - delegate to stateManager dan clear errors
    fun updateNik(value: String) {
        stateManager.updateNik(value)
        validator.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateKewarganegaraan(value: String) {
        stateManager.updateKewarganegaraan(value)
        validator.clearFieldError("kewarganegaraan")
    }

    fun updateNoKk(value: String) {
        stateManager.updateNoKk(value)
        validator.clearFieldError("no_kk")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updatePendidikanId(value: String) {
        stateManager.updatePendidikanId(value)
        validator.clearFieldError("pendidikan_id")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    // Step 2 Update Functions
    fun updateNamaPasangan(value: String) {
        stateManager.updateNamaPasangan(value)
        validator.clearFieldError("nama_pasangan")
    }

    fun updateTanggalNikah(value: String) {
        stateManager.updateTanggalNikah(value)
        validator.clearFieldError("tanggal_nikah")
    }

    fun updateKepalaKeluarga(value: String) {
        stateManager.updateKepalaKeluarga(value)
        validator.clearFieldError("kepala_keluarga")
    }

    fun updateKecamatanKua(value: String) {
        stateManager.updateKecamatanKua(value)
        validator.clearFieldError("kecamatan_kua")
    }

    // Step 3 Update Functions
    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Navigation functions
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStepWithEvent(1)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            2 -> {
                if (validateStepWithEvent(2)) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            3 -> {
                if (validateStepWithEvent(3)) {
                    stateManager.showConfirmation()
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            emitStepChangedEvent()
        }
    }

    // Dialog functions
    fun showPreview() {
        if (!validator.validateAllSteps()) {
            viewModelScope.launch {
                _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.hidePreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            stateManager.showConfirmation()
        } else {
            viewModelScope.launch {
                _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.SubmitSuccess) }
                .onFailure { _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.SubmitError(it.message ?: "Error")) }
        }
    }

    // Validation functions - delegate to validator
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utility functions
    fun clearError() = stateManager.clearError()
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Private helper functions
    private fun validateStepWithEvent(step: Int): Boolean {
        val isValid = when (step) {
            1 -> validator.validateStep1()
            2 -> validator.validateStep2()
            3 -> validator.validateStep3()
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmDuplikatSuratNikahEvent.emit(SPMDuplikatSuratNikahEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPMDuplikatSuratNikahEvent {
        data class StepChanged(val step: Int) : SPMDuplikatSuratNikahEvent()
        data object SubmitSuccess : SPMDuplikatSuratNikahEvent()
        data class SubmitError(val message: String) : SPMDuplikatSuratNikahEvent()
        data object ValidationError : SPMDuplikatSuratNikahEvent()
        data class UserDataLoadError(val message: String) : SPMDuplikatSuratNikahEvent()
    }
}