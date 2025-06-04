package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmampu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMampuResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMampuUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKTidakMampuViewModel(
    createSuratCatatanTidakMampuUseCase: CreateSuratTidakMampuUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Components
    private val stateManager = SKTidakMampuStateManager()
    private val validator = SKTidakMampuValidator()
    private val dataLoader = SKTidakMampuDataLoader(getUserInfoUseCase, getAgamaUseCase, getStatusKawinUseCase)
    private val formSubmitter = SKTidakMampuFormSubmitter(createSuratCatatanTidakMampuUseCase)

    // UI State for backward compatibility
    private val _uiState = MutableStateFlow(SKTidakMampuUiState())
    val uiState = _uiState.asStateFlow()

    // Events
    private val _catatanTidakMampuEvent = MutableSharedFlow<SKTidakMampuEvent>()
    val catatanTidakMampuEvent = _catatanTidakMampuEvent.asSharedFlow()

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Expose component properties for public interface compatibility
    val agamaList get() = dataLoader.agamaList
    val isLoadingAgama get() = dataLoader.isLoadingAgama
    val agamaErrorMessage get() = dataLoader.agamaErrorMessage

    val statusKawinList get() = dataLoader.statusKawinList
    val isLoadingStatusKawin get() = dataLoader.isLoadingStatusKawin
    val statusKawinErrorMessage get() = dataLoader.statusKawinErrorMessage

    val isLoading get() = formSubmitter.isLoading
    val currentStep get() = stateManager.currentStep
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val isLoadingUserData get() = dataLoader.isLoadingUserData
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog

    // Form field getters - copy semua dari kode asli
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

    val validationErrors = validator.validationErrors

    // Public interface methods - delegate to components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyData(checked)
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
            try {
                when (val result = dataLoader.loadUserData()) {
                    is UserInfoResult.Success -> {
                        stateManager.populateWithUserData(result.data.data)
                        validator.clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat", "status_kawin_id", "agama_id"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        stateManager.updateUseMyData(false)
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                stateManager.updateUseMyData(false)
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.UserDataLoadError(errorMessage!!))
            }
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            when (val result = dataLoader.loadAgama()) {
                is AgamaResult.Error -> {
                    _catatanTidakMampuEvent.emit(SKTidakMampuEvent.AgamaLoadError(result.message))
                }
                is AgamaResult.Success -> {
                    // Success handled in dataLoader
                }
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            when (val result = dataLoader.loadStatusKawin()) {
                is StatusKawinResult.Error -> {
                    _catatanTidakMampuEvent.emit(SKTidakMampuEvent.StatusKawinLoadError(result.message))
                }
                is StatusKawinResult.Success -> {
                    // Success handled in dataLoader
                }
            }
        }
    }

    // Field update methods - delegate to stateManager and clear validation errors
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
        validator.clearFieldError("gender")
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
        validator.clearFieldError("agama")
    }

    fun updateStatusKawin(value: String) {
        stateManager.updateStatusKawin(value)
        validator.clearFieldError("status_kawin")
    }

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }


    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        if (!step1Valid) {
            viewModelScope.launch {
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.showConfirmationDialog()
        } else {
            viewModelScope.launch {
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.ValidationError)
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
            errorMessage = null
            try {
                val request = stateManager.createRequest()
                when (val result = formSubmitter.submitForm(request)) {
                    is SuratTidakMampuResult.Success -> {
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.SubmitSuccess)
                        stateManager.resetForm()
                        validator.clearAllErrors()
                    }
                    is SuratTidakMampuResult.Error -> {
                        errorMessage = result.message
                        _catatanTidakMampuEvent.emit(SKTidakMampuEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _catatanTidakMampuEvent.emit(SKTidakMampuEvent.SubmitError(errorMessage!!))
            }
        }
    }

    // Validation methods - delegate to validator
    fun validateAllSteps(): Boolean = validator.validateAllSteps(stateManager)
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utility methods
    fun clearError() { errorMessage = null }
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Events - copy dari kode asli
    sealed class SKTidakMampuEvent {
        data class StepChanged(val step: Int) : SKTidakMampuEvent()
        data object SubmitSuccess : SKTidakMampuEvent()
        data class SubmitError(val message: String) : SKTidakMampuEvent()
        data object ValidationError : SKTidakMampuEvent()
        data class UserDataLoadError(val message: String) : SKTidakMampuEvent()
        data class AgamaLoadError(val message: String) : SKTidakMampuEvent()
        data class StatusKawinLoadError(val message: String) : SKTidakMampuEvent()
    }
}