package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val createSKPenghasilanUseCase: CreateSuratPenghasilanUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKPenghasilanUiState())
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
    private val _skPenghasilanEvent = MutableSharedFlow<SKPenghasilanEvent>()
    val skPenghasilanEvent = _skPenghasilanEvent.asSharedFlow()

    // Step 1 - Informasi Pribadi Pemohon
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Orang Tua
    var nikOrtuValue by mutableStateOf("")
        private set
    var namaOrtuValue by mutableStateOf("")
        private set
    var tempatLahirOrtuValue by mutableStateOf("")
        private set
    var tanggalLahirOrtuValue by mutableStateOf("")
        private set
    var jenisKelaminOrtuValue by mutableStateOf("")
        private set
    var alamatOrtuValue by mutableStateOf("")
        private set
    var pekerjaanOrtuValue by mutableStateOf("")
        private set
    var penghasilanOrtuValue by mutableIntStateOf(0)
        private set
    var tanggunganOrtuValue by mutableIntStateOf(0)
        private set

    // Step 3 - Informasi Anak
    var nikAnakValue by mutableStateOf("")
        private set
    var namaAnakValue by mutableStateOf("")
        private set
    var tempatLahirAnakValue by mutableStateOf("")
        private set
    var tanggalLahirAnakValue by mutableStateOf("")
        private set
    var jenisKelaminAnakValue by mutableStateOf("")
        private set
    var namaSekolahAnakValue by mutableStateOf("")
        private set
    var kelasAnakValue by mutableStateOf("")
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
                        alamatValue = userData.alamat

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf("nik", "nama", "alamat"))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skPenghasilanEvent.emit(SKPenghasilanEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skPenghasilanEvent.emit(SKPenghasilanEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        // Step 1 - Informasi Pribadi
        nikValue = ""
        namaValue = ""
        alamatValue = ""
    }

    // Step 1 - Informasi Pribadi Update Functions
    fun updateNik(value: String) {
        nikValue = value
        clearFieldError("nik")
    }

    fun updateNama(value: String) {
        namaValue = value
        clearFieldError("nama")
    }

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    fun updateKeperluan(value: String) {
        keperluanValue = value
        clearFieldError("keperluan")
    }

    // Step 2 - Informasi Orang Tua Update Functions
    fun updateNikOrtu(value: String) {
        nikOrtuValue = value
        clearFieldError("nik_ortu")
    }

    fun updateNamaOrtu(value: String) {
        namaOrtuValue = value
        clearFieldError("nama_ortu")
    }

    fun updateTempatLahirOrtu(value: String) {
        tempatLahirOrtuValue = value
        clearFieldError("tempat_lahir_ortu")
    }

    fun updateTanggalLahirOrtu(value: String) {
        tanggalLahirOrtuValue = value
        clearFieldError("tanggal_lahir_ortu")
    }

    fun updateJenisKelaminOrtu(value: String) {
        jenisKelaminOrtuValue = value
        clearFieldError("jenis_kelamin_ortu")
    }

    fun updateAlamatOrtu(value: String) {
        alamatOrtuValue = value
        clearFieldError("alamat_ortu")
    }

    fun updatePekerjaanOrtu(value: String) {
        pekerjaanOrtuValue = value
        clearFieldError("pekerjaan_ortu")
    }

    fun updatePenghasilanOrtu(value: Int) {
        penghasilanOrtuValue = value
        clearFieldError("penghasilan_ortu")
    }

    fun updateTanggunganOrtu(value: Int) {
        tanggunganOrtuValue = value
        clearFieldError("tanggungan_ortu")
    }

    // Step 3 - Informasi Anak Update Functions
    fun updateNikAnak(value: String) {
        nikAnakValue = value
        clearFieldError("nik_anak")
    }

    fun updateNamaAnak(value: String) {
        namaAnakValue = value
        clearFieldError("nama_anak")
    }

    fun updateTempatLahirAnak(value: String) {
        tempatLahirAnakValue = value
        clearFieldError("tempat_lahir_anak")
    }

    fun updateTanggalLahirAnak(value: String) {
        tanggalLahirAnakValue = value
        clearFieldError("tanggal_lahir_anak")
    }

    fun updateJenisKelaminAnak(value: String) {
        jenisKelaminAnakValue = value
        clearFieldError("jenis_kelamin_anak")
    }

    fun updateNamaSekolahAnak(value: String) {
        namaSekolahAnakValue = value
        clearFieldError("nama_sekolah_anak")
    }

    fun updateKelasAnak(value: String) {
        kelasAnakValue = value
        clearFieldError("kelas_anak")
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
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
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
                        _skPenghasilanEvent.emit(SKPenghasilanEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _skPenghasilanEvent.emit(SKPenghasilanEvent.StepChanged(currentStep))
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
                _skPenghasilanEvent.emit(SKPenghasilanEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
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
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skPenghasilanEvent.emit(SKPenghasilanEvent.ValidationError)
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
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikOrtuValue.isBlank()) {
            errors["nik_ortu"] = "NIK Orang Tua tidak boleh kosong"
        } else if (nikOrtuValue.length != 16) {
            errors["nik_ortu"] = "NIK Orang Tua harus 16 digit"
        }

        if (namaOrtuValue.isBlank()) errors["nama_ortu"] = "Nama Orang Tua tidak boleh kosong"
        if (tempatLahirOrtuValue.isBlank()) errors["tempat_lahir_ortu"] = "Tempat lahir Orang Tua tidak boleh kosong"
        if (tanggalLahirOrtuValue.isBlank()) errors["tanggal_lahir_ortu"] = "Tanggal lahir Orang Tua tidak boleh kosong"
        if (jenisKelaminOrtuValue.isBlank()) errors["jenis_kelamin_ortu"] = "Jenis kelamin Orang Tua tidak boleh kosong"
        if (alamatOrtuValue.isBlank()) errors["alamat_ortu"] = "Alamat Orang Tua tidak boleh kosong"
        if (pekerjaanOrtuValue.isBlank()) errors["pekerjaan_ortu"] = "Pekerjaan Orang Tua tidak boleh kosong"
        if (penghasilanOrtuValue <= 0) errors["penghasilan_ortu"] = "Penghasilan Orang Tua harus lebih dari 0"
        if (tanggunganOrtuValue <= 0) errors["tanggungan_ortu"] = "Jumlah tanggungan harus lebih dari 0"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikAnakValue.isBlank()) {
            errors["nik_anak"] = "NIK Anak tidak boleh kosong"
        } else if (nikAnakValue.length != 16) {
            errors["nik_anak"] = "NIK Anak harus 16 digit"
        }

        if (namaAnakValue.isBlank()) errors["nama_anak"] = "Nama Anak tidak boleh kosong"
        if (tempatLahirAnakValue.isBlank()) errors["tempat_lahir_anak"] = "Tempat lahir Anak tidak boleh kosong"
        if (tanggalLahirAnakValue.isBlank()) errors["tanggal_lahir_anak"] = "Tanggal lahir Anak tidak boleh kosong"
        if (jenisKelaminAnakValue.isBlank()) errors["jenis_kelamin_anak"] = "Jenis kelamin Anak tidak boleh kosong"
        if (namaSekolahAnakValue.isBlank()) errors["nama_sekolah_anak"] = "Nama sekolah Anak tidak boleh kosong"
        if (kelasAnakValue.isBlank()) errors["kelas_anak"] = "Kelas Anak tidak boleh kosong"

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
                val request = SKPenghasilanRequest(
                    disahkan_oleh = "",
                    alamat = alamatValue,
                    alamat_ortu = alamatOrtuValue,
                    jenis_kelamin_anak = jenisKelaminAnakValue,
                    jenis_kelamin_ortu = jenisKelaminOrtuValue,
                    kelas_anak = kelasAnakValue,
                    keperluan = keperluanValue,
                    nama = namaValue,
                    nama_anak = namaAnakValue,
                    nama_ortu = namaOrtuValue,
                    nama_sekolah_anak = namaSekolahAnakValue,
                    nik = nikValue,
                    nik_anak = nikAnakValue,
                    nik_ortu = nikOrtuValue,
                    pekerjaan_ortu = pekerjaanOrtuValue,
                    penghasilan_ortu = penghasilanOrtuValue,
                    tanggal_lahir_anak = tanggalLahirAnakValue,
                    tanggal_lahir_ortu = tanggalLahirOrtuValue,
                    tanggungan_ortu = tanggunganOrtuValue,
                    tempat_lahir_anak = tempatLahirAnakValue,
                    tempat_lahir_ortu = tempatLahirOrtuValue
                )

                when (val result = createSKPenghasilanUseCase(request)) {
                    is SuratPenghasilanResult.Success -> {
                        _skPenghasilanEvent.emit(SKPenghasilanEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratPenghasilanResult.Error -> {
                        errorMessage = result.message
                        _skPenghasilanEvent.emit(SKPenghasilanEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skPenghasilanEvent.emit(SKPenghasilanEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Informasi Pribadi
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        keperluanValue = ""

        // Step 2 - Informasi Orang Tua
        nikOrtuValue = ""
        namaOrtuValue = ""
        tempatLahirOrtuValue = ""
        tanggalLahirOrtuValue = ""
        jenisKelaminOrtuValue = ""
        alamatOrtuValue = ""
        pekerjaanOrtuValue = ""
        penghasilanOrtuValue = 0
        tanggunganOrtuValue = 0

        // Step 3 - Informasi Anak
        nikAnakValue = ""
        namaAnakValue = ""
        tempatLahirAnakValue = ""
        tanggalLahirAnakValue = ""
        jenisKelaminAnakValue = ""
        namaSekolahAnakValue = ""
        kelasAnakValue = ""

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
                alamatValue.isNotBlank() || keperluanValue.isNotBlank() ||
                nikOrtuValue.isNotBlank() || namaOrtuValue.isNotBlank() ||
                tempatLahirOrtuValue.isNotBlank() || tanggalLahirOrtuValue.isNotBlank() ||
                jenisKelaminOrtuValue.isNotBlank() || alamatOrtuValue.isNotBlank() ||
                pekerjaanOrtuValue.isNotBlank() || penghasilanOrtuValue > 0 ||
                tanggunganOrtuValue > 0 || nikAnakValue.isNotBlank() ||
                namaAnakValue.isNotBlank() || tempatLahirAnakValue.isNotBlank() ||
                tanggalLahirAnakValue.isNotBlank() || jenisKelaminAnakValue.isNotBlank() ||
                namaSekolahAnakValue.isNotBlank() || kelasAnakValue.isNotBlank()
    }

    // Events
    sealed class SKPenghasilanEvent {
        data class StepChanged(val step: Int) : SKPenghasilanEvent()
        data object SubmitSuccess : SKPenghasilanEvent()
        data class SubmitError(val message: String) : SKPenghasilanEvent()
        data object ValidationError : SKPenghasilanEvent()
        data class UserDataLoadError(val message: String) : SKPenghasilanEvent()
    }
}

// UI State data class
data class SKPenghasilanUiState(
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)