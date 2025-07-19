package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPengantarCeraiRujukUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKPengantarCeraiRujukViewModel(
    createSKPengantarCeraiRujukUseCase: CreateSuratPengantarCeraiRujukUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    private val stateManager = SKPengantarCeraiRujukStateManager()
    private val validator = SKPengantarCeraiRujukValidator(stateManager)
    private val dataLoader = SKPengantarCeraiRujukDataLoader(
        getUserInfoUseCase, getAgamaUseCase, stateManager, validator
    )
    private val formSubmitter = SKPengantarCeraiRujukFormSubmitter(createSKPengantarCeraiRujukUseCase, stateManager)

    private val _skPengantarCeraiRujukEvent = MutableSharedFlow<SKPengantarCeraiRujukEvent>()
    val skPengantarCeraiRujukEvent = _skPengantarCeraiRujukEvent.asSharedFlow()

    val uiState = MutableStateFlow(SKPengantarCeraiRujukUiState()).asStateFlow()

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

    // Step 1 Fields
    val nikValue get() = stateManager.nikValue
    val namaValue get() = stateManager.namaValue
    val alamatValue get() = stateManager.alamatValue
    val pekerjaanValue get() = stateManager.pekerjaanValue
    val tanggalLahirValue get() = stateManager.tanggalLahirValue
    val tempatLahirValue get() = stateManager.tempatLahirValue
    val agamaIdValue get() = stateManager.agamaIdValue
    val kewarganegaraanValue get() = stateManager.kewarganegaraanValue
    val namaAyahValue get() = stateManager.namaAyahValue
    val nikAyahValue get() = stateManager.nikAyahValue

    // Step 2 Fields
    val nikPasanganValue get() = stateManager.nikPasanganValue
    val namaPasanganValue get() = stateManager.namaPasanganValue
    val alamatPasanganValue get() = stateManager.alamatPasanganValue
    val pekerjaanPasanganValue get() = stateManager.pekerjaanPasanganValue
    val tanggalLahirPasanganValue get() = stateManager.tanggalLahirPasanganValue
    val tempatLahirPasanganValue get() = stateManager.tempatLahirPasanganValue
    val agamaIdPasanganValue get() = stateManager.agamaIdPasanganValue
    val kewarganegaraanPasanganValue get() = stateManager.kewarganegaraanPasanganValue
    val namaAyahPasanganValue get() = stateManager.namaAyahPasanganValue
    val nikAyahPasanganValue get() = stateManager.nikAyahPasanganValue

    // Step 3
    val keperluanValue get() = stateManager.keperluanValue

    // -----------------------------
    // Update Functions
    // -----------------------------
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        viewModelScope.launch {
            if (checked) {
                dataLoader.loadUserData().onFailure {
                    _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.AgamaLoadError(it.message ?: "Error"))
                }
            } else {
                stateManager.clearUserData()
            }
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 Updates
    fun updateNik(value: String) = updateAndClear("nik", value, stateManager::updateNik)
    fun updateNama(value: String) = updateAndClear("nama", value, stateManager::updateNama)
    fun updateAlamat(value: String) = updateAndClear("alamat", value, stateManager::updateAlamat)
    fun updatePekerjaan(value: String) = updateAndClear("pekerjaan", value, stateManager::updatePekerjaan)
    fun updateTanggalLahir(value: String) = updateAndClear("tanggal_lahir", value, stateManager::updateTanggalLahir)
    fun updateTempatLahir(value: String) = updateAndClear("tempat_lahir", value, stateManager::updateTempatLahir)
    fun updateAgamaId(value: String) = updateAndClear("agama_id", value, stateManager::updateAgamaId)
    fun updateKewarganegaraan(value: String) = updateAndClear("kewarganegaraan", value, stateManager::updateKewarganegaraan)
    fun updateNamaAyah(value: String) = updateAndClear("nama_ayah", value, stateManager::updateNamaAyah)
    fun updateNikAyah(value: String) = updateAndClear("nik_ayah", value, stateManager::updateNikAyah)

    // Step 2 Updates
    fun updateNikPasangan(value: String) = updateAndClear("nik_pasangan", value, stateManager::updateNikPasangan)
    fun updateNamaPasangan(value: String) = updateAndClear("nama_pasangan", value, stateManager::updateNamaPasangan)
    fun updateAlamatPasangan(value: String) = updateAndClear("alamat_pasangan", value, stateManager::updateAlamatPasangan)
    fun updatePekerjaanPasangan(value: String) = updateAndClear("pekerjaan_pasangan", value, stateManager::updatePekerjaanPasangan)
    fun updateTanggalLahirPasangan(value: String) = updateAndClear("tanggal_lahir_pasangan", value, stateManager::updateTanggalLahirPasangan)
    fun updateTempatLahirPasangan(value: String) = updateAndClear("tempat_lahir_pasangan", value, stateManager::updateTempatLahirPasangan)
    fun updateAgamaIdPasangan(value: String) = updateAndClear("agama_id_pasangan", value, stateManager::updateAgamaIdPasangan)
    fun updateKewarganegaraanPasangan(value: String) = updateAndClear("kewarganegaraan_pasangan", value, stateManager::updateKewarganegaraanPasangan)
    fun updateNamaAyahPasangan(value: String) = updateAndClear("nama_ayah_pasangan", value, stateManager::updateNamaAyahPasangan)
    fun updateNikAyahPasangan(value: String) = updateAndClear("nik_ayah_pasangan", value, stateManager::updateNikAyahPasangan)

    // Step 3 Update
    fun updateKeperluan(value: String) = updateAndClear("keperluan", value, stateManager::updateKeperluan)

    private fun updateAndClear(field: String, value: String, updater: (String) -> Unit) {
        updater(value)
        validator.clearFieldError(field)
    }

    // -----------------------------
    // Navigation
    // -----------------------------
    fun nextStep() {
        when (currentStep) {
            1 -> if (validateStepWithEvent(1)) {
                stateManager.nextStep()
                emitStepChangedEvent()
            }
            2 -> if (validateStepWithEvent(2)) {
                stateManager.showConfirmation()
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            stateManager.previousStep()
            emitStepChangedEvent()
        }
    }

    // -----------------------------
    // Dialog
    // -----------------------------
    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            stateManager.showConfirmation()
        } else {
            emitValidationError()
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()
    fun showPreview() = stateManager.showPreview()
    fun dismissPreview() = stateManager.hidePreview()

    // -----------------------------
    // Submit
    // -----------------------------
    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.SubmitSuccess) }
                .onFailure { _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.SubmitError(it.message ?: "Error")) }
        }
    }

    // -----------------------------
    // Utilities
    // -----------------------------
    fun clearError() = stateManager.clearError()
    fun getFieldError(field: String): String? = validator.getFieldError(field)
    fun hasFieldError(field: String): Boolean = validator.hasFieldError(field)
    fun hasFormData(): Boolean = stateManager.hasFormData()

    private fun validateStepWithEvent(step: Int): Boolean {
        val isValid = when (step) {
            1 -> validator.validateStep1()
            2 -> validator.validateStep2()
            else -> false
        }
        if (!isValid) emitValidationError()
        return isValid
    }

    private fun emitValidationError() {
        viewModelScope.launch {
            _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.ValidationError)
        }
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skPengantarCeraiRujukEvent.emit(SKPengantarCeraiRujukEvent.StepChanged(currentStep))
        }
    }

    // -----------------------------
    // Event Sealed Class
    // -----------------------------
    sealed class SKPengantarCeraiRujukEvent {
        data class StepChanged(val step: Int) : SKPengantarCeraiRujukEvent()
        data object SubmitSuccess : SKPengantarCeraiRujukEvent()
        data class SubmitError(val message: String) : SKPengantarCeraiRujukEvent()
        data object ValidationError : SKPengantarCeraiRujukEvent()
        data class UserDataLoadError(val message: String) : SKPengantarCeraiRujukEvent()
        data class AgamaLoadError(val message: String) : SKPengantarCeraiRujukEvent()
    }
}
