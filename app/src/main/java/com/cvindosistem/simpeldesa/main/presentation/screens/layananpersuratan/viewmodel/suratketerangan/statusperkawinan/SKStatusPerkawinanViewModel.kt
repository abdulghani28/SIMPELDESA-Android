package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKStatusPerkawinanRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratStatusPerkawinanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratStatusPerkawinanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 2. Validator - Mengelola validasi form
class SKStatusPerkawinanValidator {
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    fun validateStep1(stateManager: SKStatusPerkawinanStateManager): Boolean {
        val errors = mutableMapOf<String, String>()

        if (stateManager.nikValue.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (stateManager.nikValue.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (stateManager.namaValue.isBlank()) {
            errors["nama"] = "Nama lengkap tidak boleh kosong"
        }

        if (stateManager.tempatLahirValue.isBlank()) {
            errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        }

        if (stateManager.tanggalLahirValue.isBlank()) {
            errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        }

        if (stateManager.selectedGender.isBlank()) {
            errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        }

        if (stateManager.pekerjaanValue.isBlank()) {
            errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"
        }

        if (stateManager.alamatValue.isBlank()) {
            errors["alamat"] = "Alamat tidak boleh kosong"
        }

        if (stateManager.agamaValue.isBlank()) {
            errors["agama_id"] = "Agama tidak boleh kosong"
        }

        if (stateManager.statusKawinValue.isBlank()) {
            errors["status_kawin_id"] = "Status kawin tidak boleh kosong"
        }

        if (stateManager.keperluanValue.isBlank()) {
            errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun validateAllSteps(stateManager: SKStatusPerkawinanStateManager): Boolean {
        return validateStep1(stateManager)
    }

    fun clearFieldError(fieldName: String) {
        val currentErrors = _validationErrors.value.toMutableMap()
        currentErrors.remove(fieldName)
        _validationErrors.value = currentErrors
    }

    fun clearMultipleFieldErrors(fieldNames: List<String>) {
        val currentErrors = _validationErrors.value.toMutableMap()
        fieldNames.forEach { fieldName ->
            currentErrors.remove(fieldName)
        }
        _validationErrors.value = currentErrors
    }

    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
    }

    fun clearAllErrors() {
        _validationErrors.value = emptyMap()
    }
}

// 3. Data Loader - Mengelola loading data dropdown dan user data
class SKStatusPerkawinanDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) {
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingAgama by mutableStateOf(false)
    var agamaErrorMessage by mutableStateOf<String?>(null)

    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
    var isLoadingStatusKawin by mutableStateOf(false)
    var statusKawinErrorMessage by mutableStateOf<String?>(null)

    suspend fun loadUserData(
        stateManager: SKStatusPerkawinanStateManager,
        validator: SKStatusPerkawinanValidator,
        onSuccess: () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        stateManager.setUserDataLoading(true)
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    stateManager.populateUserData(userData)

                    // Clear validation errors for populated fields
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan", "alamat", "status_kawin_id",
                        "agama_id"
                    ))
                    onSuccess()
                }
                is UserInfoResult.Error -> {
                    stateManager.setUseMyData(false)
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data pengguna"
            stateManager.setUseMyData(false)
            onError(message)
        } finally {
            stateManager.setUserDataLoading(false)
        }
    }

    suspend fun loadAgama(onError: suspend (String) -> Unit) {
        isLoadingAgama = true
        agamaErrorMessage = null
        try {
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    agamaList = result.data.data
                }
                is AgamaResult.Error -> {
                    agamaErrorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data agama"
            agamaErrorMessage = message
            onError(message)
        } finally {
            isLoadingAgama = false
        }
    }

    suspend fun loadStatusKawin(onError: suspend (String) -> Unit) {
        isLoadingStatusKawin = true
        statusKawinErrorMessage = null
        try {
            when (val result = getStatusKawinUseCase()) {
                is StatusKawinResult.Success -> {
                    statusKawinList = result.data.data
                }
                is StatusKawinResult.Error -> {
                    statusKawinErrorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data status kawin"
            statusKawinErrorMessage = message
            onError(message)
        } finally {
            isLoadingStatusKawin = false
        }
    }
}

// 4. Form Submitter - Mengelola submit form
class SKStatusPerkawinanFormSubmitter(
    private val createSuratCatatanStatusPerkawinanUseCase: CreateSuratStatusPerkawinanUseCase
) {
    suspend fun submitForm(
        request: SKStatusPerkawinanRequest,
        onSuccess: suspend () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        try {
            when (val result = createSuratCatatanStatusPerkawinanUseCase(request)) {
                is SuratStatusPerkawinanResult.Success -> {
                    onSuccess()
                }
                is SuratStatusPerkawinanResult.Error -> {
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            onError(e.message ?: "Terjadi kesalahan")
        }
    }
}

// 5. Refactored ViewModel menggunakan composition
class SKStatusPerkawinanViewModel(
    private val createSuratCatatanStatusPerkawinanUseCase: CreateSuratStatusPerkawinanUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // Composition components
    private val stateManager = SKStatusPerkawinanStateManager()
    private val validator = SKStatusPerkawinanValidator()
    private val dataLoader = SKStatusPerkawinanDataLoader(
        getUserInfoUseCase, getAgamaUseCase, getStatusKawinUseCase
    )
    private val formSubmitter = SKStatusPerkawinanFormSubmitter(createSuratCatatanStatusPerkawinanUseCase)

    // Public interface - delegate to components
    val uiState = MutableStateFlow(SKStatusPerkawinanUiState()).asStateFlow()
    val validationErrors = validator.validationErrors

    // Delegate properties to stateManager
    val nikValue get() = stateManager.nikValue
    val namaValue get() = stateManager.namaValue
    val tempatLahirValue get() = stateManager.tempatLahirValue
    val tanggalLahirValue get() = stateManager.tanggalLahirValue
    val selectedGender get() = stateManager.selectedGender
    val pekerjaanValue get() = stateManager.pekerjaanValue
    val alamatValue get() = stateManager.alamatValue
    val agamaValue get() = stateManager.agamaValue
    val statusKawinValue get() = stateManager.statusKawinValue
    val keperluanValue get() = stateManager.keperluanValue

    val currentStep get() = stateManager.currentStep
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val isLoading get() = stateManager.isLoading
    val errorMessage get() = stateManager.errorMessage
    val isLoadingUserData get() = stateManager.isLoadingUserData
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog

    // Delegate properties to dataLoader
    val agamaList get() = dataLoader.agamaList
    val isLoadingAgama get() = dataLoader.isLoadingAgama
    val agamaErrorMessage get() = dataLoader.agamaErrorMessage
    val statusKawinList get() = dataLoader.statusKawinList
    val isLoadingStatusKawin get() = dataLoader.isLoadingStatusKawin
    val statusKawinErrorMessage get() = dataLoader.statusKawinErrorMessage

    // Events - sama seperti sebelumnya
    private val _catatanStatusPerkawinanEvent = MutableSharedFlow<SKStatusPerkawinanEvent>()
    val catatanStatusPerkawinanEvent = _catatanStatusPerkawinanEvent.asSharedFlow()

    // Public methods - delegate to appropriate components
    fun updateNik(value: String) {
        stateManager.updateNik(value)
        validator.clearFieldError("nik")
    }

    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateTempatLahir(value: String) {
        stateManager.updateTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateTanggalLahir(value: String) {
        stateManager.updateTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateGender(value: String) {
        stateManager.updateGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePekerjaan(value: String) {
        stateManager.updatePekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateAgama(value: String) {
        stateManager.updateAgama(value)
        validator.clearFieldError("agama_id")
    }

    fun updateStatusKawin(value: String) {
        stateManager.updateStatusKawin(value)
        validator.clearFieldError("status_kawin_id")
    }

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    fun updateUseMyData(checked: Boolean) {
        stateManager.setUseMyData(checked)
        if (checked) {
            loadUserData()
            loadAgama()
            loadStatusKawin()
        } else {
            stateManager.clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            dataLoader.loadUserData(
                stateManager = stateManager,
                validator = validator,
                onSuccess = {},
                onError = { message ->
                    stateManager.setError(message)
                    _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.UserDataLoadError(message))
                }
            )
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama { message ->
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.AgamaLoadError(message))
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            dataLoader.loadStatusKawin { message ->
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.StatusKawinLoadError(message))
            }
        }
    }

    fun showPreview() {
        val step1Valid = validator.validateStep1(stateManager)
        if (!step1Valid) {
            viewModelScope.launch {
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()

    fun showConfirmationDialog() {
        if (validator.validateAllSteps(stateManager)) {
            stateManager.showConfirmation()
        } else {
            viewModelScope.launch {
                _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.dismissConfirmation()

    fun confirmSubmit() {
        stateManager.dismissConfirmation()
        submitForm()
    }

    private fun submitForm() {
        viewModelScope.launch {
            stateManager.updateLoading(true)
            stateManager.setError(null)

            val request = stateManager.createRequest()

            formSubmitter.submitForm(
                request = request,
                onSuccess = {
                    _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.SubmitSuccess)
                    stateManager.resetForm()
                    validator.clearAllErrors()
                },
                onError = { message ->
                    stateManager.setError(message)
                    _catatanStatusPerkawinanEvent.emit(SKStatusPerkawinanEvent.SubmitError(message))
                }
            )

            stateManager.updateLoading(false)
        }
    }

    // Validation methods
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun clearError() = stateManager.setError(null)
    fun hasFormData(): Boolean = stateManager.hasFormData()

    // Events class - copy langsung dari kode asli
    sealed class SKStatusPerkawinanEvent {
        data class StepChanged(val step: Int) : SKStatusPerkawinanEvent()
        data object SubmitSuccess : SKStatusPerkawinanEvent()
        data class SubmitError(val message: String) : SKStatusPerkawinanEvent()
        data object ValidationError : SKStatusPerkawinanEvent()
        data class UserDataLoadError(val message: String) : SKStatusPerkawinanEvent()
        data class AgamaLoadError(val message: String) : SKStatusPerkawinanEvent()
        data class StatusKawinLoadError(val message: String) : SKStatusPerkawinanEvent()
    }
}

// UI State - copy langsung dari kode asli
data class SKStatusPerkawinanUiState(
    val isFormDirty: Boolean = false,
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val statusKawinList: List<StatusKawinResponse.Data> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)