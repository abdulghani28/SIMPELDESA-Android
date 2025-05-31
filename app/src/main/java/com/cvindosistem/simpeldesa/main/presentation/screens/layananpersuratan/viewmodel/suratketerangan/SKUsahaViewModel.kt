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
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.BidangUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.JenisUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKUsahaRequest
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratUsahaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKUsahaViewModel(
    private val createSuratUsahaUseCase: CreateSuratUsahaUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getBidangUsahaUseCase: BidangUsahaUseCase,
    private val getJenisUsahaUseCase: JenisUsahaUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKUsahaUiState())
    val uiState = _uiState.asStateFlow()

    // Dropdown data
    var bidangUsahaList by mutableStateOf<List<BidangUsahaResponse.Data>>(emptyList())
    var jenisUsahaList by mutableStateOf<List<JenisUsahaResponse.Data>>(emptyList())
    var isLoadingBidangUsaha by mutableStateOf(false)
    var isLoadingJenisUsaha by mutableStateOf(false)

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Current tab (0 = Warga Desa, 1 = Pendatang)
    var currentTab by mutableIntStateOf(0)
        private set

    // Current step for each tab
    var currentStepWargaDesa by mutableIntStateOf(1)
        private set
    var currentStepPendatang by mutableIntStateOf(1)
        private set

    // Use My Data state (only for Warga Desa)
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
    private val _usahaEvent = MutableSharedFlow<SKUsahaEvent>()
    val usahaEvent = _usahaEvent.asSharedFlow()

    // ===== WARGA DESA DATA =====
    // Step 1 - Informasi Pelapor Warga
    var wargaNikValue by mutableStateOf("")
        private set
    var wargaNamaValue by mutableStateOf("")
        private set
    var wargaTempatLahirValue by mutableStateOf("")
        private set
    var wargaTanggalLahirValue by mutableStateOf("")
        private set
    var wargaSelectedGender by mutableStateOf("")
        private set
    var wargaPekerjaanValue by mutableStateOf("")
        private set
    var wargaAlamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Usaha Warga
    var wargaNamaUsahaValue by mutableStateOf("")
        private set
    var wargaBidangUsahaValue by mutableStateOf("")
        private set
    var wargaJenisUsahaValue by mutableStateOf("")
        private set
    var wargaNpwpValue by mutableStateOf("")
        private set
    var wargaAlamatUsahaValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Pelengkap Warga
    var wargaKeperluanValue by mutableStateOf("")
        private set

    // ===== PENDATANG DATA =====
    // Step 1 - Informasi Pelapor Pendatang
    var pendatangNikValue by mutableStateOf("")
        private set
    var pendatangNamaValue by mutableStateOf("")
        private set
    var pendatangTempatLahirValue by mutableStateOf("")
        private set
    var pendatangTanggalLahirValue by mutableStateOf("")
        private set
    var pendatangSelectedGender by mutableStateOf("")
        private set
    var pendatangPekerjaanValue by mutableStateOf("")
        private set
    var pendatangAlamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Usaha Pendatang
    var pendatangNamaUsahaValue by mutableStateOf("")
        private set
    var pendatangBidangUsahaValue by mutableStateOf("")
        private set
    var pendatangJenisUsahaValue by mutableStateOf("")
        private set
    var pendatangAlamatUsahaValue by mutableStateOf("")
        private set
    var pendatangNpwpValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Pelengkap Pendatang
    var pendatangKeperluanValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    init {
        loadDropdownData()
    }

    private fun loadDropdownData() {
        loadBidangUsaha()
        loadJenisUsaha()
    }

    private fun loadBidangUsaha() {
        viewModelScope.launch {
            isLoadingBidangUsaha = true
            try {
                when (val result = getBidangUsahaUseCase()) {
                    is BidangUsahaResult.Success -> {
                        bidangUsahaList = result.data.data
                    }
                    is BidangUsahaResult.Error -> {
                        _usahaEvent.emit(SKUsahaEvent.BidangUsahaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                _usahaEvent.emit(SKUsahaEvent.BidangUsahaLoadError(e.message ?: "Gagal memuat bidang usaha"))
            } finally {
                isLoadingBidangUsaha = false
            }
        }
    }

    private fun loadJenisUsaha() {
        viewModelScope.launch {
            isLoadingJenisUsaha = true
            try {
                when (val result = getJenisUsahaUseCase()) {
                    is JenisUsahaResult.Success -> {
                        jenisUsahaList = result.data.data
                    }
                    is JenisUsahaResult.Error -> {
                        _usahaEvent.emit(SKUsahaEvent.JenisUsahaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                _usahaEvent.emit(SKUsahaEvent.JenisUsahaLoadError(e.message ?: "Gagal memuat jenis usaha"))
            } finally {
                isLoadingJenisUsaha = false
            }
        }
    }

    // Tab management
    fun updateCurrentTab(tab: Int) {
        currentTab = tab
        // Clear validation errors when switching tabs
        _validationErrors.value = emptyMap()
    }

    // Step management
    private fun getCurrentStep(): Int {
        return if (currentTab == 0) currentStepWargaDesa else currentStepPendatang
    }

    private fun getTotalSteps(): Int = 3

    fun nextStep() {
        val currentStep = getCurrentStep()
        val isValid = when (currentStep) {
            1 -> validateStep1WithEvent()
            2 -> validateStep2WithEvent()
            3 -> validateStep3WithEvent()
            else -> false
        }

        if (isValid) {
            if (currentStep < getTotalSteps()) {
                if (currentTab == 0) {
                    currentStepWargaDesa += 1
                } else {
                    currentStepPendatang += 1
                }
                viewModelScope.launch {
                    _usahaEvent.emit(SKUsahaEvent.StepChanged(getCurrentStep()))
                }
            } else {
                // Last step, show confirmation
                showConfirmationDialog = true
            }
        }
    }

    fun previousStep() {
        val currentStep = getCurrentStep()
        if (currentStep > 1) {
            if (currentTab == 0) {
                currentStepWargaDesa -= 1
            } else {
                currentStepPendatang -= 1
            }
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.StepChanged(getCurrentStep()))
            }
        }
    }

    // Use My Data functionality (only for Warga Desa)
    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
        } else {
            clearWargaUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            isLoadingUserData = true
            try {
                when (val result = getUserInfoUseCase()) {
                    is UserInfoResult.Success -> {
                        val userData = result.data.data
                        wargaNikValue = userData.nik
                        wargaNamaValue = userData.nama_warga
                        wargaTempatLahirValue = userData.tempat_lahir
                        wargaTanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
                        wargaSelectedGender = userData.jenis_kelamin
                        wargaPekerjaanValue = userData.pekerjaan
                        wargaAlamatValue = userData.alamat

                        // Clear validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "pekerjaan", "alamat"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _usahaEvent.emit(SKUsahaEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _usahaEvent.emit(SKUsahaEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearWargaUserData() {
        wargaNikValue = ""
        wargaNamaValue = ""
        wargaTempatLahirValue = ""
        wargaTanggalLahirValue = ""
        wargaSelectedGender = ""
        wargaPekerjaanValue = ""
        wargaAlamatValue = ""
    }

    // ===== WARGA DESA FIELD UPDATES =====
    // Step 1 - Informasi Pelapor Warga
    fun updateWargaNik(value: String) {
        wargaNikValue = value
        clearFieldError("nik")
    }

    fun updateWargaNama(value: String) {
        wargaNamaValue = value
        clearFieldError("nama")
    }

    fun updateWargaTempatLahir(value: String) {
        wargaTempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updateWargaTanggalLahir(value: String) {
        wargaTanggalLahirValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal_lahir")
    }

    fun updateWargaGender(value: String) {
        wargaSelectedGender = value
        clearFieldError("jenis_kelamin")
    }

    fun updateWargaPekerjaan(value: String) {
        wargaPekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updateWargaAlamat(value: String) {
        wargaAlamatValue = value
        clearFieldError("alamat")
    }

    // Step 2 - Informasi Usaha Warga
    fun updateWargaNamaUsaha(value: String) {
        wargaNamaUsahaValue = value
        clearFieldError("nama_usaha")
    }

    fun updateWargaBidangUsaha(value: String) {
        wargaBidangUsahaValue = value
        clearFieldError("bidang_usaha_id")
    }

    fun updateWargaJenisUsaha(value: String) {
        wargaJenisUsahaValue = value
        clearFieldError("jenis_usaha_id")
    }

    fun updateWargaAlamatUsaha(value: String) {
        wargaAlamatUsahaValue = value
        clearFieldError("alamat_usaha")
    }

    fun updateWargaNpwp(value: String) {
        wargaNpwpValue = value
        clearFieldError("npwp")
    }

    // Step 3 - Informasi Pelengkap Warga
    fun updateWargaKeperluan(value: String) {
        wargaKeperluanValue = value
        clearFieldError("keperluan")
    }

    // ===== PENDATANG FIELD UPDATES =====
    // Step 1 - Informasi Pelapor Pendatang
    fun updatePendatangNik(value: String) {
        pendatangNikValue = value
        clearFieldError("nik")
    }

    fun updatePendatangNama(value: String) {
        pendatangNamaValue = value
        clearFieldError("nama")
    }

    fun updatePendatangTempatLahir(value: String) {
        pendatangTempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updatePendatangTanggalLahir(value: String) {
        pendatangTanggalLahirValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal_lahir")
    }

    fun updatePendatangGender(value: String) {
        pendatangSelectedGender = value
        clearFieldError("jenis_kelamin")
    }

    fun updatePendatangPekerjaan(value: String) {
        pendatangPekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    fun updatePendatangAlamat(value: String) {
        pendatangAlamatValue = value
        clearFieldError("alamat")
    }

    // Step 2 - Informasi Usaha Pendatang
    fun updatePendatangNamaUsaha(value: String) {
        pendatangNamaUsahaValue = value
        clearFieldError("nama_usaha")
    }

    fun updatePendatangBidangUsaha(value: String) {
        pendatangBidangUsahaValue = value
        clearFieldError("bidang_usaha_id")
    }

    fun updatePendatangJenisUsaha(value: String) {
        pendatangJenisUsahaValue = value
        clearFieldError("jenis_usaha_id")
    }

    fun updatePendatangAlamatUsaha(value: String) {
        pendatangAlamatUsahaValue = value
        clearFieldError("alamat_usaha")
    }

    fun updatePendatangNpwp(value: String) {
        pendatangNpwpValue = value
        clearFieldError("npwp")
    }

    // Step 3 - Informasi Pelengkap Pendatang
    fun updatePendatangKeperluan(value: String) {
        pendatangKeperluanValue = value
        clearFieldError("keperluan")
    }

    // ===== GETTER FUNCTIONS FOR CURRENT TAB =====
    fun getCurrentNik(): String = if (currentTab == 0) wargaNikValue else pendatangNikValue
    fun getCurrentNama(): String = if (currentTab == 0) wargaNamaValue else pendatangNamaValue
    fun getCurrentTempatLahir(): String = if (currentTab == 0) wargaTempatLahirValue else pendatangTempatLahirValue
    fun getCurrentTanggalLahir(): String = if (currentTab == 0) wargaTanggalLahirValue else pendatangTanggalLahirValue
    fun getCurrentGender(): String = if (currentTab == 0) wargaSelectedGender else pendatangSelectedGender
    fun getCurrentPekerjaan(): String = if (currentTab == 0) wargaPekerjaanValue else pendatangPekerjaanValue
    fun getCurrentAlamat(): String = if (currentTab == 0) wargaAlamatValue else pendatangAlamatValue
    fun getCurrentNamaUsaha(): String = if (currentTab == 0) wargaNamaUsahaValue else pendatangNamaUsahaValue
    fun getCurrentBidangUsaha(): String = if (currentTab == 0) wargaBidangUsahaValue else pendatangBidangUsahaValue
    fun getCurrentJenisUsaha(): String = if (currentTab == 0) wargaJenisUsahaValue else pendatangJenisUsahaValue
    fun getCurrentAlamatUsaha(): String = if (currentTab == 0) wargaAlamatUsahaValue else pendatangAlamatUsahaValue
    fun getCurrentKeperluan(): String = if (currentTab == 0) wargaKeperluanValue else pendatangKeperluanValue
    fun getCurrentNpwp(): String = if (currentTab == 1) pendatangNpwpValue else wargaNpwpValue

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()
        val nik = getCurrentNik()
        val nama = getCurrentNama()
        val tempatLahir = getCurrentTempatLahir()
        val tanggalLahir = getCurrentTanggalLahir()
        val gender = getCurrentGender()
        val pekerjaan = getCurrentPekerjaan()
        val alamat = getCurrentAlamat()

        if (nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (nama.isBlank()) errors["nama"] = "Nama lengkap tidak boleh kosong"
        if (tempatLahir.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahir.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (gender.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        if (pekerjaan.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        if (alamat.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()
        val namaUsaha = getCurrentNamaUsaha()
        val bidangUsaha = getCurrentBidangUsaha()
        val jenisUsaha = getCurrentJenisUsaha()
        val alamatUsaha = getCurrentAlamatUsaha()

        if (namaUsaha.isBlank()) errors["nama_usaha"] = "Nama usaha tidak boleh kosong"
        if (bidangUsaha.isBlank()) errors["bidang_usaha_id"] = "Bidang usaha harus dipilih"
        if (jenisUsaha.isBlank()) errors["jenis_usaha_id"] = "Jenis usaha harus dipilih"
        if (alamatUsaha.isBlank()) errors["alamat_usaha"] = "Alamat usaha tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (currentTab == 0) {
            // Warga Desa - validasi keperluan
            if (wargaKeperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        } else {
            // Pendatang - validasi NPWP
            if (pendatangNpwpValue.isBlank()) errors["npwp"] = "NPWP tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep1WithEvent(): Boolean {
        val isValid = validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
        return isValid
    }

    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3()
    }

    // Preview and confirmation dialogs
    fun showPreview() {
        if (validateAllSteps()) {
            showPreviewDialog = true
        } else {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
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

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val request = SKUsahaRequest(
                    alamat = getCurrentAlamat(),
                    alamat_usaha = getCurrentAlamatUsaha(),
                    bidang_usaha_id = getCurrentBidangUsaha(),
                    disahkan_oleh = "", // Set by backend
                    jenis_kelamin = getCurrentGender(),
                    jenis_usaha_id = getCurrentJenisUsaha(),
                    keperluan = getCurrentKeperluan(),
                    nama = getCurrentNama(),
                    nama_usaha = getCurrentNamaUsaha(),
                    nik = getCurrentNik(),
                    npwp = getCurrentNpwp(),
                    pekerjaan = getCurrentPekerjaan(),
                    tanggal_lahir = getCurrentTanggalLahir(),
                    tempat_lahir = getCurrentTempatLahir()
                )

                when (val result = createSuratUsahaUseCase(request)) {
                    is SuratUsahaResult.Success -> {
                        _usahaEvent.emit(SKUsahaEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratUsahaResult.Error -> {
                        errorMessage = result.message
                        _usahaEvent.emit(SKUsahaEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _usahaEvent.emit(SKUsahaEvent.SubmitError(errorMessage!!))
            } finally {
                isLoading = false
            }
        }
    }

    // Validation helper functions
    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
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

    // Reset form
    private fun resetForm() {
        currentTab = 0
        currentStepWargaDesa = 1
        currentStepPendatang = 1
        useMyDataChecked = false

        // Reset Warga Desa fields
        wargaNikValue = ""
        wargaNamaValue = ""
        wargaTempatLahirValue = ""
        wargaTanggalLahirValue = ""
        wargaSelectedGender = ""
        wargaPekerjaanValue = ""
        wargaAlamatValue = ""
        wargaNamaUsahaValue = ""
        wargaBidangUsahaValue = ""
        wargaJenisUsahaValue = ""
        wargaAlamatUsahaValue = ""
        wargaKeperluanValue = ""
        wargaNpwpValue = ""

        // Reset Pendatang fields
        pendatangNikValue = ""
        pendatangNamaValue = ""
        pendatangTempatLahirValue = ""
        pendatangTanggalLahirValue = ""
        pendatangSelectedGender = ""
        pendatangPekerjaanValue = ""
        pendatangAlamatValue = ""
        pendatangNamaUsahaValue = ""
        pendatangBidangUsahaValue = ""
        pendatangJenisUsahaValue = ""
        pendatangAlamatUsahaValue = ""
        pendatangNpwpValue = ""
        pendatangKeperluanValue = ""

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
        return if (currentTab == 0) {
            wargaNikValue.isNotBlank() || wargaNamaValue.isNotBlank() ||
                    wargaNamaUsahaValue.isNotBlank() || wargaKeperluanValue.isNotBlank()
        } else {
            pendatangNikValue.isNotBlank() || pendatangNamaValue.isNotBlank() ||
                    pendatangNamaUsahaValue.isNotBlank() || pendatangNpwpValue.isNotBlank()
        }
    }

    // Get current step for UI
    fun getCurrentStepForUI(): Int = getCurrentStep()

    // Get preview data
    fun getPreviewData(): Map<String, String> {
        val commonData = mapOf(
            "NIK" to getCurrentNik(),
            "Nama" to getCurrentNama(),
            "Tempat Lahir" to getCurrentTempatLahir(),
            "Tanggal Lahir" to getCurrentTanggalLahir(),
            "Jenis Kelamin" to getCurrentGender(),
            "Pekerjaan" to getCurrentPekerjaan(),
            "Alamat" to getCurrentAlamat(),
            "Nama Usaha" to getCurrentNamaUsaha(),
            "Bidang Usaha" to getCurrentBidangUsaha(),
            "Jenis Usaha" to getCurrentJenisUsaha(),
            "Alamat Usaha" to getCurrentAlamatUsaha(),
            "Keperluan" to getCurrentKeperluan(),
            "NPWP" to getCurrentNpwp()
        )

        return commonData
    }

    // Events
    sealed class SKUsahaEvent {
        data class StepChanged(val step: Int) : SKUsahaEvent()
        data object SubmitSuccess : SKUsahaEvent()
        data class SubmitError(val message: String) : SKUsahaEvent()
        data object ValidationError : SKUsahaEvent()
        data class UserDataLoadError(val message: String) : SKUsahaEvent()
        data class BidangUsahaLoadError(val message: String) : SKUsahaEvent()
        data class JenisUsahaLoadError(val message: String) : SKUsahaEvent()
    }
}

// UI State data class
data class SKUsahaUiState(
    val isFormDirty: Boolean = false,
    val currentTab: Int = 0,
    val totalTabs: Int = 2,
    val currentStepWargaDesa: Int = 1,
    val currentStepPendatang: Int = 1,
    val totalSteps: Int = 3
)