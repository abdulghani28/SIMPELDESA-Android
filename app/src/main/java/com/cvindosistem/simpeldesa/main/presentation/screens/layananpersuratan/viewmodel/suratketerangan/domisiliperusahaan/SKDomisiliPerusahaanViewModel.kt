package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.domain.usecases.BidangUsahaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliPerusahaanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.JenisUsahaUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SKDomisiliPerusahaanViewModel(
    createSuratDomisiliPerusahaanUseCase: CreateSuratDomisiliPerusahaanUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    getBidangUsahaUseCase: BidangUsahaUseCase,
    getJenisUsahaUseCase: JenisUsahaUseCase,
    getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // ===== COMPOSITION COMPONENTS =====
    private val stateManager = SKDomisiliPerusahaanStateManager()
    private val validator = SKDomisiliPerusahaanValidator(stateManager)
    private val dataLoader = SKDomisiliPerusahaanDataLoader(
        getBidangUsahaUseCase, getJenisUsahaUseCase, getAgamaUseCase,
        getUserInfoUseCase, stateManager, validator
    )
    private val formSubmitter = SKDomisiliPerusahaanFormSubmitter(
        createSuratDomisiliPerusahaanUseCase, stateManager
    )

    // ===== PUBLIC INTERFACE - Delegate ke komponen yang sesuai =====

    // UI State
    private val _uiState = MutableStateFlow(SKDomisiliPerusahaanUiState())
    val uiState = _uiState.asStateFlow()

    // Dropdown data - delegate ke dataLoader
    val bidangUsahaList get() = dataLoader.bidangUsahaList
    val jenisUsahaList get() = dataLoader.jenisUsahaList
    val agamaList get() = dataLoader.agamaList
    val isLoadingBidangUsaha get() = dataLoader.isLoadingBidangUsaha
    val isLoadingJenisUsaha get() = dataLoader.isLoadingJenisUsaha
    val isLoadingAgama get() = dataLoader.isLoadingAgama

    // Loading & Error states - delegate ke formSubmitter
    val isLoading get() = formSubmitter.isLoading
    val errorMessage get() = formSubmitter.errorMessage

    // State management - delegate ke stateManager
    val currentTab get() = stateManager.currentTab
    val currentStepWargaDesa get() = stateManager.currentStepWargaDesa
    val currentStepPendatang get() = stateManager.currentStepPendatang
    val useMyDataChecked get() = stateManager.useMyDataChecked
    val isLoadingUserData get() = dataLoader.isLoadingUserData
    val showConfirmationDialog get() = stateManager.showConfirmationDialog
    val showPreviewDialog get() = stateManager.showPreviewDialog

    // Validation - delegate ke validator
    val validationErrors = validator.validationErrors

    // Events - combine dari semua komponen
    private val _domisiliEvent = MutableSharedFlow<SKDomisiliPerusahaanEvent>()
    val domisiliEvent = _domisiliEvent.asSharedFlow()

    // ===== FIELD VALUES - Delegate ke stateManager =====
    // ===== WARGA DESA DATA =====
    // Step 1 - Informasi Pelapor Warga
    val wargaNikValue get() = stateManager.wargaNikValue
    val wargaNamaValue get() = stateManager.wargaNamaValue
    val wargaTempatLahirValue get() = stateManager.wargaTempatLahirValue
    val wargaTanggalLahirValue get() = stateManager.wargaTanggalLahirValue
    val wargaSelectedGender get() = stateManager.wargaSelectedGender
    val wargaSelectedAgama get() = stateManager.wargaSelectedAgama
    val wargaAlamatValue get() = stateManager.wargaAlamatValue
    val wargaPekerjaanValue get() = stateManager.wargaPekerjaanValue

    // Step 2 - Informasi Perusahaan Warga
    val wargaNamaPerusahaanValue get() = stateManager.wargaNamaPerusahaanValue
    val wargaJenisUsahaValue get() = stateManager.wargaJenisUsahaValue
    val wargaBidangUsahaValue get() = stateManager.wargaBidangUsahaValue
    val wargaNomorAktaValue get() = stateManager.wargaNomorAktaValue
    val wargaNibValue get() = stateManager.wargaNibValue
    val wargaStatusKepemilikanBangunanValue get() = stateManager.wargaStatusKepemilikanBangunanValue
    val wargaJumlahKaryawanValue get() = stateManager.wargaJumlahKaryawanValue
    val wargaAlamatPerusahaanValue get() = stateManager.wargaAlamatPerusahaanValue

    // Step 3 - Informasi Pelengkap Warga
    val wargaKeperluanValue get() = stateManager.wargaKeperluanValue

    // ===== PENDATANG DATA =====
// Step 1 - Informasi Pelapor Pendatang
    val pendatangNikValue get() = stateManager.pendatangNikValue
    val pendatangNamaValue get() = stateManager.pendatangNamaValue
    val pendatangTempatLahirValue get() = stateManager.pendatangTempatLahirValue
    val pendatangTanggalLahirValue get() = stateManager.pendatangTanggalLahirValue
    val pendatangSelectedGender get() = stateManager.pendatangSelectedGender
    val pendatangAlamatValue get() = stateManager.pendatangAlamatValue
    val pendatangPekerjaanValue get() = stateManager.pendatangPekerjaanValue

    // Step 2 - Informasi Perusahaan Pendatang
    val pendatangNamaPerusahaanValue get() = stateManager.pendatangNamaPerusahaanValue
    val pendatangJenisUsahaValue get() = stateManager.pendatangJenisUsahaValue
    val pendatangBidangUsahaValue get() = stateManager.pendatangBidangUsahaValue
    val pendatangNomorAktaValue get() = stateManager.pendatangNomorAktaValue
    val pendatangPeruntukanBangunanValue get() = stateManager.pendatangPeruntukanBangunanValue
    val pendatangLuasTanahValue get() = stateManager.pendatangLuasTanahValue
    val pendatangLuasBangunanValue get() = stateManager.pendatangLuasBangunanValue
    val pendatangStatusKepemilikanBangunanValue get() = stateManager.pendatangStatusKepemilikanBangunanValue
    val pendatangJumlahKaryawanValue get() = stateManager.pendatangJumlahKaryawanValue
    val pendatangAlamatPerusahaanValue get() = stateManager.pendatangAlamatPerusahaanValue
    val pendatangNpwpValue get() = stateManager.pendatangNpwpValue

    // Step 3 - Informasi Pelengkap Pendatang
    val pendatangKeperluanValue get() = stateManager.pendatangKeperluanValue

    init {
        loadDropdownData()
        observeEvents()
    }

    private fun loadDropdownData() {
        viewModelScope.launch {
            dataLoader.loadAllDropdownData()
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            // Observe data loader events
            dataLoader.dataEvents.collect { event ->
                when (event) {
                    is SKDomisiliPerusahaanDataLoader.DataLoaderEvent.BidangUsahaLoadError -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.BidangUsahaLoadError(event.message))
                    }
                    is SKDomisiliPerusahaanDataLoader.DataLoaderEvent.AgamaLoadError -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.AgamaLoadError(event.message))
                    }
                    is SKDomisiliPerusahaanDataLoader.DataLoaderEvent.JenisUsahaLoadError -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.JenisUsahaLoadError(event.message))
                    }
                    is SKDomisiliPerusahaanDataLoader.DataLoaderEvent.UserDataLoadError -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.UserDataLoadError(event.message))
                    }
                }
            }
        }

        viewModelScope.launch {
            // Observe form submitter events
            formSubmitter.submitEvents.collect { event ->
                when (event) {
                    is SKDomisiliPerusahaanFormSubmitter.SubmitEvent.Success -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.SubmitSuccess)
                    }
                    is SKDomisiliPerusahaanFormSubmitter.SubmitEvent.Error -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.SubmitError(event.message))
                    }
                }
            }
        }
    }

    // ===== PUBLIC METHODS - Delegate ke komponen yang sesuai =====
    // Tab management
    fun updateCurrentTab(tab: Int) {
        stateManager.updateCurrentTab(tab)
        validator.clearAllErrors()
    }

    // Step management
    fun nextStep() {
        val currentStep = stateManager.getCurrentStep()
        val isValid = when (currentStep) {
            1 -> validator.validateStep1()
            2 -> validator.validateStep2()
            3 -> validator.validateStep3()
            else -> false
        }

        if (isValid) {
            if (currentStep < 3) {
                stateManager.nextStep()
                viewModelScope.launch {
                    _domisiliEvent.emit(SKDomisiliPerusahaanEvent.StepChanged(stateManager.getCurrentStep()))
                }
            } else {
                stateManager.showConfirmation()
            }
        } else {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
            }
        }
    }

    fun previousStep() {
        if (stateManager.getCurrentStep() > 1) {
            stateManager.previousStep()
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.StepChanged(stateManager.getCurrentStep()))
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
            stateManager.clearWargaUserData()
        }
    }

    // ===== WARGA DESA FIELD UPDATES =====
// Step 1 - Informasi Pelapor Warga
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
        stateManager.updateWargaTanggalLahir(dateFormatterToApiFormat(value))
        validator.clearFieldError("tanggal_lahir")
    }

    fun updateWargaGender(value: String) {
        stateManager.updateWargaGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updateWargaAgama(value: String) {
        stateManager.updateWargaAgama(value)
        validator.clearFieldError("agama_id")
    }

    fun updateWargaAlamat(value: String) {
        stateManager.updateWargaAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updateWargaPekerjaan(value: String) {
        stateManager.updateWargaPekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    // Step 2 - Informasi Perusahaan Warga
    fun updateWargaNamaPerusahaan(value: String) {
        stateManager.updateWargaNamaPerusahaan(value)
        validator.clearFieldError("nama_perusahaan")
    }

    fun updateWargaJenisUsaha(value: String) {
        stateManager.updateWargaJenisUsaha(value)
        validator.clearFieldError("jenis_usaha_id")
    }

    fun updateWargaBidangUsaha(value: String) {
        stateManager.updateWargaBidangUsaha(value)
        validator.clearFieldError("bidang_usaha_id")
    }

    fun updateWargaNomorAkta(value: String) {
        stateManager.updateWargaNomorAkta(value)
        validator.clearFieldError("nomor_akta_pendirian")
    }

    fun updateWargaNib(value: String) {
        stateManager.updateWargaNib(value)
        validator.clearFieldError("nib")
    }

    fun updateWargaStatusKepemilikanBangunan(value: String) {
        stateManager.updateWargaStatusKepemilikanBangunan(value)
        validator.clearFieldError("status_kepemilikan_bangunan")
    }

    fun updateWargaJumlahKaryawan(value: String) {
        stateManager.updateWargaJumlahKaryawan(value)
        validator.clearFieldError("jumlah_karyawan")
    }

    fun updateWargaAlamatPerusahaan(value: String) {
        stateManager.updateWargaAlamatPerusahaan(value)
        validator.clearFieldError("alamat_perusahaan")
    }

    // Step 3 - Informasi Pelengkap Warga
    fun updateWargaKeperluan(value: String) {
        stateManager.updateWargaKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // ===== PENDATANG FIELD UPDATES =====
// Step 1 - Informasi Pelapor Pendatang
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
        stateManager.updatePendatangTanggalLahir(dateFormatterToApiFormat(value))
        validator.clearFieldError("tanggal_lahir")
    }

    fun updatePendatangGender(value: String) {
        stateManager.updatePendatangGender(value)
        validator.clearFieldError("jenis_kelamin")
    }

    fun updatePendatangAlamat(value: String) {
        stateManager.updatePendatangAlamat(value)
        validator.clearFieldError("alamat")
    }

    fun updatePendatangPekerjaan(value: String) {
        stateManager.updatePendatangPekerjaan(value)
        validator.clearFieldError("pekerjaan")
    }

    // Step 2 - Informasi Perusahaan Pendatang
    fun updatePendatangNamaPerusahaan(value: String) {
        stateManager.updatePendatangNamaPerusahaan(value)
        validator.clearFieldError("nama_perusahaan")
    }

    fun updatePendatangJenisUsaha(value: String) {
        stateManager.updatePendatangJenisUsaha(value)
        validator.clearFieldError("jenis_usaha_id")
    }

    fun updatePendatangBidangUsaha(value: String) {
        stateManager.updatePendatangBidangUsaha(value)
        validator.clearFieldError("bidang_usaha_id")
    }

    fun updatePendatangNomorAkta(value: String) {
        stateManager.updatePendatangNomorAkta(value)
        validator.clearFieldError("nomor_akta_pendirian")
    }

    fun updatePendatangPeruntukanBangunan(value: String) {
        stateManager.updatePendatangPeruntukanBangunan(value)
        validator.clearFieldError("peruntukan_bangunan")
    }

    fun updatePendatangLuasTanah(value: Int) {
        stateManager.updatePendatangLuasTanah(value)
        validator.clearFieldError("luas_tanah")
    }

    fun updatePendatangLuasBangunan(value: Int) {
        stateManager.updatePendatangLuasBangunan(value)
        validator.clearFieldError("luas_bangunan")
    }

    fun updatePendatangStatusKepemilikanBangunan(value: String) {
        stateManager.updatePendatangStatusKepemilikanBangunan(value)
        validator.clearFieldError("status_kepemilikan_bangunan")
    }

    fun updatePendatangJumlahKaryawan(value: String) {
        stateManager.updatePendatangJumlahKaryawan(value)
        validator.clearFieldError("jumlah_karyawan")
    }

    fun updatePendatangAlamatPerusahaan(value: String) {
        stateManager.updatePendatangAlamatPerusahaan(value)
        validator.clearFieldError("alamat_perusahaan")
    }

    fun updatePendatangNpwp(value: String) {
        stateManager.updatePendatangNpwp(value)
        validator.clearFieldError("npwp")
    }

    // Step 3 - Informasi Pelengkap Pendatang
    fun updatePendatangKeperluan(value: String) {
        stateManager.updatePendatangKeperluan(value)
        validator.clearFieldError("keperluan")
    }

    // Validation methods - delegate ke validator
    fun getFieldError(fieldName: String): String? = validator.getFieldError(fieldName)
    fun hasFieldError(fieldName: String): Boolean = validator.hasFieldError(fieldName)

    // Preview and confirmation
    fun showPreview() {
        if (validator.validateAllSteps()) {
            stateManager.showPreview()
        } else {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
            }
        }
    }

    fun dismissPreview() = stateManager.dismissPreview()
    fun showConfirmationDialog() = stateManager.showConfirmation()
    fun dismissConfirmationDialog() = stateManager.dismissConfirmation()

    fun confirmSubmit() {
        stateManager.dismissConfirmation()
        viewModelScope.launch {
            formSubmitter.submitForm()
        }
    }

    // Utility methods - delegate ke komponen yang sesuai
    fun clearError() = formSubmitter.clearError()
    fun hasFormData(): Boolean = stateManager.hasFormData()
    fun getCurrentStepForUI(): Int = stateManager.getCurrentStep()

    fun getPreviewData(): Map<String, String> {
        return mapOf(
            "NIK" to stateManager.getCurrentNik(),
            "Nama" to stateManager.getCurrentNama(),
            "Tempat Lahir" to stateManager.getCurrentTempatLahir(),
            "Tanggal Lahir" to stateManager.getCurrentTanggalLahir(),
            "Jenis Kelamin" to stateManager.getCurrentGender(),
            "Agama" to stateManager.getCurrentAgama(),
            "Alamat" to stateManager.getCurrentAlamat(),
            "Pekerjaan" to stateManager.getCurrentPekerjaan(),
            "Nama Perusahaan" to stateManager.getCurrentNamaPerusahaan(),
            "Jenis Usaha" to stateManager.getCurrentJenisUsaha(),
            "Bidang Usaha" to stateManager.getCurrentBidangUsaha(),
            "Nomor Akta Pendirian" to stateManager.getCurrentNomorAkta(),
            "Status Kepemilikan Bangunan" to stateManager.getCurrentStatusKepemilikanBangunan(),
            "Jumlah Karyawan" to stateManager.getCurrentJumlahKaryawan(),
            "Alamat Perusahaan" to stateManager.getCurrentAlamatPerusahaan(),
            "NIB" to stateManager.getWargaNib(),
            "Keperluan Warga" to stateManager.getWargaKeperluan(),
            "Keperluan Pendatang" to stateManager.getPendatangKeperluan(),
            "Peruntukan Bangunan" to stateManager.getPendatangPeruntukanBangunan(),
            "NPWP" to stateManager.getWargaNpwp(),
            "NPWP" to stateManager.getPendatangNpwp()
        )
    }

    // Event class - sama seperti di kode asli
    sealed class SKDomisiliPerusahaanEvent {
        data class StepChanged(val step: Int) : SKDomisiliPerusahaanEvent()
        data object SubmitSuccess : SKDomisiliPerusahaanEvent()
        data class SubmitError(val message: String) : SKDomisiliPerusahaanEvent()
        data object ValidationError : SKDomisiliPerusahaanEvent()
        data class UserDataLoadError(val message: String) : SKDomisiliPerusahaanEvent()
        data class BidangUsahaLoadError(val message: String) : SKDomisiliPerusahaanEvent()
        data class JenisUsahaLoadError(val message: String) : SKDomisiliPerusahaanEvent()
        data class AgamaLoadError(val message: String) : SKDomisiliPerusahaanEvent()
    }
}