package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKeramaianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeramaianUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SRKeramaianViewModel(
    private val createSuratKeramaianUseCase: CreateSuratKeramaianUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SRKeramaianUiState())
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
    private val _keramaianEvent = MutableSharedFlow<SRKeramaianEvent>()
    val keramaianEvent = _keramaianEvent.asSharedFlow()

    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var selectedGender by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Kegiatan
    var namaAcaraValue by mutableStateOf("")
        private set
    var tempatAcaraValue by mutableStateOf("")
        private set
    var hariValue by mutableStateOf("")
        private set
    var tanggalValue by mutableStateOf("")
        private set
    var jamMulaiValue by mutableStateOf("")
        private set
    var jamSelesaiValue by mutableStateOf("")
        private set
    var penanggungJawabValue by mutableStateOf("")
        private set
    var kontakValue by mutableStateOf("")
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
                        selectedGender = userData.jenis_kelamin
                        pekerjaanValue = userData.pekerjaan
                        alamatValue = userData.alamat

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _keramaianEvent.emit(SRKeramaianEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _keramaianEvent.emit(SRKeramaianEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
    }

    // Step 1 field updates
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
        tanggalLahirValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal_lahir")
    }

    fun updateGender(value: String) {
        selectedGender = value
        clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    // Step 2 field updates
    fun updateNamaAcara(value: String) {
        namaAcaraValue = value
        clearFieldError("nama_acara")
    }

    fun updateTempatAcara(value: String) {
        tempatAcaraValue = value
        clearFieldError("tempat_acara")
    }

    fun updateHari(value: String) {
        hariValue = value
        clearFieldError("hari")
    }

    fun updateTanggal(value: String) {
        tanggalValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal")
    }

    fun updateJamMulai(value: String) {
        jamMulaiValue = value
        clearFieldError("dimulai")
    }

    fun updateJamSelesai(value: String) {
        jamSelesaiValue = value
        clearFieldError("selesai")
    }

    fun updatePenanggungJawab(value: String) {
        penanggungJawabValue = value
        clearFieldError("penanggung_jawab")
    }

    fun updateKontak(value: String) {
        kontakValue = value
        clearFieldError("kontak")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
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
                        _keramaianEvent.emit(SRKeramaianEvent.StepChanged(currentStep))
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
                _keramaianEvent.emit(SRKeramaianEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
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
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _keramaianEvent.emit(SRKeramaianEvent.ValidationError)
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

        if (namaValue.isBlank()) {
            errors["nama"] = "Nama lengkap tidak boleh kosong"
        }

        if (tempatLahirValue.isBlank()) {
            errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        }

        if (tanggalLahirValue.isBlank()) {
            errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        }

        if (selectedGender.isBlank()) {
            errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        }

        if (pekerjaanValue.isBlank()) {
            errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        if (alamatValue.isBlank()) {
            errors["alamat"] = "Alamat tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (namaAcaraValue.isBlank()) {
            errors["nama_acara"] = "Nama acara tidak boleh kosong"
        }

        if (tempatAcaraValue.isBlank()) {
            errors["tempat_acara"] = "Tempat acara tidak boleh kosong"
        }

        if (hariValue.isBlank()) {
            errors["hari"] = "Hari harus dipilih"
        }

        if (tanggalValue.isBlank()) {
            errors["tanggal"] = "Tanggal acara tidak boleh kosong"
        }

        if (jamMulaiValue.isBlank()) {
            errors["dimulai"] = "Jam mulai tidak boleh kosong"
        }

        if (jamSelesaiValue.isBlank()) {
            errors["selesai"] = "Jam selesai tidak boleh kosong"
        }

        if (penanggungJawabValue.isBlank()) {
            errors["penanggung_jawab"] = "Penanggung jawab tidak boleh kosong"
        }

        if (kontakValue.isBlank()) {
            errors["kontak"] = "Kontak tidak boleh kosong"
        }

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
                val request = SRKeramaianRequest(
                    alamat = alamatValue,
                    dimulai = jamMulaiValue,
                    disahkan_oleh = "", // This might be set on the backend
                    hari = hariValue,
                    jenis_kelamin = selectedGender,
                    kontak = kontakValue,
                    nama = namaValue,
                    nama_acara = namaAcaraValue,
                    nik = nikValue,
                    pekerjaan = pekerjaanValue,
                    penanggung_jawab = penanggungJawabValue,
                    selesai = jamSelesaiValue,
                    tanggal = dateFormatterToApiFormat(tanggalValue),
                    tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
                    tempat_acara = tempatAcaraValue,
                    tempat_lahir = tempatLahirValue
                )

                when (val result = createSuratKeramaianUseCase(request)) {
                    is SuratKeramaianResult.Success -> {
                        _keramaianEvent.emit(SRKeramaianEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratKeramaianResult.Error -> {
                        errorMessage = result.message
                        _keramaianEvent.emit(SRKeramaianEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _keramaianEvent.emit(SRKeramaianEvent.SubmitError(errorMessage!!))
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

        // Reset Step 1 fields
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""

        // Reset Step 2 fields
        namaAcaraValue = ""
        tempatAcaraValue = ""
        hariValue = ""
        tanggalValue = ""
        jamMulaiValue = ""
        jamSelesaiValue = ""
        penanggungJawabValue = ""
        kontakValue = ""

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
                namaAcaraValue.isNotBlank() || tempatAcaraValue.isNotBlank()
    }

    // Events
    sealed class SRKeramaianEvent {
        data class StepChanged(val step: Int) : SRKeramaianEvent()
        data object SubmitSuccess : SRKeramaianEvent()
        data class SubmitError(val message: String) : SRKeramaianEvent()
        data object ValidationError : SRKeramaianEvent()
        data class UserDataLoadError(val message: String) : SRKeramaianEvent()
    }
}

// UI State data class
data class SRKeramaianUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)