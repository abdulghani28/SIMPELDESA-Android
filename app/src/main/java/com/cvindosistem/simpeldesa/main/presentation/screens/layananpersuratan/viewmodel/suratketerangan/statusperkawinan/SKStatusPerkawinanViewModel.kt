package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratStatusPerkawinanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 5. Refactored ViewModel menggunakan composition
class SKStatusPerkawinanViewModel(
    createSuratCatatanStatusPerkawinanUseCase: CreateSuratStatusPerkawinanUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Composition components
    private val stateManager = SKStatusPerkawinanStateManager()
    private val validator = SKStatusPerkawinanValidator()
    private val dataLoader = SKStatusPerkawinanDataLoader(
        getUserInfoUseCase, getAgamaUseCase, getStatusKawinUseCase
    )
    private val formSubmitter = SKStatusPerkawinanFormSubmitter(createSuratCatatanStatusPerkawinanUseCase)

    // Public interface - delegate to components
    val uiState = MutableStateFlow(SKStatusPerkawinanUiState()).asStateFlow()
    val validationErrors = validator.validationErrors

    // Delegate properties to stateManager
    val nikValue get() = stateManager.nikValue
    val namaValue get() = stateManager.namaValue
    val tempatLahirValue get() = stateManager.tempatLahirValue
    val tanggalLahirValue get() = stateManager.tanggalLahirValue
    val selectedGender get() = stateManager.selectedGender
    val pekerjaanValue get() = stateManager.pekerjaanValue
    val alamatValue get() = stateManager.alamatValue
    val agamaValue get() = stateManager.agamaValue
    val statusKawinValue get() = stateManager.statusKawinValue
    val keperluanValue get() = stateManager.keperluanValue

    val currentStep get() = stateManager.currentStep
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val isLoading get() = stateManager.isLoading
    val errorMessage get() = stateManager.errorMessage
    val isLoadingUserData get() = stateManager.isLoadingUserData
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog

    // Delegate properties to dataLoader
    val agamaList get() = dataLoader.agamaList
    val isLoadingAgama get() = dataLoader.isLoadingAgama
    val agamaErrorMessage get() = dataLoader.agamaErrorMessage
    val statusKawinList get() = dataLoader.statusKawinList
    val isLoadingStatusKawin get() = dataLoader.isLoadingStatusKawin
    val statusKawinErrorMessage get() = dataLoader.statusKawinErrorMessage

    // Events - sama seperti sebelumnya
    private val _catatanStatusPerkawinanEvent = MutableSharedFlow<SKStatusPerkawinanEvent>()
    val catatanStatusPerkawinanEvent = _catatanStatusPerkawinanEvent.asSharedFlow()

    // Public methods - delegate to appropriate components
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

    fun updateAgama(value: String) {
        stateManager.updateAgama(value)
        validator.clearFieldError("agama_id")
    }

    fun updateStatusKawin(value: String) {
        stateManager.updateStatusKawin(value)
        validator.clearFieldError("status_kawin_id")
    }

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    fun updateUseMyData(checked: Boolean) {
        stateManager.setUseMyData(checked)
        if (checked) {
            loadUserData()
            loadAgama()
            loadStatusKawin()
        } else {
            stateManager.clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            dataLoader.loadUserData(
                stateManager = stateManager,
                validator = validator,
                onSuccess = {},
                onError = { message ->
                    stateManager.setError(message)
                    _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.UserDataLoadError(message))
                }
            )
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama { message ->
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.AgamaLoadError(message))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin { message ->
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.StatusKawinLoadError(message))
            }
        }
    }

    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        if (!step1Valid) {
            viewModelScope.launch {
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.ValidationError)
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
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.dismissConfirmation()

    fun confirmSubmit() {
        stateManager.dismissConfirmation()
        submitForm()
    }

    private fun submitForm() {
        viewModelScope.launch {
            stateManager.updateLoading(true)
            stateManager.setError(null)

            val request = stateManager.createRequest()

            formSubmitter.submitForm(
                request = request,
                onSuccess = {
                    _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.SubmitSuccess)
                    stateManager.resetForm()
                    validator.clearAllErrors()
                },
                onError = { message ->
                    stateManager.setError(message)
                    _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.SubmitError(message))
                }
            )

            stateManager.updateLoading(false)
        }
    }

    // Validation methods
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun clearError() = stateManager.setError(null)
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Events class - copy langsung dari kode asli
    sealed class SKStatusPerkawinanEvent {
        data class StepChanged(val step: Int) : SKStatusPerkawinanEvent()
        data object SubmitSuccess : SKStatusPerkawinanEvent()
        data class SubmitError(val message: String) : SKStatusPerkawinanEvent()
        data object ValidationError : SKStatusPerkawinanEvent()
        data class UserDataLoadError(val message: String) : SKStatusPerkawinanEvent()
        data class AgamaLoadError(val message: String) : SKStatusPerkawinanEvent()
        data class StatusKawinLoadError(val message: String) : SKStatusPerkawinanEvent()
    }
}