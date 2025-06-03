package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel.SKDomisiliEvent.AgamaLoadError
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel.SKDomisiliEvent.SubmitError
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel.SKDomisiliEvent.SubmitSuccess
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel.SKDomisiliEvent.UserDataLoadError
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel.SKDomisiliEvent.ValidationError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKDomisiliViewModel(
    createSuratDomisiliUseCase: CreateSuratDomisiliUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // Compose components
    private val stateManager = SKDomisiliStateManager()
    private val validator = SKDomisiliValidator(stateManager)
    private val dataLoader = SKDomisiliDataLoader(getUserInfoUseCase, getAgamaUseCase, stateManager, validator)
    private val formSubmitter = SKDomisiliFormSubmitter(createSuratDomisiliUseCase, stateManager)

    // Events
    private val _domisiliEvent = MutableSharedFlow<SKDomisiliEvent>()
    val domisiliEvent = _domisiliEvent.asSharedFlow()

    // Expose public interface - Delegate ke components
    val uiState = stateManager.uiState
    val validationErrors = validator.validationErrors

    // State properties delegation
    val agamaList by dataLoader::agamaList
    val isLoadingAgama by dataLoader::isLoadingAgama
    val agamaErrorMessage by dataLoader::agamaErrorMessage
    val isLoadingUserData by dataLoader::isLoadingUserData
    val isLoading by formSubmitter::isLoading
    val errorMessage by formSubmitter::errorMessage

    // Form field properties delegation
    val nikValue by stateManager::nikValue
    val namaValue by stateManager::namaValue
    val tempatLahirValue by stateManager::tempatLahirValue
    val tanggalLahirValue by stateManager::tanggalLahirValue
    val selectedGender by stateManager::selectedGender
    val pekerjaanValue by stateManager::pekerjaanValue
    val alamatValue by stateManager::alamatValue
    val agamaValue by stateManager::agamaValue
    val keperluanValue by stateManager::keperluanValue
    val selectedKewarganegaraan by stateManager::selectedKewarganegaraan
    val alamatSesuaiKTP by stateManager::alamatSesuaiKTP
    val alamatTinggalSekarang by stateManager::alamatTinggalSekarang
    val jumlahPengikut by stateManager::jumlahPengikut
    val currentTab by stateManager::currentTab
    val useMyDataChecked by stateManager::useMyDataChecked
    val showConfirmationDialog by stateManager::isShowConfirmationDialog
    val showPreviewDialog by stateManager::showPreviewDialog

    // Public functions - Delegate ke appropriate components
    fun updateCurrentTab(tab: Int) {
        stateManager.updateCurrentTab(tab)
        validator.clearAllErrors()
    }

    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyData(checked)
        if (checked) {
            loadUserData()
            loadAgama()
        } else {
            stateManager.clearUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            dataLoader.loadUserData().onFailure { exception ->
                _domisiliEvent.emit(UserDataLoadError(exception.message ?: "Unknown error"))
            }
        }
    }

    fun loadAgama() {
        viewModelScope.launch {
            dataLoader.loadAgama().onFailure { exception ->
                _domisiliEvent.emit(AgamaLoadError(exception.message ?: "Unknown error"))
            }
        }
    }

    // Field update functions - Delegate to state manager and clear validation errors
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
        stateManager.updateTanggalLahir(dateFormatterToApiFormat(value))
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

    fun updateKeperluan(value: String) {
        stateManager.updateKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Pendatang specific field updates
    fun updateKewarganegaraan(value: String) {
        stateManager.updateKewarganegaraan(value)
        validator.clearFieldError("kewarganegaraan")
    }

    fun updateAlamatSesuaiKTP(value: String) {
        stateManager.updateAlamatSesuaiKTP(value)
        validator.clearFieldError("alamat_identitas")
    }

    fun updateAlamatTinggalSekarang(value: String) {
        stateManager.updateAlamatTinggalSekarang(value)
        validator.clearFieldError("alamat_tinggal_sekarang")
    }

    fun updateJumlahPengikut(value: String) {
        stateManager.updateJumlahPengikut(value)
        validator.clearFieldError("jumlah_pengikut")
    }


    fun showPreview() {
        val isValid = validator.validateAllFields()
        if (!isValid) {
            viewModelScope.launch {
                _domisiliEvent.emit(ValidationError)
            }
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()

    fun showConfirmationDialog() {
        val isValid = validator.validateAllFields()
        if (isValid) {
            stateManager.showConfirmationDialog()
        } else {
            viewModelScope.launch {
                _domisiliEvent.emit(ValidationError)
            }
        }
    }

    fun dismissConfirmationDialog() = stateManager.dismissConfirmationDialog()

    fun confirmSubmit() {
        stateManager.dismissConfirmationDialog()
        submitForm()
    }

    private fun submitForm() {
        viewModelScope.launch {
            formSubmitter.submitForm().fold(
                onSuccess = {
                    _domisiliEvent.emit(SubmitSuccess)
                },
                onFailure = { exception ->
                    _domisiliEvent.emit(SubmitError(exception.message ?: "Unknown error"))
                }
            )
        }
    }

    // Validation functions delegation
    fun validateAllFields(): Boolean = validator.validateAllFields()
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utility functions delegation
    fun clearError() = formSubmitter.clearError()
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun getPreviewData(): Map<String, String> = stateManager.getPreviewData()

    // Events - Copy langsung dari kode asli tanpa perubahan
    sealed class SKDomisiliEvent {
        data object SubmitSuccess : SKDomisiliEvent()
        data class SubmitError(val message: String) : SKDomisiliEvent()
        data object ValidationError : SKDomisiliEvent()
        data class UserDataLoadError(val message: String) : SKDomisiliEvent()
        data class AgamaLoadError(val message: String) : SKDomisiliEvent()
    }
}