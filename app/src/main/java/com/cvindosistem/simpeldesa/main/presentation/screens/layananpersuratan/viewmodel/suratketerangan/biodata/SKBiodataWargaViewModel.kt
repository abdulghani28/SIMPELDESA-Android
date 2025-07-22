package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
//import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.DisabilitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganBiodataWargaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
//import com.cvindosistem.simpeldesa.main.domain.usecases.GetDisabilitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPendidikanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.walihakim.SKWaliHakimViewModel.SKWaliHakimEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKBiodataWargaViewModel(
    createSKBiodataWargaUseCase: CreateSuratKeteranganBiodataWargaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getPendidikanUseCase: GetPendidikanUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
//    getDisabilitasUseCase: GetDisabilitasUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKBiodataWargaStateManager()
    private val validator = SKBiodataWargaValidator(stateManager)
    private val dataLoader = SKBiodataWargaDataLoader(
        getUserInfoUseCase,
        getAgamaUseCase,
        getPendidikanUseCase,
        getStatusKawinUseCase,
//        getDisabilitasUseCase,
        stateManager,
        validator
    )
    private val formSubmitter = SKBiodataWargaFormSubmitter(createSKBiodataWargaUseCase, stateManager)

    // Events
    private val _skBiodataWargaEvent = MutableSharedFlow<SKBiodataWargaEvent>()
    val skBiodataWargaEvent = _skBiodataWargaEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKBiodataWargaUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val isLoadingAgama: Boolean get() = stateManager.isLoadingAgama
    val agamaErrorMessage: String? get() = stateManager.agamaErrorMessage
    val pendidikanList: List<PendidikanResponse.Data> get() = stateManager.pendidikanList
    val isLoadingPendidikan: Boolean get() = stateManager.isLoadingPendidikan
    val pendidikanErrorMessage: String? get() = stateManager.pendidikanErrorMessage
//    val disabilitasList: List<DisabilitasResponse.Data> get() = stateManager.disabilitasList
    val isLoadingDisabilitas: Boolean get() = stateManager.isLoadingDisabilitas
    val disabilitasErrorMessage: String? get() = stateManager.disabilitasErrorMessage
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

    // Form field properties - delegate to stateManager
    // Step 1: Data Diri Pemohon
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val golonganDarahValue: String get() = stateManager.golonganDarahValue
    val agamaIdValue: String get() = stateManager.agamaIdValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val pendidikanIdValue: String get() = stateManager.pendidikanIdValue
    val disabilitasIdValue: String get() = stateManager.disabilitasIdValue

    // Step 2: Alamat & Status
    val alamatValue: String get() = stateManager.alamatValue
    val statusValue: String get() = stateManager.statusValue
    val hubunganValue: String get() = stateManager.hubunganValue

    // Step 3: Informasi Perkawinan
    val aktaKawinValue: String get() = stateManager.aktaKawinValue
    val tanggalKawinValue: String get() = stateManager.tanggalKawinValue
    val aktaCeraiValue: String get() = stateManager.aktaCeraiValue
    val tanggalCeraiValue: String get() = stateManager.tanggalCeraiValue

    // Step 4: Data Orang Tua
    val namaAyahValue: String get() = stateManager.namaAyahValue
    val nikAyahValue: String get() = stateManager.nikAyahValue
    val namaIbuValue: String get() = stateManager.namaIbuValue
    val nikIbuValue: String get() = stateManager.nikIbuValue
    val aktaLahirValue: String get() = stateManager.aktaLahirValue

    // Step 5: Keperluan
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skBiodataWargaEvent.emit(SKBiodataWargaEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _skBiodataWargaEvent.emit(SKBiodataWargaEvent.AgamaLoadError(it.message ?: "Error"))
                }
                dataLoader.loadPendidikan().onFailure {
                    _skBiodataWargaEvent.emit(SKBiodataWargaEvent.PendidikanLoadError(it.message ?: "Error"))
                }
//                dataLoader.loadDisabilitas().onFailure {
//                    _skBiodataWargaEvent.emit(SKBiodataWargaEvent.DisabilitasLoadError(it.message ?: "Error"))
//                }
                dataLoader.loadStatusKawin().onFailure {
                    _skBiodataWargaEvent.emit(SKBiodataWargaEvent.StatusKawinLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadPendidikan() {
        viewModelScope.launch {
            dataLoader.loadPendidikan().onFailure {
                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.PendidikanLoadError(it.message ?: "Error"))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin().onFailure {
                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.StatusKawinLoadError(it.message ?: "Error"))
            }
        }
    }


//    fun loadDisabilitas() {
//        viewModelScope.launch {
//            dataLoader.loadDisabilitas().onFailure {
//                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.DisabilitasLoadError(it.message ?: "Error"))
//            }
//        }
//    }

    // Step 1 update functions - Data Diri Pemohon
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

    fun updateGolonganDarah(value: String) {
        stateManager.updateGolonganDarah(value)
        validator.clearFieldError("golongan_darah")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        validator.clearFieldError("agama_id")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updatePendidikanId(value: String) {
        stateManager.updatePendidikanId(value)
        validator.clearFieldError("pendidikan_id")
    }

    fun updateDisabilitasId(value: String) {
        stateManager.updateDisabilitasId(value)
        validator.clearFieldError("disabilitas_id")
    }

    // Step 2 Update Functions - Alamat & Status
    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateStatus(value: String) {
        stateManager.updateStatus(value)
        validator.clearFieldError("status")
    }

    fun updateHubungan(value: String) {
        stateManager.updateHubungan(value)
        validator.clearFieldError("hubungan")
    }

    // Step 3 Update Functions - Informasi Perkawinan
    fun updateAktaKawin(value: String) {
        stateManager.updateAktaKawin(value)
        validator.clearFieldError("akta_kawin")
    }

    fun updateTanggalKawin(value: String) {
        stateManager.updateTanggalKawin(value)
        validator.clearFieldError("tanggal_kawin")
    }

    fun updateAktaCerai(value: String) {
        stateManager.updateAktaCerai(value)
        validator.clearFieldError("akta_cerai")
    }

    fun updateTanggalCerai(value: String) {
        stateManager.updateTanggalCerai(value)
        validator.clearFieldError("tanggal_cerai")
    }

    // Step 4 Update Functions - Data Orang Tua
    fun updateNamaAyah(value: String) {
        stateManager.updateNamaAyah(value)
        validator.clearFieldError("nama_ayah")
    }

    fun updateNikAyah(value: String) {
        stateManager.updateNikAyah(value)
        validator.clearFieldError("nik_ayah")
    }

    fun updateNamaIbu(value: String) {
        stateManager.updateNamaIbu(value)
        validator.clearFieldError("nama_ibu")
    }

    fun updateNikIbu(value: String) {
        stateManager.updateNikIbu(value)
        validator.clearFieldError("nik_ibu")
    }

    fun updateAktaLahir(value: String) {
        stateManager.updateAktaLahir(value)
        validator.clearFieldError("akta_lahir")
    }

    // Step 5 Update Functions - Keperluan
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
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            5 -> {
                if (validateStepWithEvent(5)) {
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
                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.ValidationError)
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
                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skBiodataWargaEvent.emit(SKBiodataWargaEvent.SubmitSuccess) }
                .onFailure { _skBiodataWargaEvent.emit(SKBiodataWargaEvent.SubmitError(it.message ?: "Error")) }
        }
    }

    // Validation functions - delegate to validator
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utility functions
    fun clearError() = stateManager.clearError()
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun resetForm() = stateManager.resetForm()

    // Private helper functions
    private fun validateStepWithEvent(step: Int): Boolean {
        val isValid = when (step) {
            1 -> validator.validateStep1()
            2 -> validator.validateStep2()
            3 -> validator.validateStep3()
            4 -> validator.validateStep4()
            5 -> validator.validateStep5()
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _skBiodataWargaEvent.emit(SKBiodataWargaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skBiodataWargaEvent.emit(SKBiodataWargaEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKBiodataWargaEvent {
        data class StepChanged(val step: Int) : SKBiodataWargaEvent()
        data object SubmitSuccess : SKBiodataWargaEvent()
        data class SubmitError(val message: String) : SKBiodataWargaEvent()
        data object ValidationError : SKBiodataWargaEvent()
        data class UserDataLoadError(val message: String) : SKBiodataWargaEvent()
        data class AgamaLoadError(val message: String) : SKBiodataWargaEvent()
        data class PendidikanLoadError(val message: String) : SKBiodataWargaEvent()
        data class StatusKawinLoadError(val message: String) : SKBiodataWargaEvent()
        data class DisabilitasLoadError(val message: String) : SKBiodataWargaEvent()
    }
}