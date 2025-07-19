package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganIzinOrangTuaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKIzinOrangTuaViewModel(
    createSKIzinOrangTuaUseCase: CreateSuratKeteranganIzinOrangTuaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKIzinOrangTuaStateManager()
    private val validator = SKIzinOrangTuaValidator(stateManager)
    private val dataLoader = SKIzinOrangTuaDataLoader(
        getUserInfoUseCase, getAgamaUseCase, stateManager, validator
    )
    private val formSubmitter = SKIzinOrangTuaFormSubmitter(createSKIzinOrangTuaUseCase, stateManager)

    // Events
    private val _skIzinOrangTuaEvent = MutableSharedFlow<SKIzinOrangTuaEvent>()
    val skIzinOrangTuaEvent = _skIzinOrangTuaEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKIzinOrangTuaUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Informasi Pemberi Izin - Orang Tua)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue
    val memberiIzinValue: String get() = stateManager.memberiIzinValue

    // Form field properties - Step 2 (Informasi yang Diberi Izin - Anak)
    val nik2Value: String get() = stateManager.nik2Value
    val nama2Value: String get() = stateManager.nama2Value
    val alamat2Value: String get() = stateManager.alamat2Value
    val pekerjaan2Value: String get() = stateManager.pekerjaan2Value
    val tanggalLahir2Value: String get() = stateManager.tanggalLahir2Value
    val tempatLahir2Value: String get() = stateManager.tempatLahir2Value
    val agama2IdValue: String get() = stateManager.agama2IdValue
    val kewarganegaraan2Value: String get() = stateManager.kewarganegaraan2Value
    val diberiIzinValue: String get() = stateManager.diberiIzinValue
    val statusPekerjaanValue: String get() = stateManager.statusPekerjaanValue

    // Form field properties - Step 3 (Informasi Pelengkap)
    val namaPerusahaanValue: String get() = stateManager.namaPerusahaanValue
    val negaraTujuanValue: String get() = stateManager.negaraTujuanValue
    val masaKontrakValue: String get() = stateManager.masaKontrakValue
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.AgamaLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 update functions - delegate to stateManager and clear errors
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

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updateKewarganegaraan(value: String) {
        stateManager.updateKewarganegaraan(value)
        validator.clearFieldError("kewarganegaraan")
    }

    fun updateMemberiIzin(value: String) {
        stateManager.updateMemberiIzin(value)
        validator.clearFieldError("memberi_izin")
    }

    // Step 2 update functions
    fun updateNik2(value: String) {
        stateManager.updateNik2(value)
        validator.clearFieldError("nik2")
    }

    fun updateNama2(value: String) {
        stateManager.updateNama2(value)
        validator.clearFieldError("nama2")
    }

    fun updateAlamat2(value: String) {
        stateManager.updateAlamat2(value)
        validator.clearFieldError("alamat2")
    }

    fun updatePekerjaan2(value: String) {
        stateManager.updatePekerjaan2(value)
        validator.clearFieldError("pekerjaan2")
    }

    fun updateTanggalLahir2(value: String) {
        stateManager.updateTanggalLahir2(value)
        validator.clearFieldError("tanggal_lahir2")
    }

    fun updateTempatLahir2(value: String) {
        stateManager.updateTempatLahir2(value)
        validator.clearFieldError("tempat_lahir2")
    }

    fun updateAgama2Id(value: String) {
        stateManager.updateAgama2Id(value)
        validator.clearFieldError("agama2_id")
    }

    fun updateKewarganegaraan2(value: String) {
        stateManager.updateKewarganegaraan2(value)
        validator.clearFieldError("kewarganegaraan2")
    }

    fun updateDiberiIzin(value: String) {
        stateManager.updateDiberiIzin(value)
        validator.clearFieldError("diberi_izin")
    }

    fun updateStatusPekerjaan(value: String) {
        stateManager.updateStatusPekerjaan(value)
        validator.clearFieldError("status_pekerjaan")
    }

    // Step 3 update functions
    fun updateNamaPerusahaan(value: String) {
        stateManager.updateNamaPerusahaan(value)
        validator.clearFieldError("nama_perusahaan")
    }

    fun updateNegaraTujuan(value: String) {
        stateManager.updateNegaraTujuan(value)
        validator.clearFieldError("negara_tujuan")
    }

    fun updateMasaKontrak(value: String) {
        stateManager.updateMasaKontrak(value)
        validator.clearFieldError("masa_kontrak")
    }

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
                _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.ValidationError)
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
                _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.SubmitSuccess) }
                .onFailure { _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.SubmitError(it.message ?: "Error")) }
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
                _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skIzinOrangTuaEvent.emit(SKIzinOrangTuaEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKIzinOrangTuaEvent {
        data class StepChanged(val step: Int) : SKIzinOrangTuaEvent()
        data object SubmitSuccess : SKIzinOrangTuaEvent()
        data class SubmitError(val message: String) : SKIzinOrangTuaEvent()
        data object ValidationError : SKIzinOrangTuaEvent()
        data class UserDataLoadError(val message: String) : SKIzinOrangTuaEvent()
        data class AgamaLoadError(val message: String) : SKIzinOrangTuaEvent()
    }
}