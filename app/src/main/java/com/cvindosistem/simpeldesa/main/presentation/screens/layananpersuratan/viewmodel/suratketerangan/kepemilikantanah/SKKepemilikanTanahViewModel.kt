package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganKepemilikanTanahUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKepemilikanTanahViewModel(
    createSKKepemilikanTanahUseCase: CreateSuratKeteranganKepemilikanTanahUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SKKepemilikanTanahStateManager()
    private val validator = SKKepemilikanTanahValidator(stateManager)
    private val dataLoader = SKKepemilikanTanahDataLoader(
        getUserInfoUseCase, stateManager, validator
    )
    private val formSubmitter = SKKepemilikanTanahFormSubmitter(createSKKepemilikanTanahUseCase, stateManager)

    // Events
    private val _skKepemilikanTanahEvent = MutableSharedFlow<SKKepemilikanTanahEvent>()
    val skKepemilikanTanahEvent = _skKepemilikanTanahEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SKKepemilikanTanahUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Form field properties - Step 1 (Data Pemohon)
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val tempatLahirValue: String get() = stateManager.tempatLahirValue
    val tanggalLahirValue: String get() = stateManager.tanggalLahirValue
    val jenisKelaminValue: String get() = stateManager.jenisKelaminValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue

    // Form field properties - Step 2 (Informasi Tanah)
    val alamatValue: String get() = stateManager.alamatValue
    val luasTanahValue: String get() = stateManager.luasTanahValue
    val jenisTanahValue: String get() = stateManager.jenisTanahValue
    val batasUtaraValue: String get() = stateManager.batasUtaraValue
    val batasTimurValue: String get() = stateManager.batasTimurValue
    val batasSelatanValue: String get() = stateManager.batasSelatanValue
    val batasBaratValue: String get() = stateManager.batasBaratValue

    // Form field properties - Step 3 (Bukti Kepemilikan)
    val atasNamaValue: String get() = stateManager.atasNamaValue
    val asalKepemilikanTanahValue: String get() = stateManager.asalKepemilikanTanahValue
    val buktiKepemilikanTanahValue: String get() = stateManager.buktiKepemilikanTanahValue
    val buktiKepemilikanTanahTanahValue: String get() = stateManager.buktiKepemilikanTanahTanahValue
    val nomorBuktiKepemilikanValue: String get() = stateManager.nomorBuktiKepemilikanValue

    // Form field properties - Step 4 (Keperluan)
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 update functions - delegate to stateManager dan clear errors
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

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    // Step 2 Update Functions
    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateLuasTanah(value: String) {
        stateManager.updateLuasTanah(value)
        validator.clearFieldError("luas_tanah")
    }

    fun updateJenisTanah(value: String) {
        stateManager.updateJenisTanah(value)
        validator.clearFieldError("jenis_tanah")
    }

    fun updateBatasUtara(value: String) {
        stateManager.updateBatasUtara(value)
        validator.clearFieldError("batas_utara")
    }

    fun updateBatasTimur(value: String) {
        stateManager.updateBatasTimur(value)
        validator.clearFieldError("batas_timur")
    }

    fun updateBatasSelatan(value: String) {
        stateManager.updateBatasSelatan(value)
        validator.clearFieldError("batas_selatan")
    }

    fun updateBatasBarat(value: String) {
        stateManager.updateBatasBarat(value)
        validator.clearFieldError("batas_barat")
    }

    // Step 3 Update Functions
    fun updateAtasNama(value: String) {
        stateManager.updateAtasNama(value)
        validator.clearFieldError("atas_nama")
    }

    fun updateAsalKepemilikanTanah(value: String) {
        stateManager.updateAsalKepemilikanTanah(value)
        validator.clearFieldError("asal_kepemilikan_tanah")
    }

    fun updateBuktiKepemilikanTanah(value: String) {
        stateManager.updateBuktiKepemilikanTanah(value)
        validator.clearFieldError("bukti_kepemilikan_tanah")
    }

    fun updateBuktiKepemilikanTanahTanah(value: String) {
        stateManager.updateBuktiKepemilikanTanahTanah(value)
        validator.clearFieldError("bukti_kepemilikan_tanah_tanah")
    }

    fun updateNomorBuktiKepemilikan(value: String) {
        stateManager.updateNomorBuktiKepemilikan(value)
        validator.clearFieldError("nomor_bukti_kepemilikan")
    }

    // Step 4 Update Functions
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
                _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.ValidationError)
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
                _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.SubmitSuccess) }
                .onFailure { _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.SubmitError(it.message ?: "Error")) }
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
                _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skKepemilikanTanahEvent.emit(SKKepemilikanTanahEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SKKepemilikanTanahEvent {
        data class StepChanged(val step: Int) : SKKepemilikanTanahEvent()
        data object SubmitSuccess : SKKepemilikanTanahEvent()
        data class SubmitError(val message: String) : SKKepemilikanTanahEvent()
        data object ValidationError : SKKepemilikanTanahEvent()
        data class UserDataLoadError(val message: String) : SKKepemilikanTanahEvent()
    }
}