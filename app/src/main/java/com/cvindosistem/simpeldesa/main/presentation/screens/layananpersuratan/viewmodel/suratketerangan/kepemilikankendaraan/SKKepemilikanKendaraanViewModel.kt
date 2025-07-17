package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKepemilikanKendaraanUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKepemilikanKendaraanViewModel(
    createSKKepemilikanKendaraanUseCase: CreateSuratKepemilikanKendaraanUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKKepemilikanKendaraanStateManager()
    private val validator = SKKepemilikanKendaraanValidator(stateManager)
    private val dataLoader = SKKepemilikanKendaraanDataLoader(getUserInfoUseCase, stateManager, validator)
    private val formSubmitter = SKKepemilikanKendaraanFormSubmitter(createSKKepemilikanKendaraanUseCase, stateManager)

    // Events
    private val _skKepemilikanKendaraanEvent = MutableSharedFlow<SKKepemilikanKendaraanEvent>()
    val skKepemilikanKendaraanEvent = _skKepemilikanKendaraanEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKKepemilikanKendaraanUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Data Pemilik)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val alamatValue: String get() = stateManager.alamatValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue

    // Form field properties - Step 2 (Data Kendaraan)
    val merkValue: String get() = stateManager.merkValue
    val tahunPembuatanValue: String get() = stateManager.tahunPembuatanValue
    val warnaValue: String get() = stateManager.warnaValue
    val nomorPolisiValue: String get() = stateManager.nomorPolisiValue
    val nomorMesinValue: String get() = stateManager.nomorMesinValue
    val nomorRangkaValue: String get() = stateManager.nomorRangkaValue
    val nomorBpkbValue: String get() = stateManager.nomorBpkbValue
    val bahanBakarValue: String get() = stateManager.bahanBakarValue
    val isiSilinderValue: String get() = stateManager.isiSilinderValue
    val atasNamaValue: String get() = stateManager.atasNamaValue
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 Update Functions (Data Pemilik)
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

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    // Step 2 Update Functions (Data Kendaraan)
    fun updateMerk(value: String) {
        stateManager.updateMerk(value)
        validator.clearFieldError("merk")
    }

    fun updateTahunPembuatan(value: String) {
        stateManager.updateTahunPembuatan(value)
        validator.clearFieldError("tahun_pembuatan")
    }

    fun updateWarna(value: String) {
        stateManager.updateWarna(value)
        validator.clearFieldError("warna")
    }

    fun updateNomorPolisi(value: String) {
        stateManager.updateNomorPolisi(value)
        validator.clearFieldError("nomor_polisi")
    }

    fun updateNomorMesin(value: String) {
        stateManager.updateNomorMesin(value)
        validator.clearFieldError("nomor_mesin")
    }

    fun updateNomorRangka(value: String) {
        stateManager.updateNomorRangka(value)
        validator.clearFieldError("nomor_rangka")
    }

    fun updateNomorBpkb(value: String) {
        stateManager.updateNomorBpkb(value)
        validator.clearFieldError("nomor_bpkb")
    }

    fun updateBahanBakar(value: String) {
        stateManager.updateBahanBakar(value)
        validator.clearFieldError("bahan_bakar")
    }

    fun updateIsiSilinder(value: String) {
        stateManager.updateIsiSilinder(value)
        validator.clearFieldError("isi_silinder")
    }

    fun updateAtasNama(value: String) {
        stateManager.updateAtasNama(value)
        validator.clearFieldError("atas_nama")
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
                _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.ValidationError)
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
                _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.SubmitSuccess) }
                .onFailure { _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.SubmitError(it.message ?: "Error")) }
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
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skKepemilikanKendaraanEvent.emit(SKKepemilikanKendaraanEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKKepemilikanKendaraanEvent {
        data class StepChanged(val step: Int) : SKKepemilikanKendaraanEvent()
        data object SubmitSuccess : SKKepemilikanKendaraanEvent()
        data class SubmitError(val message: String) : SKKepemilikanKendaraanEvent()
        data object ValidationError : SKKepemilikanKendaraanEvent()
        data class UserDataLoadError(val message: String) : SKKepemilikanKendaraanEvent()
    }
}