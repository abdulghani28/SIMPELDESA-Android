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
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBerpergianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratBerpergianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBerpergianUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKBerpergianViewModel(
    private val createSKBerpergianUseCase: CreateSuratBerpergianUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKBerpergianUiState())
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
    private val _skBerpergianEvent = MutableSharedFlow<SKBerpergianEvent>()
    val skBerpergianEvent = _skBerpergianEvent.asSharedFlow()

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
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Kepergian
    var tempatTujuanValue by mutableStateOf("")
        private set
    var maksudTujuanValue by mutableStateOf("")
        private set
    var tanggalKeberangkatanValue by mutableStateOf("")
        private set
    var lamaValue by mutableStateOf(0)
        private set
    var satuanLamaValue by mutableStateOf("Hari") // Default: Hari, Bulan, Tahun
        private set
    var jumlahPengikutValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Pelengkap
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
                            "jenis_kelamin", "pekerjaan", "alamat",
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skBerpergianEvent.emit(SKBerpergianEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skBerpergianEvent.emit(SKBerpergianEvent.UserDataLoadError(errorMessage!!))
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
        jenisKelaminValue = ""
        pekerjaanValue = ""
        alamatValue = ""
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

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    // Step 2 - Informasi Kepergian Update Functions
    fun updateTempatTujuan(value: String) {
        tempatTujuanValue = value
        clearFieldError("tempat_tujuan")
    }

    fun updateMaksudTujuan(value: String) {
        maksudTujuanValue = value
        clearFieldError("maksud_tujuan")
    }

    fun updateTanggalKeberangkatan(value: String) {
        tanggalKeberangkatanValue = value
        clearFieldError("tanggal_keberangkatan")
    }

    fun updateLama(value: Int) {
        lamaValue = value
        clearFieldError("lama")
    }

    fun updateSatuanLama(value: String) {
        satuanLamaValue = value
        clearFieldError("satuan_lama")
    }

    fun updateJumlahPengikut(value: String) {
        jumlahPengikutValue = value
        clearFieldError("jumlah_pengikut")
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
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
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
                        _skBerpergianEvent.emit(SKBerpergianEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _skBerpergianEvent.emit(SKBerpergianEvent.StepChanged(currentStep))
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
                _skBerpergianEvent.emit(SKBerpergianEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
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
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skBerpergianEvent.emit(SKBerpergianEvent.ValidationError)
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
        if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (tempatTujuanValue.isBlank()) errors["tempat_tujuan"] = "Tempat tujuan tidak boleh kosong"
        if (maksudTujuanValue.isBlank()) errors["maksud_tujuan"] = "Maksud tujuan tidak boleh kosong"
        if (tanggalKeberangkatanValue.isBlank()) errors["tanggal_keberangkatan"] = "Tanggal keberangkatan tidak boleh kosong"
        if (lamaValue == 0) {
            errors["lama"] = "Lama kepergian tidak boleh 0"
        } else {
            try {
                val lamaInt = lamaValue.toInt()
                if (lamaInt <= 0) {
                    errors["lama"] = "Lama kepergian harus lebih dari 0"
                }
            } catch (e: NumberFormatException) {
                errors["lama"] = "Lama kepergian harus berupa angka"
            }
        }
        if (jumlahPengikutValue.isBlank()) {
            errors["jumlah_pengikut"] = "Jumlah pengikut tidak boleh kosong"
        } else {
            try {
                val jumlahInt = jumlahPengikutValue.toInt()
                if (jumlahInt < 0) {
                    errors["jumlah_pengikut"] = "Jumlah pengikut tidak boleh negatif"
                }
            } catch (e: NumberFormatException) {
                errors["jumlah_pengikut"] = "Jumlah pengikut harus berupa angka"
            }
        }

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
                val request = SKBerpergianRequest(
                    // Step 1 - Informasi Pelapor
                    nik = nikValue,
                    nama = namaValue,
                    tempat_lahir = tempatLahirValue,
                    tanggal_lahir = tanggalLahirValue,
                    jenis_kelamin = jenisKelaminValue,
                    pekerjaan = pekerjaanValue,
                    alamat = alamatValue,

                    // Step 2 - Informasi Kepergian
                    tempat_tujuan = tempatTujuanValue,
                    maksud_tujuan = maksudTujuanValue,
                    tanggal_keberangkatan = tanggalKeberangkatanValue,
                    lama = lamaValue,
                    satuan_lama = satuanLamaValue,
                    jumlah_pengikut = jumlahPengikutValue.toIntOrNull() ?: 0,

                    // Step 3 - Informasi Pelengkap
                    keperluan = keperluanValue,

                    // Other fields (set by backend or default)
                    disahkan_oleh = ""
                )

                when (val result = createSKBerpergianUseCase(request)) {
                    is SuratBerpergianResult.Success -> {
                        _skBerpergianEvent.emit(SKBerpergianEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratBerpergianResult.Error -> {
                        errorMessage = result.message
                        _skBerpergianEvent.emit(SKBerpergianEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skBerpergianEvent.emit(SKBerpergianEvent.SubmitError(errorMessage!!))
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
        pekerjaanValue = ""
        alamatValue = ""

        // Step 2 - Informasi Kepergian
        tempatTujuanValue = ""
        maksudTujuanValue = ""
        tanggalKeberangkatanValue = ""
        lamaValue = 0
        satuanLamaValue = "Hari"
        jumlahPengikutValue = ""

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
                jenisKelaminValue.isNotBlank()
                pekerjaanValue.isNotBlank() || alamatValue.isNotBlank() ||
                tempatTujuanValue.isNotBlank() || maksudTujuanValue.isNotBlank() ||
                tanggalKeberangkatanValue.isNotBlank() || lamaValue != 0 ||
                jumlahPengikutValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    // Events
    sealed class SKBerpergianEvent {
        data class StepChanged(val step: Int) : SKBerpergianEvent()
        data object SubmitSuccess : SKBerpergianEvent()
        data class SubmitError(val message: String) : SKBerpergianEvent()
        data object ValidationError : SKBerpergianEvent()
        data class UserDataLoadError(val message: String) : SKBerpergianEvent()
    }
}

// UI State data class
data class SKBerpergianUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)