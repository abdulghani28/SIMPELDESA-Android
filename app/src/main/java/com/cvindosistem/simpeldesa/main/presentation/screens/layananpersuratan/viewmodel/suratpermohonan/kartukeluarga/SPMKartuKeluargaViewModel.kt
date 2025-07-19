package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.kartukeluarga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanKartuKeluargaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMKartuKeluargaViewModel(
    createSPMKartuKeluargaUseCase: CreateSuratPermohonanKartuKeluargaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMKartuKeluargaStateManager()
    private val validator = SPMKartuKeluargaValidator(stateManager)
    private val dataLoader = SPMKartuKeluargaDataLoader(
        getUserInfoUseCase, getAgamaUseCase, stateManager, validator
    )
    private val formSubmitter = SPMKartuKeluargaFormSubmitter(createSPMKartuKeluargaUseCase, stateManager)

    // Events
    private val _spmKartuKeluargaEvent = MutableSharedFlow<SPMKartuKeluargaEvent>()
    val spmKartuKeluargaEvent = _spmKartuKeluargaEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPMKartuKeluargaUiState()).asStateFlow()

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
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val noKkValue: String get() = stateManager.noKkValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val alasanPermohonanValue: String get() = stateManager.alasanPermohonanValue

    // Form Data States - Step 2
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.AgamaLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.AgamaLoadError(it.message ?: "Error"))
            }
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

    fun updateNoKk(value: String) {
        stateManager.updateNoKk(value)
        validator.clearFieldError("no_kk")
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

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updateAlasanPermohonan(value: String) {
        stateManager.updateAlasanPermohonan(value)
        validator.clearFieldError("alasan_permohonan")
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
                _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.ValidationError)
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
                _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.SubmitSuccess) }
                .onFailure { _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.SubmitError(it.message ?: "Error")) }
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
                _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmKartuKeluargaEvent.emit(SPMKartuKeluargaEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPMKartuKeluargaEvent {
        data class StepChanged(val step: Int) : SPMKartuKeluargaEvent()
        data object SubmitSuccess : SPMKartuKeluargaEvent()
        data class SubmitError(val message: String) : SPMKartuKeluargaEvent()
        data object ValidationError : SPMKartuKeluargaEvent()
        data class UserDataLoadError(val message: String) : SPMKartuKeluargaEvent()
        data class AgamaLoadError(val message: String) : SPMKartuKeluargaEvent()
    }
}