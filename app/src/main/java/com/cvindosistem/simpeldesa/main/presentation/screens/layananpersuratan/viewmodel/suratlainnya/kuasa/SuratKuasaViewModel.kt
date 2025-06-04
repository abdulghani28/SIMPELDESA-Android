package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKuasaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// 5. Refactored ViewModel - Composition dari semua komponen
class SuratKuasaViewModel(
    createSuratKuasaUseCase: CreateSuratKuasaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // Composed components
    private val stateManager = SuratKuasaStateManager()
    private val validator = SuratKuasaValidator()
    private val dataLoader = SuratKuasaDataLoader(getUserInfoUseCase)
    private val formSubmitter = SuratKuasaFormSubmitter(createSuratKuasaUseCase)

    // Events
    private val _kuasaEvent = MutableSharedFlow<SuratKuasaEvent>()
    val kuasaEvent = _kuasaEvent.asSharedFlow()

    // Expose public interface - SAMA SEPERTI SEBELUMNYA
    val uiState = stateManager.uiState
    val validationErrors = validator.validationErrors

    // State properties (delegated to stateManager)
    val isLoading by stateManager::isLoading
    val errorMessage by stateManager::errorMessage
    val currentStep by stateManager::currentStep
    val useMyDataChecked by stateManager::useMyDataChecked
    val isLoadingUserData by stateManager::isLoadingUserData
    val showConfirmationDialog by stateManager::showConfirmationDialog
    val showPreviewDialog by stateManager::showPreviewDialog

    // Form fields (delegated to stateManager)
    val nikValue by stateManager::nikValue
    val namaValue by stateManager::namaValue
    val jabatanValue by stateManager::jabatanValue
    val kuasaSebagaiValue by stateManager::kuasaSebagaiValue
    val kuasaUntukValue by stateManager::kuasaUntukValue
    val nikPenerimaValue by stateManager::nikPenerimaValue
    val namaPenerimaValue by stateManager::namaPenerimaValue
    val jabatanPenerima by stateManager::jabatanPenerima

    // Public methods - Interface tetap sama

    // Field updates
    fun updateNik(value: String) {
        stateManager.updateNik(value)
        validator.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateJabatan(value: String) {
        stateManager.updateJabatan(value)
        validator.clearFieldError("jabatan")
    }

    fun updateKuasaSebagai(value: String) {
        stateManager.updateKuasaSebagai(value)
        validator.clearFieldError("kuasa_sebagai")
    }

    fun updateKuasaUntuk(value: String) {
        stateManager.updateKuasaUntuk(value)
        validator.clearFieldError("kuasa_untuk")
    }

    fun updateNikPenerima(value: String) {
        stateManager.updateNikPenerima(value)
        validator.clearFieldError("nik_penerima")
    }

    fun updateNamaPenerima(value: String) {
        stateManager.updateNamaPenerima(value)
        validator.clearFieldError("nama_penerima")
    }

    fun updateJabatanPenerima(value: String) {
        stateManager.updateJabatanPenerima(value)
        validator.clearFieldError("jabatan_penerima")
    }

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        stateManager.setUseMyData(checked)
        if (checked) {
            loadUserData()
        } else {
            stateManager.clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            stateManager.setUserDataLoading(true)

            when (val result = dataLoader.loadUserData()) {
                is SuratKuasaDataLoader.UserDataResult.Success -> {
                    stateManager.populateUserData(result.nik, result.nama)
                    validator.clearMultipleFieldErrors(listOf("nik", "nama"))
                }
                is SuratKuasaDataLoader.UserDataResult.Error -> {
                    stateManager.setError(result.message)
                    stateManager.setUseMyData(false)
                    _kuasaEvent.emit(SuratKuasaEvent.UserDataLoadError(result.message))
                }
            }

            stateManager.setUserDataLoading(false)
        }
    }

    // Navigation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    stateManager.nextStep()
                    viewModelScope.launch {
                        _kuasaEvent.emit(SuratKuasaEvent.StepChanged(stateManager.currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    stateManager.showConfirmation()
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.StepChanged(stateManager.currentStep))
            }
        }
    }

    // Validation with events
    private fun validateStep1WithEvent(): Boolean {
        val isValid = validator.validateStep1(
            stateManager.nikValue, stateManager.namaValue, stateManager.jabatanValue,
            stateManager.kuasaUntukValue, stateManager.kuasaSebagaiValue
        )
        if (!isValid) {
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validator.validateStep2(
            stateManager.nikPenerimaValue,
            stateManager.namaPenerimaValue,
            stateManager.jabatanPenerima
        )
        if (!isValid) {
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
            }
        }
        return isValid
    }

    // Dialog functions
    fun showPreview() {
        val step1Valid = validator.validateStep1(
            stateManager.nikValue, stateManager.namaValue, stateManager.jabatanValue,
            stateManager.kuasaUntukValue, stateManager.kuasaSebagaiValue
        )
        val step2Valid = validator.validateStep2(
            stateManager.nikPenerimaValue,
            stateManager.namaPenerimaValue,
            stateManager.jabatanPenerima
        )

        if (!step1Valid || !step2Valid) {
            viewModelScope.launch {
                _kuasaEvent.emit(SuratKuasaEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()
    fun showConfirmationDialog() = stateManager.showConfirmation()
    fun dismissConfirmationDialog() = stateManager.dismissConfirmation()

    fun confirmSubmit() {
        stateManager.dismissConfirmation()
        submitForm()
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            stateManager.updateLoading(true)
            stateManager.setError(null)

            when (val result = formSubmitter.submitForm(stateManager.createRequest())) {
                is SuratKuasaFormSubmitter.SubmitResult.Success -> {
                    _kuasaEvent.emit(SuratKuasaEvent.SubmitSuccess)
                    stateManager.resetForm()
                }
                is SuratKuasaFormSubmitter.SubmitResult.Error -> {
                    stateManager.setError(result.message)
                    _kuasaEvent.emit(SuratKuasaEvent.SubmitError(result.message))
                }
            }

            stateManager.updateLoading(false)
        }
    }

    // Validation helper methods (delegated to validator)
    fun validateAllSteps(): Boolean = validator.validateAllSteps(
        stateManager.nikValue, stateManager.namaValue, stateManager.jabatanValue,
        stateManager.kuasaUntukValue, stateManager.kuasaSebagaiValue,
        stateManager.nikPenerimaValue, stateManager.namaPenerimaValue,
        stateManager.jabatanPenerima
    )

    fun getFieldError(fieldName: String) = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String) = validator.hasFieldError(fieldName)
    fun clearError() = stateManager.setError(null)
    fun hasFormData() = stateManager.hasFormData()

    // Events - SAMA SEPERTI KODE ASLI
    sealed class SuratKuasaEvent {
        data class StepChanged(val step: Int) : SuratKuasaEvent()
        data object SubmitSuccess : SuratKuasaEvent()
        data class SubmitError(val message: String) : SuratKuasaEvent()
        data object ValidationError : SuratKuasaEvent()
        data class UserDataLoadError(val message: String) : SuratKuasaEvent()
    }
}