package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDuplikatKelahiranUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPMDuplikatKelahiranViewModel(
    createSPMDuplikatKelahiranUseCase: CreateSuratDuplikatKelahiranUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPMDuplikatKelahiranStateManager()
    private val validator = SPMDuplikatKelahiranValidator(stateManager)
    private val dataLoader = SPMDuplikatKelahiranDataLoader(
        getUserInfoUseCase, getAgamaUseCase, stateManager, validator
    )
    private val formSubmitter = SPMDuplikatKelahiranFormSubmitter(createSPMDuplikatKelahiranUseCase, stateManager)

    // Events
    private val _spmDuplikatKelahiranEvent = MutableSharedFlow<SPMDuplikatKelahiranEvent>()
    val spmDuplikatKelahiranEvent = _spmDuplikatKelahiranEvent.asSharedFlow()

    // UI State (optional, if needed)
    val uiState = MutableStateFlow(SPMDuplikatKelahiranUiState()).asStateFlow()

    // Delegate properties to stateManager
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

    // Step 1: Informasi Pelapor
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue

    // Step 2: Informasi Anak
    val nikAnakValue: String get() = stateManager.nikAnakValue
    val namaAnakValue: String get() = stateManager.namaAnakValue
    val tanggalLahirAnakValue: String get() = stateManager.tanggalLahirAnakValue
    val tempatLahirAnakValue: String get() = stateManager.tempatLahirAnakValue
    val jenisKelaminAnakValue: String get() = stateManager.jenisKelaminAnakValue
    val agamaIdAnakValue: String get() = stateManager.agamaIdAnakValue
    val alamatAnakValue: String get() = stateManager.alamatAnakValue

    // Step 3: Informasi Orang Tua
    val namaAyahValue: String get() = stateManager.namaAyahValue
    val nikAyahValue: String get() = stateManager.nikAyahValue
    val alamatAyahValue: String get() = stateManager.alamatAyahValue
    val pekerjaanAyahValue: String get() = stateManager.pekerjaanAyahValue
    val namaIbuValue: String get() = stateManager.namaIbuValue
    val nikIbuValue: String get() = stateManager.nikIbuValue
    val alamatIbuValue: String get() = stateManager.alamatIbuValue
    val pekerjaanIbuValue: String get() = stateManager.pekerjaanIbuValue

    // Step 4: Informasi Pelengkap
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.UserDataLoadError(it.message ?: "Error"))
                }
                dataLoader.loadAgama().onFailure {
                    _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.AgamaLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure {
                _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.AgamaLoadError(it.message ?: "Error"))
            }
        }
    }

    // Step 1 update functions (Informasi Pelapor)
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

    // Step 2 update functions (Informasi Anak)
    fun updateNikAnak(value: String) {
        stateManager.updateNikAnak(value)
        validator.clearFieldError("nik_anak")
    }
    fun updateNamaAnak(value: String) {
        stateManager.updateNamaAnak(value)
        validator.clearFieldError("nama_anak")
    }
    fun updateTanggalLahirAnak(value: String) {
        stateManager.updateTanggalLahirAnak(value)
        validator.clearFieldError("tanggal_lahir_anak")
    }
    fun updateTempatLahirAnak(value: String) {
        stateManager.updateTempatLahirAnak(value)
        validator.clearFieldError("tempat_lahir_anak")
    }
    fun updateJenisKelaminAnak(value: String) {
        stateManager.updateJenisKelaminAnak(value)
        validator.clearFieldError("jenis_kelamin_anak")
    }
    fun updateAgamaIdAnak(value: String) {
        stateManager.updateAgamaIdAnak(value)
        validator.clearFieldError("agama_id_anak")
    }
    fun updateAlamatAnak(value: String) {
        stateManager.updateAlamatAnak(value)
        validator.clearFieldError("alamat_anak")
    }

    // Step 3 update functions (Informasi Orang Tua)
    fun updateNamaAyah(value: String) {
        stateManager.updateNamaAyah(value)
        validator.clearFieldError("nama_ayah")
    }
    fun updateNikAyah(value: String) {
        stateManager.updateNikAyah(value)
        validator.clearFieldError("nik_ayah")
    }
    fun updateAlamatAyah(value: String) {
        stateManager.updateAlamatAyah(value)
        validator.clearFieldError("alamat_ayah")
    }
    fun updatePekerjaanAyah(value: String) {
        stateManager.updatePekerjaanAyah(value)
        validator.clearFieldError("pekerjaan_ayah")
    }
    fun updateNamaIbu(value: String) {
        stateManager.updateNamaIbu(value)
        validator.clearFieldError("nama_ibu")
    }
    fun updateNikIbu(value: String) {
        stateManager.updateNikIbu(value)
        validator.clearFieldError("nik_ibu")
    }
    fun updateAlamatIbu(value: String) {
        stateManager.updateAlamatIbu(value)
        validator.clearFieldError("alamat_ibu")
    }
    fun updatePekerjaanIbu(value: String) {
        stateManager.updatePekerjaanIbu(value)
        validator.clearFieldError("pekerjaan_ibu")
    }

    // Step 4 update functions (Informasi Pelengkap)
    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Navigation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> if (validateStepWithEvent(1)) {
                stateManager.nextStep()
                emitStepChangedEvent()
            }
            2 -> if (validateStepWithEvent(2)) {
                stateManager.nextStep()
                emitStepChangedEvent()
            }
            3 -> if (validateStepWithEvent(3)) {
                stateManager.nextStep()
                emitStepChangedEvent()
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
                _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.ValidationError)
            }
            return
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.hidePreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            stateManager.showConfirmation()
        } else {
            viewModelScope.launch {
                _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess {
                    _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.SubmitSuccess)
                }
                .onFailure {
                    _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.SubmitError(it.message ?: "Error"))
                }
        }
    }

    // Validation helper
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utilities
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
                _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spmDuplikatKelahiranEvent.emit(SPMDuplikatKelahiranEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPMDuplikatKelahiranEvent {
        data class StepChanged(val step: Int) : SPMDuplikatKelahiranEvent()
        data object SubmitSuccess : SPMDuplikatKelahiranEvent()
        data class SubmitError(val message: String) : SPMDuplikatKelahiranEvent()
        data object ValidationError : SPMDuplikatKelahiranEvent()
        data class UserDataLoadError(val message: String) : SPMDuplikatKelahiranEvent()
        data class AgamaLoadError(val message: String) : SPMDuplikatKelahiranEvent()
    }
}