package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.kehilangan

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
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKehilanganResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKehilanganUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPKehilanganViewModel(
    createSuratKehilanganUseCase: CreateSuratKehilanganUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // Composed Components
    private val stateManager = SPKehilanganStateManager()
    private val validator = SPKehilanganValidator()
    private val dataLoader = SPKehilanganDataLoader(getUserInfoUseCase)
    private val formSubmitter = SPKehilanganFormSubmitter(createSuratKehilanganUseCase)

    // UI State for the form
    private val _uiState = MutableStateFlow(SPKehilanganUiState())
    val uiState = _uiState.asStateFlow()

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _kehilanganEvent = MutableSharedFlow<SPKehilanganEvent>()
    val kehilanganEvent = _kehilanganEvent.asSharedFlow()

    // Expose necessary properties from stateManager
    val currentStep get() = stateManager.currentStep
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val isLoadingUserData get() = dataLoader.isLoadingUserData
    val isLoading get() = formSubmitter.isLoading
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog
    val validationErrors get() = validator.validationErrors

    // Step 1 Properties - Copy langsung dari kode utama
    val nikValue get() = stateManager.nikValue
    val namaValue get() = stateManager.namaValue
    val tempatLahirValue get() = stateManager.tempatLahirValue
    val jenisKelaminValue get() = stateManager.jenisKelaminValue
    val tanggalLahirValue get() = stateManager.tanggalLahirValue
    val pekerjaanValue get() = stateManager.pekerjaanValue
    val alamatValue get() = stateManager.alamatValue

    val jenisBarangValue get() = stateManager.jenisBarangValue
    val ciriCiriBarangValue get() = stateManager.ciriCiriBarangValue
    val tempatKehilanganValue get() = stateManager.tempatKehilanganValue
    val tanggalKehilanganValue get() = stateManager.tanggalKehilanganValue

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyData(checked)
        if (checked) {
            loadUserData()
        } else {
            stateManager.clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val result = dataLoader.loadUserData()
                result.onSuccess { userData ->
                    stateManager.populateUserData(userData)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "pekerjaan", "alamat", "jenis_kelamin"
                    ))
                }.onFailure { exception ->
                    errorMessage = exception.message ?: "Gagal memuat data pengguna"
                    stateManager.updateUseMyData(false)
                    _kehilanganEvent.emit(SPKehilanganEvent.UserDataLoadError(errorMessage!!))
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                stateManager.updateUseMyData(false)
                _kehilanganEvent.emit(SPKehilanganEvent.UserDataLoadError(errorMessage!!))
            }
        }
    }

    // Update functions - Delegate to stateManager and clear validation
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

    fun updateJenisKelamin(value: String) {
        stateManager.updateJenisKelamin(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateJenisBarang(value: String) {
        stateManager.updateJenisBarang(value)
        validator.clearFieldError("jenis_barang")
    }

    fun updateCiriCiriBarang(value: String) {
        stateManager.updateCiriCiriBarang(value)
        validator.clearFieldError("ciri_ciri_barang")
    }

    fun updateTempatKehilangan(value: String) {
        stateManager.updateTempatKehilangan(value)
        validator.clearFieldError("tempat_kehilangan")
    }

    fun updateTanggalKehilangan(value: String) {
        stateManager.updateTanggalKehilangan(value)
        validator.clearFieldError("tanggal_kehilangan")
    }

    // Step navigation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    stateManager.nextStep()
                    viewModelScope.launch {
                        _kehilanganEvent.emit(SPKehilanganEvent.StepChanged(stateManager.currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    stateManager.showConfirmationDialog()
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.StepChanged(stateManager.currentStep))
            }
        }
    }

    // Validation with events
    private fun validateStep1WithEvent(): Boolean {
        val isValid = validator.validateStep1(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validator.validateStep2(stateManager)
        if (!isValid) {
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
            }
        }
        return isValid
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            errorMessage = null

            try {
                val request = stateManager.createRequest()
                val result = formSubmitter.submitForm(request)

                result.onSuccess {
                    _kehilanganEvent.emit(SPKehilanganEvent.SubmitSuccess)
                    resetForm()
                }.onFailure { exception ->
                    errorMessage = exception.message
                    _kehilanganEvent.emit(SPKehilanganEvent.SubmitError(exception.message ?: "Terjadi kesalahan"))
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _kehilanganEvent.emit(SPKehilanganEvent.SubmitError(errorMessage!!))
            }
        }
    }

    // Delegate functions
    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        val step2Valid = validator.validateStep2(stateManager)

        if (!step1Valid || !step2Valid) {
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
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
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
            }
        }
    }
    fun dismissConfirmationDialog() = stateManager.dismissConfirmationDialog()
    fun confirmSubmit() {
        stateManager.dismissConfirmationDialog()
        submitForm()
    }
    fun validateAllSteps() = validator.validateAllSteps(stateManager)
    fun getFieldError(fieldName: String) = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String) = validator.hasFieldError(fieldName)
    fun clearError() { errorMessage = null }
    fun hasFormData() = stateManager.hasFormData()
    private fun resetForm() {
        stateManager.resetForm()
        validator.clearAllErrors()
        errorMessage = null
    }

    // Events - Copy langsung dari kode utama
    sealed class SPKehilanganEvent {
        data class StepChanged(val step: Int) : SPKehilanganEvent()
        data object SubmitSuccess : SPKehilanganEvent()
        data class SubmitError(val message: String) : SPKehilanganEvent()
        data object ValidationError : SPKehilanganEvent()
        data class UserDataLoadError(val message: String) : SPKehilanganEvent()
    }
}