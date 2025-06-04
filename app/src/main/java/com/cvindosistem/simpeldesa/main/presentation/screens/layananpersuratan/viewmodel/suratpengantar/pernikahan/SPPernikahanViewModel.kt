package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPernikahanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernikahanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPPernikahanViewModel(
    createSuratPernikahanUseCase: CreateSuratPernikahanUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase,
    getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Komponen-komponen
    private val stateManager = SPPernikahanStateManager()
    private val validator = SPPernikahanValidator()
    private val dataLoader = SPPernikahanDataLoader(
        getUserInfoUseCase, getAgamaUseCase, getStatusKawinUseCase,
        stateManager, validator
    )
    private val formSubmitter = SPPernikahanFormSubmitter(
        createSuratPernikahanUseCase, stateManager
    )

    // UI State exposure - Delegate ke StateManager
    private val _uiState = MutableStateFlow(SPPernikahanUiState())
    val uiState = _uiState.asStateFlow()

    // Expose states (delegate ke StateManager)
    val isLoading: Boolean get() = stateManager.isLoading
    val errorMessage: String? get() = stateManager.errorMessage
    val currentStep: Int get() = stateManager.currentStep
    val agamaList: List<AgamaResponse.Data> get() = stateManager.agamaList
    val statusKawinList: List<StatusKawinResponse.Data> get() = stateManager.statusKawinList
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = stateManager.isLoadingUserData
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog

    // Form field exposures - delegate ke StateManager
    val nikSuamiValue: String get() = stateManager.nikSuamiValue
    val namaSuamiValue: String get() = stateManager.namaSuamiValue
    val tempatLahirSuamiValue: String get() = stateManager.tempatLahirSuamiValue
    val tanggalLahirSuamiValue: String get() = stateManager.tanggalLahirSuamiValue
    val pekerjaanSuamiValue: String get() = stateManager.pekerjaanSuamiValue
    val alamatSuamiValue: String get() = stateManager.alamatSuamiValue
    val agamaSuamiIdValue: String get() = stateManager.agamaSuamiIdValue
    val kewarganegaraanSuamiValue: String get() = stateManager.kewarganegaraanSuamiValue
    val jumlahIstriValue: Int get() = stateManager.jumlahIstriValue
    val statusKawinSuamiIdValue: String get() = stateManager.statusKawinSuamiIdValue
    val namaIstriSebelumnyaValue: String get() = stateManager.namaIstriSebelumnyaValue
    val agamaAyahSuamiIdValue: String get() = stateManager.agamaAyahSuamiIdValue
    val alamatAyahSuamiValue: String get() = stateManager.alamatAyahSuamiValue
    val kewarganegaraanAyahSuamiValue: String get() = stateManager.kewarganegaraanAyahSuamiValue
    val namaAyahSuamiValue: String get() = stateManager.namaAyahSuamiValue
    val nikAyahSuamiValue: String get() = stateManager.nikAyahSuamiValue
    val pekerjaanAyahSuamiValue: String get() = stateManager.pekerjaanAyahSuamiValue
    val tempatLahirAyahSuamiValue: String get() = stateManager.tempatLahirAyahSuamiValue
    val tanggalLahirAyahSuamiValue: String get() = stateManager.tanggalLahirAyahSuamiValue

    val agamaIbuSuamiIdValue: String get() = stateManager.agamaIbuSuamiIdValue
    val alamatIbuSuamiValue: String get() = stateManager.alamatIbuSuamiValue
    val kewarganegaraanIbuSuamiValue: String get() = stateManager.kewarganegaraanIbuSuamiValue
    val namaIbuSuamiValue: String get() = stateManager.namaIbuSuamiValue
    val nikIbuSuamiValue: String get() = stateManager.nikIbuSuamiValue
    val pekerjaanIbuSuamiValue: String get() = stateManager.pekerjaanIbuSuamiValue
    val tanggalLahirIbuSuamiValue: String get() = stateManager.tanggalLahirIbuSuamiValue
    val tempatLahirIbuSuamiValue: String get() = stateManager.tempatLahirIbuSuamiValue

    val agamaIstriIdValue: String get() = stateManager.agamaIstriIdValue
    val alamatIstriValue: String get() = stateManager.alamatIstriValue
    val kewarganegaraanIstriValue: String get() = stateManager.kewarganegaraanIstriValue
    val namaIstriValue: String get() = stateManager.namaIstriValue
    val nikIstriValue: String get() = stateManager.nikIstriValue
    val pekerjaanIstriValue: String get() = stateManager.pekerjaanIstriValue
    val statusKawinIstriIdValue: String get() = stateManager.statusKawinIstriIdValue
    val tanggalLahirIstriValue: String get() = stateManager.tanggalLahirIstriValue
    val tempatLahirIstriValue: String get() = stateManager.tempatLahirIstriValue
    val namaSuamiSebelumnyaValue: String get() = stateManager.namaSuamiSebelumnyaValue

    val agamaAyahIstriIdValue: String get() = stateManager.agamaAyahIstriIdValue
    val agamaIbuIstriIdValue: String get() = stateManager.agamaIbuIstriIdValue
    val alamatAyahIstriValue: String get() = stateManager.alamatAyahIstriValue
    val alamatIbuIstriValue: String get() = stateManager.alamatIbuIstriValue
    val kewarganegaraanAyahIstriValue: String get() = stateManager.kewarganegaraanAyahIstriValue
    val kewarganegaraanIbuIstriValue: String get() = stateManager.kewarganegaraanIbuIstriValue
    val namaAyahIstriValue: String get() = stateManager.namaAyahIstriValue
    val namaIbuIstriValue: String get() = stateManager.namaIbuIstriValue
    val nikAyahIstriValue: String get() = stateManager.nikAyahIstriValue
    val nikIbuIstriValue: String get() = stateManager.nikIbuIstriValue
    val pekerjaanAyahIstriValue: String get() = stateManager.pekerjaanAyahIstriValue
    val pekerjaanIbuIstriValue: String get() = stateManager.pekerjaanIbuIstriValue
    val tanggalLahirAyahIstriValue: String get() = stateManager.tanggalLahirAyahIstriValue
    val tanggalLahirIbuIstriValue: String get() = stateManager.tanggalLahirIbuIstriValue
    val tempatLahirAyahIstriValue: String get() = stateManager.tempatLahirAyahIstriValue
    val tempatLahirIbuIstriValue: String get() = stateManager.tempatLahirIbuIstriValue

    val jamPernikahanValue: String get() = stateManager.jamPernikahanValue
    val tempatPernikahanValue: String get() = stateManager.tempatPernikahanValue
    val hariPernikahanValue: String get() = stateManager.hariPernikahanValue
    val tanggalPernikahanValue: String get() = stateManager.tanggalPernikahanValue


    // Events
    private val _pernikahanEvent = MutableSharedFlow<SPPernikahanEvent>()
    val pernikahanEvent = _pernikahanEvent.asSharedFlow()

    // Validation exposure - delegate ke Validator
    val validationErrors = validator.validationErrors

    // PUBLIC INTERFACE - Sama seperti ViewModel asli

    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyData(checked)
        if (checked) {
            viewModelScope.launch {
                val userDataResult = dataLoader.loadUserData()
                val agamaResult = dataLoader.loadAgama()
                val statusKawinResult = dataLoader.loadStatusKawin()

                // Handle results and emit events
                if (userDataResult.isFailure) {
                    _pernikahanEvent.emit(
                        SPPernikahanEvent.UserDataLoadError(
                            userDataResult.exceptionOrNull()?.message ?: "Error"
                        )
                    )
                }
                if (agamaResult.isFailure) {
                    _pernikahanEvent.emit(
                        SPPernikahanEvent.AgamaLoadError(
                            agamaResult.exceptionOrNull()?.message ?: "Error"
                        )
                    )
                }
                if (statusKawinResult.isFailure) {
                    _pernikahanEvent.emit(
                        SPPernikahanEvent.StatusKawinLoadError(
                            statusKawinResult.exceptionOrNull()?.message ?: "Error"
                        )
                    )
                }
            }
        } else {
            stateManager.clearUserData()
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            val result = dataLoader.loadAgama()
            if (result.isFailure) {
                _pernikahanEvent.emit(
                    SPPernikahanEvent.AgamaLoadError(
                        result.exceptionOrNull()?.message ?: "Error"
                    )
                )
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            val result = dataLoader.loadStatusKawin()
            if (result.isFailure) {
                _pernikahanEvent.emit(
                    SPPernikahanEvent.StatusKawinLoadError(
                        result.exceptionOrNull()?.message ?: "Error"
                    )
                )
            }
        }
    }

    // Form field updates - delegate ke StateManager + clear validation
    fun updateNikSuami(value: String) {
        stateManager.updateNikSuami(value)
        validator.clearFieldError("nik_suami")
    }

    fun updateNamaSuami(value: String) {
        stateManager.updateNamaSuami(value)
        validator.clearFieldError("nama_suami")
    }

    fun updateTempatLahirSuami(value: String) {
        stateManager.updateTempatLahirSuami(value)
        validator.clearFieldError("tempat_lahir_suami")
    }

    fun updateTanggalLahirSuami(value: String) {
        stateManager.updateTanggalLahirSuami(value)
        validator.clearFieldError("tanggal_lahir_suami")
    }

    fun updatePekerjaanSuami(value: String) {
        stateManager.updatePekerjaanSuami(value)
        validator.clearFieldError("pekerjaan_suami")
    }

    fun updateAlamatSuami(value: String) {
        stateManager.updateAlamatSuami(value)
        validator.clearFieldError("alamat_suami")
    }

    fun updateAgamaSuamiId(value: String) {
        stateManager.updateAgamaSuamiId(value)
        validator.clearFieldError("agama_suami_id")
    }

    fun updateKewarganegaraanSuami(value: String) {
        stateManager.updateKewarganegaraanSuami(value)
        validator.clearFieldError("kewarganegaraan_suami")
    }

    fun updateJumlahIstri(value: Int) {
        stateManager.updateJumlahIstri(value)
        validator.clearFieldError("jumlah_istri")
    }

    fun updateStatusKawinSuamiId(value: String) {
        stateManager.updateStatusKawinSuamiId(value)
        validator.clearFieldError("status_kawin_suami_id")
    }

    fun updateNamaIstriSebelumnya(value: String) {
        stateManager.updateNamaIstriSebelumnya(value)
        validator.clearFieldError("nama_istri_sebelumnya")
    }

    fun updateAgamaAyahSuamiId(value: String) {
        stateManager.updateAgamaAyahSuamiId(value)
        validator.clearFieldError("agama_ayah_suami_id")
    }

    fun updateAlamatAyahSuami(value: String) {
        stateManager.updateAlamatAyahSuami(value)
        validator.clearFieldError("alamat_ayah_suami")
    }

    fun updateKewarganegaraanAyahSuami(value: String) {
        stateManager.updateKewarganegaraanAyahSuami(value)
        validator.clearFieldError("kewarganegaraan_ayah_suami")
    }

    fun updateNamaAyahSuami(value: String) {
        stateManager.updateNamaAyahSuami(value)
        validator.clearFieldError("nama_ayah_suami")
    }

    fun updateNikAyahSuami(value: String) {
        stateManager.updateNikAyahSuami(value)
        validator.clearFieldError("nik_ayah_suami")
    }

    fun updatePekerjaanAyahSuami(value: String) {
        stateManager.updatePekerjaanAyahSuami(value)
        validator.clearFieldError("pekerjaan_ayah_suami")
    }

    fun updateTempatLahirAyahSuami(value: String) {
        stateManager.updateTempatLahirAyahSuami(value)
        validator.clearFieldError("tempat_lahir_ayah_suami")
    }

    fun updateTanggalLahiAyahSuami(value: String) {
        stateManager.updateTanggalLahiAyahSuami(value)
        validator.clearFieldError("tanggal_lahi_ayah_suami")
    }

    fun updateTanggalLahirAyahSuami(value: String) {
        stateManager.updateTanggalLahirAyahSuami(value)
        validator.clearFieldError("tanggal_lahir_ayah_suami")
    }

    fun updateAgamaIbuSuamiId(value: String) {
        stateManager.updateAgamaIbuSuamiId(value)
        validator.clearFieldError("agama_ibu_suami_id")
    }

    fun updateAlamatIbuSuami(value: String) {
        stateManager.updateAlamatIbuSuami(value)
        validator.clearFieldError("alamat_ibu_suami")
    }

    fun updateKewarganegaraanIbuSuami(value: String) {
        stateManager.updateKewarganegaraanIbuSuami(value)
        validator.clearFieldError("kewarganegaraan_ibu_suami")
    }

    fun updateNamaIbuSuami(value: String) {
        stateManager.updateNamaIbuSuami(value)
        validator.clearFieldError("nama_ibu_suami")
    }

    fun updateNikIbuSuami(value: String) {
        stateManager.updateNikIbuSuami(value)
        validator.clearFieldError("nik_ibu_suami")
    }

    fun updatePekerjaanIbuSuami(value: String) {
        stateManager.updatePekerjaanIbuSuami(value)
        validator.clearFieldError("pekerjaan_ibu_suami")
    }

    fun updateTanggalLahiIbuSuami(value: String) {
        stateManager.updateTanggalLahiIbuSuami(value)
        validator.clearFieldError("tanggal_lahi_ibu_suami")
    }

    fun updateTanggalLahirIbuSuami(value: String) {
        stateManager.updateTanggalLahirIbuSuami(value)
        validator.clearFieldError("tanggal_lahir_ibu_suami")
    }

    fun updateTempatLahirIbuSuami(value: String) {
        stateManager.updateTempatLahirIbuSuami(value)
        validator.clearFieldError("tempat_lahir_ibu_suami")
    }

    fun updateAgamaIstriId(value: String) {
        stateManager.updateAgamaIstriId(value)
        validator.clearFieldError("agama_istri_id")
    }

    fun updateAlamatIstri(value: String) {
        stateManager.updateAlamatIstri(value)
        validator.clearFieldError("alamat_istri")
    }

    fun updateKewarganegaraanIstri(value: String) {
        stateManager.updateKewarganegaraanIstri(value)
        validator.clearFieldError("kewarganegaraan_istri")
    }

    fun updateNamaIstri(value: String) {
        stateManager.updateNamaIstri(value)
        validator.clearFieldError("nama_istri")
    }

    fun updateNikIstri(value: String) {
        stateManager.updateNikIstri(value)
        validator.clearFieldError("nik_istri")
    }

    fun updatePekerjaanIstri(value: String) {
        stateManager.updatePekerjaanIstri(value)
        validator.clearFieldError("pekerjaan_istri")
    }

    fun updateStatusKawinIstriId(value: String) {
        stateManager.updateStatusKawinIstriId(value)
        validator.clearFieldError("status_kawin_istri_id")
    }

    fun updateTanggalLahirIstri(value: String) {
        stateManager.updateTanggalLahirIstri(value)
        validator.clearFieldError("tanggal_lahir_istri")
    }

    fun updateTempatLahirIstri(value: String) {
        stateManager.updateTempatLahirIstri(value)
        validator.clearFieldError("tempat_lahir_istri")
    }

    fun updateNamaSuamiSebelumnya(value: String) {
        stateManager.updateNamaSuamiSebelumnya(value)
        validator.clearFieldError("nama_suami_sebelumnya")
    }

    fun updateAgamaAyahIstriId(value: String) {
        stateManager.updateAgamaAyahIstriId(value)
        validator.clearFieldError("agama_ayah_istri_id")
    }

    fun updateAgamaIbuIstriId(value: String) {
        stateManager.updateAgamaIbuIstriId(value)
        validator.clearFieldError("agama_ibu_istri_id")
    }

    fun updateAlamatAyahIstri(value: String) {
        stateManager.updateAlamatAyahIstri(value)
        validator.clearFieldError("alamat_ayah_istri")
    }

    fun updateAlamatIbuIstri(value: String) {
        stateManager.updateAlamatIbuIstri(value)
        validator.clearFieldError("alamat_ibu_istri")
    }

    fun updateKewarganegaraanAyahIstri(value: String) {
        stateManager.updateKewarganegaraanAyahIstri(value)
        validator.clearFieldError("kewarganegaraan_ayah_istri")
    }

    fun updateKewarganegaraanIbuIstri(value: String) {
        stateManager.updateKewarganegaraanIbuIstri(value)
        validator.clearFieldError("kewarganegaraan_ibu_istri")
    }

    fun updateNamaAyahIstri(value: String) {
        stateManager.updateNamaAyahIstri(value)
        validator.clearFieldError("nama_ayah_istri")
    }

    fun updateNamaIbuIstri(value: String) {
        stateManager.updateNamaIbuIstri(value)
        validator.clearFieldError("nama_ibu_istri")
    }

    fun updateNikAyahIstri(value: String) {
        stateManager.updateNikAyahIstri(value)
        validator.clearFieldError("nik_ayah_istri")
    }

    fun updateNikIbuIstri(value: String) {
        stateManager.updateNikIbuIstri(value)
        validator.clearFieldError("nik_ibu_istri")
    }

    fun updatePekerjaanAyahIstri(value: String) {
        stateManager.updatePekerjaanAyahIstri(value)
        validator.clearFieldError("pekerjaan_ayah_istri")
    }

    fun updatePekerjaanIbuIstri(value: String) {
        stateManager.updatePekerjaanIbuIstri(value)
        validator.clearFieldError("pekerjaan_ibu_istri")
    }

    fun updateTanggalLahiAyahIstri(value: String) {
        stateManager.updateTanggalLahiAyahIstri(value)
        validator.clearFieldError("tanggal_lahi_ayah_istri")
    }

    fun updateTanggalLahiIbuIstri(value: String) {
        stateManager.updateTanggalLahiIbuIstri(value)
        validator.clearFieldError("tanggal_lahi_ibu_istri")
    }

    fun updateTanggalLahirAyahIstri(value: String) {
        stateManager.updateTanggalLahirAyahIstri(value)
        validator.clearFieldError("tanggal_lahir_ayah_istri")
    }

    fun updateTanggalLahirIbuIstri(value: String) {
        stateManager.updateTanggalLahirIbuIstri(value)
        validator.clearFieldError("tanggal_lahir_ibu_istri")
    }

    fun updateTempatLahirAyahIstri(value: String) {
        stateManager.updateTempatLahirAyahIstri(value)
        validator.clearFieldError("tempat_lahir_ayah_istri")
    }

    fun updateTempatLahirIbuIstri(value: String) {
        stateManager.updateTempatLahirIbuIstri(value)
        validator.clearFieldError("tempat_lahir_ibu_istri")
    }

    fun updateJamPernikahan(value: String) {
        stateManager.updateJamPernikahan(value)
        validator.clearFieldError("jam_pernikahan")
    }

    fun updateTempatPernikahan(value: String) {
        stateManager.updateTempatPernikahan(value)
        validator.clearFieldError("tempat_pernikahan")
    }

    fun updateHariPernikahan(value: String) {
        stateManager.updateHariPernikahan(value)
        validator.clearFieldError("hari_pernikahan")
    }

    fun updateTanggalPernikahan(value: String) {
        stateManager.updateTanggalPernikahan(value)
        validator.clearFieldError("tanggal_pernikahan")
    }

    // Step navigation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStepWithEvent { validator.validateStep1(stateManager) }) {
                    stateManager.updateCurrentStep(2)
                    emitStepChangedEvent()
                }
            }
            2 -> {
                if (validateStepWithEvent { validator.validateStep2(stateManager) }) {
                    stateManager.updateCurrentStep(3)
                    emitStepChangedEvent()
                }
            }
            3 -> {
                if (validateStepWithEvent { validator.validateStep3(stateManager) }) {
                    stateManager.updateCurrentStep(4)
                    emitStepChangedEvent()
                }
            }
            4 -> {
                if (validateStepWithEvent { validator.validateStep4(stateManager) }) {
                    stateManager.updateCurrentStep(5)
                    emitStepChangedEvent()
                }
            }
            5 -> {
                if (validateStepWithEvent { validator.validateStep5(stateManager) }) {
                    stateManager.updateConfirmationDialog(true)
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.updateCurrentStep(stateManager.currentStep - 1)
            emitStepChangedEvent()
        }
    }

    // Dialog management
    fun showPreview() {
        validator.validateAllSteps(stateManager)
        stateManager.updatePreviewDialog(true)
    }

    fun dismissPreview() {
        stateManager.updatePreviewDialog(false)
    }

    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.updateConfirmationDialog(true)
        } else {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        stateManager.updateConfirmationDialog(false)
    }

    fun confirmSubmit() {
        stateManager.updateConfirmationDialog(false)
        viewModelScope.launch {
            val result = formSubmitter.submitForm()
            if (result.isSuccess) {
                _pernikahanEvent.emit(SPPernikahanEvent.SubmitSuccess)
            } else {
                _pernikahanEvent.emit(
                    SPPernikahanEvent.SubmitError(
                        result.exceptionOrNull()?.message ?: "Error"
                    )
                )
            }
        }
    }

    // Validation helpers - delegate ke Validator
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun clearError() = stateManager.updateErrorMessage(null)
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Private helpers
    private fun validateStepWithEvent(validationFn: () -> Boolean): Boolean {
        val isValid = validationFn()
        if (!isValid) {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _pernikahanEvent.emit(SPPernikahanEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events - sama seperti kode asli
    sealed class SPPernikahanEvent {
        data class StepChanged(val step: Int) : SPPernikahanEvent()
        data object SubmitSuccess : SPPernikahanEvent()
        data class SubmitError(val message: String) : SPPernikahanEvent()
        data object ValidationError : SPPernikahanEvent()
        data class UserDataLoadError(val message: String) : SPPernikahanEvent()
        data class AgamaLoadError(val message: String) : SPPernikahanEvent()
        data class StatusKawinLoadError(val message: String) : SPPernikahanEvent()
    }
}