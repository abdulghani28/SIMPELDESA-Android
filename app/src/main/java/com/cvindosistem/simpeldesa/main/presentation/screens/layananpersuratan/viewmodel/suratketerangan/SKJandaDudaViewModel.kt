package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJandaDudaRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJandaDudaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliViewModel.SKDomisiliEvent
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliViewModel.SKDomisiliEvent.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKJandaDudaViewModel(
    private val createSKJandaDudaUseCase: CreateSuratJandaDudaUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKJandaDudaUiState())
    val uiState = _uiState.asStateFlow()

    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingAgama by mutableStateOf(false)
    var agamaErrorMessage by mutableStateOf<String?>(null)

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Current step
    var currentStep by mutableIntStateOf(1)
        private set

    // Use My Data state
    var useMyDataChecked by mutableStateOf(false)
        private set

    var isLoadingUserData by mutableStateOf(false)
        private set

    // Show confirmation dialog
    var showConfirmationDialog by mutableStateOf(false)
        private set

    // Show preview dialog
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Events
    private val _skJandaDudaEvent = MutableSharedFlow<SKJandaDudaEvent>()
    val skJandaDudaEvent = _skJandaDudaEvent.asSharedFlow()

    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Janda/Duda
    var dasarPengajuanValue by mutableStateOf("")
        private set
    var nomorPengajuanValue by mutableStateOf("")
        private set
    var penyebabValue by mutableStateOf("")
        private set

    // Step 3 - Keperluan
    var keperluanValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
            loadAgama()
        } else {
            clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            isLoadingUserData = true
            try {
                when (val result = getUserInfoUseCase()) {
                    is UserInfoResult.Success -> {
                        val userData = result.data.data
                        nikValue = userData.nik
                        namaValue = userData.nama_warga
                        tempatLahirValue = userData.tempat_lahir
                        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
                        jenisKelaminValue = userData.jenis_kelamin
                        pekerjaanValue = userData.pekerjaan
                        alamatValue = userData.alamat
                        agamaIdValue = userData.agama_id

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat",
                            "agama_id"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skJandaDudaEvent.emit(SKJandaDudaEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skJandaDudaEvent.emit(SKJandaDudaEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            isLoadingAgama = true
            agamaErrorMessage = null
            try {
                when (val result = getAgamaUseCase()) {
                    is AgamaResult.Success -> {
                        agamaList = result.data.data
                    }
                    is AgamaResult.Error -> {
                        agamaErrorMessage = result.message
                        _skJandaDudaEvent.emit(SKJandaDudaEvent.AgamaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                agamaErrorMessage = e.message ?: "Gagal memuat data agama"
                _skJandaDudaEvent.emit(SKJandaDudaEvent.AgamaLoadError(agamaErrorMessage!!))
            } finally {
                isLoadingAgama = false
            }
        }
    }

    private fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaIdValue = ""
    }

    // Step 1 - Informasi Pelapor Update Functions
    fun updateNik(value: String) {
        nikValue = value
        clearFieldError("nik")
    }

    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        tempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        tanggalLahirValue = value
        clearFieldError("tanggal_lahir")
    }

    fun updateJenisKelamin(value: String) {
        jenisKelaminValue = value
        clearFieldError("jenis_kelamin")
    }

    fun updateAgamaId(value: String) {
        agamaIdValue = value
        clearFieldError("agama_id")
    }

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    // Step 2 - Informasi Janda/Duda Update Functions
    fun updateDasarPengajuan(value: String) {
        dasarPengajuanValue = value
        clearFieldError("dasar_pengajuan")
    }

    fun updateNomorPengajuan(value: String) {
        nomorPengajuanValue = value
        clearFieldError("nomor_pengajuan")
    }

    fun updatePenyebab(value: String) {
        penyebabValue = value
        clearFieldError("penyebab")
    }

    // Step 3 - Keperluan Update Functions
    fun updateKeperluan(value: String) {
        keperluanValue = value
        clearFieldError("keperluan")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()
        val step3Valid = validateStep3()

        if (!step1Valid || !step2Valid || !step3Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }

        showPreviewDialog = true
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    // Step navigation
    fun nextStep() {
        when (currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    currentStep = 2
                    viewModelScope.launch {
                        _skJandaDudaEvent.emit(SKJandaDudaEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _skJandaDudaEvent.emit(SKJandaDudaEvent.StepChanged(currentStep))
                    }
                }
            }
            3 -> {
                if (validateStep3WithEvent()) {
                    showConfirmationDialog = true
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            currentStep -= 1
            viewModelScope.launch {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        showConfirmationDialog = false
    }

    fun confirmSubmit() {
        showConfirmationDialog = false
        submitForm()
    }

    private fun validateStep1WithEvent(): Boolean {
        val isValid = validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skJandaDudaEvent.emit(SKJandaDudaEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (namaValue.isBlank()) errors["nama"] = "Nama tidak boleh kosong"
        if (tempatLahirValue.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahirValue.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (agamaIdValue.isBlank()) errors["agama_id"] = "Agama tidak boleh kosong"
        if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (dasarPengajuanValue.isBlank()) errors["dasar_pengajuan"] = "Dasar pengajuan tidak boleh kosong"
        if (nomorPengajuanValue.isBlank()) errors["nomor_pengajuan"] = "Nomor pengajuan tidak boleh kosong"
        if (penyebabValue.isBlank()) errors["penyebab"] = "Penyebab tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3()
    }

    private fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
        _validationErrors.value = currentErrors
    }

    private fun clearMultipleFieldErrors(fieldNames: List<String>) {
        val currentErrors = _validationErrors.value.toMutableMap()
        fieldNames.forEach { fieldName ->
            currentErrors.remove(fieldName)
        }
        _validationErrors.value = currentErrors
    }

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val request = SKJandaDudaRequest(
                    agama_id = agamaIdValue,
                    alamat = alamatValue,
                    dasar_pengajuan = dasarPengajuanValue,
                    disahkan_oleh = "",
                    jenis_kelamin = jenisKelaminValue,
                    keperluan = keperluanValue,
                    nama = namaValue,
                    nik = nikValue,
                    nomor_pengajuan = nomorPengajuanValue,
                    pekerjaan = pekerjaanValue,
                    penyebab = penyebabValue,
                    tanggal_lahir = tanggalLahirValue,
                    tempat_lahir = tempatLahirValue
                )

                when (val result = createSKJandaDudaUseCase(request)) {
                    is SuratJandaDudaResult.Success -> {
                        _skJandaDudaEvent.emit(SKJandaDudaEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratJandaDudaResult.Error -> {
                        errorMessage = result.message
                        _skJandaDudaEvent.emit(SKJandaDudaEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skJandaDudaEvent.emit(SKJandaDudaEvent.SubmitError(errorMessage!!))
            } finally {
                isLoading = false
            }
        }
    }

    // Get validation error for specific field
    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    // Check if field has error
    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }

    // Reset form
    private fun resetForm() {
        currentStep = 1
        useMyDataChecked = false

        // Step 1 - Informasi Pelapor
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        agamaIdValue = ""
        pekerjaanValue = ""
        alamatValue = ""

        // Step 2 - Informasi Janda/Duda
        dasarPengajuanValue = ""
        nomorPengajuanValue = ""
        penyebabValue = ""

        // Step 3 - Keperluan
        keperluanValue = ""

        _validationErrors.value = emptyMap()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    // Clear error message
    fun clearError() {
        errorMessage = null
    }

    // Check if form has data
    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || agamaIdValue.isNotBlank() ||
                pekerjaanValue.isNotBlank() || alamatValue.isNotBlank() ||
                dasarPengajuanValue.isNotBlank() || nomorPengajuanValue.isNotBlank() ||
                penyebabValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    // Events
    sealed class SKJandaDudaEvent {
        data class StepChanged(val step: Int) : SKJandaDudaEvent()
        data object SubmitSuccess : SKJandaDudaEvent()
        data class SubmitError(val message: String) : SKJandaDudaEvent()
        data object ValidationError : SKJandaDudaEvent()
        data class UserDataLoadError(val message: String) : SKJandaDudaEvent()
        data class AgamaLoadError(val message: String) : SKJandaDudaEvent()
    }
}

// UI State data class
data class SKJandaDudaUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)