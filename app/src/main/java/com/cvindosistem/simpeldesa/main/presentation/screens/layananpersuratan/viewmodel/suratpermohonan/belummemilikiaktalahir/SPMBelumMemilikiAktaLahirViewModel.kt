package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.belummemilikiaktalahir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBelumMemilikiAktaLahirUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMBelumMemilikiAktaLahirViewModel(
    createSPMBelumMemilikiAktaLahirUseCase: CreateSuratBelumMemilikiAktaLahirUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMBelumMemilikiAktaLahirStateManager()
    private val validator = SPMBelumMemilikiAktaLahirValidator(stateManager)
    private val dataLoader = SPMBelumMemilikiAktaLahirDataLoader(
        getUserInfoUseCase, stateManager, validator
    )
    private val formSubmitter = SPMBelumMemilikiAktaLahirFormSubmitter(
        createSPMBelumMemilikiAktaLahirUseCase, stateManager
    )

    // Events
    private val _spmBelumMemilikiAktaLahirEvent = MutableSharedFlow<SPMBelumMemilikiAktaLahirEvent>()
    val spmBelumMemilikiAktaLahirEvent = _spmBelumMemilikiAktaLahirEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPMBelumMemilikiAktaLahirUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Informasi Pelapor)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue

    // Form field properties - Step 2 (Informasi Orang Tua)
    val namaAyahValue: String get() = stateManager.namaAyahValue
    val nikAyahValue: String get() = stateManager.nikAyahValue
    val namaIbuValue: String get() = stateManager.namaIbuValue
    val nikIbuValue: String get() = stateManager.nikIbuValue

    // Public functions - Use My Data
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmBelumMemilikiAktaLahirEvent.emit(
                        SPMBelumMemilikiAktaLahirEvent.UserDataLoadError(it.message ?: "Error")
                    )
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 update functions (Informasi Pelapor)
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

    // Step 2 update functions (Informasi Orang Tua)
    fun updateNamaAyah(value: String) {
        stateManager.updateNamaAyah(value)
        validator.clearFieldError("nama_ayah")
    }

    fun updateNikAyah(value: String) {
        stateManager.updateNikAyah(value)
        validator.clearFieldError("nik_ayah")
    }

    fun updateNamaIbu(value: String) {
        stateManager.updateNamaIbu(value)
        validator.clearFieldError("nama_ibu")
    }

    fun updateNikIbu(value: String) {
        stateManager.updateNikIbu(value)
        validator.clearFieldError("nik_ibu")
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
                _spmBelumMemilikiAktaLahirEvent.emit(SPMBelumMemilikiAktaLahirEvent.ValidationError)
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
                _spmBelumMemilikiAktaLahirEvent.emit(SPMBelumMemilikiAktaLahirEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess {
                    _spmBelumMemilikiAktaLahirEvent.emit(SPMBelumMemilikiAktaLahirEvent.SubmitSuccess)
                }
                .onFailure {
                    _spmBelumMemilikiAktaLahirEvent.emit(
                        SPMBelumMemilikiAktaLahirEvent.SubmitError(it.message ?: "Error")
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
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _spmBelumMemilikiAktaLahirEvent.emit(SPMBelumMemilikiAktaLahirEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmBelumMemilikiAktaLahirEvent.emit(
                SPMBelumMemilikiAktaLahirEvent.StepChanged(stateManager.currentStep)
            )
        }
    }

    // Events
    sealed class SPMBelumMemilikiAktaLahirEvent {
        data class StepChanged(val step: Int) : SPMBelumMemilikiAktaLahirEvent()
        data object SubmitSuccess : SPMBelumMemilikiAktaLahirEvent()
        data class SubmitError(val message: String) : SPMBelumMemilikiAktaLahirEvent()
        data object ValidationError : SPMBelumMemilikiAktaLahirEvent()
        data class UserDataLoadError(val message: String) : SPMBelumMemilikiAktaLahirEvent()
    }
}