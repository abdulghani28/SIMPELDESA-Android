package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ktpdalamproses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKTPDalamProsesUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKTPDalamProsesViewModel(
    createSKKTPDalamProsesUseCase: CreateSuratKTPDalamProsesUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKKTPDalamProsesStateManager()
    private val validator = SKKTPDalamProsesValidator(stateManager)
    private val dataLoader = SKKTPDalamProsesDataLoader(getUserInfoUseCase, getAgamaUseCase, stateManager, validator, getStatusKawinUseCase)
    private val formSubmitter = SKKTPDalamProsesFormSubmitter(createSKKTPDalamProsesUseCase, stateManager)

    // Events
    private val _skKTPDalamProsesEvent = MutableSharedFlow<SKKTPDalamProsesEvent>()
    val skKTPDalamProsesEvent = _skKTPDalamProsesEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKKTPDalamProsesUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val agamaList get() = stateManager.agamaList
    val statusKawinList get() = stateManager.statusKawinList
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val statusKawinIdValue: String get() = stateManager.statusKawinIdValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue

    // Form field properties - Step 2
    val keperluanValue: String get() = stateManager.keperluanValue

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgamaData().onFailure {
                _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.AgamaDataLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin().onFailure {
                _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.StatusKawinDataLoadError(it.message ?: "Error"))
            }
        }
    }

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.UserDataLoadError(it.message ?: "Error"))
                }
                loadAgama()
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 Update Functions
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

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateStatusKawinId(value: String) {
        stateManager.updateStatusKawinId(value)
        validator.clearFieldError("status_kawin_id")
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

    // Step 2 Update Functions
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
                _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.ValidationError)
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
                _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.SubmitSuccess) }
                .onFailure { _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.SubmitError(it.message ?: "Error")) }
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
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skKTPDalamProsesEvent.emit(SKKTPDalamProsesEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKKTPDalamProsesEvent {
        data class StepChanged(val step: Int) : SKKTPDalamProsesEvent()
        data object SubmitSuccess : SKKTPDalamProsesEvent()
        data class SubmitError(val message: String) : SKKTPDalamProsesEvent()
        data object ValidationError : SKKTPDalamProsesEvent()
        data class UserDataLoadError(val message: String) : SKKTPDalamProsesEvent()
        data class AgamaDataLoadError(val message: String) : SKKTPDalamProsesEvent()
        data class StatusKawinDataLoadError(val message: String) : SKKTPDalamProsesEvent()
    }
}