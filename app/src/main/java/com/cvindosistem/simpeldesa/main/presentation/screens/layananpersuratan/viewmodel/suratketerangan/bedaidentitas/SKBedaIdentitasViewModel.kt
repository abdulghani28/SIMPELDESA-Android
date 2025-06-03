package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBedaIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPerbedaanIdentitasUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetTercantumIdentitasUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// 5. ViewModel yang sudah dimodularisasi
class SKBedaIdentitasViewModel(
    createSKBedaIdentitasUseCase: CreateSuratBedaIdentitasUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getTercantumIdentitasUseCase: GetTercantumIdentitasUseCase,
    getPerbedaanIdentitasUseCase: GetPerbedaanIdentitasUseCase
) : ViewModel() {

    // Komponen modular
    private val stateManager = SKBedaIdentitasStateManager()
    private val validator = SKBedaIdentitasValidator(stateManager)
    private val dataLoader = SKBedaIdentitasDataLoader(
        getTercantumIdentitasUseCase,
        getPerbedaanIdentitasUseCase,
        getUserInfoUseCase,
        stateManager
    ) { event ->
        viewModelScope.launch { _skBedaIdentitasEvent.emit(event) }
    }
    private val formSubmitter = SKBedaIdentitasFormSubmitter(
        createSKBedaIdentitasUseCase,
        stateManager
    ) { event ->
        viewModelScope.launch { _skBedaIdentitasEvent.emit(event) }
    }

    // Events
    private val _skBedaIdentitasEvent = MutableSharedFlow<SKBedaIdentitasEvent>()
    val skBedaIdentitasEvent = _skBedaIdentitasEvent.asSharedFlow()

    // Delegated properties untuk backward compatibility
    val uiState = stateManager.uiState
    val validationErrors = stateManager.validationErrors

    var tercantumIdentitasList by stateManager::tercantumIdentitasList
    var isLoadingTercantumIdentitas by stateManager::isLoadingTercantumIdentitas
    var tercantumIdentitasErrorMessage by stateManager::tercantumIdentitasErrorMessage

    var perbedaanIdentitasList by stateManager::perbedaanIdentitasList
    var isLoadingPerbedaanIdentitas by stateManager::isLoadingPerbedaanIdentitas
    var perbedaanIdentitasErrorMessage by stateManager::perbedaanIdentitasErrorMessage

    var isLoading by stateManager::isLoading
    var errorMessage by stateManager::errorMessage
    var currentStep by stateManager::currentStep
    var useMyDataChecked by stateManager::useMyDataChecked
    var isLoadingUserData by stateManager::isLoadingUserData
    var showConfirmationDialog by stateManager::showConfirmationDialog
    var showPreviewDialog by stateManager::showPreviewDialog

    // Form fields
    var perbedaanIdValue by stateManager::perbedaanIdValue
    var nama1Value by stateManager::nama1Value
    var nomor1Value by stateManager::nomor1Value
    var tempatLahir1Value by stateManager::tempatLahir1Value
    var tanggalLahir1Value by stateManager::tanggalLahir1Value
    var alamat1Value by stateManager::alamat1Value
    var tercantumId1Value by stateManager::tercantumId1Value

    var nama2Value by stateManager::nama2Value
    var nomor2Value by stateManager::nomor2Value
    var tempatLahir2Value by stateManager::tempatLahir2Value
    var tanggalLahir2Value by stateManager::tanggalLahir2Value
    var alamat2Value by stateManager::alamat2Value
    var tercantumId2Value by stateManager::tercantumId2Value

    var keperluanValue by stateManager::keperluanValue

    init {
        loadTercantumIdentitas()
        loadPerbedaanIdentitas()
    }

    // Data loading functions
    fun loadTercantumIdentitas() {
        viewModelScope.launch { dataLoader.loadTercantumIdentitas() }
    }

    fun loadPerbedaanIdentitas() {
        viewModelScope.launch { dataLoader.loadPerbedaanIdentitas() }
    }

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        stateManager.useMyDataChecked = checked
        if (checked) {
            viewModelScope.launch { dataLoader.loadUserData() }
        } else {
            clearUserData()
        }
    }

    private fun clearUserData() {
        stateManager.nama1Value = ""
        stateManager.nomor1Value = ""
        stateManager.tempatLahir1Value = ""
        stateManager.tanggalLahir1Value = ""
        stateManager.alamat1Value = ""
    }

    // Step 1 - Identitas Pertama Update Functions
    fun updateNama1(value: String) {
        stateManager.nama1Value = value
        stateManager.clearFieldError("nama_1")
    }

    fun updateNomor1(value: String) {
        stateManager.nomor1Value = value
        stateManager.clearFieldError("nomor_1")
    }

    fun updateTempatLahir1(value: String) {
        stateManager.tempatLahir1Value = value
        stateManager.clearFieldError("tempat_lahir_1")
    }

    fun updateTanggalLahir1(value: String) {
        stateManager.tanggalLahir1Value = value
        stateManager.clearFieldError("tanggal_lahir_1")
    }

    fun updateAlamat1(value: String) {
        stateManager.alamat1Value = value
        stateManager.clearFieldError("alamat_1")
    }

    fun updateTercantumId1(value: String) {
        stateManager.tercantumId1Value = value
        stateManager.clearFieldError("tercantum_id")
    }

    // Step 2 - Identitas Kedua Update Functions
    fun updateNama2(value: String) {
        stateManager.nama2Value = value
        stateManager.clearFieldError("nama_2")
    }

    fun updateNomor2(value: String) {
        stateManager.nomor2Value = value
        stateManager.clearFieldError("nomor_2")
    }

    fun updateTempatLahir2(value: String) {
        stateManager.tempatLahir2Value = value
        stateManager.clearFieldError("tempat_lahir_2")
    }

    fun updateTanggalLahir2(value: String) {
        stateManager.tanggalLahir2Value = value
        stateManager.clearFieldError("tanggal_lahir_2")
    }

    fun updateAlamat2(value: String) {
        stateManager.alamat2Value = value
        stateManager.clearFieldError("alamat_2")
    }

    fun updateTercantumId2(value: String) {
        stateManager.tercantumId2Value = value
        stateManager.clearFieldError("tercantum_id_2")
    }

    // Step 3 - Perbedaan dan Keperluan Update Functions
    fun updatePerbedaanId(value: String) {
        stateManager.perbedaanIdValue = value
        stateManager.clearFieldError("perbedaan_id")
    }

    fun updateKeperluan(value: String) {
        stateManager.keperluanValue = value
        stateManager.clearFieldError("keperluan")
    }

    // Preview dialog functions
    fun showPreview() {
        val step1Valid = validator.validateStep1()
        val step2Valid = validator.validateStep2()
        val step3Valid = validator.validateStep3()

        if (!step1Valid || !step2Valid || !step3Valid) {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }

        stateManager.showPreviewDialog = true
    }

    fun dismissPreview() {
        stateManager.showPreviewDialog = false
    }

    // Step navigation
    fun nextStep() {
        when (stateManager.currentStep) {
            1 -> {
                if (validateStep1WithEvent()) {
                    stateManager.currentStep = 2
                    viewModelScope.launch {
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.StepChanged(stateManager.currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    stateManager.currentStep = 3
                    viewModelScope.launch {
                        _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.StepChanged(stateManager.currentStep))
                    }
                }
            }
            3 -> {
                if (validateStep3WithEvent()) {
                    stateManager.showConfirmationDialog = true
                }
            }
        }
    }

    fun previousStep() {
        if (stateManager.currentStep > 1) {
            stateManager.currentStep -= 1
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.StepChanged(stateManager.currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validator.validateAllSteps()) {
            stateManager.showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() {
        stateManager.showConfirmationDialog = false
    }

    fun confirmSubmit() {
        stateManager.showConfirmationDialog = false
        viewModelScope.launch { formSubmitter.submitForm() }
    }

    // Validation functions dengan event
    private fun validateStep1WithEvent(): Boolean {
        val isValid = validator.validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validator.validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validator.validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _skBedaIdentitasEvent.emit(SKBedaIdentitasEvent.ValidationError)
            }
        }
        return isValid
    }

    // Public validation functions
    fun validateAllSteps(): Boolean = validator.validateAllSteps()
    fun getFieldError(fieldName: String): String? = stateManager.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = stateManager.hasFieldError(fieldName)
    fun clearError() { stateManager.errorMessage = null }
    fun hasFormData(): Boolean = stateManager.hasFormData()

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