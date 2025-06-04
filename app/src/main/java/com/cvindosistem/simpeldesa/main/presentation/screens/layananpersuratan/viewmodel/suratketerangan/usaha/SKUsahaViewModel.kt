package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SKUsahaViewModel(
    createSuratUsahaUseCase: CreateSuratUsahaUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getBidangUsahaUseCase: BidangUsahaUseCase,
    getJenisUsahaUseCase: JenisUsahaUseCase
) : ViewModel() {

    // Composition components
    private val stateManager = SKUsahaStateManager()
    private val validator = SKUsahaFormValidator(stateManager)
    private val dataLoader = SKUsahaDataLoader(
        getBidangUsahaUseCase, getJenisUsahaUseCase, getUserInfoUseCase, stateManager, validator
    )
    private val formSubmitter = SKUsahaFormSubmitter(createSuratUsahaUseCase, stateManager)

    // Public exposed properties (delegate to components)
    val bidangUsahaList get() = dataLoader.bidangUsahaList
    val jenisUsahaList get() = dataLoader.jenisUsahaList
    val isLoadingBidangUsaha get() = dataLoader.isLoadingBidangUsaha
    val isLoadingJenisUsaha get() = dataLoader.isLoadingJenisUsaha
    val isLoadingUserData get() = dataLoader.isLoadingUserData
    val isLoading get() = formSubmitter.isLoading
    val errorMessage get() = formSubmitter.errorMessage
    val validationErrors = validator.validationErrors

    // Expose state manager properties
    val currentTab get() = stateManager.currentTab
    val currentStepWargaDesa get() = stateManager.currentStepWargaDesa
    val currentStepPendatang get() = stateManager.currentStepPendatang
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog

    // WARGA
    val wargaNikValue get() = stateManager.wargaNikValue
    val wargaNamaValue get() = stateManager.wargaNamaValue
    val wargaTempatLahirValue get() = stateManager.wargaTempatLahirValue
    val wargaTanggalLahirValue get() = stateManager.wargaTanggalLahirValue
    val wargaSelectedGender get() = stateManager.wargaSelectedGender
    val wargaPekerjaanValue get() = stateManager.wargaPekerjaanValue
    val wargaAlamatValue get() = stateManager.wargaAlamatValue

    val wargaNamaUsahaValue get() = stateManager.wargaNamaUsahaValue
    val wargaBidangUsahaValue get() = stateManager.wargaBidangUsahaValue
    val wargaJenisUsahaValue get() = stateManager.wargaJenisUsahaValue
    val wargaAlamatUsahaValue get() = stateManager.wargaAlamatUsahaValue
    val wargaNpwpValue get() = stateManager.wargaNpwpValue
    val wargaKeperluanValue get() = stateManager.wargaKeperluanValue

    // PENDATANG
    val pendatangNikValue get() = stateManager.pendatangNikValue
    val pendatangNamaValue get() = stateManager.pendatangNamaValue
    val pendatangTempatLahirValue get() = stateManager.pendatangTempatLahirValue
    val pendatangTanggalLahirValue get() = stateManager.pendatangTanggalLahirValue
    val pendatangSelectedGender get() = stateManager.pendatangSelectedGender
    val pendatangPekerjaanValue get() = stateManager.pendatangPekerjaanValue
    val pendatangAlamatValue get() = stateManager.pendatangAlamatValue

    val pendatangNamaUsahaValue get() = stateManager.pendatangNamaUsahaValue
    val pendatangBidangUsahaValue get() = stateManager.pendatangBidangUsahaValue
    val pendatangJenisUsahaValue get() = stateManager.pendatangJenisUsahaValue
    val pendatangAlamatUsahaValue get() = stateManager.pendatangAlamatUsahaValue
    val pendatangNpwpValue get() = stateManager.pendatangNpwpValue
    val pendatangKeperluanValue get() = stateManager.pendatangKeperluanValue

    // Unified events
    private val _usahaEvent = MutableSharedFlow<SKUsahaEvent>()
    val usahaEvent = _usahaEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            dataLoader.loadDropdownData()
        }

        // Combine events from all components
        viewModelScope.launch {
            dataLoader.events.collect { event ->
                when (event) {
                    is SKUsahaDataLoader.DataLoaderEvent.UserDataLoadError ->
                        _usahaEvent.emit(SKUsahaEvent.UserDataLoadError(event.message))
                    is SKUsahaDataLoader.DataLoaderEvent.BidangUsahaLoadError ->
                        _usahaEvent.emit(SKUsahaEvent.BidangUsahaLoadError(event.message))
                    is SKUsahaDataLoader.DataLoaderEvent.JenisUsahaLoadError ->
                        _usahaEvent.emit(SKUsahaEvent.JenisUsahaLoadError(event.message))
                }
            }
        }

        viewModelScope.launch {
            formSubmitter.events.collect { event ->
                when (event) {
                    is SKUsahaFormSubmitter.SubmitEvent.Success ->
                        _usahaEvent.emit(SKUsahaEvent.SubmitSuccess)
                    is SKUsahaFormSubmitter.SubmitEvent.Error ->
                        _usahaEvent.emit(SKUsahaEvent.SubmitError(event.message))
                }
            }
        }
    }

    // ===== PUBLIC INTERFACE (tidak berubah dari aslinya) =====
    // Tab management
    fun updateCurrentTab(tab: Int) {
        stateManager.updateCurrentTab(tab)
        validator.clearAllErrors()
    }

    // Step management
    fun nextStep() {
        val currentStep = stateManager.getCurrentStep()
        val isValid = when (currentStep) {
            1 -> validateStep1WithEvent()
            2 -> validateStep2WithEvent()
            3 -> validateStep3WithEvent()
            else -> false
        }

        if (isValid) {
            if (currentStep < 3) {
                stateManager.nextStep()
                viewModelScope.launch {
                    _usahaEvent.emit(SKUsahaEvent.StepChanged(stateManager.getCurrentStep()))
                }
            } else {
                stateManager.showConfirmationDialog()
            }
        }
    }

    fun previousStep() {
        if (stateManager.getCurrentStep() > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.StepChanged(stateManager.getCurrentStep()))
            }
        }
    }

    // Use My Data functionality
    fun updateUseMyData(checked: Boolean) {
        stateManager.updateUseMyData(checked)
        if (checked) {
            viewModelScope.launch {
                dataLoader.loadUserData()
            }
        } else {
            // Clear warga user data - implement in stateManager
            stateManager.clearWargaUserData()
        }
    }

    // Field update methods - delegate to stateManager
    fun updateWargaNik(value: String) {
        stateManager.updateWargaNik(value)
        validator.clearFieldError("nik")
    }

    fun updateWargaNama(value: String) {
        stateManager.updateWargaNama(value)
        validator.clearFieldError("nama")
    }

    fun updateWargaTempatLahir(value: String) {
        stateManager.updateWargaTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updateWargaTanggalLahir(value: String) {
        stateManager.updateWargaTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateWargaGender(value: String) {
        stateManager.updateWargaGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateWargaPekerjaan(value: String) {
        stateManager.updateWargaPekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updateWargaAlamat(value: String) {
        stateManager.updateWargaAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateWargaNamaUsaha(value: String) {
        stateManager.updateWargaNamaUsaha(value)
        validator.clearFieldError("nama_usaha")
    }

    fun updateWargaBidangUsaha(value: String) {
        stateManager.updateWargaBidangUsaha(value)
        validator.clearFieldError("bidang_usaha_id")
    }

    fun updateWargaJenisUsaha(value: String) {
        stateManager.updateWargaJenisUsaha(value)
        validator.clearFieldError("jenis_usaha_id")
    }

    fun updateWargaAlamatUsaha(value: String) {
        stateManager.updateWargaAlamatUsaha(value)
        validator.clearFieldError("alamat_usaha")
    }

    fun updateWargaNpwp(value: String) {
        stateManager.updateWargaNpwp(value)
        validator.clearFieldError("npwp")
    }

    fun updateWargaKeperluan(value: String) {
        stateManager.updateWargaKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    fun updatePendatangNik(value: String) {
        stateManager.updatePendatangNik(value)
        validator.clearFieldError("nik")
    }

    fun updatePendatangNama(value: String) {
        stateManager.updatePendatangNama(value)
        validator.clearFieldError("nama")
    }

    fun updatePendatangTempatLahir(value: String) {
        stateManager.updatePendatangTempatLahir(value)
        validator.clearFieldError("tempat_lahir")
    }

    fun updatePendatangTanggalLahir(value: String) {
        stateManager.updatePendatangTanggalLahir(value)
        validator.clearFieldError("tanggal_lahir")
    }

    fun updatePendatangGender(value: String) {
        stateManager.updatePendatangGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePendatangPekerjaan(value: String) {
        stateManager.updatePendatangPekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    fun updatePendatangAlamat(value: String) {
        stateManager.updatePendatangAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updatePendatangNamaUsaha(value: String) {
        stateManager.updatePendatangNamaUsaha(value)
        validator.clearFieldError("nama_usaha")
    }

    fun updatePendatangBidangUsaha(value: String) {
        stateManager.updatePendatangBidangUsaha(value)
        validator.clearFieldError("bidang_usaha_id")
    }

    fun updatePendatangJenisUsaha(value: String) {
        stateManager.updatePendatangJenisUsaha(value)
        validator.clearFieldError("jenis_usaha_id")
    }

    fun updatePendatangAlamatUsaha(value: String) {
        stateManager.updatePendatangAlamatUsaha(value)
        validator.clearFieldError("alamat_usaha")
    }

    fun updatePendatangNpwp(value: String) {
        stateManager.updatePendatangNpwp(value)
        validator.clearFieldError("npwp")
    }

    fun updatePendatangKeperluan(value: String) {
        stateManager.updatePendatangKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Getter methods - delegate to stateManager
    fun getCurrentNik(): String = stateManager.getCurrentNik()
    fun getCurrentNama(): String = stateManager.getCurrentNama()
    fun getCurrentTempatLahir(): String = stateManager.getCurrentTempatLahir()
    fun getCurrentTanggalLahir(): String = stateManager.getCurrentTanggalLahir()
    fun getCurrentGender(): String = stateManager.getCurrentGender()
    fun getCurrentPekerjaan(): String = stateManager.getCurrentPekerjaan()
    fun getCurrentAlamat(): String = stateManager.getCurrentAlamat()
    fun getCurrentNamaUsaha(): String = stateManager.getCurrentNamaUsaha()
    fun getCurrentBidangUsaha(): String = stateManager.getCurrentBidangUsaha()
    fun getCurrentJenisUsaha(): String = stateManager.getCurrentJenisUsaha()
    fun getCurrentAlamatUsaha(): String = stateManager.getCurrentAlamatUsaha()
    fun getCurrentNpwp(): String = stateManager.getCurrentNpwp()
    fun getCurrentKeperluan(): String = stateManager.getCurrentKeperluan()

    // Validation with events
    private fun validateStep1WithEvent(): Boolean {
        val isValid = validator.validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validator.validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validator.validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _usahaEvent.emit(SKUsahaEvent.ValidationError)
            }
        }
        return isValid
    }

    // Dialog management
    fun showPreview() {
        viewModelScope.launch {
            _usahaEvent.emit(SKUsahaEvent.ValidationError)
        }
        stateManager.showPreview()
    }

    fun dismissPreview() = stateManager.dismissPreview()
    fun showConfirmationDialog() = stateManager.showConfirmationDialog()
    fun dismissConfirmationDialog() = stateManager.dismissConfirmationDialog()

    fun confirmSubmit() {
        stateManager.dismissConfirmationDialog()
        viewModelScope.launch {
            formSubmitter.submitForm()
        }
    }

    // Validation helper methods - delegate to validator
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Utility methods - delegate to stateManager
    fun clearError() = formSubmitter.clearError()
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun getCurrentStepForUI(): Int = stateManager.getCurrentStep()
    fun getPreviewData(): Map<String, String> = stateManager.getPreviewData()

    // Events - tetap sama
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