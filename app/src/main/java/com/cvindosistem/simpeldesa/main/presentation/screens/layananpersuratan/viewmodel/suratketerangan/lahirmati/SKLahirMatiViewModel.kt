package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.HubunganResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratLahirMatiUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetHubunganUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKLahirMatiViewModel(
    createSKLahirMatiUseCase: CreateSuratLahirMatiUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getHubunganUseCase: GetHubunganUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKLahirMatiStateManager()
    private val validator = SKLahirMatiValidator(stateManager)
    private val dataLoader = SKLahirMatiDataLoader(getUserInfoUseCase, getAgamaUseCase, getHubunganUseCase, stateManager, validator)
    private val formSubmitter = SKLahirMatiFormSubmitter(createSKLahirMatiUseCase, stateManager)

    // Events
    private val _skLahirMatiEvent = MutableSharedFlow<SKLahirMatiEvent>()
    val skLahirMatiEvent = _skLahirMatiEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKLahirMatiUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val hubunganList: List<HubunganResponse.Data> get() = stateManager.hubunganList
    val isLoadingHubungan: Boolean get() = stateManager.isLoadingHubungan
    val hubunganErrorMessage: String? get() = stateManager.hubunganErrorMessage
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
    val keperluanValue: String get() = stateManager.keperluanValue
    
    // Step 2
    val nikIbuValue: String get() = stateManager.nikIbuValue
    val namaIbuValue: String get() = stateManager.namaIbuValue
    val tempatLahirIbuValue: String get() = stateManager.tempatLahirIbuValue
    val tanggalLahirIbuValue: String get() = stateManager.tanggalLahirIbuValue
    val agamaIbuIdValue: String get() = stateManager.agamaIbuIdValue
    val kewarganegaraanIbuIdValue: String get() = stateManager.kewarganegaraanIbuIdValue
    val pekerjaanIbuValue: String get() = stateManager.pekerjaanIbuValue
    val alamatIbuValue: String get() = stateManager.alamatIbuValue
    
    // Step 3
    val lamaDikandungValue: String get() = stateManager.lamaDikandungValue
    val tanggalMeninggalValue: String get() = stateManager.tanggalMeninggalValue
    val tempatMeninggalValue: String get() = stateManager.tempatMeninggalValue
    val hubunganIdValue: String get() = stateManager.hubunganIdValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skLahirMatiEvent.emit(SKLahirMatiEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skLahirMatiEvent.emit(SKLahirMatiEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadHubungan() {
        viewModelScope.launch {
            dataLoader.loadHubungan().onFailure {
                _skLahirMatiEvent.emit(SKLahirMatiEvent.HubunganLoadError(it.message ?: "Error"))
            }
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

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Step 2 Update Functions
    fun updateNikIbu(value: String) {
        stateManager.updateNikIbu(value)
        validator.clearFieldError("nik_ibu")
    }

    fun updateNamaIbu(value: String) {
        stateManager.updateNamaIbu(value)
        validator.clearFieldError("nama_ibu")
    }

    fun updateTempatLahirIbu(value: String) {
        stateManager.updateTempatLahirIbu(value)
        validator.clearFieldError("tempat_lahir_ibu")
    }

    fun updateTanggalLahirIbu(value: String) {
        stateManager.updateTanggalLahirIbu(value)
        validator.clearFieldError("tanggal_lahir_ibu")
    }

    fun updateAgamaIbuId(value: String) {
        stateManager.updateAgamaIbuId(value)
        validator.clearFieldError("agama_ibu_id")
    }

    fun updateKewarganegaraanIbuId(value: String) {
        stateManager.updateKewarganegaraanIbuId(value)
        validator.clearFieldError("kewarganegaraan_ibu_id")
    }

    fun updatePekerjaanIbu(value: String) {
        stateManager.updatePekerjaanIbu(value)
        validator.clearFieldError("pekerjaan_ibu")
    }

    fun updateAlamatIbu(value: String) {
        stateManager.updateAlamatIbu(value)
        validator.clearFieldError("alamat_ibu")
    }

    // Step 3 Update Functions
    fun updateLamaDikandung(value: String) {
        stateManager.updateLamaDikandung(value)
        validator.clearFieldError("lama_dikandung")
    }

    fun updateTanggalMeninggal(value: String) {
        stateManager.updateTanggalMeninggal(value)
        validator.clearFieldError("tanggal_meninggal")
    }

    fun updateTempatMeninggal(value: String) {
        stateManager.updateTempatMeninggal(value)
        validator.clearFieldError("tempat_meninggal")
    }

    fun updateHubunganId(value: String) {
        stateManager.updateHubunganId(value)
        validator.clearFieldError("hubungan_id")
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
                _skLahirMatiEvent.emit(SKLahirMatiEvent.ValidationError)
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
                _skLahirMatiEvent.emit(SKLahirMatiEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skLahirMatiEvent.emit(SKLahirMatiEvent.SubmitSuccess) }
                .onFailure { _skLahirMatiEvent.emit(SKLahirMatiEvent.SubmitError(it.message ?: "Error")) }
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
                _skLahirMatiEvent.emit(SKLahirMatiEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skLahirMatiEvent.emit(SKLahirMatiEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKLahirMatiEvent {
        data class StepChanged(val step: Int) : SKLahirMatiEvent()
        data object SubmitSuccess : SKLahirMatiEvent()
        data class SubmitError(val message: String) : SKLahirMatiEvent()
        data object ValidationError : SKLahirMatiEvent()
        data class UserDataLoadError(val message: String) : SKLahirMatiEvent()
        data class AgamaLoadError(val message: String) : SKLahirMatiEvent()
        data class HubunganLoadError(val message: String) : SKLahirMatiEvent()
    }
}