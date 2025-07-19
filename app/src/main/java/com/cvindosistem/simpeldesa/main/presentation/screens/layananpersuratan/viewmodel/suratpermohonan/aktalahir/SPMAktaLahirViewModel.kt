package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratAktaLahirUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMAktaLahirViewModel(
    createSPMAktaLahirUseCase: CreateSuratAktaLahirUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMAktaLahirStateManager()
    private val validator = SPMAktaLahirValidator(stateManager)
    private val dataLoader = SPMAktaLahirDataLoader(getUserInfoUseCase, stateManager, validator)
    private val formSubmitter = SPMAktaLahirFormSubmitter(createSPMAktaLahirUseCase, stateManager)

    // Events
    private val _spmAktaLahirEvent = MutableSharedFlow<SPMAktaLahirEvent>()
    val spmAktaLahirEvent = _spmAktaLahirEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPMAktaLahirUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Pelapor)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue

    // Form field properties - Step 2 (Anak)
    val namaAnakValue: String get() = stateManager.namaAnakValue
    val tanggalLahirAnakValue: String get() = stateManager.tanggalLahirAnakValue
    val tempatLahirAnakValue: String get() = stateManager.tempatLahirAnakValue
    val alamatAnakValue: String get() = stateManager.alamatAnakValue

    // Form field properties - Step 3 (Orang Tua)
    val namaAyahValue: String get() = stateManager.namaAyahValue
    val nikAyahValue: String get() = stateManager.nikAyahValue
    val namaIbuValue: String get() = stateManager.namaIbuValue
    val nikIbuValue: String get() = stateManager.nikIbuValue
    val alamatOrangTuaValue: String get() = stateManager.alamatOrangTuaValue

    // Form field properties - Step 4 (Keperluan)
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmAktaLahirEvent.emit(SPMAktaLahirEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 Update Functions (Pelapor)
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

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    // Step 2 Update Functions (Anak)
    fun updateNamaAnak(value: String) {
        stateManager.updateNamaAnak(value)
        validator.clearFieldError("nama_anak")
    }

    fun updateTanggalLahirAnak(value: String) {
        stateManager.updateTanggalLahirAnak(value)
        validator.clearFieldError("tanggal_lahir_anak")
    }

    fun updateTempatLahirAnak(value: String) {
        stateManager.updateTempatLahirAnak(value)
        validator.clearFieldError("tempat_lahir_anak")
    }

    fun updateAlamatAnak(value: String) {
        stateManager.updateAlamatAnak(value)
        validator.clearFieldError("alamat_anak")
    }

    // Step 3 Update Functions (Orang Tua)
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

    fun updateAlamatOrangTua(value: String) {
        stateManager.updateAlamatOrangTua(value)
        validator.clearFieldError("alamat_orang_tua")
    }

    // Step 4 Update Functions (Keperluan)
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
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            4 -> {
                if (validateStepWithEvent(4)) {
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
                _spmAktaLahirEvent.emit(SPMAktaLahirEvent.ValidationError)
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
                _spmAktaLahirEvent.emit(SPMAktaLahirEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spmAktaLahirEvent.emit(SPMAktaLahirEvent.SubmitSuccess) }
                .onFailure { _spmAktaLahirEvent.emit(SPMAktaLahirEvent.SubmitError(it.message ?: "Error")) }
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
            4 -> validator.validateStep4()
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _spmAktaLahirEvent.emit(SPMAktaLahirEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmAktaLahirEvent.emit(SPMAktaLahirEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPMAktaLahirEvent {
        data class StepChanged(val step: Int) : SPMAktaLahirEvent()
        data object SubmitSuccess : SPMAktaLahirEvent()
        data class SubmitError(val message: String) : SPMAktaLahirEvent()
        data object ValidationError : SPMAktaLahirEvent()
        data class UserDataLoadError(val message: String) : SPMAktaLahirEvent()
    }
}