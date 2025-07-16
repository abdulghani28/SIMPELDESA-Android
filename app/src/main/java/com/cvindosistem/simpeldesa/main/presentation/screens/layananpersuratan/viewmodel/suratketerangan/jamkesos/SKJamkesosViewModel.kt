package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jamkesos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJamkesosUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPendidikanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKJamkesosViewModel(
    createSKJamkesosUseCase: CreateSuratJamkesosUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase,
    getPendidikanUseCase: GetPendidikanUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKJamkesosStateManager()
    private val validator = SKJamkesosValidator(stateManager)
    private val dataLoader = SKJamkesosDataLoader(getUserInfoUseCase, getAgamaUseCase, getStatusKawinUseCase, getPendidikanUseCase, stateManager, validator)
    private val formSubmitter = SKJamkesosFormSubmitter(createSKJamkesosUseCase, stateManager)

    // Events
    private val _skJamkesosEvent = MutableSharedFlow<SKJamkesosEvent>()
    val skJamkesosEvent = _skJamkesosEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKJamkesosUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val statusKawinList: List<StatusKawinResponse.Data> get() = stateManager.statusKawinList
    val isLoadingStatusKawin: Boolean get() = stateManager.isLoadingStatusKawin
    val statusKawinErrorMessage: String? get() = stateManager.statusKawinErrorMessage
    val pendidikanList: List<PendidikanResponse.Data> get() = stateManager.pendidikanList
    val isLoadingPendidikan: Boolean get() = stateManager.isLoadingPendidikan
    val pendidikanErrorMessage: String? get() = stateManager.pendidikanErrorMessage
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Personal Data)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val alamatValue: String get() = stateManager.alamatValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val statusKawinIdValue: String get() = stateManager.statusKawinIdValue
    val pendidikanIdValue: String get() = stateManager.pendidikanIdValue

    // Form field properties - Step 2 (Card Info)
    val noKartuValue: String get() = stateManager.noKartuValue
    val berlakuDariValue: String get() = stateManager.berlakuDariValue
    val berlakuSampaiValue: String get() = stateManager.berlakuSampaiValue

    // Form field properties - Step 3 (Purpose)
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skJamkesosEvent.emit(SKJamkesosEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 Update Functions (Personal Data)
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

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updateStatusKawinId(value: String) {
        stateManager.updateStatusKawinId(value)
        validator.clearFieldError("status_kawin_id")
    }

    fun updatePendidikanId(value: String) {
        stateManager.updatePendidikanId(value)
        validator.clearFieldError("pendidikan_id")
    }

    // Step 2 Update Functions (Card Info)
    fun updateNoKartu(value: String) {
        stateManager.updateNoKartu(value)
        validator.clearFieldError("no_kartu")
    }

    fun updateBerlakuDari(value: String) {
        stateManager.updateBerlakuDari(value)
        validator.clearFieldError("berlaku_dari")
    }

    fun updateBerlakuSampai(value: String) {
        stateManager.updateBerlakuSampai(value)
        validator.clearFieldError("berlaku_sampai")
    }

    // Step 3 Update Functions (Purpose)
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
                _skJamkesosEvent.emit(SKJamkesosEvent.ValidationError)
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
                _skJamkesosEvent.emit(SKJamkesosEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skJamkesosEvent.emit(SKJamkesosEvent.SubmitSuccess) }
                .onFailure { _skJamkesosEvent.emit(SKJamkesosEvent.SubmitError(it.message ?: "Error")) }
        }
    }

    // Data loading functions
    fun loadAgamaData() {
        viewModelScope.launch {
            dataLoader.loadAgamaData()
                .onSuccess { _skJamkesosEvent.emit(SKJamkesosEvent.AgamaDataLoaded(it)) }
                .onFailure { _skJamkesosEvent.emit(SKJamkesosEvent.AgamaDataLoadError(it.message ?: "Error")) }
        }
    }

    fun loadStatusKawinData() {
        viewModelScope.launch {
            dataLoader.loadStatusKawinData()
                .onSuccess { _skJamkesosEvent.emit(SKJamkesosEvent.StatusKawinDataLoaded(it)) }
                .onFailure { _skJamkesosEvent.emit(SKJamkesosEvent.StatusKawinDataLoadError(it.message ?: "Error")) }
        }
    }

    fun loadPendidikanData() {
        viewModelScope.launch {
            dataLoader.loadPendidikanData()
                .onSuccess { _skJamkesosEvent.emit(SKJamkesosEvent.PendidikanDataLoaded(it)) }
                .onFailure { _skJamkesosEvent.emit(SKJamkesosEvent.PendidikanDataLoadError(it.message ?: "Error")) }
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
                _skJamkesosEvent.emit(SKJamkesosEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skJamkesosEvent.emit(SKJamkesosEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKJamkesosEvent {
        data class StepChanged(val step: Int) : SKJamkesosEvent()
        data object SubmitSuccess : SKJamkesosEvent()
        data class SubmitError(val message: String) : SKJamkesosEvent()
        data object ValidationError : SKJamkesosEvent()
        data class UserDataLoadError(val message: String) : SKJamkesosEvent()
        data class AgamaDataLoaded(val data: AgamaResponse) : SKJamkesosEvent()
        data class AgamaDataLoadError(val message: String) : SKJamkesosEvent()
        data class StatusKawinDataLoaded(val data: StatusKawinResponse) : SKJamkesosEvent()
        data class StatusKawinDataLoadError(val message: String) : SKJamkesosEvent()
        data class PendidikanDataLoaded(val data: PendidikanResponse) : SKJamkesosEvent()
        data class PendidikanDataLoadError(val message: String) : SKJamkesosEvent()
    }
}