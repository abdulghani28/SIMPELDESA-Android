package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasRequest
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPengantarPasLintasBatasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPPermohonanPenerbitanBukuPasLintasBatasViewModel(
    createSPPermohonanPenerbitanBukuPasLintasBatasUseCase: CreateSuratPengantarPasLintasBatasUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPPermohonanPenerbitanBukuPasLintasBatasStateManager()
    private val validator = SPPermohonanPenerbitanBukuPasLintasBatasValidator(stateManager)
    private val dataLoader = SPPermohonanPenerbitanBukuPasLintasBatasDataLoader(
        getUserInfoUseCase, getAgamaUseCase, getStatusKawinUseCase,
        stateManager, validator
    )
    private val formSubmitter = SPPermohonanPenerbitanBukuPasLintasBatasFormSubmitter(
        createSPPermohonanPenerbitanBukuPasLintasBatasUseCase, stateManager
    )

    // Events
    private val _spPermohonanPenerbitanBukuPasLintasBatasEvent = MutableSharedFlow<SPPermohonanPenerbitanBukuPasLintasBatasEvent>()
    val spPermohonanPenerbitanBukuPasLintasBatasEvent = _spPermohonanPenerbitanBukuPasLintasBatasEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPPermohonanPenerbitanBukuPasLintasBatasUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val statusKawinList: List<StatusKawinResponse.Data> get() = stateManager.statusKawinList
    val isLoadingStatusKawin: Boolean get() = stateManager.isLoadingStatusKawin
    val statusKawinErrorMessage: String? get() = stateManager.statusKawinErrorMessage
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Step 1 - Data Pemohon form field properties
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val kewarganegaraanValue: String get() = stateManager.kewarganegaraanValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val statusKawinIdValue: String get() = stateManager.statusKawinIdValue

    // Step 2 - Alamat & Keluarga form field properties
    val alamatValue: String get() = stateManager.alamatValue
    val noKkValue: String get() = stateManager.noKkValue
    val kepalaKeluargaValue: String get() = stateManager.kepalaKeluargaValue

    // Step 3 - Anggota Keluarga yang Ikut form field properties
    val anggotaKeluargaValue: List<SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga> get() = stateManager.anggotaKeluargaValue

    // Step 4 - Keperluan form field properties
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.AgamaLoadError(it.message ?: "Error"))
                }
                dataLoader.loadStatusKawin().onFailure {
                    _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.StatusKawinLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin().onFailure {
                _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.StatusKawinLoadError(it.message ?: "Error"))
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

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateKewarganegaraan(value: String) {
        stateManager.updateKewarganegaraan(value)
        validator.clearFieldError("kewarganegaraan")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateStatusKawinId(value: String) {
        stateManager.updateStatusKawinId(value)
        validator.clearFieldError("status_kawin_id")
    }

    // Step 2 Update Functions - Alamat & Keluarga
    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateNoKk(value: String) {
        stateManager.updateNoKk(value)
        validator.clearFieldError("no_kk")
    }

    fun updateKepalaKeluarga(value: String) {
        stateManager.updateKepalaKeluarga(value)
        validator.clearFieldError("kepala_keluarga")
    }

    // Step 3 Update Functions - Anggota Keluarga yang Ikut
    fun updateAnggotaKeluarga(value: List<SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga>) {
        stateManager.updateAnggotaKeluarga(value)
        validator.clearFieldError("anggota_keluarga")
    }

    fun addAnggotaKeluarga(anggota: SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga) {
        stateManager.addAnggotaKeluarga(anggota)
        validator.clearFieldError("anggota_keluarga")
    }

    fun removeAnggotaKeluarga(index: Int) {
        stateManager.removeAnggotaKeluarga(index)
        validator.clearFieldError("anggota_keluarga")
    }

    fun updateAnggotaKeluargaAt(index: Int, anggota: SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga) {
        stateManager.updateAnggotaKeluargaAt(index, anggota)
        validator.clearFieldError("anggota_keluarga")
    }

    // Step 4 Update Functions - Keperluan
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
                    stateManager.nextStep()
                    emitStepChangedEvent()
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
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            emitStepChangedEvent()
        }
    }

    // Dialog functions
    fun showPreview() {
        if (!validator.validateAllSteps()) {
            viewModelScope.launch {
                _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.ValidationError)
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
                _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.SubmitSuccess) }
                .onFailure { _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.SubmitError(it.message ?: "Error")) }
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
            4 -> validator.validateStep4()
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spPermohonanPenerbitanBukuPasLintasBatasEvent.emit(SPPermohonanPenerbitanBukuPasLintasBatasEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPPermohonanPenerbitanBukuPasLintasBatasEvent {
        data class StepChanged(val step: Int) : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
        data object SubmitSuccess : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
        data class SubmitError(val message: String) : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
        data object ValidationError : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
        data class UserDataLoadError(val message: String) : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
        data class AgamaLoadError(val message: String) : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
        data class StatusKawinLoadError(val message: String) : SPPermohonanPenerbitanBukuPasLintasBatasEvent()
    }
}