package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar

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
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratKehilanganResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKehilanganUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPKehilanganViewModel(
    private val createSuratKehilanganUseCase: CreateSuratKehilanganUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SPKehilanganUiState())
    val uiState = _uiState.asStateFlow()

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
    private val _kehilanganEvent = MutableSharedFlow<SPKehilanganEvent>()
    val kehilanganEvent = _kehilanganEvent.asSharedFlow()

    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi BarangHilang
    var jenisBarangValue by mutableStateOf("")
        private set
    var ciriCiriBarangValue by mutableStateOf("")
        private set
    var tempatKehilanganValue by mutableStateOf("")
        private set
    var tanggalKehilanganValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
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

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "pekerjaan", "alamat", "jenis_kelamin"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _kehilanganEvent.emit(SPKehilanganEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _kehilanganEvent.emit(SPKehilanganEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        // Step 1 - Calon 
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        pekerjaanValue = ""
        alamatValue = ""
        jenisKelaminValue = ""

        // Step 2 - Barang
        jenisBarangValue = ""
        ciriCiriBarangValue = ""
        tempatKehilanganValue = ""
        tanggalKehilanganValue = ""
    }

    // Step 1 - Pelapor
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

    fun updateJenisKelamin(value: String) {
        tempatLahirValue = value
        clearFieldError("jenis_kelamin")
    }

    fun updateTanggalLahir(value: String) {
        tanggalLahirValue = value
        clearFieldError("tanggal_lahir")
    }

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    // Step 2 - Orang Tua
    fun updateTanggalKehilangan(value: String) {
        tanggalKehilanganValue = value
        clearFieldError("tanggal_kehilangan")
    }

    fun updatejenisBarang(value: String) {
        jenisBarangValue = value
        clearFieldError("jenis_barang")
    }

    fun updateCiriCiriBarang(value: String) {
        ciriCiriBarangValue = value
        clearFieldError("ciri_barang")
    }

    fun updateTempatKehilangan(value: String) {
        tempatKehilanganValue = value
        clearFieldError("tempat_kehilangan")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
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
                        _kehilanganEvent.emit(SPKehilanganEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    showConfirmationDialog = true
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            currentStep -= 1
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
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
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _kehilanganEvent.emit(SPKehilanganEvent.ValidationError)
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
        if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (tanggalKehilanganValue.isBlank()) errors["tanggal_kehilangan"] = "Tanggal kehilangan tidak boleh kosong"
        if (tempatKehilanganValue.isBlank()) errors["tempat_kehilangan"] = "Tempat kehilangan tidak boleh kosong"
        if (jenisBarangValue.isBlank()) errors["jenis_barang"] = "Jenis barang tidak boleh kosong"
        if (ciriCiriBarangValue.isBlank()) errors["ciri_barang"] = "Ciri-ciri barang tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2()
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
                val request = SPKehilanganRequest(
                    disahkan_oleh = "",
                    alamat = alamatValue,
                    ciri = ciriCiriBarangValue,
                    jenis_barang = jenisBarangValue,
                    jenis_kelamin = jenisKelaminValue,
                    keperluan = "",
                    nama = namaValue,
                    nik = nikValue,
                    pekerjaan = pekerjaanValue,
                    tanggal_lahir = tanggalLahirValue,
                    tempat_kehilangan = tanggalKehilanganValue,
                    tempat_lahir = tempatLahirValue,
                    waktu_kehilangan = tanggalKehilanganValue,
                )

                when (val result = createSuratKehilanganUseCase(request)) {
                    is SuratKehilanganResult.Success -> {
                        _kehilanganEvent.emit(SPKehilanganEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratKehilanganResult.Error -> {
                        errorMessage = result.message
                        _kehilanganEvent.emit(SPKehilanganEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _kehilanganEvent.emit(SPKehilanganEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Calon 
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        pekerjaanValue = ""
        alamatValue = ""
        jenisKelaminValue = ""

        // Step 2 - Orang Tua
        jenisBarangValue = ""
        ciriCiriBarangValue = ""
        tempatKehilanganValue = ""
        tanggalKehilanganValue = ""

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
        return nikValue.isNotBlank() || namaValue.isNotBlank()
    }

    // Events
    sealed class SPKehilanganEvent {
        data class StepChanged(val step: Int) : SPKehilanganEvent()
        data object SubmitSuccess : SPKehilanganEvent()
        data class SubmitError(val message: String) : SPKehilanganEvent()
        data object ValidationError : SPKehilanganEvent()
        data class UserDataLoadError(val message: String) : SPKehilanganEvent()
    }
}

// UI State data class
data class SPKehilanganUiState(
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val statusKawinList: List<StatusKawinResponse.Data> = emptyList(),
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)