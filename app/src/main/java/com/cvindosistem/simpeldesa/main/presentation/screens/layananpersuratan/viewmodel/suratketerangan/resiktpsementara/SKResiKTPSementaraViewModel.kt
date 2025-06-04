package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.resiktpsementara

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateResiKTPSementaraUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKResiKTPSementaraViewModel(
    createSuratCatatanResiKTPSementaraUseCase: CreateResiKTPSementaraUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition components
    private val stateManager = SKResiKTPSementaraStateManager()
    private val validator = SKResiKTPSementaraValidator()
    private val dataLoader = SKResiKTPSementaraDataLoader(getUserInfoUseCase, getAgamaUseCase)
    private val formSubmitter = SKResiKTPSementaraFormSubmitter(createSuratCatatanResiKTPSementaraUseCase)

    // Events
    private val _catatanResiKTPSementaraEvent = MutableSharedFlow<SKResiKTPSementaraEvent>()
    val catatanResiKTPSementaraEvent = _catatanResiKTPSementaraEvent.asSharedFlow()

    // Expose state through delegation - mempertahankan public interface
    val uiState = stateManager.uiState
    val validationErrors = stateManager.validationErrors

    // Expose states as properties
    var agamaList: List<AgamaResponse.Data>
        get() = stateManager.agamaList
        set(value) { stateManager.agamaList = value }

    var isLoadingAgama: Boolean
        get() = stateManager.isLoadingAgama
        set(value) { stateManager.isLoadingAgama = value }

    var agamaErrorMessage: String?
        get() = stateManager.agamaErrorMessage
        set(value) { stateManager.agamaErrorMessage = value }

    var isLoading: Boolean
        get() = stateManager.isLoading
        set(value) { stateManager.isLoading = value }

    var errorMessage: String?
        get() = stateManager.errorMessage
        set(value) { stateManager.errorMessage = value }

    var currentStep: Int
        get() = stateManager.currentStep
        set(value) { stateManager.currentStep = value }

    var useMyDataChecked: Boolean
        get() = stateManager.useMyDataChecked
        set(value) { stateManager.useMyDataChecked = value }

    var isLoadingUserData: Boolean
        get() = stateManager.isLoadingUserData
        set(value) { stateManager.isLoadingUserData = value }

    var showConfirmationDialog: Boolean
        get() = stateManager.showConfirmationDialog
        set(value) { stateManager.showConfirmationDialog = value }

    var showPreviewDialog: Boolean
        get() = stateManager.showPreviewDialog
        set(value) { stateManager.showPreviewDialog = value }

    // Form fields
    var nikValue: String
        get() = stateManager.nikValue
        set(value) { stateManager.nikValue = value }

    var namaValue: String
        get() = stateManager.namaValue
        set(value) { stateManager.namaValue = value }

    var tempatLahirValue: String
        get() = stateManager.tempatLahirValue
        set(value) { stateManager.tempatLahirValue = value }

    var tanggalLahirValue: String
        get() = stateManager.tanggalLahirValue
        set(value) { stateManager.tanggalLahirValue = value }

    var selectedGender: String
        get() = stateManager.selectedGender
        set(value) { stateManager.selectedGender = value }

    var pekerjaanValue: String
        get() = stateManager.pekerjaanValue
        set(value) { stateManager.pekerjaanValue = value }

    var alamatValue: String
        get() = stateManager.alamatValue
        set(value) { stateManager.alamatValue = value }

    var agamaValue: String
        get() = stateManager.agamaValue
        set(value) { stateManager.agamaValue = value }

    var keperluanValue: String
        get() = stateManager.keperluanValue
        set(value) { stateManager.keperluanValue = value }

    // Public interface methods - delegate ke komponen yang sesuai
    fun updateUseMyData(checked: Boolean) {
        stateManager.useMyDataChecked = checked
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData(stateManager) { errorMsg ->
                    viewModelScope.launch {
                        _catatanResiKTPSementaraEvent.emit(
                            SKResiKTPSementaraEvent.UserDataLoadError(errorMsg)
                        )
                    }
                }
            }
            loadAgama()
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama(stateManager) { errorMsg ->
                viewModelScope.launch {
                    _catatanResiKTPSementaraEvent.emit(
                        SKResiKTPSementaraEvent.AgamaLoadError(errorMsg)
                    )
                }
            }
        }
    }

    // Field update functions - delegate dengan validation clearing
    fun updateNik(value: String) {
        stateManager.nikValue = value
        stateManager.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.namaValue = value
        stateManager.clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        stateManager.tempatLahirValue = value
        stateManager.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.tanggalLahirValue = dateFormatterToApiFormat(value)
        stateManager.clearFieldError("tanggal_lahir")
    }

    fun updateGender(value: String) {
        stateManager.selectedGender = value
        stateManager.clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        stateManager.pekerjaanValue = value
        stateManager.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.alamatValue = value
        stateManager.clearFieldError("alamat")
    }

    fun updateAgama(value: String) {
        stateManager.agamaValue = value
        stateManager.clearFieldError("agama_id")
    }

    fun updateKeperluan(value: String) {
        stateManager.keperluanValue = value
        stateManager.clearFieldError("keperluan")
    }

    // Dialog functions
    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        if (!step1Valid) {
            viewModelScope.launch {
                _catatanResiKTPSementaraEvent.emit(SKResiKTPSementaraEvent.ValidationError)
            }
        }
        stateManager.showPreviewDialog = true
    }

    fun dismissPreview() {
        stateManager.showPreviewDialog = false
    }

    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _catatanResiKTPSementaraEvent.emit(SKResiKTPSementaraEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        stateManager.showConfirmationDialog = false
    }

    fun confirmSubmit() {
        stateManager.showConfirmationDialog = false
        viewModelScope.launch {
            formSubmitter.submitForm(
                stateManager = stateManager,
                onSuccess = {
                    viewModelScope.launch {
                        _catatanResiKTPSementaraEvent.emit(SKResiKTPSementaraEvent.SubmitSuccess)
                    }
                },
                onError = { errorMsg ->
                    viewModelScope.launch {
                        _catatanResiKTPSementaraEvent.emit(
                            SKResiKTPSementaraEvent.SubmitError(errorMsg)
                        )
                    }
                }
            )
        }
    }

    // Utility functions - delegate ke state manager
    fun getFieldError(fieldName: String): String? = stateManager.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = stateManager.hasFieldError(fieldName)
    fun clearError() { stateManager.errorMessage = null }
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Events - copy langsung dari kode asli
    sealed class SKResiKTPSementaraEvent {
        data class StepChanged(val step: Int) : SKResiKTPSementaraEvent()
        data object SubmitSuccess : SKResiKTPSementaraEvent()
        data class SubmitError(val message: String) : SKResiKTPSementaraEvent()
        data object ValidationError : SKResiKTPSementaraEvent()
        data class UserDataLoadError(val message: String) : SKResiKTPSementaraEvent()
        data class AgamaLoadError(val message: String) : SKResiKTPSementaraEvent()
    }
}