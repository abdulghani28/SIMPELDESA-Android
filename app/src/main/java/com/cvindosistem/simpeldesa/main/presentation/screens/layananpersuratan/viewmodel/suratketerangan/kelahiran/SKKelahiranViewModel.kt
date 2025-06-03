package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKelahiranRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKelahiranUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKelahiranViewModel(
    createSKKelahiranUseCase: CreateSuratKelahiranUseCase,
) : ViewModel() {

    // Composed components
    private val stateManager = SKKelahiranStateManager()
    private val validator = SKKelahiranValidator()
    private val formSubmitter = SKKelahiranFormSubmitter(createSKKelahiranUseCase)

    // Expose state through delegation
    val isLoading by stateManager::isLoading
    val errorMessage by stateManager::errorMessage
    val currentStep by stateManager::currentStep
    val useMyDataChecked by stateManager::useMyDataChecked
    val isLoadingUserData by stateManager::isLoadingUserData
    val showConfirmationDialog by stateManager::showConfirmationDialog
    val showPreviewDialog by stateManager::showPreviewDialog

    // Expose form values
    val namaValue by stateManager::namaValue
    val jenisKelaminValue by stateManager::jenisKelaminValue
    val tempatLahirValue by stateManager::tempatLahirValue
    val tanggalLahirValue by stateManager::tanggalLahirValue
    val jamLahirValue by stateManager::jamLahirValue
    val anakKeValue by stateManager::anakKeValue

    val namaAyahValue by stateManager::namaAyahValue
    val nikAyahValue by stateManager::nikAyahValue
    val tempatLahirAyahValue by stateManager::tempatLahirAyahValue
    val tanggalLahirAyahValue by stateManager::tanggalLahirAyahValue
    val alamatAyahValue by stateManager::alamatAyahValue

    val namaIbuValue by stateManager::namaIbuValue
    val nikIbuValue by stateManager::nikIbuValue
    val tempatLahirIbuValue by stateManager::tempatLahirIbuValue
    val tanggalLahirIbuValue by stateManager::tanggalLahirIbuValue
    val alamatIbuValue by stateManager::alamatIbuValue

    val keperluanValue by stateManager::keperluanValue

    // Expose validation
    val validationErrors = validator.validationErrors

    // Events
    private val _skKelahiranEvent = MutableSharedFlow<SKKelahiranEvent>()
    val skKelahiranEvent = _skKelahiranEvent.asSharedFlow()

    // UI State for the form
    private val _uiState = MutableStateFlow(SKKelahiranUiState())
    val uiState = _uiState.asStateFlow()

    // Delegate update functions
    // Step 1 - Informasi Anak Update Functions
    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateJamLahir(value: String) {
        stateManager.updateJamLahir(value)
        validator.clearFieldError("jam_lahir")
    }

    fun updateAnakKe(value: Int) {
        stateManager.updateAnakKe(value)
        validator.clearFieldError("anak_ke")
    }

    // Step 2 - Informasi Ayah Update Functions
    fun updateNamaAyah(value: String) {
        stateManager.updateNamaAyah(value)
        validator.clearFieldError("nama_ayah")
    }

    fun updateNikAyah(value: String) {
        stateManager.updateNikAyah(value)
        validator.clearFieldError("nik_ayah")
    }

    fun updateTempatLahirAyah(value: String) {
        stateManager.updateTempatLahirAyah(value)
        validator.clearFieldError("tempat_lahir_ayah")
    }

    fun updateTanggalLahirAyah(value: String) {
        stateManager.updateTanggalLahirAyah(value)
        validator.clearFieldError("tanggal_lahir_ayah")
    }

    fun updateAlamatAyah(value: String) {
        stateManager.updateAlamatAyah(value)
        validator.clearFieldError("alamat_ayah")
    }

    // Step 3 - Informasi Ibu Update Functions
    fun updateNamaIbu(value: String) {
        stateManager.updateNamaIbu(value)
        validator.clearFieldError("nama_ibu")
    }

    fun updateNikIbu(value: String) {
        stateManager.updateNikIbu(value)
        validator.clearFieldError("nik_ibu")
    }

    fun updateTempatLahirIbu(value: String) {
        stateManager.updateTempatLahirIbu(value)
        validator.clearFieldError("tempat_lahir_ibu")
    }

    fun updateTanggalLahirIbu(value: String) {
        stateManager.updateTanggalLahirIbu(value)
        validator.clearFieldError("tanggal_lahir_ibu")
    }

    fun updateAlamatIbu(value: String) {
        stateManager.updateAlamatIbu(value)
        validator.clearFieldError("alamat_ibu")
    }

    // Step 4 - Keperluan Update Functions
    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Step navigation with validation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStepWithEvent(1)) {
                    stateManager.nextStep()
                    emitStepChanged()
                }
            }
            2 -> {
                if (validateStepWithEvent(2)) {
                    stateManager.nextStep()
                    emitStepChanged()
                }
            }
            3 -> {
                if (validateStepWithEvent(3)) {
                    stateManager.nextStep()
                    emitStepChanged()
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
        stateManager.previousStep()
        emitStepChanged()
    }

    private fun validateStepWithEvent(step: Int): Boolean {
        val isValid = when (step) {
            1 -> validator.validateStep1(stateManager)
            2 -> validator.validateStep2(stateManager)
            3 -> validator.validateStep3(stateManager)
            4 -> validator.validateStep4(stateManager)
            else -> false
        }

        if (!isValid) {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChanged() {
        viewModelScope.launch {
            _skKelahiranEvent.emit(SKKelahiranEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Dialog functions
    fun showPreview() {
        val allValid = validator.validateAllSteps(stateManager)
        if (!allValid) {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.showConfirmation()
        } else {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.dismissConfirmationDialog()

    fun confirmSubmit() {
        stateManager.dismissConfirmationDialog()
        submitForm()
    }

    private fun submitForm() {
        viewModelScope.launch {
            when (val result = formSubmitter.submitForm(stateManager)) {
                is SuratKelahiranResult.Success -> {
                    _skKelahiranEvent.emit(SKKelahiranEvent.SubmitSuccess)
                    stateManager.resetForm()
                    validator.clearAllErrors()
                }
                is SuratKelahiranResult.Error -> {
                    _skKelahiranEvent.emit(SKKelahiranEvent.SubmitError(result.message))
                }
            }
        }
    }

    // Validation helper functions
    fun validateAllSteps() = validator.validateAllSteps(stateManager)
    fun getFieldError(fieldName: String) = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String) = validator.hasFieldError(fieldName)
    fun clearError() = stateManager.clearError()
    fun hasFormData() = stateManager.hasFormData()

    // Events - Copy dari kode asli
    sealed class SKKelahiranEvent {
        data class StepChanged(val step: Int) : SKKelahiranEvent()
        data object SubmitSuccess : SKKelahiranEvent()
        data class SubmitError(val message: String) : SKKelahiranEvent()
        data object ValidationError : SKKelahiranEvent()
        data class UserDataLoadError(val message: String) : SKKelahiranEvent()
    }
}