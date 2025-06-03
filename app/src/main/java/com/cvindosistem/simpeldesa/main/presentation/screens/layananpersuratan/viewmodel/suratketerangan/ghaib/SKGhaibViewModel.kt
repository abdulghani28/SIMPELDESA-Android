package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ghaib

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratGhaibResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratGhaibUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.onSuccess

class SKGhaibViewModel(
    createSKGhaibUseCase: CreateSuratGhaibUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // Components
    private val stateManager = SKGhaibStateManager()
    private val validator = SKGhaibValidator()
    private val dataLoader = SKGhaibDataLoader(getUserInfoUseCase)
    private val formSubmitter = SKGhaibFormSubmitter(createSKGhaibUseCase)

    // UI State for the form
    private val _uiState = MutableStateFlow(SKGhaibUiState())
    val uiState = _uiState.asStateFlow()

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _skGhaibEvent = MutableSharedFlow<SKGhaibEvent>()
    val skGhaibEvent = _skGhaibEvent.asSharedFlow()

    // Public interface - delegate ke components

    // Loading states
    val isLoading: Boolean get() = formSubmitter.isLoading
    val isLoadingUserData: Boolean get() = dataLoader.isLoadingUserData

    // Form states (delegate ke StateManager)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val hubunganIdValue: String get() = stateManager.hubunganIdValue
    val namaOrangHilangValue: String get() = stateManager.namaOrangHilangValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val usiaValue: Int get() = stateManager.usiaValue
    val alamatValue: String get() = stateManager.alamatValue
    val hilangSejakValue: String get() = stateManager.hilangSejakValue

    // UI states
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog

    // Validation
    val validationErrors = validator.validationErrors

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
            dataLoader.loadUserData()
                .onSuccess { userData ->
                    stateManager.loadUserData(userData)
                    validator.clearMultipleFieldErrors(listOf("nik", "nama"))
                }
                .onFailure { exception ->
                    errorMessage = exception.message ?: "Gagal memuat data pengguna"
                    stateManager.setUseMyData(false)
                    _skGhaibEvent.emit(SKGhaibEvent.UserDataLoadError(errorMessage!!))
                }
        }
    }

    // Update functions dengan validation clearing
    fun updateNik(value: String) {
        stateManager.updateNik(value)
        validator.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateHubunganId(value: String) {
        stateManager.updateHubunganId(value)
        validator.clearFieldError("hubungan_id")
    }

    // Step 2 - Informasi Orang Yang Hilang Update Functions
    fun updateNamaOrangHilang(value: String) {
        stateManager.updateNamaOrangHilang(value)
        validator.clearFieldError("nama_orang_hilang")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateUsia(value: Int) {
        stateManager.updateUsia(value)
        validator.clearFieldError("usia")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateHilangSejak(value: String) {
        stateManager.updateHilangSejak(value)
        validator.clearFieldError("hilang_sejak")
    }

    // Step navigation dengan validation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validator.validateStep1(stateManager)) {
                    stateManager.nextStep()
                    viewModelScope.launch {
                        _skGhaibEvent.emit(SKGhaibEvent.StepChanged(stateManager.currentStep))
                    }
                } else {
                    viewModelScope.launch {
                        _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
                    }
                }
            }
            2 -> {
                if (validator.validateStep2(stateManager)) {
                    stateManager.showConfirmation()
                } else {
                    viewModelScope.launch {
                        _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
                    }
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.StepChanged(stateManager.currentStep))
            }
        }
    }

    // Dialog functions
    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        val step2Valid = validator.validateStep2(stateManager)

        if (!step1Valid || !step2Valid) {
            viewModelScope.launch {
                _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
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
                _skGhaibEvent.emit(SKGhaibEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.dismissConfirmation()

    fun confirmSubmit() {
        stateManager.dismissConfirmation()
        submitForm()
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            errorMessage = null
            val request = stateManager.createRequest()

            formSubmitter.submitForm(request)
                .onSuccess {
                    _skGhaibEvent.emit(SKGhaibEvent.SubmitSuccess)
                    resetForm()
                }
                .onFailure { exception ->
                    errorMessage = exception.message ?: "Terjadi kesalahan"
                    _skGhaibEvent.emit(SKGhaibEvent.SubmitError(errorMessage!!))
                }
        }
    }

    // Utility functions - delegate ke appropriate components
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun clearError() { errorMessage = null }

    private fun resetForm() {
        stateManager.resetForm()
        validator.clearAllErrors()
        errorMessage = null
    }

    // Events - copy langsung dari original
    sealed class SKGhaibEvent {
        data class StepChanged(val step: Int) : SKGhaibEvent()
        data object SubmitSuccess : SKGhaibEvent()
        data class SubmitError(val message: String) : SKGhaibEvent()
        data object ValidationError : SKGhaibEvent()
        data class UserDataLoadError(val message: String) : SKGhaibEvent()
    }
}
