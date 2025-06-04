package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTugasUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SuratTugasViewModel(
    createSuratTugasUseCase: CreateSuratTugasUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // Compose all components
    private val stateManager = SuratTugasStateManager()
    private val validator = SuratTugasValidator()
    private val dataLoader = SuratTugasDataLoader(getUserInfoUseCase)
    private val formSubmitter = SuratTugasSubmitter(createSuratTugasUseCase)

    // Events
    private val _tugasEvent = MutableSharedFlow<SuratTugasEvent>()
    val tugasEvent = _tugasEvent.asSharedFlow()

    // Public interface - delegate to components
    val uiState = stateManager.uiState
    val validationErrors = validator.validationErrors

    // Loading states
    val isLoading: Boolean
        get() = formSubmitter.isLoading

    val isLoadingUserData: Boolean
        get() = dataLoader.isLoadingUserData

    // Error states
    val errorMessage: String?
        get() = formSubmitter.errorMessage

    // Current states
    val currentStep: Int
        get() = stateManager.currentStep

    val useMyDataChecked: Boolean
        get() = dataLoader.useMyDataChecked

    val additionalRecipients: List<SuratTugasStateManager.AdditionalRecipient>
        get() = stateManager.additionalRecipients

    val showConfirmationDialog: Boolean
        get() = stateManager.showConfirmationDialog

    val showPreviewDialog: Boolean
        get() = stateManager.showPreviewDialog

    // Form values
    val nikValue: String
        get() = stateManager.nikValue

    val namaValue: String
        get() = stateManager.namaValue

    val jabatanValue: String
        get() = stateManager.jabatanValue

    val ditugaskanUntukValue: String
        get() = stateManager.ditugaskanUntukValue

    val deskripsiValue: String
        get() = stateManager.deskripsiValue

    val disahkanOlehValue: String
        get() = stateManager.disahkanOlehValue

    // Step 1 - Penerima Tugas Update Functions
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

    // Step 2 - Informasi Tugas Update Functions
    fun updateDitugaskanUntuk(value: String) {
        stateManager.updateDitugaskanUntuk(value)
        validator.clearFieldError("ditugaskan_untuk")
    }

    fun updateDeskripsi(value: String) {
        stateManager.updateDeskripsi(value)
        validator.clearFieldError("deskripsi")
    }

    fun updateDisahkanOleh(value: String) {
        stateManager.updateDisahkanOleh(value)
        validator.clearFieldError("disahkan_oleh")
    }

    // Additional Recipients Management
    fun addAdditionalRecipient() {
        stateManager.addAdditionalRecipient()
    }

    fun removeAdditionalRecipient(index: Int) {
        stateManager.removeAdditionalRecipient(index)
        validator.clearAdditionalRecipientErrors(index)
    }

    fun updateAdditionalRecipientNik(index: Int, value: String) {
        stateManager.updateAdditionalRecipientNik(index, value)
        validator.clearFieldError("nik_$index")
    }

    fun updateAdditionalRecipientNama(index: Int, value: String) {
        stateManager.updateAdditionalRecipientNama(index, value)
        validator.clearFieldError("nama_$index")
    }

    fun updateAdditionalRecipientJabatan(index: Int, value: String) {
        stateManager.updateAdditionalRecipientJabatan(index, value)
        validator.clearFieldError("jabatan_$index")
    }


    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        dataLoader.updateUseMyData(checked)
        if (checked) {
            loadUserData()
        } else {
            clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            dataLoader.loadUserData(
                onSuccess = { userData ->
                    stateManager.updateNik(userData.nik)
                    stateManager.updateNama(userData.nama_warga)
                    validator.clearMultipleFieldErrors(listOf("nik", "nama"))
                },
                onError = { message ->
                    _tugasEvent.emit(SuratTugasEvent.UserDataLoadError(message))
                }
            )
        }
    }

    private fun clearUserData() {
        stateManager.updateNik("")
        stateManager.updateNama("")
    }

    // Navigation
    fun nextStep() {
        when (currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    stateManager.updateCurrentStep(2)
                    viewModelScope.launch {
                        _tugasEvent.emit(SuratTugasEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    stateManager.showConfirmationDialog()
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            stateManager.updateCurrentStep(currentStep - 1)
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.StepChanged(currentStep))
            }
        }
    }

    // Dialog Management
    fun showPreview() {
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
            }
        }

        stateManager.showPreview()
    }

    fun dismissPreview() {
        stateManager.dismissPreview()
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            stateManager.showConfirmationDialog()
        } else {
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        stateManager.dismissConfirmationDialog()
    }

    fun confirmSubmit() {
        stateManager.dismissConfirmationDialog()
        submitForm()
    }

    // Validation Functions
    private fun validateStep1(): Boolean {
        return validator.validateStep1(
            nikValue, namaValue, jabatanValue, additionalRecipients
        )
    }

    private fun validateStep2(): Boolean {
        return validator.validateStep2(ditugaskanUntukValue, deskripsiValue)
    }

    private fun validateStep1WithEvent(): Boolean {
        val isValid = validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _tugasEvent.emit(SuratTugasEvent.ValidationError)
            }
        }
        return isValid
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2()
    }

    // Form Submission
    private fun submitForm() {
        viewModelScope.launch {
            formSubmitter.submitForm(
                stateManager = stateManager,
                onSuccess = {
                    _tugasEvent.emit(SuratTugasEvent.SubmitSuccess)
                    stateManager.resetForm()
                    validator.clearAllErrors()
                },
                onError = { message ->
                    _tugasEvent.emit(SuratTugasEvent.SubmitError(message))
                }
            )
        }
    }

    // Utility Functions
    fun getFieldError(fieldName: String): String? {
        return validator.getFieldError(fieldName)
    }

    fun hasFieldError(fieldName: String): Boolean {
        return validator.hasFieldError(fieldName)
    }

    fun clearError() {
        formSubmitter.clearError()
    }

    fun hasFormData(): Boolean {
        return stateManager.hasFormData()
    }

    // Events - copy dari kode asli
    sealed class SuratTugasEvent {
        data class StepChanged(val step: Int) : SuratTugasEvent()
        data object SubmitSuccess : SuratTugasEvent()
        data class SubmitError(val message: String) : SuratTugasEvent()
        data object ValidationError : SuratTugasEvent()
        data class UserDataLoadError(val message: String) : SuratTugasEvent()
    }
}