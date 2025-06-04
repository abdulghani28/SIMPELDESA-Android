package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMasukKerjaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKTidakMasukKerjaViewModel(
    createSKTidakMasukKerjaUseCase: CreateSuratTidakMasukKerjaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition components
    private val stateManager = SKTidakMasukKerjaStateManager()
    private val validator = SKTidakMasukKerjaValidator(stateManager)
    private val dataLoader = SKTidakMasukKerjaDataLoader(getUserInfoUseCase, getAgamaUseCase, stateManager)
    private val formSubmitter = SKTidakMasukKerjaFormSubmitter(createSKTidakMasukKerjaUseCase, stateManager)

    // Events
    private val _skTidakMasukKerjaEvent = MutableSharedFlow<SKTidakMasukKerjaEvent>()
    val skTidakMasukKerjaEvent = _skTidakMasukKerjaEvent.asSharedFlow()

    // Expose state through delegation - mempertahankan public interface
    val uiState = stateManager.uiState
    val validationErrors = stateManager.validationErrors

    // Expose properties (delegate ke stateManager)
    val agamaList get() = stateManager.agamaList
    val isLoadingAgama get() = stateManager.isLoadingAgama
    val agamaErrorMessage get() = stateManager.agamaErrorMessage
    val isLoading get() = stateManager.isLoading
    val errorMessage get() = stateManager.errorMessage
    val currentStep get() = stateManager.currentStep
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val isLoadingUserData get() = stateManager.isLoadingUserData
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog

    // Step 1 properties
    val nikValue get() = stateManager.nikValue
    val namaValue get() = stateManager.namaValue
    val tempatLahirValue get() = stateManager.tempatLahirValue
    val tanggalLahirValue get() = stateManager.tanggalLahirValue
    val jenisKelaminValue get() = stateManager.jenisKelaminValue
    val agamaIdValue get() = stateManager.agamaIdValue
    val alamatValue get() = stateManager.alamatValue
    val pekerjaanValue get() = stateManager.pekerjaanValue

    // Step 2 properties
    val jabatanValue get() = stateManager.jabatanValue
    val namaPerusahaanValue get() = stateManager.namaPerusahaanValue
    val alasanIzinValue get() = stateManager.alasanIzinValue
    val keperluanValue get() = stateManager.keperluanValue
    val lamaValue get() = stateManager.lamaValue
    val terhitungDariValue get() = stateManager.terhitungDariValue

    // Public functions - delegate ke components yang sesuai
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.AgamaLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 update functions - delegate + clear error
    fun updateNik(value: String) {
        stateManager.updateNik(value)
        stateManager.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updateNama(value)
        stateManager.clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        stateManager.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        stateManager.clearFieldError("tanggal_lahir")
    }

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        stateManager.clearFieldError("jenis_kelamin")
    }

    fun updateAgamaId(value: String) {
        stateManager.updateAgamaId(value)
        stateManager.clearFieldError("agama_id")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        stateManager.clearFieldError("alamat")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        stateManager.clearFieldError("pekerjaan")
    }

    // Step 2 - Informasi Pekerjaan & Izin Update Functions
    fun updateJabatan(value: String) {
        stateManager.updateJabatan(value)
        stateManager.clearFieldError("jabatan")
    }

    fun updateNamaPerusahaan(value: String) {
        stateManager.updateNamaPerusahaan(value)
        stateManager.clearFieldError("nama_perusahaan")
    }

    fun updateAlasanIzin(value: String) {
        stateManager.updateAlasanIzin(value)
        stateManager.clearFieldError("alasan_izin")
    }

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        stateManager.clearFieldError("keperluan")
    }

    fun updateLama(value: Int) {
        stateManager.updateLama(value)
        stateManager.clearFieldError("lama")
    }

    fun updateTerhitungDari(value: String) {
        stateManager.updateTerhitungDari(value)
        stateManager.clearFieldError("terhitung_dari")
    }

    // Navigation functions
    fun nextStep() {
        when (currentStep) {
            1 -> {
                if (validator.validateStep1()) {
                    stateManager.updateCurrentStep(2)
                    viewModelScope.launch {
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.StepChanged(currentStep))
                    }
                } else {
                    viewModelScope.launch {
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
                    }
                }
            }
            2 -> {
                if (validator.validateStep2()) {
                    stateManager.updateShowConfirmationDialog(true)
                } else {
                    viewModelScope.launch {
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
                    }
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            stateManager.updateCurrentStep(currentStep - 1)
            viewModelScope.launch {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.StepChanged(currentStep))
            }
        }
    }

    // Dialog functions
    fun showPreview() {
        val step1Valid = validator.validateStep1()
        val step2Valid = validator.validateStep2()

        if (!step1Valid || !step2Valid) {
            viewModelScope.launch {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
            }
        }
        stateManager.updateShowPreviewDialog(true)
    }

    fun dismissPreview() = stateManager.updateShowPreviewDialog(false)

    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            stateManager.updateShowConfirmationDialog(true)
        } else {
            viewModelScope.launch {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.updateShowConfirmationDialog(false)

    fun confirmSubmit() {
        stateManager.updateShowConfirmationDialog(false)
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.SubmitSuccess) }
                .onFailure { _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.SubmitError(it.message ?: "Error")) }
        }
    }

    // Utility functions - delegate ke stateManager
    fun getFieldError(fieldName: String): String? = stateManager.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = stateManager.hasFieldError(fieldName)
    fun clearError() = stateManager.updateErrorMessage(null)
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Events - copy dari kode asli
    sealed class SKTidakMasukKerjaEvent {
        data class StepChanged(val step: Int) : SKTidakMasukKerjaEvent()
        data object SubmitSuccess : SKTidakMasukKerjaEvent()
        data class SubmitError(val message: String) : SKTidakMasukKerjaEvent()
        data object ValidationError : SKTidakMasukKerjaEvent()
        data class AgamaLoadError(val message: String) : SKTidakMasukKerjaEvent()
        data class UserDataLoadError(val message: String) : SKTidakMasukKerjaEvent()
    }
}