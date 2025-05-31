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
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan.SKDomisiliResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliViewModel.SKDomisiliEvent.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKDomisiliViewModel(
    private val createSuratDomisiliUseCase: CreateSuratDomisiliUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKDomisiliUiState())
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

    // Current tab (0 = Warga Desa, 1 = Pendatang)
    var currentTab by mutableIntStateOf(0)
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
    private val _domisiliEvent = MutableSharedFlow<SKDomisiliEvent>()
    val domisiliEvent = _domisiliEvent.asSharedFlow()

    // Common fields for both tabs
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
    var agamaValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set

    // Additional fields for Pendatang tab
    var selectedKewarganegaraan by mutableStateOf("")
        private set
    var alamatSesuaiKTP by mutableStateOf("")
        private set
    var alamatTinggalSekarang by mutableStateOf("")
        private set
    var jumlahPengikut by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // Tab management
    fun updateCurrentTab(tab: Int) {
        currentTab = tab
        // Clear validation errors when switching tabs
        _validationErrors.value = emptyMap()
    }

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
                        selectedGender = userData.jenis_kelamin
                        pekerjaanValue = userData.pekerjaan
                        alamatValue = userData.alamat
                        agamaValue = userData.agama_id

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
                        _domisiliEvent.emit(SKDomisiliEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _domisiliEvent.emit(SKDomisiliEvent.UserDataLoadError(errorMessage!!))
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
                        _domisiliEvent.emit(SKDomisiliEvent.AgamaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                agamaErrorMessage = e.message ?: "Gagal memuat data agama"
                _domisiliEvent.emit(SKDomisiliEvent.AgamaLoadError(agamaErrorMessage!!))
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
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaValue = ""
    }

    // Common field updates
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

    fun updateAgama(value: String) {
        agamaValue = value
        clearFieldError("agama_id")
    }

    fun updateKeperluan(value: String) {
        keperluanValue = value
        clearFieldError("keperluan")
    }

    // Pendatang specific field updates
    fun updateKewarganegaraan(value: String) {
        selectedKewarganegaraan = value
        clearFieldError("kewarganegaraan")
    }

    fun updateAlamatSesuaiKTP(value: String) {
        alamatSesuaiKTP = value
        clearFieldError("alamat_identitas")
    }

    fun updateAlamatTinggalSekarang(value: String) {
        alamatTinggalSekarang = value
        clearFieldError("alamat_tinggal_sekarang")
    }

    fun updateJumlahPengikut(value: String) {
        jumlahPengikut = value
        clearFieldError("jumlah_pengikut")
    }

    // Preview dialog functions
    fun showPreview() {
        val isValid = if (currentTab == 0) validateWargaDesa() else validatePendatang()

        if (!isValid) {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliEvent.ValidationError)
            }
        }

        showPreviewDialog = true
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    fun showConfirmationDialog() {
        val isValid = if (currentTab == 0) validateWargaDesa() else validatePendatang()

        if (isValid) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliEvent.ValidationError)
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

    // Validation functions
    private fun validateCommonFields(): MutableMap<String, String> {
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

        if (agamaValue.isBlank()) {
            errors["agama_id"] = "Agama tidak boleh kosong"
        }

        if (keperluanValue.isBlank()) {
            errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        return errors
    }

    private fun validateWargaDesa(): Boolean {
        val errors = validateCommonFields()
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validatePendatang(): Boolean {
        val errors = validateCommonFields()

        if (selectedKewarganegaraan.isBlank()) {
            errors["kewarganegaraan"] = "Kewarganegaraan harus dipilih"
        }

        if (alamatSesuaiKTP.isBlank()) {
            errors["alamat_identitas"] = "Alamat sesuai identitas tidak boleh kosong"
        }

        if (alamatTinggalSekarang.isBlank()) {
            errors["alamat_tinggal_sekarang"] = "Alamat tempat tinggal sekarang tidak boleh kosong"
        }

        if (jumlahPengikut.isBlank()) {
            errors["jumlah_pengikut"] = "Jumlah pengikut tidak boleh kosong"
        } else {
            try {
                jumlahPengikut.toInt()
            } catch (e: NumberFormatException) {
                errors["jumlah_pengikut"] = "Jumlah pengikut harus berupa angka"
            }
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllFields(): Boolean {
        return if (currentTab == 0) validateWargaDesa() else validatePendatang()
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
                val request = if (currentTab == 0) {
                    // Warga Desa request
                    SKDomisiliRequest(
                        alamat = alamatValue,
                        alamat_identitas = alamatValue, // Use same alamat for warga desa
                        disahkan_oleh = "", // This might be set on the backend
                        jenis_kelamin = selectedGender,
                        jumlah_pengikut = 0, // Default 0 for warga desa
                        keperluan = keperluanValue,
                        kewarganegaraan = "WNI", // Default for warga desa
                        nama = namaValue,
                        nik = nikValue,
                        pekerjaan = pekerjaanValue,
                        tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
                        tempat_lahir = tempatLahirValue,
                        warga_desa = true,
                        agama_id = agamaValue
                    )
                } else {
                    // Pendatang request
                    SKDomisiliRequest(
                        alamat = alamatTinggalSekarang,
                        alamat_identitas = alamatSesuaiKTP,
                        disahkan_oleh = "", // This might be set on the backend
                        jenis_kelamin = selectedGender,
                        jumlah_pengikut = jumlahPengikut.toIntOrNull() ?: 0,
                        keperluan = keperluanValue,
                        kewarganegaraan = selectedKewarganegaraan,
                        nama = namaValue,
                        nik = nikValue,
                        pekerjaan = pekerjaanValue,
                        tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
                        tempat_lahir = tempatLahirValue,
                        warga_desa = false,
                        agama_id = agamaValue
                    )
                }

                when (val result = createSuratDomisiliUseCase(request)) {
                    is SuratDomisiliResult.Success -> {
                        _domisiliEvent.emit(SubmitSuccess)
                        resetForm()
                    }
                    is SuratDomisiliResult.Error -> {
                        errorMessage = result.message
                        _domisiliEvent.emit(SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _domisiliEvent.emit(SKDomisiliEvent.SubmitError(errorMessage!!))
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
        currentTab = 0
        useMyDataChecked = false

        // Reset common fields
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaValue = ""
        keperluanValue = ""

        // Reset pendatang specific fields
        selectedKewarganegaraan = ""
        alamatSesuaiKTP = ""
        alamatTinggalSekarang = ""
        jumlahPengikut = ""

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
                keperluanValue.isNotBlank() || alamatSesuaiKTP.isNotBlank() ||
                alamatTinggalSekarang.isNotBlank() || jumlahPengikut.isNotBlank()
    }

    // Get display data for preview
    fun getPreviewData(): Map<String, String> {
        val commonData = mapOf(
            "NIK" to nikValue,
            "Nama" to namaValue,
            "Tempat Lahir" to tempatLahirValue,
            "Tanggal Lahir" to tanggalLahirValue,
            "Jenis Kelamin" to selectedGender,
            "Pekerjaan" to pekerjaanValue,
            "Alamat" to alamatValue,
            "Agama" to agamaValue,
            "Keperluan" to keperluanValue
        )

        return if (currentTab == 0) {
            commonData
        } else {
            commonData + mapOf(
                "Kewarganegaraan" to selectedKewarganegaraan,
                "Alamat Sesuai Identitas" to alamatSesuaiKTP,
                "Alamat Tinggal Sekarang" to alamatTinggalSekarang,
                "Jumlah Pengikut" to jumlahPengikut
            )
        }
    }

    // Events
    sealed class SKDomisiliEvent {
        data object SubmitSuccess : SKDomisiliEvent()
        data class SubmitError(val message: String) : SKDomisiliEvent()
        data object ValidationError : SKDomisiliEvent()
        data class UserDataLoadError(val message: String) : SKDomisiliEvent()
        data class AgamaLoadError(val message: String) : SKDomisiliEvent()
    }
}

// UI State data class
data class SKDomisiliUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val currentTab: Int = 0,
    val totalTabs: Int = 2
)
