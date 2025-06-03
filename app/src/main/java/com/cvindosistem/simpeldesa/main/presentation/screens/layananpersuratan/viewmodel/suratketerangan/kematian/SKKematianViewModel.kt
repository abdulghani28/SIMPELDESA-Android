package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKematianUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKKematianViewModel(
    createSKKematianUseCase: CreateSuratKematianUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    // Components
    private val stateManager = SKKematianStateManager()
    private val validator = SKKematianValidator(stateManager)
    private val dataLoader = SKKematianDataLoader(
        getUserInfoUseCase, stateManager, validator, viewModelScope
    )
    private val formSubmitter = SKKematianFormSubmitter(
        createSKKematianUseCase, stateManager, viewModelScope
    )
    private val stepManager = SKKematianStepManager(validator, viewModelScope)

    // UI State
    private val _uiState = MutableStateFlow(SKKematianUiState())
    val uiState = _uiState.asStateFlow()

    // Consolidated state
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Events
    private val _skKematianEvent = MutableSharedFlow<SKKematianEvent>()
    val skKematianEvent = _skKematianEvent.asSharedFlow()

    // Delegated properties
    val currentStep: Int get() = stepManager.currentStep
    val isLoading: Boolean get() = formSubmitter.isLoading
    val useMyDataChecked: Boolean get() = dataLoader.useMyDataChecked
    val isLoadingUserData: Boolean get() = dataLoader.isLoadingUserData
    val validationErrors = validator.validationErrors

    // State access properties
    val namaValue: String get() = stateManager.namaValue
    val alamatValue: String get() = stateManager.alamatValue
    val hubunganIdValue: String get() = stateManager.hubunganIdValue

    val nikMendiangValue: String get() = stateManager.nikMendiangValue
    val namaMendiangValue: String get() = stateManager.namaMendiangValue
    val tempatLahirMendiangValue: String get() = stateManager.tempatLahirMendiangValue
    val tanggalLahirMendiangValue: String get() = stateManager.tanggalLahirMendiangValue
    val jenisKelaminMendiangValue: String get() = stateManager.jenisKelaminMendiangValue
    val alamatMendiangValue: String get() = stateManager.alamatMendiangValue
    val hariMeninggalValue: String get() = stateManager.hariMeninggalValue
    val tanggalMeninggalValue: String get() = stateManager.tanggalMeninggalValue
    val tempatMeninggalValue: String get() = stateManager.tempatMeninggalValue
    val sebabMeninggalValue: String get() = stateManager.sebabMeninggalValue

    val keperluanValue: String get() = stateManager.keperluanValue

    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            // Observe step events
            stepManager.events.collect { event ->
                when (event) {
                    is SKKematianStepManager.StepEvent.StepChanged -> {
                        _skKematianEvent.emit(SKKematianEvent.StepChanged(event.step))
                    }
                    is SKKematianStepManager.StepEvent.ValidationError -> {
                        _skKematianEvent.emit(SKKematianEvent.ValidationError)
                    }
                    is SKKematianStepManager.StepEvent.ReadyToSubmit -> {
                        showConfirmationDialog = true
                    }
                }
            }
        }

        viewModelScope.launch {
            // Observe data loader events
            dataLoader.events.collect { event ->
                when (event) {
                    is SKKematianDataLoader.DataLoaderEvent.LoadError -> {
                        errorMessage = event.message
                        _skKematianEvent.emit(SKKematianEvent.UserDataLoadError(event.message))
                    }
                    is SKKematianDataLoader.DataLoaderEvent.UserDataLoaded -> {
                        // Handle successful data load if needed
                    }
                }
            }
        }

        viewModelScope.launch {
            // Observe form submitter events
            formSubmitter.events.collect { event ->
                when (event) {
                    is SKKematianFormSubmitter.SubmitterEvent.SubmitSuccess -> {
                        _skKematianEvent.emit(SKKematianEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SKKematianFormSubmitter.SubmitterEvent.SubmitError -> {
                        errorMessage = event.message
                        _skKematianEvent.emit(SKKematianEvent.SubmitError(event.message))
                    }
                }
            }
        }
    }

    // Public interface methods - sama seperti original
    fun updateNama(value: String) {
        stateManager.updateNama(value)
        validator.clearFieldError("nama")
    }

    fun updateAlamat(value: String) {
        stateManager.updateAlamat(value)
        validator.clearFieldError("alamat")
    }


    fun updateHubunganId(value: String) {
        stateManager.updateHubunganId(value)
        validator.clearFieldError("hubungan_id")
    }

    // Step 2 Update Functions
    fun updateNikMendiang(value: String) {
        stateManager.updateNikMendiang(value)
        validator.clearFieldError("nik_mendiang")
    }

    fun updateNamaMendiang(value: String) {
        stateManager.updateNamaMendiang(value)
        validator.clearFieldError("nama_mendiang")
    }

    fun updateTempatLahirMendiang(value: String) {
        stateManager.updateTempatLahirMendiang(value)
        validator.clearFieldError("tempat_lahir_mendiang")
    }

    fun updateTanggalLahirMendiang(value: String) {
        stateManager.updateTanggalLahirMendiang(value)
        validator.clearFieldError("tanggal_lahir_mendiang")
    }

    fun updateJenisKelaminMendiang(value: String) {
        stateManager.updateJenisKelaminMendiang(value)
        validator.clearFieldError("jenis_kelamin_mendiang")
    }

    fun updateAlamatMendiang(value: String) {
        stateManager.updateAlamatMendiang(value)
        validator.clearFieldError("alamat_mendiang")
    }

    fun updateHariMeninggal(value: String) {
        stateManager.updateHariMeninggal(value)
        validator.clearFieldError("hari_meninggal")
    }

    fun updateTanggalMeninggal(value: String) {
        stateManager.updateTanggalMeninggal(value)
        validator.clearFieldError("tanggal_meninggal")
    }

    fun updateTempatMeninggal(value: String) {
        stateManager.updateTempatMeninggal(value)
        validator.clearFieldError("tempat_meninggal")
    }

    fun updateSebabMeninggal(value: String) {
        stateManager.updateSebabMeninggal(value)
        validator.clearFieldError("sebab_meninggal")
    }

    // Step 3 Update Function
    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }


    fun updateUseMyData(checked: Boolean) = dataLoader.updateUseMyData(checked)
    fun nextStep() = stepManager.nextStep()
    fun previousStep() = stepManager.previousStep()

    fun showPreview() {
        val allValid = validator.validateAllSteps()
        if (!allValid) {
            viewModelScope.launch {
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
            }
        }
        showPreviewDialog = true
    }

    fun dismissPreview() { showPreviewDialog = false }

    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skKematianEvent.emit(SKKematianEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() { showConfirmationDialog = false }
    fun confirmSubmit() {
        showConfirmationDialog = false
        formSubmitter.submitForm()
    }

    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun clearError() { errorMessage = null }

    private fun resetForm() {
        stepManager.resetToFirstStep()
        stateManager.resetAll()
        validator.clearAllErrors()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    // Events - copy langsung dari kode asli
    sealed class SKKematianEvent {
        data class StepChanged(val step: Int) : SKKematianEvent()
        data object SubmitSuccess : SKKematianEvent()
        data class SubmitError(val message: String) : SKKematianEvent()
        data object ValidationError : SKKematianEvent()
        data class UserDataLoadError(val message: String) : SKKematianEvent()
    }
}