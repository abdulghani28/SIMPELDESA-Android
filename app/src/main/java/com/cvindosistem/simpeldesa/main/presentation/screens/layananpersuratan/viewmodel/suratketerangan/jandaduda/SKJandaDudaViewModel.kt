package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJandaDudaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKJandaDudaViewModel(
    createSKJandaDudaUseCase: CreateSuratJandaDudaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKJandaDudaStateManager()
    private val validator = SKJandaDudaValidator(stateManager)
    private val dataLoader = SKJandaDudaDataLoader(getUserInfoUseCase, getAgamaUseCase, stateManager, validator)
    private val formSubmitter = SKJandaDudaFormSubmitter(createSKJandaDudaUseCase, stateManager)

    // Events
    private val _skJandaDudaEvent = MutableSharedFlow<SKJandaDudaEvent>()
    val skJandaDudaEvent = _skJandaDudaEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKJandaDudaUiState()).asStateFlow()

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
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val alamatValue: String get() = stateManager.alamatValue

    // Form Data States - Step 2
    val dasarPengajuanValue: String get() = stateManager.dasarPengajuanValue
    val nomorPengajuanValue: String get() = stateManager.nomorPengajuanValue
    val penyebabValue: String get() = stateManager.penyebabValue

    // Form Data States - Step 3
    val keperluanValue: String get() = stateManager.keperluanValue
    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skJandaDudaEvent.emit(SKJandaDudaEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _skJandaDudaEvent.emit(SKJandaDudaEvent.AgamaLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 update functions - delegate to stateManager dan clear errors
// Step 1 Update Functions
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

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    // Step 2 Update Functions
    fun updateDasarPengajuan(value: String) {
        stateManager.updateDasarPengajuan(value)
        validator.clearFieldError("dasar_pengajuan")

        if (value == "Surat Keterangan Kematian") {
            stateManager.updatePenyebab("cerai_mati")
        }
    }

    fun updateNomorPengajuan(value: String) {
        stateManager.updateNomorPengajuan(value)
        validator.clearFieldError("nomor_pengajuan")
    }

    fun updatePenyebab(value: String) {
        stateManager.updatePenyebab(value)
        validator.clearFieldError("penyebab")
    }

    // Step 3 Update Function
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
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
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
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }
    }
    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skJandaDudaEvent.emit(SKJandaDudaEvent.SubmitSuccess) }
                .onFailure { _skJandaDudaEvent.emit(SKJandaDudaEvent.SubmitError(it.message ?: "Error")) }
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
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skJandaDudaEvent.emit(SKJandaDudaEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events - copy langsung dari kode asli
    sealed class SKJandaDudaEvent {
        data class StepChanged(val step: Int) : SKJandaDudaEvent()
        data object SubmitSuccess : SKJandaDudaEvent()
        data class SubmitError(val message: String) : SKJandaDudaEvent()
        data object ValidationError : SKJandaDudaEvent()
        data class UserDataLoadError(val message: String) : SKJandaDudaEvent()
        data class AgamaLoadError(val message: String) : SKJandaDudaEvent()
    }
}