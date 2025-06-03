package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPenghasilanRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratPenghasilanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPenghasilanUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKPenghasilanViewModel(
    createSKPenghasilanUseCase: CreateSuratPenghasilanUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // Composed components
    private val stateManager = SKPenghasilanStateManager()
    private val validator = SKPenghasilanValidator()
    private val dataLoader = SKPenghasilanDataLoader(getUserInfoUseCase)
    private val formSubmitter = SKPenghasilanFormSubmitter(createSKPenghasilanUseCase)

    // UI State
    private val _uiState = MutableStateFlow(SKPenghasilanUiState())
    val uiState = _uiState.asStateFlow()

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _skPenghasilanEvent = MutableSharedFlow<SKPenghasilanEvent>()
    val skPenghasilanEvent = _skPenghasilanEvent.asSharedFlow()

    // Delegated properties untuk seamless interface
    val currentStep: Int get() = stateManager.currentStep
    val useMyDataChecked: Boolean get() = stateManager.useMyDataChecked
    val isLoadingUserData: Boolean get() = dataLoader.isLoadingUserData
    val isLoading: Boolean get() = formSubmitter.isLoading
    val showConfirmationDialog: Boolean get() = stateManager.showConfirmationDialog
    val showPreviewDialog: Boolean get() = stateManager.showPreviewDialog
    val validationErrors = validator.validationErrors

    // Step 1 Properties
    val nikValue: String get() = stateManager.nikValue
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val keperluanValue: String get() = stateManager.keperluanValue

    // Step 2 Properties
    val nikOrtuValue: String get() = stateManager.nikOrtuValue
    val namaOrtuValue: String get() = stateManager.namaOrtuValue
    val tempatLahirOrtuValue: String get() = stateManager.tempatLahirOrtuValue
    val tanggalLahirOrtuValue: String get() = stateManager.tanggalLahirOrtuValue
    val jenisKelaminOrtuValue: String get() = stateManager.jenisKelaminOrtuValue
    val alamatOrtuValue: String get() = stateManager.alamatOrtuValue
    val pekerjaanOrtuValue: String get() = stateManager.pekerjaanOrtuValue
    val penghasilanOrtuValue: Int get() = stateManager.penghasilanOrtuValue
    val tanggunganOrtuValue: Int get() = stateManager.tanggunganOrtuValue

    // Step 3 Properties (contoh update anak)
    val nikAnakValue: String get() = stateManager.nikAnakValue
    val namaAnakValue: String get() = stateManager.namaAnakValue
    val tempatLahirAnakValue: String get() = stateManager.tempatLahirAnakValue
    val tanggalLahirAnakValue: String get() = stateManager.tanggalLahirAnakValue
    val jenisKelaminAnakValue: String get() = stateManager.jenisKelaminAnakValue
    val namaSekolahAnakValue: String get() = stateManager.namaSekolahAnakValue
    val kelasAnakValue: String get() = stateManager.kelasAnakValue

    // Public interface methods - tidak berubah dari kode asli
    fun updateUseMyData(checked: Boolean) {
        stateManager.setUseMyData(checked)
        if (checked) {
            loadUserData()
        } else {
            stateManager.clearUserData()
        }
    }

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

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Step 2 - update orang tua
    fun updateNikOrtu(value: String) {
        stateManager.updateNikOrtu(value)
        validator.clearFieldError("nik_ortu")
    }

    fun updateNamaOrtu(value: String) {
        stateManager.updateNamaOrtu(value)
        validator.clearFieldError("nama_ortu")
    }

    fun updateTempatLahirOrtu(value: String) {
        stateManager.updateTempatLahirOrtu(value)
        validator.clearFieldError("tempat_lahir_ortu")
    }

    fun updateTanggalLahirOrtu(value: String) {
        stateManager.updateTanggalLahirOrtu(value)
        validator.clearFieldError("tanggal_lahir_ortu")
    }

    fun updateJenisKelaminOrtu(value: String) {
        stateManager.updateJenisKelaminOrtu(value)
        validator.clearFieldError("jenis_kelamin_ortu")
    }

    fun updateAlamatOrtu(value: String) {
        stateManager.updateAlamatOrtu(value)
        validator.clearFieldError("alamat_ortu")
    }

    fun updatePekerjaanOrtu(value: String) {
        stateManager.updatePekerjaanOrtu(value)
        validator.clearFieldError("pekerjaan_ortu")
    }

    fun updatePenghasilanOrtu(value: Int) {
        stateManager.updatePenghasilanOrtu(value)
        validator.clearFieldError("penghasilan_ortu")
    }

    fun updateTanggunganOrtu(value: Int) {
        stateManager.updateTanggunganOrtu(value)
        validator.clearFieldError("tanggungan_ortu")
    }

    // Step 3 - update anak
    fun updateNikAnak(value: String) {
        stateManager.updateNikAnak(value)
        validator.clearFieldError("nik_anak")
    }

    fun updateNamaAnak(value: String) {
        stateManager.updateNamaAnak(value)
        validator.clearFieldError("nama_anak")
    }

    fun updateTempatLahirAnak(value: String) {
        stateManager.updateTempatLahirAnak(value)
        validator.clearFieldError("tempat_lahir_anak")
    }

    fun updateTanggalLahirAnak(value: String) {
        stateManager.updateTanggalLahirAnak(value)
        validator.clearFieldError("tanggal_lahir_anak")
    }

    fun updateJenisKelaminAnak(value: String) {
        stateManager.updateJenisKelaminAnak(value)
        validator.clearFieldError("jenis_kelamin_anak")
    }

    fun updateNamaSekolahAnak(value: String) {
        stateManager.updateNamaSekolahAnak(value)
        validator.clearFieldError("nama_sekolah_anak")
    }

    fun updateKelasAnak(value: String) {
        stateManager.updateKelasAnak(value)
        validator.clearFieldError("kelas_anak")
    }


    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    stateManager.nextStep()
                    emitStepChangedEvent()
                }
            }
            3 -> {
                if (validateStep3WithEvent()) {
                    stateManager.showConfirmationDialog()
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

    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validator.validateStep1(stateManager)
        val step2Valid = validator.validateStep2(stateManager)
        val step3Valid = validator.validateStep3(stateManager)

        if (!step1Valid || !step2Valid || !step3Valid) {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }

        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()
    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.showConfirmationDialog()
        } else {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }
    }
    fun dismissConfirmationDialog() = stateManager.dismissConfirmationDialog()

    fun confirmSubmit() {
        stateManager.dismissConfirmationDialog()
        submitForm()
    }

    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun clearError() { errorMessage = null }
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Private helper methods
    private fun loadUserData() {
        viewModelScope.launch {
            dataLoader.loadUserData()
                .onSuccess { userData ->
                    stateManager.loadUserData(userData)
                    validator.clearMultipleFieldErrors(listOf("nik", "nama", "alamat"))
                }
                .onFailure { exception ->
                    errorMessage = exception.message ?: "Gagal memuat data pengguna"
                    stateManager.setUseMyData(false)
                    _skPenghasilanEvent.emit(SKPenghasilanEvent.UserDataLoadError(errorMessage!!))
                }
        }
    }

    private fun validateStep1WithEvent(): Boolean {
        val isValid = validator.validateStep1(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validator.validateStep2(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validator.validateStep3(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun submitForm() {
        viewModelScope.launch {
            errorMessage = null

            formSubmitter.submitForm(stateManager)
                .onSuccess {
                    _skPenghasilanEvent.emit(SKPenghasilanEvent.SubmitSuccess)
                    stateManager.resetForm()
                    validator.clearAllErrors()
                }
                .onFailure { exception ->
                    errorMessage = exception.message ?: "Terjadi kesalahan"
                    _skPenghasilanEvent.emit(SKPenghasilanEvent.SubmitError(errorMessage!!))
                }
        }
    }

    private fun emitStepChangedEvent() {
        viewModelScope.launch {
            _skPenghasilanEvent.emit(SKPenghasilanEvent.StepChanged(stateManager.currentStep))
        }
    }

    // Events - copy langsung dari kode asli
    sealed class SKPenghasilanEvent {
        data class StepChanged(val step: Int) : SKPenghasilanEvent()
        data object SubmitSuccess : SKPenghasilanEvent()
        data class SubmitError(val message: String) : SKPenghasilanEvent()
        data object ValidationError : SKPenghasilanEvent()
        data class UserDataLoadError(val message: String) : SKPenghasilanEvent()
    }
}
