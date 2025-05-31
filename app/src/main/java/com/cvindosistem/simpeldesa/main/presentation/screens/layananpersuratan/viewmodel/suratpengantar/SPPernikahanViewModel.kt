package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar

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
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratPernikahanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernikahanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SPPernikahanViewModel(
    private val createSuratPernikahanUseCase: CreateSuratPernikahanUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SPPernikahanUiState())
    val uiState = _uiState.asStateFlow()

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Current step
    var currentStep by mutableIntStateOf(1)
        private set

    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingAgama by mutableStateOf(false)
    var agamaErrorMessage by mutableStateOf<String?>(null)

    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
    var isLoadingStatusKawin by mutableStateOf(false)
    var statusKawinErrorMessage by mutableStateOf<String?>(null)


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
    private val _pernikahanEvent = MutableSharedFlow<SPPernikahanEvent>()
    val pernikahanEvent = _pernikahanEvent.asSharedFlow()

    // Step 1 - Informasi Calon Suami
    var nikSuamiValue by mutableStateOf("")
        private set
    var namaSuamiValue by mutableStateOf("")
        private set
    var tempatLahirSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirSuamiValue by mutableStateOf("")
        private set
    var pekerjaanSuamiValue by mutableStateOf("")
        private set
    var alamatSuamiValue by mutableStateOf("")
        private set
    var agamaSuamiIdValue by mutableStateOf("")
        private set
    var kewarganegaraanSuamiValue by mutableStateOf("")
        private set
    var jumlahIstriValue by mutableIntStateOf(0)
        private set
    var statusKawinSuamiIdValue by mutableStateOf("")
        private set
    var namaIstriSebelumnyaValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Orang Tua Suami
    var agamaAyahSuamiIdValue by mutableStateOf("")
        private set
    var alamatAyahSuamiValue by mutableStateOf("")
        private set
    var kewarganegaraanAyahSuamiValue by mutableStateOf("")
        private set
    var namaAyahSuamiValue by mutableStateOf("")
        private set
    var nikAyahSuamiValue by mutableStateOf("")
        private set
    var pekerjaanAyahSuamiValue by mutableStateOf("")
        private set
    var tempatLahirAyahSuamiValue by mutableStateOf("")
        private set
    var tanggalLahiAyahSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirAyahSuamiValue by mutableStateOf("")
        private set
    var agamaIbuSuamiIdValue by mutableStateOf("")
        private set
    var alamatIbuSuamiValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuSuamiValue by mutableStateOf("")
        private set
    var namaIbuSuamiValue by mutableStateOf("")
        private set
    var nikIbuSuamiValue by mutableStateOf("")
        private set
    var pekerjaanIbuSuamiValue by mutableStateOf("")
        private set
    var tanggalLahiIbuSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirIbuSuamiValue by mutableStateOf("")
        private set
    var tempatLahirIbuSuamiValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Calon Istri
    var agamaIstriIdValue by mutableStateOf("")
        private set
    var alamatIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanIstriValue by mutableStateOf("")
        private set
    var namaIstriValue by mutableStateOf("")
        private set
    var nikIstriValue by mutableStateOf("")
        private set
    var pekerjaanIstriValue by mutableStateOf("")
        private set
    var statusKawinIstriIdValue by mutableStateOf("")
        private set
    var tanggalLahirIstriValue by mutableStateOf("")
        private set
    var tempatLahirIstriValue by mutableStateOf("")
        private set
    var namaSuamiSebelumnyaValue by mutableStateOf("")
        private set

    // Step 4 - Informasi Orang Tua Calon Istri
    var agamaAyahIstriIdValue by mutableStateOf("")
        private set
    var agamaIbuIstriIdValue by mutableStateOf("")
        private set
    var alamatAyahIstriValue by mutableStateOf("")
        private set
    var alamatIbuIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanAyahIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuIstriValue by mutableStateOf("")
        private set
    var namaAyahIstriValue by mutableStateOf("")
        private set
    var namaIbuIstriValue by mutableStateOf("")
        private set
    var nikAyahIstriValue by mutableStateOf("")
        private set
    var nikIbuIstriValue by mutableStateOf("")
        private set
    var pekerjaanAyahIstriValue by mutableStateOf("")
        private set
    var pekerjaanIbuIstriValue by mutableStateOf("")
        private set
    var tanggalLahiAyahIstriValue by mutableStateOf("")
        private set
    var tanggalLahiIbuIstriValue by mutableStateOf("")
        private set
    var tanggalLahirAyahIstriValue by mutableStateOf("")
        private set
    var tanggalLahirIbuIstriValue by mutableStateOf("")
        private set
    var tempatLahirAyahIstriValue by mutableStateOf("")
        private set
    var tempatLahirIbuIstriValue by mutableStateOf("")
        private set

    // Step 5 - Informasi Pernikahan
    var jamPernikahanValue by mutableStateOf("")
        private set
    var tempatPernikahanValue by mutableStateOf("")
        private set
    var hariPernikahanValue by mutableStateOf("")
        private set
    var tanggalPernikahanValue by mutableStateOf("")
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
            loadStatusKawin()
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
                        nikSuamiValue = userData.nik
                        namaSuamiValue = userData.nama_warga
                        tempatLahirSuamiValue = userData.tempat_lahir
                        tanggalLahirSuamiValue = dateFormatterToApiFormat(userData.tanggal_lahir)
                        agamaSuamiIdValue = userData.agama_id
                        pekerjaanSuamiValue = userData.pekerjaan
                        alamatSuamiValue = userData.alamat
                        kewarganegaraanSuamiValue = userData.kewarganegaraan
                        statusKawinSuamiIdValue = userData.status_kawin_id

                        // Clear any existing validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik_suami", "nama_suami", "tempat_lahir_suami", "tanggal_lahir_suami",
                            "agama_suami", "pekerjaan_suami", "alamat_suami", "kewarganegaraan_suami",
                            "status_kawin_suami"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _pernikahanEvent.emit(SPPernikahanEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _pernikahanEvent.emit(SPPernikahanEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearUserData() {
        // Step 1 - Calon Suami
        nikSuamiValue = ""
        namaSuamiValue = ""
        tempatLahirSuamiValue = ""
        tanggalLahirSuamiValue = ""
        pekerjaanSuamiValue = ""
        alamatSuamiValue = ""
        agamaSuamiIdValue = ""
        kewarganegaraanSuamiValue = ""
        statusKawinSuamiIdValue = ""
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
                        _pernikahanEvent.emit(SPPernikahanEvent.AgamaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                agamaErrorMessage = e.message ?: "Gagal memuat data agama"
                _pernikahanEvent.emit(SPPernikahanEvent.AgamaLoadError(agamaErrorMessage!!))
            } finally {
                isLoadingAgama = false
            }
        }
    }

    fun loadStatusKawin() {
        viewModelScope.launch {
            isLoadingStatusKawin = true
            statusKawinErrorMessage = null
            try {
                when (val result = getStatusKawinUseCase()) {
                    is StatusKawinResult.Success -> {
                        statusKawinList = result.data.data // <- ini penting!
                    }
                    is StatusKawinResult.Error -> {
                        statusKawinErrorMessage = result.message
                        _pernikahanEvent.emit(SPPernikahanEvent.StatusKawinLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                statusKawinErrorMessage = e.message ?: "Gagal memuat data statusKawin"
                _pernikahanEvent.emit(SPPernikahanEvent.StatusKawinLoadError(statusKawinErrorMessage!!))
            } finally {
                isLoadingStatusKawin = false
            }
        }
    }

    // Step 1 - Calon Suami
    fun updateNikSuami(value: String) {
        nikSuamiValue = value
        clearFieldError("nik_suami")
    }

    fun updateNamaSuami(value: String) {
        namaSuamiValue = value
        clearFieldError("nama_suami")
    }

    fun updateTempatLahirSuami(value: String) {
        tempatLahirSuamiValue = value
        clearFieldError("tempat_lahir_suami")
    }

    fun updateTanggalLahirSuami(value: String) {
        tanggalLahirSuamiValue = value
        clearFieldError("tanggal_lahir_suami")
    }

    fun updatePekerjaanSuami(value: String) {
        pekerjaanSuamiValue = value
        clearFieldError("pekerjaan_suami")
    }

    fun updateAlamatSuami(value: String) {
        alamatSuamiValue = value
        clearFieldError("alamat_suami")
    }

    fun updateAgamaSuamiId(value: String) {
        agamaSuamiIdValue = value
        clearFieldError("agama_suami_id")
    }

    fun updateKewarganegaraanSuami(value: String) {
        kewarganegaraanSuamiValue = value
        clearFieldError("kewarganegaraan_suami")
    }

    fun updateJumlahIstri(value: Int) {
        jumlahIstriValue = value
        clearFieldError("jumlah_istri")
    }

    fun updateStatusKawinSuamiId(value: String) {
        statusKawinSuamiIdValue = value
        clearFieldError("status_kawin_suami_id")
    }

    fun updateNamaIstriSebelumnya(value: String) {
        namaIstriSebelumnyaValue = value
        clearFieldError("nama_istri_sebelumnya")
    }

    // Step 2 - Orang Tua Suami
    fun updateNikAyahSuami(value: String) {
        nikAyahSuamiValue = value
        clearFieldError("nik_ayah_suami")
    }

    fun updateNamaAyahSuami(value: String) {
        namaAyahSuamiValue = value
        clearFieldError("nama_ayah_suami")
    }

    fun updateTempatLahirAyahSuami(value: String) {
        tempatLahirAyahSuamiValue = value
        clearFieldError("tempat_lahir_ayah_suami")
    }

    fun updateTanggalLahirAyahSuami(value: String) {
        tanggalLahirAyahSuamiValue = value
        clearFieldError("tanggal_lahir_ayah_suami")
    }

    fun updatePekerjaanAyahSuami(value: String) {
        pekerjaanAyahSuamiValue = value
        clearFieldError("pekerjaan_ayah_suami")
    }

    fun updateAlamatAyahSuami(value: String) {
        alamatAyahSuamiValue = value
        clearFieldError("alamat_ayah_suami")
    }

    fun updateAgamaAyahSuamiId(value: String) {
        agamaAyahSuamiIdValue = value
        clearFieldError("agama_ayah_suami_id")
    }

    fun updateKewarganegaraanAyahSuami(value: String) {
        kewarganegaraanAyahSuamiValue = value
        clearFieldError("kewarganegaraan_ayah_suami")
    }

    fun updateNikIbuSuami(value: String) {
        nikIbuSuamiValue = value
        clearFieldError("nik_ibu_suami")
    }

    fun updateNamaIbuSuami(value: String) {
        namaIbuSuamiValue = value
        clearFieldError("nama_ibu_suami")
    }

    fun updateTempatLahirIbuSuami(value: String) {
        tempatLahirIbuSuamiValue = value
        clearFieldError("tempat_lahir_ibu_suami")
    }

    fun updateTanggalLahirIbuSuami(value: String) {
        tanggalLahirIbuSuamiValue = value
        clearFieldError("tanggal_lahir_ibu_suami")
    }

    fun updatePekerjaanIbuSuami(value: String) {
        pekerjaanIbuSuamiValue = value
        clearFieldError("pekerjaan_ibu_suami")
    }

    fun updateAlamatIbuSuami(value: String) {
        alamatIbuSuamiValue = value
        clearFieldError("alamat_ibu_suami")
    }

    fun updateAgamaIbuSuamiId(value: String) {
        agamaIbuSuamiIdValue = value
        clearFieldError("agama_ibu_suami_id")
    }

    fun updateKewarganegaraanIbuSuami(value: String) {
        kewarganegaraanIbuSuamiValue = value
        clearFieldError("kewarganegaraan_ibu_suami")
    }

    // Step 3 - Calon Istri
    fun updateNikIstri(value: String) {
        nikIstriValue = value
        clearFieldError("nik_istri")
    }

    fun updateNamaIstri(value: String) {
        namaIstriValue = value
        clearFieldError("nama_istri")
    }

    fun updateTempatLahirIstri(value: String) {
        tempatLahirIstriValue = value
        clearFieldError("tempat_lahir_istri")
    }

    fun updateTanggalLahirIstri(value: String) {
        tanggalLahirIstriValue = value
        clearFieldError("tanggal_lahir_istri")
    }

    fun updatePekerjaanIstri(value: String) {
        pekerjaanIstriValue = value
        clearFieldError("pekerjaan_istri")
    }

    fun updateAlamatIstri(value: String) {
        alamatIstriValue = value
        clearFieldError("alamat_istri")
    }

    fun updateAgamaIstriId(value: String) {
        agamaIstriIdValue = value
        clearFieldError("agama_istri_id")
    }

    fun updateKewarganegaraanIstri(value: String) {
        kewarganegaraanIstriValue = value
        clearFieldError("kewarganegaraan_istri")
    }

    fun updateStatusKawinIstriId(value: String) {
        statusKawinIstriIdValue = value
        clearFieldError("status_kawin_istri_id")
    }

    fun updateNamaSuamiSebelumnya(value: String) {
        namaSuamiSebelumnyaValue = value
        clearFieldError("nama_suami_sebelumnya")
    }

    // Step 4 - Orang Tua Istri
    fun updateNikAyahIstri(value: String) {
        nikAyahIstriValue = value
        clearFieldError("nik_ayah_istri")
    }

    fun updateNamaAyahIstri(value: String) {
        namaAyahIstriValue = value
        clearFieldError("nama_ayah_istri")
    }

    fun updateTempatLahirAyahIstri(value: String) {
        tempatLahirAyahIstriValue = value
        clearFieldError("tempat_lahir_ayah_istri")
    }

    fun updateTanggalLahirAyahIstri(value: String) {
        tanggalLahirAyahIstriValue = value
        clearFieldError("tanggal_lahir_ayah_istri")
    }

    fun updatePekerjaanAyahIstri(value: String) {
        pekerjaanAyahIstriValue = value
        clearFieldError("pekerjaan_ayah_istri")
    }

    fun updateAlamatAyahIstri(value: String) {
        alamatAyahIstriValue = value
        clearFieldError("alamat_ayah_istri")
    }

    fun updateAgamaAyahIstriId(value: String) {
        agamaAyahIstriIdValue = value
        clearFieldError("agama_ayah_istri_id")
    }

    fun updateKewarganegaraanAyahIstri(value: String) {
        kewarganegaraanAyahIstriValue = value
        clearFieldError("kewarganegaraan_ayah_istri")
    }

    fun updateNikIbuIstri(value: String) {
        nikIbuIstriValue = value
        clearFieldError("nik_ibu_istri")
    }

    fun updateNamaIbuIstri(value: String) {
        namaIbuIstriValue = value
        clearFieldError("nama_ibu_istri")
    }

    fun updateTempatLahirIbuIstri(value: String) {
        tempatLahirIbuIstriValue = value
        clearFieldError("tempat_lahir_ibu_istri")
    }

    fun updateTanggalLahirIbuIstri(value: String) {
        tanggalLahirIbuIstriValue = value
        clearFieldError("tanggal_lahir_ibu_istri")
    }

    fun updatePekerjaanIbuIstri(value: String) {
        pekerjaanIbuIstriValue = value
        clearFieldError("pekerjaan_ibu_istri")
    }

    fun updateAlamatIbuIstri(value: String) {
        alamatIbuIstriValue = value
        clearFieldError("alamat_ibu_istri")
    }

    fun updateAgamaIbuIstriId(value: String) {
        agamaIbuIstriIdValue = value
        clearFieldError("agama_ibu_istri_id")
    }

    fun updateKewarganegaraanIbuIstri(value: String) {
        kewarganegaraanIbuIstriValue = value
        clearFieldError("kewarganegaraan_ibu_istri")
    }

    // Step 5 - Informasi Pernikahan
    fun updateTanggalPernikahan(value: String) {
        tanggalPernikahanValue = value
        clearFieldError("tanggal_pernikahan")
    }

    fun updateHariPernikahan(value: String) {
        hariPernikahanValue = value
        clearFieldError("hari_pernikahan")
    }

    fun updateJamPernikahan(value: String) {
        jamPernikahanValue = value
        clearFieldError("jam_pernikahan")
    }

    fun updateTempatPernikahan(value: String) {
        tempatPernikahanValue = value
        clearFieldError("tempat_pernikahan")
    }

    // Preview dialog functions
    fun showPreview() {
        // Validate all steps before showing preview
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()
        val step3Valid = validateStep3()
        val step4Valid = validateStep4()
        val step5Valid = validateStep5()

        if (!step1Valid || !step2Valid || !step3Valid || !step4Valid || !step5Valid) {
            // Show validation errors but still allow preview
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
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
                        _pernikahanEvent.emit(SPPernikahanEvent.StepChanged(currentStep))
                    }
                }
            }
            2 -> {
                if (validateStep2WithEvent()) {
                    currentStep = 3
                    viewModelScope.launch {
                        _pernikahanEvent.emit(SPPernikahanEvent.StepChanged(currentStep))
                    }
                }
            }
            3 -> {
                if (validateStep3WithEvent()) {
                    currentStep = 4
                    viewModelScope.launch {
                        _pernikahanEvent.emit(SPPernikahanEvent.StepChanged(currentStep))
                    }
                }
            }
            4 -> {
                if (validateStep4WithEvent()) {
                    currentStep = 5
                    viewModelScope.launch {
                        _pernikahanEvent.emit(SPPernikahanEvent.StepChanged(currentStep))
                    }
                }
            }
            5 -> {
                if (validateStep5WithEvent()) {
                    showConfirmationDialog = true
                }
            }
        }
    }

    fun previousStep() {
        if (currentStep > 1) {
            currentStep -= 1
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.StepChanged(currentStep))
            }
        }
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
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
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep4WithEvent(): Boolean {
        val isValid = validateStep4()
        if (!isValid) {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep5WithEvent(): Boolean {
        val isValid = validateStep5()
        if (!isValid) {
            viewModelScope.launch {
                _pernikahanEvent.emit(SPPernikahanEvent.ValidationError)
            }
        }
        return isValid
    }

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikSuamiValue.isBlank()) {
            errors["nik_suami"] = "NIK tidak boleh kosong"
        } else if (nikSuamiValue.length != 16) {
            errors["nik_suami"] = "NIK harus 16 digit"
        }

        if (namaSuamiValue.isBlank()) errors["nama_suami"] = "Nama tidak boleh kosong"
        if (tempatLahirSuamiValue.isBlank()) errors["tempat_lahir_suami"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahirSuamiValue.isBlank()) errors["tanggal_lahir_suami"] = "Tanggal lahir tidak boleh kosong"
        if (pekerjaanSuamiValue.isBlank()) errors["pekerjaan_suami"] = "Pekerjaan tidak boleh kosong"
        if (alamatSuamiValue.isBlank()) errors["alamat_suami"] = "Alamat tidak boleh kosong"
        if (agamaSuamiIdValue.isBlank()) errors["agama_suami_id"] = "Agama harus dipilih"
        if (kewarganegaraanSuamiValue.isBlank()) errors["kewarganegaraan_suami"] = "Kewarganegaraan tidak boleh kosong"
        if (statusKawinSuamiIdValue.isBlank()) errors["status_kawin_suami_id"] = "Status kawin harus dipilih"
        if (statusKawinSuamiIdValue == "2" && namaIstriSebelumnyaValue.isBlank()) {
            errors["nama_istri_sebelumnya"] = "Nama istri sebelumnya wajib diisi"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        // Ayah
        if (nikAyahSuamiValue.isBlank()) {
            errors["nik_ayah_suami"] = "NIK ayah tidak boleh kosong"
        } else if (nikAyahSuamiValue.length != 16) {
            errors["nik_ayah_suami"] = "NIK harus 16 digit"
        }

        if (namaAyahSuamiValue.isBlank()) errors["nama_ayah_suami"] = "Nama ayah tidak boleh kosong"
        if (tempatLahirAyahSuamiValue.isBlank()) errors["tempat_lahir_ayah_suami"] = "Tempat lahir ayah tidak boleh kosong"
        if (tanggalLahirAyahSuamiValue.isBlank()) errors["tanggal_lahir_ayah_suami"] = "Tanggal lahir ayah tidak boleh kosong"
        if (pekerjaanAyahSuamiValue.isBlank()) errors["pekerjaan_ayah_suami"] = "Pekerjaan ayah tidak boleh kosong"
        if (alamatAyahSuamiValue.isBlank()) errors["alamat_ayah_suami"] = "Alamat ayah tidak boleh kosong"
        if (agamaAyahSuamiIdValue.isBlank()) errors["agama_ayah_suami_id"] = "Agama ayah harus dipilih"
        if (kewarganegaraanAyahSuamiValue.isBlank()) errors["kewarganegaraan_ayah_suami"] = "Kewarganegaraan ayah tidak boleh kosong"

        // Ibu
        if (nikIbuSuamiValue.isBlank()) {
            errors["nik_ibu_suami"] = "NIK ibu tidak boleh kosong"
        } else if (nikIbuSuamiValue.length != 16) {
            errors["nik_ibu_suami"] = "NIK harus 16 digit"
        }

        if (namaIbuSuamiValue.isBlank()) errors["nama_ibu_suami"] = "Nama ibu tidak boleh kosong"
        if (tempatLahirIbuSuamiValue.isBlank()) errors["tempat_lahir_ibu_suami"] = "Tempat lahir ibu tidak boleh kosong"
        if (tanggalLahirIbuSuamiValue.isBlank()) errors["tanggal_lahir_ibu_suami"] = "Tanggal lahir ibu tidak boleh kosong"
        if (pekerjaanIbuSuamiValue.isBlank()) errors["pekerjaan_ibu_suami"] = "Pekerjaan ibu tidak boleh kosong"
        if (alamatIbuSuamiValue.isBlank()) errors["alamat_ibu_suami"] = "Alamat ibu tidak boleh kosong"
        if (agamaIbuSuamiIdValue.isBlank()) errors["agama_ibu_suami_id"] = "Agama ibu harus dipilih"
        if (kewarganegaraanIbuSuamiValue.isBlank()) errors["kewarganegaraan_ibu_suami"] = "Kewarganegaraan ibu tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (nikIstriValue.isBlank()) {
            errors["nik_istri"] = "NIK tidak boleh kosong"
        } else if (nikIstriValue.length != 16) {
            errors["nik_istri"] = "NIK harus 16 digit"
        }

        if (namaIstriValue.isBlank()) errors["nama_istri"] = "Nama tidak boleh kosong"
        if (tempatLahirIstriValue.isBlank()) errors["tempat_lahir_istri"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahirIstriValue.isBlank()) errors["tanggal_lahir_istri"] = "Tanggal lahir tidak boleh kosong"
        if (pekerjaanIstriValue.isBlank()) errors["pekerjaan_istri"] = "Pekerjaan tidak boleh kosong"
        if (alamatIstriValue.isBlank()) errors["alamat_istri"] = "Alamat tidak boleh kosong"
        if (agamaIstriIdValue.isBlank()) errors["agama_istri_id"] = "Agama harus dipilih"
        if (kewarganegaraanIstriValue.isBlank()) errors["kewarganegaraan_istri"] = "Kewarganegaraan tidak boleh kosong"
        if (statusKawinIstriIdValue.isBlank()) errors["status_kawin_istri_id"] = "Status kawin harus dipilih"
        if (statusKawinIstriIdValue == "2" && namaSuamiSebelumnyaValue.isBlank()) {
            errors["nama_suami_sebelumnya"] = "Nama suami sebelumnya wajib diisi"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep4(): Boolean {
        val errors = mutableMapOf<String, String>()

        // Ayah
        if (nikAyahIstriValue.isBlank()) {
            errors["nik_ayah_istri"] = "NIK ayah tidak boleh kosong"
        } else if (nikAyahIstriValue.length != 16) {
            errors["nik_ayah_istri"] = "NIK harus 16 digit"
        }

        if (namaAyahIstriValue.isBlank()) errors["nama_ayah_istri"] = "Nama ayah tidak boleh kosong"
        if (tempatLahirAyahIstriValue.isBlank()) errors["tempat_lahir_ayah_istri"] = "Tempat lahir ayah tidak boleh kosong"
        if (tanggalLahirAyahIstriValue.isBlank()) errors["tanggal_lahir_ayah_istri"] = "Tanggal lahir ayah tidak boleh kosong"
        if (pekerjaanAyahIstriValue.isBlank()) errors["pekerjaan_ayah_istri"] = "Pekerjaan ayah tidak boleh kosong"
        if (alamatAyahIstriValue.isBlank()) errors["alamat_ayah_istri"] = "Alamat ayah tidak boleh kosong"
        if (agamaAyahIstriIdValue.isBlank()) errors["agama_ayah_istri_id"] = "Agama ayah harus dipilih"
        if (kewarganegaraanAyahIstriValue.isBlank()) errors["kewarganegaraan_ayah_istri"] = "Kewarganegaraan ayah tidak boleh kosong"

        // Ibu
        if (nikIbuIstriValue.isBlank()) {
            errors["nik_ibu_istri"] = "NIK ibu tidak boleh kosong"
        } else if (nikIbuIstriValue.length != 16) {
            errors["nik_ibu_istri"] = "NIK harus 16 digit"
        }

        if (namaIbuIstriValue.isBlank()) errors["nama_ibu_istri"] = "Nama ibu tidak boleh kosong"
        if (tempatLahirIbuIstriValue.isBlank()) errors["tempat_lahir_ibu_istri"] = "Tempat lahir ibu tidak boleh kosong"
        if (tanggalLahirIbuIstriValue.isBlank()) errors["tanggal_lahir_ibu_istri"] = "Tanggal lahir ibu tidak boleh kosong"
        if (pekerjaanIbuIstriValue.isBlank()) errors["pekerjaan_ibu_istri"] = "Pekerjaan ibu tidak boleh kosong"
        if (alamatIbuIstriValue.isBlank()) errors["alamat_ibu_istri"] = "Alamat ibu tidak boleh kosong"
        if (agamaIbuIstriIdValue.isBlank()) errors["agama_ibu_istri_id"] = "Agama ibu harus dipilih"
        if (kewarganegaraanIbuIstriValue.isBlank()) errors["kewarganegaraan_ibu_istri"] = "Kewarganegaraan ibu tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep5(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (tanggalPernikahanValue.isBlank()) errors["tanggal_pernikahan"] = "Tanggal pernikahan tidak boleh kosong"
        if (hariPernikahanValue.isBlank()) errors["hari_pernikahan"] = "Hari pernikahan tidak boleh kosong"
        if (jamPernikahanValue.isBlank()) errors["jam_pernikahan"] = "Jam pernikahan tidak boleh kosong"
        if (tempatPernikahanValue.isBlank()) errors["tempat_pernikahan"] = "Tempat pernikahan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    // Validation helper functions
    fun validateAllSteps(): Boolean {
        return validateStep1() && validateStep2() && validateStep3() && validateStep4() && validateStep5()
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
                val request = SPPernikahanRequest(
                    disahkan_oleh = "",
                    hari = hariPernikahanValue,
                    tanggal = dateFormatterToApiFormat(tanggalPernikahanValue),

                    agama_ayah_istri_id = agamaAyahIstriIdValue,
                    agama_ayah_suami_id = agamaAyahSuamiIdValue,
                    agama_ibu_istri_id = agamaIbuIstriIdValue,
                    agama_ibu_suami_id = agamaIbuSuamiIdValue,
                    agama_istri_id = agamaIstriIdValue,
                    agama_suami_id = agamaSuamiIdValue,

                    alamat_ayah_istri = alamatAyahIstriValue,
                    alamat_ayah_suami = alamatAyahSuamiValue,
                    alamat_ibu_istri = alamatIbuIstriValue,
                    alamat_ibu_suami = alamatIbuSuamiValue,
                    alamat_istri = alamatIstriValue,
                    alamat_suami = alamatSuamiValue,

                    jam = jamPernikahanValue,
                    jumlah_istri = jumlahIstriValue,

                    kewarganegaraan_ayah_istri = kewarganegaraanAyahIstriValue,
                    kewarganegaraan_ayah_suami = kewarganegaraanAyahSuamiValue,
                    kewarganegaraan_ibu_istri = kewarganegaraanIbuIstriValue,
                    kewarganegaraan_ibu_suami = kewarganegaraanIbuSuamiValue,
                    kewarganegaraan_istri = kewarganegaraanIstriValue,
                    kewarganegaraan_suami = kewarganegaraanSuamiValue,

                    nama_ayah_istri = namaAyahIstriValue,
                    nama_ayah_suami = namaAyahSuamiValue,
                    nama_ibu_istri = namaIbuIstriValue,
                    nama_ibu_suami = namaIbuSuamiValue,
                    nama_istri = namaIstriValue,
                    nama_istri_sebelumnya = namaIstriSebelumnyaValue,
                    nama_suami = namaSuamiValue,
                    nama_suami_sebelumnya = namaSuamiSebelumnyaValue,

                    nik_ayah_istri = nikAyahIstriValue,
                    nik_ayah_suami = nikAyahSuamiValue,
                    nik_ibu_istri = nikIbuIstriValue,
                    nik_ibu_suami = nikIbuSuamiValue,
                    nik_istri = nikIstriValue,
                    nik_suami = nikSuamiValue,

                    pekerjaan_ayah_istri = pekerjaanAyahIstriValue,
                    pekerjaan_ayah_suami = pekerjaanAyahSuamiValue,
                    pekerjaan_ibu_istri = pekerjaanIbuIstriValue,
                    pekerjaan_ibu_suami = pekerjaanIbuSuamiValue,
                    pekerjaan_istri = pekerjaanIstriValue,
                    pekerjaan_suami = pekerjaanSuamiValue,

                    status_kawin_istri_id = statusKawinIstriIdValue,
                    status_kawin_suami_id = statusKawinSuamiIdValue,

                    tanggal_lahi_ayah_istri = tanggalLahiAyahIstriValue,
                    tanggal_lahi_ayah_suami = tanggalLahiAyahSuamiValue,
                    tanggal_lahi_ibu_istri = tanggalLahiIbuIstriValue,
                    tanggal_lahi_ibu_suami = tanggalLahiIbuSuamiValue,

                    tanggal_lahir_ayah_istri = tanggalLahirAyahIstriValue,
                    tanggal_lahir_ayah_suami = tanggalLahirAyahSuamiValue,
                    tanggal_lahir_ibu_istri = tanggalLahirIbuIstriValue,
                    tanggal_lahir_ibu_suami = tanggalLahirIbuSuamiValue,
                    tanggal_lahir_istri = tanggalLahirIstriValue,
                    tanggal_lahir_suami = tanggalLahirSuamiValue,

                    tempat = tempatPernikahanValue,
                    tempat_lahir_ayah_istri = tempatLahirAyahIstriValue,
                    tempat_lahir_ayah_suami = tempatLahirAyahSuamiValue,
                    tempat_lahir_ibu_istri = tempatLahirIbuIstriValue,
                    tempat_lahir_ibu_suami = tempatLahirIbuSuamiValue,
                    tempat_lahir_istri = tempatLahirIstriValue,
                    tempat_lahir_suami = tempatLahirSuamiValue
                )

                when (val result = createSuratPernikahanUseCase(request)) {
                    is SuratPernikahanResult.Success -> {
                        _pernikahanEvent.emit(SPPernikahanEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratPernikahanResult.Error -> {
                        errorMessage = result.message
                        _pernikahanEvent.emit(SPPernikahanEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _pernikahanEvent.emit(SPPernikahanEvent.SubmitError(errorMessage!!))
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

        // Step 1 - Calon Suami
        nikSuamiValue = ""
        namaSuamiValue = ""
        tempatLahirSuamiValue = ""
        tanggalLahirSuamiValue = ""
        pekerjaanSuamiValue = ""
        alamatSuamiValue = ""
        agamaSuamiIdValue = ""
        kewarganegaraanSuamiValue = ""
        jumlahIstriValue = 0
        statusKawinSuamiIdValue = ""
        namaIstriSebelumnyaValue = ""

        // Step 2 - Orang Tua Suami
        agamaAyahSuamiIdValue = ""
        alamatAyahSuamiValue = ""
        kewarganegaraanAyahSuamiValue = ""
        namaAyahSuamiValue = ""
        nikAyahSuamiValue = ""
        pekerjaanAyahSuamiValue = ""
        tempatLahirAyahSuamiValue = ""
        tanggalLahiAyahSuamiValue = ""
        tanggalLahirAyahSuamiValue = ""

        agamaIbuSuamiIdValue = ""
        alamatIbuSuamiValue = ""
        kewarganegaraanIbuSuamiValue = ""
        namaIbuSuamiValue = ""
        nikIbuSuamiValue = ""
        pekerjaanIbuSuamiValue = ""
        tanggalLahiIbuSuamiValue = ""
        tanggalLahirIbuSuamiValue = ""
        tempatLahirIbuSuamiValue = ""

        // Step 3 - Calon Istri
        agamaIstriIdValue = ""
        alamatIstriValue = ""
        kewarganegaraanIstriValue = ""
        namaIstriValue = ""
        nikIstriValue = ""
        pekerjaanIstriValue = ""
        statusKawinIstriIdValue = ""
        tanggalLahirIstriValue = ""
        tempatLahirIstriValue = ""
        namaSuamiSebelumnyaValue = ""

        // Step 4 - Orang Tua Istri
        agamaAyahIstriIdValue = ""
        agamaIbuIstriIdValue = ""
        alamatAyahIstriValue = ""
        alamatIbuIstriValue = ""
        kewarganegaraanAyahIstriValue = ""
        kewarganegaraanIbuIstriValue = ""
        namaAyahIstriValue = ""
        namaIbuIstriValue = ""
        nikAyahIstriValue = ""
        nikIbuIstriValue = ""
        pekerjaanAyahIstriValue = ""
        pekerjaanIbuIstriValue = ""
        tanggalLahirAyahIstriValue = ""
        tanggalLahiIbuIstriValue = ""
        tanggalLahirAyahIstriValue = ""
        tanggalLahirIbuIstriValue = ""
        tempatLahirAyahIstriValue = ""
        tempatLahirIbuIstriValue = ""

        // Step 5 - Informasi Pernikahan
        jamPernikahanValue = ""
        tempatPernikahanValue = ""
        hariPernikahanValue = ""
        tanggalPernikahanValue = ""

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
        return nikSuamiValue.isNotBlank() || nikIstriValue.isNotBlank() ||
                nikAyahSuamiValue.isNotBlank() || nikAyahIstriValue.isNotBlank()
    }

    // Events
    sealed class SPPernikahanEvent {
        data class StepChanged(val step: Int) : SPPernikahanEvent()
        data object SubmitSuccess : SPPernikahanEvent()
        data class SubmitError(val message: String) : SPPernikahanEvent()
        data object ValidationError : SPPernikahanEvent()
        data class UserDataLoadError(val message: String) : SPPernikahanEvent()
        data class AgamaLoadError(val message: String) : SPPernikahanEvent()
        data class StatusKawinLoadError(val message: String) : SPPernikahanEvent()
    }
}

// UI State data class
data class SPPernikahanUiState(
    val agamaList: List<AgamaResponse.Data> = emptyList(),
    val statusKawinList: List<StatusKawinResponse.Data> = emptyList(),
    val isFormDirty: Boolean = false,
    val currentStep: Int = 1,
    val totalSteps: Int = 2
)