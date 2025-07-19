package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanCeraiUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMCeraiViewModel(
    createSPMCeraiUseCase: CreateSuratPermohonanCeraiUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMCeraiStateManager()
    private val validator = SPMCeraiValidator(stateManager)
    private val dataLoader = SPMCeraiDataLoader(
        getUserInfoUseCase, getAgamaUseCase, stateManager, validator
    )
    private val formSubmitter = SPMCeraiFormSubmitter(createSPMCeraiUseCase, stateManager)

    // Events
    private val _spmCeraiEvent = MutableSharedFlow<SPMCeraiEvent>()
    val spmCeraiEvent = _spmCeraiEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPMCeraiUiState()).asStateFlow()

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

    // Form field properties - Step 1 (Suami)
    val nikSuamiValue: String get() = stateManager.nikSuamiValue
    val namaSuamiValue: String get() = stateManager.namaSuamiValue
    val alamatSuamiValue: String get() = stateManager.alamatSuamiValue
    val pekerjaanSuamiValue: String get() = stateManager.pekerjaanSuamiValue
    val tanggalLahirSuamiValue: String get() = stateManager.tanggalLahirSuamiValue
    val tempatLahirSuamiValue: String get() = stateManager.tempatLahirSuamiValue
    val agamaIdSuamiValue: String get() = stateManager.agamaIdSuamiValue

    // Form field properties - Step 2 (Istri)
    val nikIstriValue: String get() = stateManager.nikIstriValue
    val namaIstriValue: String get() = stateManager.namaIstriValue
    val alamatIstriValue: String get() = stateManager.alamatIstriValue
    val pekerjaanIstriValue: String get() = stateManager.pekerjaanIstriValue
    val tanggalLahirIstriValue: String get() = stateManager.tanggalLahirIstriValue
    val tempatLahirIstriValue: String get() = stateManager.tempatLahirIstriValue
    val agamaIdIstriValue: String get() = stateManager.agamaIdIstriValue

    // Form field properties - Step 3 (Pelengkap)
    val sebabCeraiValue: String get() = stateManager.sebabCeraiValue
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmCeraiEvent.emit(SPMCeraiEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _spmCeraiEvent.emit(SPMCeraiEvent.AgamaLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _spmCeraiEvent.emit(SPMCeraiEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 update functions - Suami
    fun updateNikSuami(value: String) {
        stateManager.updateNikSuami(value)
        validator.clearFieldError("nik_suami")
    }

    fun updateNamaSuami(value: String) {
        stateManager.updateNamaSuami(value)
        validator.clearFieldError("nama_suami")
    }

    fun updateAlamatSuami(value: String) {
        stateManager.updateAlamatSuami(value)
        validator.clearFieldError("alamat_suami")
    }

    fun updatePekerjaanSuami(value: String) {
        stateManager.updatePekerjaanSuami(value)
        validator.clearFieldError("pekerjaan_suami")
    }

    fun updateTanggalLahirSuami(value: String) {
        stateManager.updateTanggalLahirSuami(value)
        validator.clearFieldError("tanggal_lahir_suami")
    }

    fun updateTempatLahirSuami(value: String) {
        stateManager.updateTempatLahirSuami(value)
        validator.clearFieldError("tempat_lahir_suami")
    }

    fun updateAgamaIdSuami(value: String) {
        stateManager.updateAgamaIdSuami(value)
        validator.clearFieldError("agama_id_suami")
    }

    // Step 2 update functions - Istri
    fun updateNikIstri(value: String) {
        stateManager.updateNikIstri(value)
        validator.clearFieldError("nik_istri")
    }

    fun updateNamaIstri(value: String) {
        stateManager.updateNamaIstri(value)
        validator.clearFieldError("nama_istri")
    }

    fun updateAlamatIstri(value: String) {
        stateManager.updateAlamatIstri(value)
        validator.clearFieldError("alamat_istri")
    }

    fun updatePekerjaanIstri(value: String) {
        stateManager.updatePekerjaanIstri(value)
        validator.clearFieldError("pekerjaan_istri")
    }

    fun updateTanggalLahirIstri(value: String) {
        stateManager.updateTanggalLahirIstri(value)
        validator.clearFieldError("tanggal_lahir_istri")
    }

    fun updateTempatLahirIstri(value: String) {
        stateManager.updateTempatLahirIstri(value)
        validator.clearFieldError("tempat_lahir_istri")
    }

    fun updateAgamaIdIstri(value: String) {
        stateManager.updateAgamaIdIstri(value)
        validator.clearFieldError("agama_id_istri")
    }

    // Step 3 update functions - Pelengkap
    fun updateSebabCerai(value: String) {
        stateManager.updateSebabCerai(value)
        validator.clearFieldError("sebab_cerai")
    }

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
                _spmCeraiEvent.emit(SPMCeraiEvent.ValidationError)
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
                _spmCeraiEvent.emit(SPMCeraiEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spmCeraiEvent.emit(SPMCeraiEvent.SubmitSuccess) }
                .onFailure { _spmCeraiEvent.emit(SPMCeraiEvent.SubmitError(it.message ?: "Error")) }
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
                _spmCeraiEvent.emit(SPMCeraiEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmCeraiEvent.emit(SPMCeraiEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPMCeraiEvent {
        data class StepChanged(val step: Int) : SPMCeraiEvent()
        data object SubmitSuccess : SPMCeraiEvent()
        data class SubmitError(val message: String) : SPMCeraiEvent()
        data object ValidationError : SPMCeraiEvent()
        data class UserDataLoadError(val message: String) : SPMCeraiEvent()
        data class AgamaLoadError(val message: String) : SPMCeraiEvent()
    }
}