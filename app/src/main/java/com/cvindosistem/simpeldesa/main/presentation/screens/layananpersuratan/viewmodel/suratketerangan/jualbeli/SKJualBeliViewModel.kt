package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJualBeliUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKJualBeliViewModel(
    createSKJualBeliUseCase: CreateSuratJualBeliUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKJualBeliStateManager()
    private val validator = SKJualBeliValidator(stateManager)
    private val dataLoader = SKJualBeliDataLoader(getUserInfoUseCase, stateManager, validator)
    private val formSubmitter = SKJualBeliFormSubmitter(createSKJualBeliUseCase, stateManager)

    // Events
    private val _skJualBeliEvent = MutableSharedFlow<SKJualBeliEvent>()
    val skJualBeliEvent = _skJualBeliEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKJualBeliUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Penjual)
    val nik1Value: String get() = stateManager.nik1Value
    val nama1Value: String get() = stateManager.nama1Value
    val alamat1Value: String get() = stateManager.alamat1Value
    val jenisKelamin1Value: String get() = stateManager.jenisKelamin1Value
    val pekerjaan1Value: String get() = stateManager.pekerjaan1Value
    val tanggalLahir1Value: String get() = stateManager.tanggalLahir1Value
    val tempatLahir1Value: String get() = stateManager.tempatLahir1Value

    // Form field properties - Step 2 (Pembeli)
    val nik2Value: String get() = stateManager.nik2Value
    val nama2Value: String get() = stateManager.nama2Value
    val alamat2Value: String get() = stateManager.alamat2Value
    val jenisKelamin2Value: String get() = stateManager.jenisKelamin2Value
    val pekerjaan2Value: String get() = stateManager.pekerjaan2Value
    val tanggalLahir2Value: String get() = stateManager.tanggalLahir2Value
    val tempatLahir2Value: String get() = stateManager.tempatLahir2Value

    // Form field properties - Step 3 (Detail Jual Beli)
    val jenisBarangValue: String get() = stateManager.jenisBarangValue
    val rincianBarangValue: String get() = stateManager.rincianBarangValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skJualBeliEvent.emit(SKJualBeliEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 Update Functions (Penjual)
    fun updateNik1(value: String) {
        stateManager.updateNik1(value)
        validator.clearFieldError("nik_1")
    }

    fun updateNama1(value: String) {
        stateManager.updateNama1(value)
        validator.clearFieldError("nama_1")
    }

    fun updateAlamat1(value: String) {
        stateManager.updateAlamat1(value)
        validator.clearFieldError("alamat_1")
    }

    fun updateJenisKelamin1(value: String) {
        stateManager.updateJenisKelamin1(value)
        validator.clearFieldError("jenis_kelamin_1")
    }

    fun updatePekerjaan1(value: String) {
        stateManager.updatePekerjaan1(value)
        validator.clearFieldError("pekerjaan_1")
    }

    fun updateTanggalLahir1(value: String) {
        stateManager.updateTanggalLahir1(value)
        validator.clearFieldError("tanggal_lahir_1")
    }

    fun updateTempatLahir1(value: String) {
        stateManager.updateTempatLahir1(value)
        validator.clearFieldError("tempat_lahir_1")
    }

    // Step 2 Update Functions (Pembeli)
    fun updateNik2(value: String) {
        stateManager.updateNik2(value)
        validator.clearFieldError("nik_2")
    }

    fun updateNama2(value: String) {
        stateManager.updateNama2(value)
        validator.clearFieldError("nama_2")
    }

    fun updateAlamat2(value: String) {
        stateManager.updateAlamat2(value)
        validator.clearFieldError("alamat_2")
    }

    fun updateJenisKelamin2(value: String) {
        stateManager.updateJenisKelamin2(value)
        validator.clearFieldError("jenis_kelamin_2")
    }

    fun updatePekerjaan2(value: String) {
        stateManager.updatePekerjaan2(value)
        validator.clearFieldError("pekerjaan_2")
    }

    fun updateTanggalLahir2(value: String) {
        stateManager.updateTanggalLahir2(value)
        validator.clearFieldError("tanggal_lahir_2")
    }

    fun updateTempatLahir2(value: String) {
        stateManager.updateTempatLahir2(value)
        validator.clearFieldError("tempat_lahir_2")
    }

    // Step 3 Update Functions (Detail Jual Beli)
    fun updateJenisBarang(value: String) {
        stateManager.updateJenisBarang(value)
        validator.clearFieldError("jenis_barang")
    }

    fun updateRincianBarang(value: String) {
        stateManager.updateRincianBarang(value)
        validator.clearFieldError("rincian_barang")
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
                _skJualBeliEvent.emit(SKJualBeliEvent.ValidationError)
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
                _skJualBeliEvent.emit(SKJualBeliEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skJualBeliEvent.emit(SKJualBeliEvent.SubmitSuccess) }
                .onFailure { _skJualBeliEvent.emit(SKJualBeliEvent.SubmitError(it.message ?: "Error")) }
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
                _skJualBeliEvent.emit(SKJualBeliEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skJualBeliEvent.emit(SKJualBeliEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKJualBeliEvent {
        data class StepChanged(val step: Int) : SKJualBeliEvent()
        data object SubmitSuccess : SKJualBeliEvent()
        data class SubmitError(val message: String) : SKJualBeliEvent()
        data object ValidationError : SKJualBeliEvent()
        data class UserDataLoadError(val message: String) : SKJualBeliEvent()
    }
}