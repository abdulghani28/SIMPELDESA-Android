package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.perubahankk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanPerubahanKKUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMPerubahanKKViewModel(
    createSPMPerubahanKKUseCase: CreateSuratPermohonanPerubahanKKUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMPerubahanKKStateManager()
    private val validator = SPMPerubahanKKValidator(stateManager)
    private val dataLoader = SPMPerubahanKKDataLoader(
        getUserInfoUseCase, getAgamaUseCase, stateManager, validator
    )
    private val formSubmitter =
        SPMPerubahanKKFormSubmitter(createSPMPerubahanKKUseCase, stateManager)

    // Events
    private val _spmPerubahanKKEvent = MutableSharedFlow<SPMPerubahanKKEvent>()
    val spmPerubahanKKEvent = _spmPerubahanKKEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPMPerubahanKKUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - delegate to stateManager
    // Step 1
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue

    // Step 2
    val alamatValue: String get() = stateManager.alamatValue
    val noKkValue: String get() = stateManager.noKkValue
    val alasanPermohonanValue: String get() = stateManager.alasanPermohonanValue

    // Step 3
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmPerubahanKKEvent.emit(
                        SPMPerubahanKKEvent.UserDataLoadError(
                            it.message ?: "Error"
                        )
                    )
                }
                dataLoader.loadAgama().onFailure {
                    _spmPerubahanKKEvent.emit(
                        SPMPerubahanKKEvent.AgamaLoadError(
                            it.message ?: "Error"
                        )
                    )
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _spmPerubahanKKEvent.emit(SPMPerubahanKKEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 update functions - delegate to stateManager and clear errors
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

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateKewarganegaraan(value: String) {
        stateManager.updateKewarganegaraan(value)
        validator.clearFieldError("kewarganegaraan")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    // Step 2 Update Functions
    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateNoKk(value: String) {
        stateManager.updateNoKk(value)
        validator.clearFieldError("no_kk")
    }

    fun updateAlasanPermohonan(value: String) {
        stateManager.updateAlasanPermohonan(value)
        validator.clearFieldError("alasan_permohonan")
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
                _spmPerubahanKKEvent.emit(SPMPerubahanKKEvent.ValidationError)
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
                _spmPerubahanKKEvent.emit(SPMPerubahanKKEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spmPerubahanKKEvent.emit(SPMPerubahanKKEvent.SubmitSuccess) }
                .onFailure {
                    _spmPerubahanKKEvent.emit(
                        SPMPerubahanKKEvent.SubmitError(
                            it.message ?: "Error"
                        )
                    )
                }
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
                _spmPerubahanKKEvent.emit(SPMPerubahanKKEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmPerubahanKKEvent.emit(SPMPerubahanKKEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPMPerubahanKKEvent {
        data class StepChanged(val step: Int) : SPMPerubahanKKEvent()
        data object SubmitSuccess : SPMPerubahanKKEvent()
        data class SubmitError(val message: String) : SPMPerubahanKKEvent()
        data object ValidationError : SPMPerubahanKKEvent()
        data class UserDataLoadError(val message: String) : SPMPerubahanKKEvent()
        data class AgamaLoadError(val message: String) : SPMPerubahanKKEvent()
    }
}