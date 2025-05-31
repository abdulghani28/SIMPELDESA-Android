package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinTidakMasukKerjaRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratTidakMasukKerjaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratTidakMasukKerjaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKTidakMampuViewModel.SKTidakMampuEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKTidakMasukKerjaViewModel(
    private val createSKTidakMasukKerjaUseCase: CreateSuratTidakMasukKerjaUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKTidakMasukKerjaUiState())
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
    private val _skTidakMasukKerjaEvent = MutableSharedFlow<SKTidakMasukKerjaEvent>()
    val skTidakMasukKerjaEvent = _skTidakMasukKerjaEvent.asSharedFlow()

    // Step 1 - Informasi Pribadi Pemohon
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
    var alamatValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Pekerjaan & Izin
    var jabatanValue by mutableStateOf("")
        private set
    var namaPerusahaanValue by mutableStateOf("")
        private set
    var alasanIzinValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set
    var lamaValue by mutableIntStateOf(0)
        private set
    var terhitungDariValue by mutableStateOf("")
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
                        alamatValue = userData.alamat
                        jenisKelaminValue = userData.jenis_kelamin
                        agamaIdValue = userData.agama_id
                        pekerjaanValue = userData.pekerjaan
                        tempatLahirValue = userData.tempat_lahir
                        tanggalLahirValue = userData.tanggal_lahir

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "alamat", "jenis_kelamin", "agama_id",
                            "pekerjaan", "tempat_lahir", "tanggal_lahir"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.UserDataLoadError(errorMessage!!))
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
                        agamaList = result.data.data // <- ini penting!
                    }
                    is AgamaResult.Error -> {
                        agamaErrorMessage = result.message
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.AgamaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                agamaErrorMessage = e.message ?: "Gagal memuat data agama"
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.AgamaLoadError(agamaErrorMessage!!))
            } finally {
                isLoadingAgama = false
            }
        }
    }

    private fun clearUserData() {
        // Step 1 - Informasi Pribadi
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        agamaIdValue = ""
        alamatValue = ""
        pekerjaanValue = ""
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

    fun updateAlamat(value: String) {
        alamatValue = value
        clearFieldError("alamat")
    }

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    // Step 2 - Informasi Pekerjaan & Izin Update Functions
    fun updateJabatan(value: String) {
        jabatanValue = value
        clearFieldError("jabatan")
    }

    fun updateNamaPerusahaan(value: String) {
        namaPerusahaanValue = value
        clearFieldError("nama_perusahaan")
    }

    fun updateAlasanIzin(value: String) {
        alasanIzinValue = value
        clearFieldError("alasan_izin")
    }

    fun updateKeperluan(value: String) {
        keperluanValue = value
        clearFieldError("keperluan")
    }

    fun updateLama(value: Int) {
        lamaValue = value
        clearFieldError("lama")
    }

    fun updateTerhitungDari(value: String) {
        terhitungDariValue = value
        clearFieldError("terhitung_dari")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()

        if (!step1Valid || !step2Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
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
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.StepChanged(currentStep))
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
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
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
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.ValidationError)
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
        if (alamatValue.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (pekerjaanValue.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (jabatanValue.isBlank()) errors["jabatan"] = "Jabatan tidak boleh kosong"
        if (namaPerusahaanValue.isBlank()) errors["nama_perusahaan"] = "Nama perusahaan tidak boleh kosong"
        if (alasanIzinValue.isBlank()) errors["alasan_izin"] = "Alasan izin tidak boleh kosong"
        if (keperluanValue.isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        if (terhitungDariValue.isBlank()) errors["terhitung_dari"] = "Terhitung dari tidak boleh kosong"

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
                val request = SKIzinTidakMasukKerjaRequest(
                    disahkan_oleh = "",
                    agama_id = agamaIdValue,
                    alamat = alamatValue,
                    alasan_izin = alasanIzinValue,
                    jabatan = jabatanValue,
                    jenis_kelamin = jenisKelaminValue,
                    keperluan = keperluanValue,
                    lama = lamaValue,
                    nama = namaValue,
                    nama_perusahaan = namaPerusahaanValue,
                    nik = nikValue,
                    pekerjaan = pekerjaanValue,
                    tanggal_lahir = tanggalLahirValue,
                    tempat_lahir = tempatLahirValue,
                    terhitung_dari = terhitungDariValue,
                )

                when (val result = createSKTidakMasukKerjaUseCase(request)) {
                    is SuratTidakMasukKerjaResult.Success -> {
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratTidakMasukKerjaResult.Error -> {
                        errorMessage = result.message
                        _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skTidakMasukKerjaEvent.emit(SKTidakMasukKerjaEvent.SubmitError(errorMessage!!))
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
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        agamaIdValue = ""
        alamatValue = ""
        pekerjaanValue = ""

        // Step 2 - Informasi Pekerjaan & Izin
        jabatanValue = ""
        namaPerusahaanValue = ""
        alasanIzinValue = ""
        keperluanValue = ""
        lamaValue = 0
        terhitungDariValue = ""

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
                alamatValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                jabatanValue.isNotBlank() || namaPerusahaanValue.isNotBlank() ||
                alasanIzinValue.isNotBlank() || keperluanValue.isNotBlank()
                || terhitungDariValue.isNotBlank()
    }

    // Events
    sealed class SKTidakMasukKerjaEvent {
        data class StepChanged(val step: Int) : SKTidakMasukKerjaEvent()
        data object SubmitSuccess : SKTidakMasukKerjaEvent()
        data class SubmitError(val message: String) : SKTidakMasukKerjaEvent()
        data object ValidationError : SKTidakMasukKerjaEvent()
        data class AgamaLoadError(val message: String) : SKTidakMasukKerjaEvent()
        data class UserDataLoadError(val message: String) : SKTidakMasukKerjaEvent()
    }
}

// UI State data class
data class SKTidakMasukKerjaUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)