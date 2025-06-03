package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKematianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKematianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKematianUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKematianViewModel(
    private val createSKKematianUseCase: CreateSuratKematianUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKKematianUiState())
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
    private val _skKematianEvent = MutableSharedFlow<SKKematianEvent>()
    val skKematianEvent = _skKematianEvent.asSharedFlow()

    // Step 1 - Informasi Pelapor
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var hubunganIdValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Mendiang
    var nikMendiangValue by mutableStateOf("")
        private set
    var namaMendiangValue by mutableStateOf("")
        private set
    var tempatLahirMendiangValue by mutableStateOf("")
        private set
    var tanggalLahirMendiangValue by mutableStateOf("")
        private set
    var jenisKelaminMendiangValue by mutableStateOf("")
        private set
    var alamatMendiangValue by mutableStateOf("")
        private set
    var hariMeninggalValue by mutableStateOf("")
        private set
    var tanggalMeninggalValue by mutableStateOf("")
        private set
    var tempatMeninggalValue by mutableStateOf("")
        private set
    var sebabMeninggalValue by mutableStateOf("")
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
                        namaValue = userData.nama_warga
                        alamatValue = userData.alamat

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf("nama", "alamat"))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skKematianEvent.emit(SKKematianEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skKematianEvent.emit(SKKematianEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        // Step 1 - Informasi Pelapor
        namaValue = ""
        alamatValue = ""
    }

    // Step 1 - Informasi Pelapor Update Functions
    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    fun updateHubunganId(value: String) {
        hubunganIdValue = value
        clearFieldError("hubungan_id")
    }

    // Step 2 - Informasi Mendiang Update Functions
    fun updateNikMendiang(value: String) {
        nikMendiangValue = value
        clearFieldError("nik_mendiang")
    }

    fun updateNamaMendiang(value: String) {
        namaMendiangValue = value
        clearFieldError("nama_mendiang")
    }

    fun updateTempatLahirMendiang(value: String) {
        tempatLahirMendiangValue = value
        clearFieldError("tempat_lahir_mendiang")
    }

    fun updateTanggalLahirMendiang(value: String) {
        tanggalLahirMendiangValue = value
        clearFieldError("tanggal_lahir_mendiang")
    }

    fun updateJenisKelaminMendiang(value: String) {
        jenisKelaminMendiangValue = value
        clearFieldError("jenis_kelamin_mendiang")
    }

    fun updateAlamatMendiang(value: String) {
        alamatMendiangValue = value
        clearFieldError("alamat_mendiang")
    }

    fun updateHariMeninggal(value: String) {
        hariMeninggalValue = value
        clearFieldError("hari_meninggal")
    }

    fun updateTanggalMeninggal(value: String) {
        tanggalMeninggalValue = value
        clearFieldError("tanggal_meninggal")
    }

    fun updateTempatMeninggal(value: String) {
        tempatMeninggalValue = value
        clearFieldError("tempat_meninggal")
    }

    fun updateSebabMeninggal(value: String) {
        sebabMeninggalValue = value
        clearFieldError("sebab_meninggal")
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
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
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
                        _skKematianEvent.emit(SKKematianEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _skKematianEvent.emit(SKKematianEvent.StepChanged(currentStep))
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
                _skKematianEvent.emit(SKKematianEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
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
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (namaValue.isBlank()) errors["nama"] = "Nama pelapor tidak boleh kosong"
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat pelapor tidak boleh kosong"
        if (hubunganIdValue.isBlank()) errors["hubungan_id"] = "Hubungan dengan mendiang tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikMendiangValue.isBlank()) {
            errors["nik_mendiang"] = "NIK mendiang tidak boleh kosong"
        } else if (nikMendiangValue.length != 16) {
            errors["nik_mendiang"] = "NIK mendiang harus 16 digit"
        }

        if (namaMendiangValue.isBlank()) errors["nama_mendiang"] = "Nama mendiang tidak boleh kosong"
        if (tempatLahirMendiangValue.isBlank()) errors["tempat_lahir_mendiang"] = "Tempat lahir mendiang tidak boleh kosong"
        if (tanggalLahirMendiangValue.isBlank()) errors["tanggal_lahir_mendiang"] = "Tanggal lahir mendiang tidak boleh kosong"
        if (jenisKelaminMendiangValue.isBlank()) errors["jenis_kelamin_mendiang"] = "Jenis kelamin mendiang tidak boleh kosong"
        if (alamatMendiangValue.isBlank()) errors["alamat_mendiang"] = "Alamat mendiang tidak boleh kosong"
        if (hariMeninggalValue.isBlank()) errors["hari_meninggal"] = "Hari meninggal tidak boleh kosong"
        if (tanggalMeninggalValue.isBlank()) errors["tanggal_meninggal"] = "Tanggal meninggal tidak boleh kosong"
        if (tempatMeninggalValue.isBlank()) errors["tempat_meninggal"] = "Tempat meninggal tidak boleh kosong"
        if (sebabMeninggalValue.isBlank()) errors["sebab_meninggal"] = "Sebab meninggal tidak boleh kosong"

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
                val request = SKKematianRequest(
                    disahkan_oleh = "",
                    alamat = alamatValue,
                    alamat_mendiang = alamatMendiangValue,
                    hari_meninggal = hariMeninggalValue,
                    hubungan_id = hubunganIdValue,
                    jenis_kelamin_mendiang = jenisKelaminMendiangValue,
                    keperluan = keperluanValue,
                    nama = namaValue,
                    nama_mendiang = namaMendiangValue,
                    nik_mendiang = nikMendiangValue,
                    sebab_meninggal = sebabMeninggalValue,
                    tanggal_lahir_mendiang = tanggalLahirMendiangValue,
                    tanggal_meninggal = tanggalMeninggalValue,
                    tempat_lahir_mendiang = tempatLahirMendiangValue,
                    tempat_meninggal = tempatMeninggalValue
                )

                when (val result = createSKKematianUseCase(request)) {
                    is SuratKematianResult.Success -> {
                        _skKematianEvent.emit(SKKematianEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratKematianResult.Error -> {
                        errorMessage = result.message
                        _skKematianEvent.emit(SKKematianEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skKematianEvent.emit(SKKematianEvent.SubmitError(errorMessage!!))
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
        namaValue = ""
        alamatValue = ""
        hubunganIdValue = ""

        // Step 2 - Informasi Mendiang
        nikMendiangValue = ""
        namaMendiangValue = ""
        tempatLahirMendiangValue = ""
        tanggalLahirMendiangValue = ""
        jenisKelaminMendiangValue = ""
        alamatMendiangValue = ""
        hariMeninggalValue = ""
        tanggalMeninggalValue = ""
        tempatMeninggalValue = ""
        sebabMeninggalValue = ""

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
        return namaValue.isNotBlank() || alamatValue.isNotBlank() ||
                hubunganIdValue.isNotBlank() || nikMendiangValue.isNotBlank() ||
                namaMendiangValue.isNotBlank() || tempatLahirMendiangValue.isNotBlank() ||
                tanggalLahirMendiangValue.isNotBlank() || jenisKelaminMendiangValue.isNotBlank() ||
                alamatMendiangValue.isNotBlank() || hariMeninggalValue.isNotBlank() ||
                tanggalMeninggalValue.isNotBlank() || tempatMeninggalValue.isNotBlank() ||
                sebabMeninggalValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    // Events
    sealed class SKKematianEvent {
        data class StepChanged(val step: Int) : SKKematianEvent()
        data object SubmitSuccess : SKKematianEvent()
        data class SubmitError(val message: String) : SKKematianEvent()
        data object ValidationError : SKKematianEvent()
        data class UserDataLoadError(val message: String) : SKKematianEvent()
    }
}

// UI State data class
data class SKKematianUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)