package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.catatankepolisian

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratSKCKUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SPCatatanKepolisianViewModel(
    createSuratCatatanKepolisianUseCase: CreateSuratSKCKUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Compose all components
    private val stateManager = SPCatatanKepolisianStateManager()
    private val validator = SPCatatanKepolisianValidator()
    private val dataLoader = SPCatatanKepolisianDataLoader(getUserInfoUseCase)
    private val formSubmitter = SPCatatanKepolisianSubmitter(createSuratCatatanKepolisianUseCase)

    // Events
    private val _catatanKepolisianEvent = MutableSharedFlow<SPCatatanKepolisianEvent>()
    val catatanKepolisianEvent = _catatanKepolisianEvent.asSharedFlow()

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

    val showConfirmationDialog: Boolean
        get() = stateManager.showConfirmationDialog

    val showPreviewDialog: Boolean
        get() = stateManager.showPreviewDialog

    // Form values
    val nikValue: String
        get() = stateManager.nikValue

    val namaValue: String
        get() = stateManager.namaValue

    val tempatLahirValue: String
        get() = stateManager.tempatLahirValue

    val tanggalLahirValue: String
        get() = stateManager.tanggalLahirValue

    val selectedGender: String
        get() = stateManager.selectedGender

    val pekerjaanValue: String
        get() = stateManager.pekerjaanValue

    val alamatValue: String
        get() = stateManager.alamatValue

    val keperluanValue: String
        get() = stateManager.keperluanValue

    // Step 1 - Informasi Pelapor Update Functions
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
        stateManager.updateTanggalLahir(dateFormatterToApiFormat(value))
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateGender(value: String) {
        stateManager.updateGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    // Step 2 - Keperluan Update Functions
    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
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
                    stateManager.fillUserData(userData)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan", "alamat"
                    ))
                },
                onError = { message ->
                    _catatanKepolisianEvent.emit(SPCatatanKepolisianEvent.UserDataLoadError(message))
                }
            )
        }
    }

    private fun clearUserData() {
        stateManager.clearUserData()
    }

    // Dialog Management
    fun showPreview() {
        val isValid = validateStep1()

        if (!isValid) {
            viewModelScope.launch {
                _catatanKepolisianEvent.emit(SPCatatanKepolisianEvent.ValidationError)
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
                _catatanKepolisianEvent.emit(SPCatatanKepolisianEvent.ValidationError)
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
            nikValue, namaValue, tempatLahirValue, tanggalLahirValue,
            selectedGender, pekerjaanValue, alamatValue, keperluanValue
        )
    }

    fun validateAllSteps(): Boolean {
        return validateStep1()
    }

    // Form Submission
    private fun submitForm() {
        viewModelScope.launch {
            formSubmitter.submitForm(
                stateManager = stateManager,
                onSuccess = {
                    _catatanKepolisianEvent.emit(SPCatatanKepolisianEvent.SubmitSuccess)
                    stateManager.resetForm()
                    validator.clearAllErrors()
                },
                onError = { message ->
                    _catatanKepolisianEvent.emit(SPCatatanKepolisianEvent.SubmitError(message))
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
    sealed class SPCatatanKepolisianEvent {
        data class StepChanged(val step: Int) : SPCatatanKepolisianEvent()
        data object SubmitSuccess : SPCatatanKepolisianEvent()
        data class SubmitError(val message: String) : SPCatatanKepolisianEvent()
        data object ValidationError : SPCatatanKepolisianEvent()
        data class UserDataLoadError(val message: String) : SPCatatanKepolisianEvent()
    }
}