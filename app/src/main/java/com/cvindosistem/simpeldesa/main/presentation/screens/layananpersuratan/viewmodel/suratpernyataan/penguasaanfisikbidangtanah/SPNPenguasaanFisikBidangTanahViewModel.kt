package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernyataanSporadikUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPNPenguasaanFisikBidangTanahViewModel(
    createSPNPenguasaanFisikBidangTanahUseCase: CreateSuratPernyataanSporadikUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composition of components
    private val stateManager = SPNPenguasaanFisikBidangTanahStateManager()
    private val validator = SPNPenguasaanFisikBidangTanahValidator(stateManager)
    private val dataLoader = SPNPenguasaanFisikBidangTanahDataLoader(
        getUserInfoUseCase, stateManager, validator
    )
    private val formSubmitter = SPNPenguasaanFisikBidangTanahFormSubmitter(
        createSPNPenguasaanFisikBidangTanahUseCase, stateManager
    )

    // Events
    private val _spnPenguasaanFisikEvent = MutableSharedFlow<SPNPenguasaanFisikEvent>()
    val spnPenguasaanFisikEvent = _spnPenguasaanFisikEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val uiState = MutableStateFlow(SPNPenguasaanFisikBidangTanahUiState()).asStateFlow()

    // Delegate properties to stateManager (public interface unchanged)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Step 1 form field properties - Data Pemohon
    val nikPemohonValue: String get() = stateManager.nikPemohonValue
    val namaPemohonValue: String get() = stateManager.namaPemohonValue
    val tempatLahirPemohonValue: String get() = stateManager.tempatLahirPemohonValue
    val tanggalLahirPemohonValue: String get() = stateManager.tanggalLahirPemohonValue
    val pekerjaanValue: String get() = stateManager.pekerjaanValue

    // Step 2 form field properties - Lokasi & Identitas Tanah
    val alamatValue: String get() = stateManager.alamatValue
    val alamat1Value: String get() = stateManager.alamat1Value
    val alamat2Value: String get() = stateManager.alamat2Value
    val rtRwValue: String get() = stateManager.rtRwValue
    val jalanValue: String get() = stateManager.jalanValue
    val desaValue: String get() = stateManager.desaValue
    val kecamatanValue: String get() = stateManager.kecamatanValue
    val kabupatenValue: String get() = stateManager.kabupatenValue
    val nibValue: String get() = stateManager.nibValue
    val luasTanahValue: String get() = stateManager.luasTanahValue
    val statusTanahValue: String get() = stateManager.statusTanahValue
    val diperggunakanValue: String get() = stateManager.diperggunakanValue

    // Step 3 form field properties - Perolehan & Batas Tanah
    val diperolehDariValue: String get() = stateManager.diperolehDariValue
    val diperolehDenganValue: String get() = stateManager.diperolehDenganValue
    val diperolehSejakValue: String get() = stateManager.diperolehSejakValue
    val batasUtaraValue: String get() = stateManager.batasUtaraValue
    val batasTimurValue: String get() = stateManager.batasTimurValue
    val batasSelatanValue: String get() = stateManager.batasSelatanValue
    val batasBaratValue: String get() = stateManager.batasBaratValue

    // Step 4 form field properties - Data Saksi
    val nik1Value: String get() = stateManager.nik1Value
    val nama1Value: String get() = stateManager.nama1Value
    val pekerjaan1Value: String get() = stateManager.pekerjaan1Value
    val isSaksi1WargaDesaValue: Boolean get() = stateManager.isSaksi1WargaDesaValue
    val nik2Value: String get() = stateManager.nik2Value
    val nama2Value: String get() = stateManager.nama2Value
    val pekerjaan2Value: String get() = stateManager.pekerjaan2Value
    val isSaksi2WargaDesaValue: Boolean get() = stateManager.isSaksi2WargaDesaValue

    // Step 5 form field properties - Keperluan
    val keperluanValue: String get() = stateManager.keperluanValue

    // Public functions - delegate to appropriate components
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyDataChecked(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData().onFailure {
                    _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.UserDataLoadError(it.message ?: "Error"))
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    // Step 1 update functions - delegate to stateManager and clear errors
    fun updateNikPemohon(value: String) {
        stateManager.updateNikPemohon(value)
        validator.clearFieldError("nik_pemohon")
    }

    fun updateNamaPemohon(value: String) {
        stateManager.updateNamaPemohon(value)
        validator.clearFieldError("nama_pemohon")
    }

    fun updateTempatLahirPemohon(value: String) {
        stateManager.updateTempatLahirPemohon(value)
        validator.clearFieldError("tempat_lahir_pemohon")
    }

    fun updateTanggalLahirPemohon(value: String) {
        stateManager.updateTanggalLahirPemohon(value)
        validator.clearFieldError("tanggal_lahir_pemohon")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    // Step 2 update functions
    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateAlamat1(value: String) {
        stateManager.updateAlamat1(value)
        validator.clearFieldError("alamat_1")
    }

    fun updateAlamat2(value: String) {
        stateManager.updateAlamat2(value)
        validator.clearFieldError("alamat_2")
    }

    fun updateRtRw(value: String) {
        stateManager.updateRtRw(value)
        validator.clearFieldError("rt_rw")
    }

    fun updateJalan(value: String) {
        stateManager.updateJalan(value)
        validator.clearFieldError("jalan")
    }

    fun updateDesa(value: String) {
        stateManager.updateDesa(value)
        validator.clearFieldError("desa")
    }

    fun updateKecamatan(value: String) {
        stateManager.updateKecamatan(value)
        validator.clearFieldError("kecamatan")
    }

    fun updateKabupaten(value: String) {
        stateManager.updateKabupaten(value)
        validator.clearFieldError("kabupaten")
    }

    fun updateNib(value: String) {
        stateManager.updateNib(value)
        validator.clearFieldError("nib")
    }

    fun updateLuasTanah(value: String) {
        stateManager.updateLuasTanah(value)
        validator.clearFieldError("luas_tanah")
    }

    fun updateStatusTanah(value: String) {
        stateManager.updateStatusTanah(value)
        validator.clearFieldError("status_tanah")
    }

    fun updateDipergunakan(value: String) {
        stateManager.updateDipergunakan(value)
        validator.clearFieldError("dipergunakan")
    }

    // Step 3 update functions
    fun updateDiperolehDari(value: String) {
        stateManager.updateDiperolehDari(value)
        validator.clearFieldError("diperoleh_dari")
    }

    fun updateDiperolehDengan(value: String) {
        stateManager.updateDiperolehDengan(value)
        validator.clearFieldError("diperoleh_dengan")
    }

    fun updateDiperolehSejak(value: String) {
        stateManager.updateDiperolehSejak(value)
        validator.clearFieldError("diperoleh_sejak")
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

    // Step 4 update functions
    fun updateNik1(value: String) {
        stateManager.updateNik1(value)
        validator.clearFieldError("nik_1")
    }

    fun updateNama1(value: String) {
        stateManager.updateNama1(value)
        validator.clearFieldError("nama_1")
    }

    fun updatePekerjaan1(value: String) {
        stateManager.updatePekerjaan1(value)
        validator.clearFieldError("pekerjaan_1")
    }

    fun updateIsSaksi1WargaDesa(value: Boolean) {
        stateManager.updateIsSaksi1WargaDesa(value)
    }

    fun updateNik2(value: String) {
        stateManager.updateNik2(value)
        validator.clearFieldError("nik_2")
    }

    fun updateNama2(value: String) {
        stateManager.updateNama2(value)
        validator.clearFieldError("nama_2")
    }

    fun updatePekerjaan2(value: String) {
        stateManager.updatePekerjaan2(value)
        validator.clearFieldError("pekerjaan_2")
    }

    fun updateIsSaksi2WargaDesa(value: Boolean) {
        stateManager.updateIsSaksi2WargaDesa(value)
    }

    // Step 5 update functions
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
                _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.ValidationError)
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
                _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.hideConfirmation()

    fun confirmSubmit() {
        stateManager.hideConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
                .onSuccess { _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.SubmitSuccess) }
                .onFailure { _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.SubmitError(it.message ?: "Error")) }
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
            5 -> validator.validateStep5()
            else -> false
        }
        if (!isValid) {
            viewModelScope.launch {
                _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _spnPenguasaanFisikEvent.emit(SPNPenguasaanFisikEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events
    sealed class SPNPenguasaanFisikEvent {
        data class StepChanged(val step: Int) : SPNPenguasaanFisikEvent()
        data object SubmitSuccess : SPNPenguasaanFisikEvent()
        data class SubmitError(val message: String) : SPNPenguasaanFisikEvent()
        data object ValidationError : SPNPenguasaanFisikEvent()
        data class UserDataLoadError(val message: String) : SPNPenguasaanFisikEvent()
    }
}