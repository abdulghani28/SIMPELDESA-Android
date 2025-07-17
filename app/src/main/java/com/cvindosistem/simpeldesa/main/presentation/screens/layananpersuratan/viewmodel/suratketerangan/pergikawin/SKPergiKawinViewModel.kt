package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pergikawin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPergiKawinUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPendidikanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKPergiKawinViewModel(
    createSKPergiKawinUseCase: CreateSuratPergiKawinUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getPendidikanUseCase: GetPendidikanUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKPergiKawinStateManager()
    private val validator = SKPergiKawinValidator(stateManager)
    private val dataLoader = SKPergiKawinDataLoader(
        getUserInfoUseCase, getAgamaUseCase, getPendidikanUseCase,
        getStatusKawinUseCase, stateManager, validator
    )
    private val formSubmitter = SKPergiKawinFormSubmitter(createSKPergiKawinUseCase, stateManager)

    // Events
    private val _skPergiKawinEvent = MutableSharedFlow<SKPergiKawinEvent>()
    val skPergiKawinEvent = _skPergiKawinEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKPergiKawinUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val pendidikanList: List<PendidikanResponse.Data> get() = stateManager.pendidikanList
    val isLoadingPendidikan: Boolean get() = stateManager.isLoadingPendidikan
    val pendidikanErrorMessage: String? get() = stateManager.pendidikanErrorMessage
    val statusKawinList: List<StatusKawinResponse.Data> get() = stateManager.statusKawinList
    val isLoadingStatusKawin: Boolean get() = stateManager.isLoadingStatusKawin
    val statusKawinErrorMessage: String? get() = stateManager.statusKawinErrorMessage
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
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val alamatValue: String get() = stateManager.alamatValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val statusKawinIdValue: String get() = stateManager.statusKawinIdValue
    val pendidikanIdValue: String get() = stateManager.pendidikanIdValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue

    // Form Data States - Step 2
    val keperluanValue: String get() = stateManager.keperluanValue
    val tujuanValue: String get() = stateManager.tujuanValue
    val berlakuMulaiValue: String get() = stateManager.berlakuMulaiValue
    val berlakuSampaiValue: String get() = stateManager.berlakuSampaiValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skPergiKawinEvent.emit(SKPergiKawinEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _skPergiKawinEvent.emit(SKPergiKawinEvent.AgamaLoadError(it.message ?: "Error"))
                }
                dataLoader.loadPendidikan().onFailure {
                    _skPergiKawinEvent.emit(SKPergiKawinEvent.PendidikanLoadError(it.message ?: "Error"))
                }
                dataLoader.loadStatusKawin().onFailure {
                    _skPergiKawinEvent.emit(SKPergiKawinEvent.StatusKawinLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skPergiKawinEvent.emit(SKPergiKawinEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadPendidikan() {
        viewModelScope.launch {
            dataLoader.loadPendidikan().onFailure {
                _skPergiKawinEvent.emit(SKPergiKawinEvent.PendidikanLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin().onFailure {
                _skPergiKawinEvent.emit(SKPergiKawinEvent.StatusKawinLoadError(it.message ?: "Error"))
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

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
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

    fun updateStatusKawinId(value: String) {
        stateManager.updateStatusKawinId(value)
        validator.clearFieldError("status_kawin_id")
    }

    fun updatePendidikanId(value: String) {
        stateManager.updatePendidikanId(value)
        validator.clearFieldError("pendidikan_id")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
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

    fun updateTujuan(value: String) {
        stateManager.updateTujuan(value)
        validator.clearFieldError("tujuan")
    }

    fun updateBerlakuMulai(value: String) {
        stateManager.updateBerlakuMulai(value)
        validator.clearFieldError("berlaku_mulai")
    }

    fun updateBerlakuSampai(value: String) {
        stateManager.updateBerlakuSampai(value)
        validator.clearFieldError("berlaku_sampai")
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
                _skPergiKawinEvent.emit(SKPergiKawinEvent.ValidationError)
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
                _skPergiKawinEvent.emit(SKPergiKawinEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skPergiKawinEvent.emit(SKPergiKawinEvent.SubmitSuccess) }
                .onFailure { _skPergiKawinEvent.emit(SKPergiKawinEvent.SubmitError(it.message ?: "Error")) }
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
                _skPergiKawinEvent.emit(SKPergiKawinEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skPergiKawinEvent.emit(SKPergiKawinEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKPergiKawinEvent {
        data class StepChanged(val step: Int) : SKPergiKawinEvent()
        data object SubmitSuccess : SKPergiKawinEvent()
        data class SubmitError(val message: String) : SKPergiKawinEvent()
        data object ValidationError : SKPergiKawinEvent()
        data class UserDataLoadError(val message: String) : SKPergiKawinEvent()
        data class AgamaLoadError(val message: String) : SKPergiKawinEvent()
        data class PendidikanLoadError(val message: String) : SKPergiKawinEvent()
        data class StatusKawinLoadError(val message: String) : SKPergiKawinEvent()
    }
}