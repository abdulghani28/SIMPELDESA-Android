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
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PerbedaanIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.TercantumIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBedaIdentitasRequest
import com.cvindosistem.simpeldesa.main.domain.model.PerbedaanIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratBedaIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.TercantumIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBedaIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPerbedaanIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetTercantumIdentitasUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKBedaIdentitasViewModel(
    private val createSKBedaIdentitasUseCase: CreateSuratBedaIdentitasUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTercantumIdentitasUseCase: GetTercantumIdentitasUseCase,
    private val getPerbedaanIdentitasUseCase: GetPerbedaanIdentitasUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKBedaIdentitasUiState())
    val uiState = _uiState.asStateFlow()

    var tercantumIdentitasList by mutableStateOf<List<TercantumIdentitasResponse.Data>>(emptyList())
    var isLoadingTercantumIdentitas by mutableStateOf(false)
    var tercantumIdentitasErrorMessage by mutableStateOf<String?>(null)

    var perbedaanIdentitasList by mutableStateOf<List<PerbedaanIdentitasResponse.Data>>(emptyList())
    var isLoadingPerbedaanIdentitas by mutableStateOf(false)
    var perbedaanIdentitasErrorMessage by mutableStateOf<String?>(null)

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
    private val _skBedaIdentitasEvent = MutableSharedFlow<SKBedaIdentitasEvent>()
    val skBedaIdentitasEvent = _skBedaIdentitasEvent.asSharedFlow()

    // Step 1 - Identitas Pertama
    var perbedaanIdValue by mutableStateOf("")
        private set
    var nama1Value by mutableStateOf("")
        private set
    var nomor1Value by mutableStateOf("")
        private set
    var tempatLahir1Value by mutableStateOf("")
        private set
    var tanggalLahir1Value by mutableStateOf("")
        private set
    var alamat1Value by mutableStateOf("")
        private set
    var tercantumId1Value by mutableStateOf("")
        private set

    // Step 2 - Identitas Kedua
    var nama2Value by mutableStateOf("")
        private set
    var nomor2Value by mutableStateOf("")
        private set
    var tempatLahir2Value by mutableStateOf("")
        private set
    var tanggalLahir2Value by mutableStateOf("")
        private set
    var alamat2Value by mutableStateOf("")
        private set
    var tercantumId2Value by mutableStateOf("")
        private set

    // Step 3 - Perbedaan dan Keperluan
    var keperluanValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    init {
        loadTercantumIdentitas()
        loadPerbedaanIdentitas()
    }

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
                        // Load data to first identity (assumsi data pengguna untuk identitas pertama)
                        nama1Value = userData.nama_warga
                        nomor1Value = userData.nik
                        tempatLahir1Value = userData.tempat_lahir
                        tanggalLahir1Value = dateFormatterToApiFormat(userData.tanggal_lahir)
                        alamat1Value = userData.alamat

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nama_1", "nomor_1", "tempat_lahir_1", "tanggal_lahir_1", "alamat_1"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    fun loadTercantumIdentitas() {
        viewModelScope.launch {
            isLoadingTercantumIdentitas = true
            tercantumIdentitasErrorMessage = null
            try {
                when (val result = getTercantumIdentitasUseCase()) {
                    is TercantumIdentitasResult.Success -> {
                        tercantumIdentitasList = result.data.data
                    }
                    is TercantumIdentitasResult.Error -> {
                        tercantumIdentitasErrorMessage = result.message
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.TercantumIdentitasLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                tercantumIdentitasErrorMessage = e.message ?: "Gagal memuat data tercantum identitas"
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.TercantumIdentitasLoadError(tercantumIdentitasErrorMessage!!))
            } finally {
                isLoadingTercantumIdentitas = false
            }
        }
    }

    fun loadPerbedaanIdentitas() {
        viewModelScope.launch {
            isLoadingPerbedaanIdentitas = true
            perbedaanIdentitasErrorMessage = null
            try {
                when (val result = getPerbedaanIdentitasUseCase()) {
                    is PerbedaanIdentitasResult.Success -> {
                        perbedaanIdentitasList = result.data.data
                    }
                    is PerbedaanIdentitasResult.Error -> {
                        perbedaanIdentitasErrorMessage = result.message
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.PerbedaanIdentitasLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                perbedaanIdentitasErrorMessage = e.message ?: "Gagal memuat data perbedaan identitas"
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.PerbedaanIdentitasLoadError(perbedaanIdentitasErrorMessage!!))
            } finally {
                isLoadingPerbedaanIdentitas = false
            }
        }
    }

    private fun clearUserData() {
        nama1Value = ""
        nomor1Value = ""
        tempatLahir1Value = ""
        tanggalLahir1Value = ""
        alamat1Value = ""
    }

    // Step 1 - Identitas Pertama Update Functions
    fun updateNama1(value: String) {
        nama1Value = value
        clearFieldError("nama_1")
    }

    fun updateNomor1(value: String) {
        nomor1Value = value
        clearFieldError("nomor_1")
    }

    fun updateTempatLahir1(value: String) {
        tempatLahir1Value = value
        clearFieldError("tempat_lahir_1")
    }

    fun updateTanggalLahir1(value: String) {
        tanggalLahir1Value = value
        clearFieldError("tanggal_lahir_1")
    }

    fun updateAlamat1(value: String) {
        alamat1Value = value
        clearFieldError("alamat_1")
    }

    fun updateTercantumId1(value: String) {
        tercantumId1Value = value
        clearFieldError("tercantum_id")
    }

    // Step 2 - Identitas Kedua Update Functions
    fun updateNama2(value: String) {
        nama2Value = value
        clearFieldError("nama_2")
    }

    fun updateNomor2(value: String) {
        nomor2Value = value
        clearFieldError("nomor_2")
    }

    fun updateTempatLahir2(value: String) {
        tempatLahir2Value = value
        clearFieldError("tempat_lahir_2")
    }

    fun updateTanggalLahir2(value: String) {
        tanggalLahir2Value = value
        clearFieldError("tanggal_lahir_2")
    }

    fun updateAlamat2(value: String) {
        alamat2Value = value
        clearFieldError("alamat_2")
    }

    fun updateTercantumId2(value: String) {
        tercantumId2Value = value
        clearFieldError("tercantum_id_2")
    }

    // Step 3 - Perbedaan dan Keperluan Update Functions
    fun updatePerbedaanId(value: String) {
        perbedaanIdValue = value
        clearFieldError("perbedaan_id")
    }

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
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
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
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.StepChanged(currentStep))
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
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
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
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (perbedaanIdValue.isBlank()) errors["perbedaan_id"] = "Perbedaan identitas tidak boleh kosong"
        if (nama1Value.isBlank()) errors["nama_1"] = "Nama pertama tidak boleh kosong"
        if (nomor1Value.isBlank()) errors["nomor_1"] = "Nomor pertama tidak boleh kosong"
        if (tempatLahir1Value.isBlank()) errors["tempat_lahir_1"] = "Tempat lahir pertama tidak boleh kosong"
        if (tanggalLahir1Value.isBlank()) errors["tanggal_lahir_1"] = "Tanggal lahir pertama tidak boleh kosong"
        if (alamat1Value.isBlank()) errors["alamat_1"] = "Alamat pertama tidak boleh kosong"
        if (tercantumId1Value.isBlank()) errors["tercantum_id"] = "Tercantum identitas pertama tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nama2Value.isBlank()) errors["nama_2"] = "Nama kedua tidak boleh kosong"
        if (nomor2Value.isBlank()) errors["nomor_2"] = "Nomor kedua tidak boleh kosong"
        if (tempatLahir2Value.isBlank()) errors["tempat_lahir_2"] = "Tempat lahir kedua tidak boleh kosong"
        if (tanggalLahir2Value.isBlank()) errors["tanggal_lahir_2"] = "Tanggal lahir kedua tidak boleh kosong"
        if (alamat2Value.isBlank()) errors["alamat_2"] = "Alamat kedua tidak boleh kosong"
        if (tercantumId2Value.isBlank()) errors["tercantum_id_2"] = "Tercantum identitas kedua tidak boleh kosong"

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
                val request = SKBedaIdentitasRequest(
                    alamat_1 = alamat1Value,
                    alamat_2 = alamat2Value,
                    disahkan_oleh = "",
                    keperluan = keperluanValue,
                    nama_1 = nama1Value,
                    nama_2 = nama2Value,
                    nomor_1 = nomor1Value,
                    nomor_2 = nomor2Value,
                    perbedaan_id = perbedaanIdValue,
                    tanggal_lahir_1 = tanggalLahir1Value,
                    tanggal_lahir_2 = tanggalLahir2Value,
                    tempat_lahir_1 = tempatLahir1Value,
                    tempat_lahir_2 = tempatLahir2Value,
                    tercantum_id = tercantumId1Value,
                    tercantum_id_2 = tercantumId2Value
                )

                when (val result = createSKBedaIdentitasUseCase(request)) {
                    is SuratBedaIdentitasResult.Success -> {
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratBedaIdentitasResult.Error -> {
                        errorMessage = result.message
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Identitas Pertama
        nama1Value = ""
        nomor1Value = ""
        tempatLahir1Value = ""
        tanggalLahir1Value = ""
        alamat1Value = ""
        tercantumId1Value = ""

        // Step 2 - Identitas Kedua
        nama2Value = ""
        nomor2Value = ""
        tempatLahir2Value = ""
        tanggalLahir2Value = ""
        alamat2Value = ""
        tercantumId2Value = ""

        // Step 3 - Perbedaan dan Keperluan
        perbedaanIdValue = ""
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
        return nama1Value.isNotBlank() || nomor1Value.isNotBlank() ||
                tempatLahir1Value.isNotBlank() || tanggalLahir1Value.isNotBlank() ||
                alamat1Value.isNotBlank() || tercantumId1Value.isNotBlank() ||
                nama2Value.isNotBlank() || nomor2Value.isNotBlank() ||
                tempatLahir2Value.isNotBlank() || tanggalLahir2Value.isNotBlank() ||
                alamat2Value.isNotBlank() || tercantumId2Value.isNotBlank() ||
                perbedaanIdValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    // Events
    sealed class SKBedaIdentitasEvent {
        data class StepChanged(val step: Int) : SKBedaIdentitasEvent()
        data object SubmitSuccess : SKBedaIdentitasEvent()
        data class SubmitError(val message: String) : SKBedaIdentitasEvent()
        data object ValidationError : SKBedaIdentitasEvent()
        data class UserDataLoadError(val message: String) : SKBedaIdentitasEvent()
        data class TercantumIdentitasLoadError(val message: String) : SKBedaIdentitasEvent()
        data class PerbedaanIdentitasLoadError(val message: String) : SKBedaIdentitasEvent()
    }
}

// UI State data class
data class SKBedaIdentitasUiState(
    val isFormDirty: Boolean = false,
    val tercantumIdentitasList: List<TercantumIdentitasResponse.Data> = emptyList(),
    val perbedaanIdentitasList: List<PerbedaanIdentitasResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)