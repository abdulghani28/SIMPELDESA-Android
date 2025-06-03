package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKelahiranRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKelahiranUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKelahiranViewModel(
    private val createSKKelahiranUseCase: CreateSuratKelahiranUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKKelahiranUiState())
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
    private val _skKelahiranEvent = MutableSharedFlow<SKKelahiranEvent>()
    val skKelahiranEvent = _skKelahiranEvent.asSharedFlow()

    // Step 1 - Informasi Anak
    var namaValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var jamLahirValue by mutableStateOf("")
        private set
    var anakKeValue by mutableIntStateOf(1)
        private set

    // Step 2 - Informasi Ayah
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set
    var tempatLahirAyahValue by mutableStateOf("")
        private set
    var tanggalLahirAyahValue by mutableStateOf("")
        private set
    var alamatAyahValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Ibu
    var namaIbuValue by mutableStateOf("")
        private set
    var nikIbuValue by mutableStateOf("")
        private set
    var tempatLahirIbuValue by mutableStateOf("")
        private set
    var tanggalLahirIbuValue by mutableStateOf("")
        private set
    var alamatIbuValue by mutableStateOf("")
        private set

    // Step 4 - Keperluan
    var keperluanValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    private fun clearUserData() {
        // Clear fields that were populated from user data
        namaAyahValue = ""
        alamatAyahValue = ""
    }

    // Step 1 - Informasi Anak Update Functions
    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateJenisKelamin(value: String) {
        jenisKelaminValue = value
        clearFieldError("jenis_kelamin")
    }

    fun updateTempatLahir(value: String) {
        tempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        tanggalLahirValue = value
        clearFieldError("tanggal_lahir")
    }

    fun updateJamLahir(value: String) {
        jamLahirValue = value
        clearFieldError("jam_lahir")
    }

    fun updateAnakKe(value: Int) {
        anakKeValue = value
        clearFieldError("anak_ke")
    }

    // Step 2 - Informasi Ayah Update Functions
    fun updateNamaAyah(value: String) {
        namaAyahValue = value
        clearFieldError("nama_ayah")
    }

    fun updateNikAyah(value: String) {
        nikAyahValue = value
        clearFieldError("nik_ayah")
    }

    fun updateTempatLahirAyah(value: String) {
        tempatLahirAyahValue = value
        clearFieldError("tempat_lahir_ayah")
    }

    fun updateTanggalLahirAyah(value: String) {
        tanggalLahirAyahValue = value
        clearFieldError("tanggal_lahir_ayah")
    }

    fun updateAlamatAyah(value: String) {
        alamatAyahValue = value
        clearFieldError("alamat_ayah")
    }

    // Step 3 - Informasi Ibu Update Functions
    fun updateNamaIbu(value: String) {
        namaIbuValue = value
        clearFieldError("nama_ibu")
    }

    fun updateNikIbu(value: String) {
        nikIbuValue = value
        clearFieldError("nik_ibu")
    }

    fun updateTempatLahirIbu(value: String) {
        tempatLahirIbuValue = value
        clearFieldError("tempat_lahir_ibu")
    }

    fun updateTanggalLahirIbu(value: String) {
        tanggalLahirIbuValue = value
        clearFieldError("tanggal_lahir_ibu")
    }

    fun updateAlamatIbu(value: String) {
        alamatIbuValue = value
        clearFieldError("alamat_ibu")
    }

    // Step 4 - Keperluan Update Functions
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
        val step4Valid = validateStep4()

        if (!step1Valid || !step2Valid || !step3Valid || !step4Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
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
                        _skKelahiranEvent.emit(SKKelahiranEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _skKelahiranEvent.emit(SKKelahiranEvent.StepChanged(currentStep))
                    }
                }
            }
            3 -> {
                if (validateStep3WithEvent()) {
                    currentStep = 4
                    viewModelScope.launch {
                        _skKelahiranEvent.emit(SKKelahiranEvent.StepChanged(currentStep))
                    }
                }
            }
            4 -> {
                if (validateStep4WithEvent()) {
                    showConfirmationDialog = true
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            currentStep -= 1
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
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
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep4WithEvent(): Boolean {
        val isValid = validateStep4()
        if (!isValid) {
            viewModelScope.launch {
                _skKelahiranEvent.emit(SKKelahiranEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (namaValue.isBlank()) errors["nama"] = "Nama anak tidak boleh kosong"
        if (jenisKelaminValue.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin tidak boleh kosong"
        if (tempatLahirValue.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahirValue.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (jamLahirValue.isBlank()) errors["jam_lahir"] = "Jam lahir tidak boleh kosong"
        if (anakKeValue <= 0) errors["anak_ke"] = "Anak ke- harus lebih dari 0"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (namaAyahValue.isBlank()) errors["nama_ayah"] = "Nama ayah tidak boleh kosong"

        if (nikAyahValue.isBlank()) {
            errors["nik_ayah"] = "NIK ayah tidak boleh kosong"
        } else if (nikAyahValue.length != 16) {
            errors["nik_ayah"] = "NIK ayah harus 16 digit"
        }

        if (tempatLahirAyahValue.isBlank()) errors["tempat_lahir_ayah"] = "Tempat lahir ayah tidak boleh kosong"
        if (tanggalLahirAyahValue.isBlank()) errors["tanggal_lahir_ayah"] = "Tanggal lahir ayah tidak boleh kosong"
        if (alamatAyahValue.isBlank()) errors["alamat_ayah"] = "Alamat ayah tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (namaIbuValue.isBlank()) errors["nama_ibu"] = "Nama ibu tidak boleh kosong"

        if (nikIbuValue.isBlank()) {
            errors["nik_ibu"] = "NIK ibu tidak boleh kosong"
        } else if (nikIbuValue.length != 16) {
            errors["nik_ibu"] = "NIK ibu harus 16 digit"
        }

        if (tempatLahirIbuValue.isBlank()) errors["tempat_lahir_ibu"] = "Tempat lahir ibu tidak boleh kosong"
        if (tanggalLahirIbuValue.isBlank()) errors["tanggal_lahir_ibu"] = "Tanggal lahir ibu tidak boleh kosong"
        if (alamatIbuValue.isBlank()) errors["alamat_ibu"] = "Alamat ibu tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep4(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3() && validateStep4()
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
                val request = SKKelahiranRequest(
                    alamat_ayah = alamatAyahValue,
                    alamat_ibu = alamatIbuValue,
                    anak_ke = anakKeValue,
                    disahkan_oleh = "",
                    jam_lahir = jamLahirValue,
                    jenis_kelamin = jenisKelaminValue,
                    keperluan = keperluanValue,
                    nama = namaValue,
                    nama_ayah = namaAyahValue,
                    nama_ibu = namaIbuValue,
                    nik_ayah = nikAyahValue,
                    nik_ibu = nikIbuValue,
                    tanggal_lahir = tanggalLahirValue,
                    tanggal_lahir_ayah = tanggalLahirAyahValue,
                    tanggal_lahir_ibu = tanggalLahirIbuValue,
                    tempat_lahir = tempatLahirValue,
                    tempat_lahir_ayah = tempatLahirAyahValue,
                    tempat_lahir_ibu = tempatLahirIbuValue
                )

                when (val result = createSKKelahiranUseCase(request)) {
                    is SuratKelahiranResult.Success -> {
                        _skKelahiranEvent.emit(SKKelahiranEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratKelahiranResult.Error -> {
                        errorMessage = result.message
                        _skKelahiranEvent.emit(SKKelahiranEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skKelahiranEvent.emit(SKKelahiranEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Informasi Anak
        namaValue = ""
        jenisKelaminValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jamLahirValue = ""
        anakKeValue = 1

        // Step 2 - Informasi Ayah
        namaAyahValue = ""
        nikAyahValue = ""
        tempatLahirAyahValue = ""
        tanggalLahirAyahValue = ""
        alamatAyahValue = ""

        // Step 3 - Informasi Ibu
        namaIbuValue = ""
        nikIbuValue = ""
        tempatLahirIbuValue = ""
        tanggalLahirIbuValue = ""
        alamatIbuValue = ""

        // Step 4 - Keperluan
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
        return namaValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jamLahirValue.isNotBlank() || anakKeValue > 1 ||
                namaAyahValue.isNotBlank() || nikAyahValue.isNotBlank() ||
                tempatLahirAyahValue.isNotBlank() || tanggalLahirAyahValue.isNotBlank() ||
                alamatAyahValue.isNotBlank() || namaIbuValue.isNotBlank() ||
                nikIbuValue.isNotBlank() || tempatLahirIbuValue.isNotBlank() ||
                tanggalLahirIbuValue.isNotBlank() || alamatIbuValue.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    // Events
    sealed class SKKelahiranEvent {
        data class StepChanged(val step: Int) : SKKelahiranEvent()
        data object SubmitSuccess : SKKelahiranEvent()
        data class SubmitError(val message: String) : SKKelahiranEvent()
        data object ValidationError : SKKelahiranEvent()
        data class UserDataLoadError(val message: String) : SKKelahiranEvent()
    }
}

// UI State data class
data class SKKelahiranUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 4
)