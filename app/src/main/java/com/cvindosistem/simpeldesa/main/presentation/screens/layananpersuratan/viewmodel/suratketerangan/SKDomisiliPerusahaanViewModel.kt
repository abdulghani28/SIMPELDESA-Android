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
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.BidangUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.JenisUsahaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliPerusahaanRequest
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliPerusahaanResult
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
    private val createSuratDomisiliPerusahaanUseCase: CreateSuratDomisiliPerusahaanUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getBidangUsahaUseCase: BidangUsahaUseCase,
    private val getJenisUsahaUseCase: JenisUsahaUseCase,
    private val getAgamaUseCase: GetAgamaUseCase
) : ViewModel() {

    // UI State for the form
    private val _uiState = MutableStateFlow(SKDomisiliPerusahaanUiState())
    val uiState = _uiState.asStateFlow()

    // Dropdown data
    var bidangUsahaList by mutableStateOf<List<BidangUsahaResponse.Data>>(emptyList())
    var jenisUsahaList by mutableStateOf<List<JenisUsahaResponse.Data>>(emptyList())
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
    var isLoadingBidangUsaha by mutableStateOf(false)
    var isLoadingJenisUsaha by mutableStateOf(false)
    var isLoadingAgama by mutableStateOf(false)

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Current tab (0 = Warga Desa, 1 = Pendatang)
    var currentTab by mutableIntStateOf(0)
        private set

    // Current step for each tab
    var currentStepWargaDesa by mutableIntStateOf(1)
        private set
    var currentStepPendatang by mutableIntStateOf(1)
        private set

    // Use My Data state (only for Warga Desa)
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
    private val _domisiliEvent = MutableSharedFlow<SKDomisiliPerusahaanEvent>()
    val domisiliEvent = _domisiliEvent.asSharedFlow()

    // ===== WARGA DESA DATA =====
    // Step 1 - Informasi Pelapor Warga
    var wargaNikValue by mutableStateOf("")
        private set
    var wargaNamaValue by mutableStateOf("")
        private set
    var wargaTempatLahirValue by mutableStateOf("")
        private set
    var wargaTanggalLahirValue by mutableStateOf("")
        private set
    var wargaSelectedGender by mutableStateOf("")
        private set
    var wargaSelectedAgama by mutableStateOf("")
        private set
    var wargaAlamatValue by mutableStateOf("")
        private set
    var wargaPekerjaanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Perusahaan Warga
    var wargaNamaPerusahaanValue by mutableStateOf("")
        private set
    var wargaJenisUsahaValue by mutableStateOf("")
        private set
    var wargaBidangUsahaValue by mutableStateOf("")
        private set
    var wargaNomorAktaValue by mutableStateOf("")
        private set
    var wargaNibValue by mutableStateOf("")
        private set
    var wargaStatusKepemilikanBangunanValue by mutableStateOf("")
        private set
    var wargaJumlahKaryawanValue by mutableStateOf("")
        private set
    var wargaAlamatPerusahaanValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Pelengkap Warga
    var wargaKeperluanValue by mutableStateOf("")
        private set

    // ===== PENDATANG DATA =====
    // Step 1 - Informasi Pelapor Pendatang
    var pendatangNikValue by mutableStateOf("")
        private set
    var pendatangNamaValue by mutableStateOf("")
        private set
    var pendatangTempatLahirValue by mutableStateOf("")
        private set
    var pendatangTanggalLahirValue by mutableStateOf("")
        private set
    var pendatangSelectedGender by mutableStateOf("")
        private set
    var pendatangSelectedAgama by mutableStateOf("")
        private set
    var pendatangAlamatValue by mutableStateOf("")
        private set
    var pendatangPekerjaanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Perusahaan Pendatang
    var pendatangNamaPerusahaanValue by mutableStateOf("")
        private set
    var pendatangJenisUsahaValue by mutableStateOf("")
        private set
    var pendatangBidangUsahaValue by mutableStateOf("")
        private set
    var pendatangNomorAktaValue by mutableStateOf("")
        private set
    var pendatangPeruntukanBangunanValue by mutableStateOf("")
        private set
    var pendatangLuasTanahValue by mutableStateOf(0)
        private set
    var pendatangLuasBangunanValue by mutableStateOf(0)
        private set
    var pendatangStatusKepemilikanBangunanValue by mutableStateOf("")
        private set
    var pendatangJumlahKaryawanValue by mutableStateOf("")
        private set
    var pendatangAlamatPerusahaanValue by mutableStateOf("")
        private set
    var pendatangNpwpValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Pelengkap Pendatang
    var pendatangKeperluanValue by mutableStateOf("")
        private set

    // Validation states
    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    init {
        loadDropdownData()
    }

    private fun loadDropdownData() {
        loadBidangUsaha()
        loadJenisUsaha()
        loadAgama()
    }

    private fun loadBidangUsaha() {
        viewModelScope.launch {
            isLoadingBidangUsaha = true
            try {
                when (val result = getBidangUsahaUseCase()) {
                    is BidangUsahaResult.Success -> {
                        bidangUsahaList = result.data.data
                    }
                    is BidangUsahaResult.Error -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.BidangUsahaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.BidangUsahaLoadError(e.message ?: "Gagal memuat bidang usaha"))
            } finally {
                isLoadingBidangUsaha = false
            }
        }
    }

    private fun loadJenisUsaha() {
        viewModelScope.launch {
            isLoadingJenisUsaha = true
            try {
                when (val result = getJenisUsahaUseCase()) {
                    is JenisUsahaResult.Success -> {
                        jenisUsahaList = result.data.data
                    }
                    is JenisUsahaResult.Error -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.JenisUsahaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.JenisUsahaLoadError(e.message ?: "Gagal memuat jenis usaha"))
            } finally {
                isLoadingJenisUsaha = false
            }
        }
    }

    private fun loadAgama() {
        viewModelScope.launch {
            isLoadingAgama = true
            try {
                when (val result = getAgamaUseCase()) {
                    is AgamaResult.Success -> {
                        agamaList = result.data.data
                    }
                    is AgamaResult.Error -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.AgamaLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.AgamaLoadError(e.message ?: "Gagal memuat data agama"))
            } finally {
                isLoadingAgama = false
            }
        }
    }

    // Tab management
    fun updateCurrentTab(tab: Int) {
        currentTab = tab
        // Clear validation errors when switching tabs
        _validationErrors.value = emptyMap()
    }

    // Step management
    private fun getCurrentStep(): Int {
        return if (currentTab == 0) currentStepWargaDesa else currentStepPendatang
    }

    private fun getTotalSteps(): Int = 3

    fun nextStep() {
        val currentStep = getCurrentStep()
        val isValid = when (currentStep) {
            1 -> validateStep1WithEvent()
            2 -> validateStep2WithEvent()
            3 -> validateStep3WithEvent()
            else -> false
        }

        if (isValid) {
            if (currentStep < getTotalSteps()) {
                if (currentTab == 0) {
                    currentStepWargaDesa += 1
                } else {
                    currentStepPendatang += 1
                }
                viewModelScope.launch {
                    _domisiliEvent.emit(SKDomisiliPerusahaanEvent.StepChanged(getCurrentStep()))
                }
            } else {
                // Last step, show confirmation
                showConfirmationDialog = true
            }
        }
    }

    fun previousStep() {
        val currentStep = getCurrentStep()
        if (currentStep > 1) {
            if (currentTab == 0) {
                currentStepWargaDesa -= 1
            } else {
                currentStepPendatang -= 1
            }
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.StepChanged(getCurrentStep()))
            }
        }
    }

    // Use My Data functionality (only for Warga Desa)
    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
        if (checked) {
            loadUserData()
        } else {
            clearWargaUserData()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            isLoadingUserData = true
            try {
                when (val result = getUserInfoUseCase()) {
                    is UserInfoResult.Success -> {
                        val userData = result.data.data
                        wargaNikValue = userData.nik
                        wargaNamaValue = userData.nama_warga
                        wargaTempatLahirValue = userData.tempat_lahir
                        wargaTanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
                        wargaSelectedGender = userData.jenis_kelamin
                        wargaSelectedAgama = userData.agama_id
                        wargaAlamatValue = userData.alamat
                        wargaPekerjaanValue = userData.pekerjaan

                        // Clear validation errors for filled fields
                        clearMultipleFieldErrors(listOf(
                            "nik", "nama", "tempat_lahir", "tanggal_lahir",
                            "jenis_kelamin", "agama_id", "alamat", "pekerjaan"
                        ))
                    }
                    is UserInfoResult.Error -> {
                        errorMessage = result.message
                        useMyDataChecked = false
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.UserDataLoadError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Gagal memuat data pengguna"
                useMyDataChecked = false
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.UserDataLoadError(errorMessage!!))
            } finally {
                isLoadingUserData = false
            }
        }
    }

    private fun clearWargaUserData() {
        wargaNikValue = ""
        wargaNamaValue = ""
        wargaTempatLahirValue = ""
        wargaTanggalLahirValue = ""
        wargaSelectedGender = ""
        wargaSelectedAgama = ""
        wargaAlamatValue = ""
        wargaPekerjaanValue = ""
    }

    // ===== WARGA DESA FIELD UPDATES =====
    // Step 1 - Informasi Pelapor Warga
    fun updateWargaNik(value: String) {
        wargaNikValue = value
        clearFieldError("nik")
    }

    fun updateWargaNama(value: String) {
        wargaNamaValue = value
        clearFieldError("nama")
    }

    fun updateWargaTempatLahir(value: String) {
        wargaTempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updateWargaTanggalLahir(value: String) {
        wargaTanggalLahirValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal_lahir")
    }

    fun updateWargaGender(value: String) {
        wargaSelectedGender = value
        clearFieldError("jenis_kelamin")
    }

    fun updateWargaAgama(value: String) {
        wargaSelectedAgama = value
        clearFieldError("agama_id")
    }

    fun updateWargaAlamat(value: String) {
        wargaAlamatValue = value
        clearFieldError("alamat")
    }

    fun updateWargaPekerjaan(value: String) {
        wargaPekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    // Step 2 - Informasi Perusahaan Warga
    fun updateWargaNamaPerusahaan(value: String) {
        wargaNamaPerusahaanValue = value
        clearFieldError("nama_perusahaan")
    }

    fun updateWargaJenisUsaha(value: String) {
        wargaJenisUsahaValue = value
        clearFieldError("jenis_usaha_id")
    }

    fun updateWargaBidangUsaha(value: String) {
        wargaBidangUsahaValue = value
        clearFieldError("bidang_usaha_id")
    }

    fun updateWargaNomorAkta(value: String) {
        wargaNomorAktaValue = value
        clearFieldError("nomor_akta_pendirian")
    }

    fun updateWargaNib(value: String) {
        wargaNibValue = value
        clearFieldError("nib")
    }

    fun updateWargaStatusKepemilikanBangunan(value: String) {
        wargaStatusKepemilikanBangunanValue = value
        clearFieldError("status_kepemilikan_bangunan")
    }

    fun updateWargaJumlahKaryawan(value: String) {
        wargaJumlahKaryawanValue = value
        clearFieldError("jumlah_karyawan")
    }

    fun updateWargaAlamatPerusahaan(value: String) {
        wargaAlamatPerusahaanValue = value
        clearFieldError("alamat_perusahaan")
    }

    // Step 3 - Informasi Pelengkap Warga
    fun updateWargaKeperluan(value: String) {
        wargaKeperluanValue = value
        clearFieldError("keperluan")
    }

    // ===== PENDATANG FIELD UPDATES =====
    // Step 1 - Informasi Pelapor Pendatang
    fun updatePendatangNik(value: String) {
        pendatangNikValue = value
        clearFieldError("nik")
    }

    fun updatePendatangNama(value: String) {
        pendatangNamaValue = value
        clearFieldError("nama")
    }

    fun updatePendatangTempatLahir(value: String) {
        pendatangTempatLahirValue = value
        clearFieldError("tempat_lahir")
    }

    fun updatePendatangTanggalLahir(value: String) {
        pendatangTanggalLahirValue = dateFormatterToApiFormat(value)
        clearFieldError("tanggal_lahir")
    }

    fun updatePendatangGender(value: String) {
        pendatangSelectedGender = value
        clearFieldError("jenis_kelamin")
    }

    fun updatePendatangAgama(value: String) {
        pendatangSelectedAgama = value
        clearFieldError("agama_id")
    }

    fun updatePendatangAlamat(value: String) {
        pendatangAlamatValue = value
        clearFieldError("alamat")
    }

    fun updatePendatangPekerjaan(value: String) {
        pendatangPekerjaanValue = value
        clearFieldError("pekerjaan")
    }

    // Step 2 - Informasi Perusahaan Pendatang
    fun updatePendatangNamaPerusahaan(value: String) {
        pendatangNamaPerusahaanValue = value
        clearFieldError("nama_perusahaan")
    }

    fun updatePendatangJenisUsaha(value: String) {
        pendatangJenisUsahaValue = value
        clearFieldError("jenis_usaha_id")
    }

    fun updatePendatangBidangUsaha(value: String) {
        pendatangBidangUsahaValue = value
        clearFieldError("bidang_usaha_id")
    }

    fun updatePendatangNomorAkta(value: String) {
        pendatangNomorAktaValue = value
        clearFieldError("nomor_akta_pendirian")
    }

    fun updatePendatangPeruntukanBangunan(value: String) {
        pendatangPeruntukanBangunanValue = value
        clearFieldError("peruntukan_bangunan")
    }

    fun updatePendatangLuasTanah(value: Int) {
        pendatangLuasTanahValue = value
        clearFieldError("luas_tanah")
    }

    fun updatePendatangLuasBangunan(value: Int) {
        pendatangLuasBangunanValue = value
        clearFieldError("luas_bangunan")
    }

    fun updatePendatangStatusKepemilikanBangunan(value: String) {
        pendatangStatusKepemilikanBangunanValue = value
        clearFieldError("status_kepemilikan_bangunan")
    }

    fun updatePendatangJumlahKaryawan(value: String) {
        pendatangJumlahKaryawanValue = value
        clearFieldError("jumlah_karyawan")
    }

    fun updatePendatangAlamatPerusahaan(value: String) {
        pendatangAlamatPerusahaanValue = value
        clearFieldError("alamat_perusahaan")
    }

    fun updatePendatangNpwp(value: String) {
        pendatangNpwpValue = value
        clearFieldError("npwp")
    }

    // Step 3 - Informasi Pelengkap Pendatang
    fun updatePendatangKeperluan(value: String) {
        pendatangKeperluanValue = value
        clearFieldError("keperluan")
    }

    // ===== GETTER FUNCTIONS FOR CURRENT TAB =====
    fun getCurrentNik(): String = if (currentTab == 0) wargaNikValue else pendatangNikValue
    fun getCurrentNama(): String = if (currentTab == 0) wargaNamaValue else pendatangNamaValue
    fun getCurrentTempatLahir(): String = if (currentTab == 0) wargaTempatLahirValue else pendatangTempatLahirValue
    fun getCurrentTanggalLahir(): String = if (currentTab == 0) wargaTanggalLahirValue else pendatangTanggalLahirValue
    fun getCurrentGender(): String = if (currentTab == 0) wargaSelectedGender else pendatangSelectedGender
    fun getCurrentAgama(): String = if (currentTab == 0) wargaSelectedAgama else pendatangSelectedAgama
    fun getCurrentAlamat(): String = if (currentTab == 0) wargaAlamatValue else pendatangAlamatValue
    fun getCurrentPekerjaan(): String = if (currentTab == 0) wargaPekerjaanValue else pendatangPekerjaanValue
    fun getCurrentNamaPerusahaan(): String = if (currentTab == 0) wargaNamaPerusahaanValue else pendatangNamaPerusahaanValue
    fun getCurrentJenisUsaha(): String = if (currentTab == 0) wargaJenisUsahaValue else pendatangJenisUsahaValue
    fun getCurrentBidangUsaha(): String = if (currentTab == 0) wargaBidangUsahaValue else pendatangBidangUsahaValue
    fun getCurrentNomorAkta(): String = if (currentTab == 0) wargaNomorAktaValue else pendatangNomorAktaValue
    fun getCurrentStatusKepemilikanBangunan(): String = if (currentTab == 0) wargaStatusKepemilikanBangunanValue else pendatangStatusKepemilikanBangunanValue
    fun getCurrentJumlahKaryawan(): String = if (currentTab == 0) wargaJumlahKaryawanValue else pendatangJumlahKaryawanValue
    fun getCurrentAlamatPerusahaan(): String = if (currentTab == 0) wargaAlamatPerusahaanValue else pendatangAlamatPerusahaanValue

    // Specific getters for pendatang only
    fun getPendatangNib(): String = "" // Not used in pendatang
    fun getPendatangPeruntukanBangunan(): String = pendatangPeruntukanBangunanValue
    fun getPendatangLuasTanah(): Int = pendatangLuasTanahValue
    fun getPendatangLuasBangunan(): Int = pendatangLuasBangunanValue
    fun getPendatangNpwp(): String = pendatangNpwpValue
    fun getPendatangKeperluan(): String = pendatangKeperluanValue
    fun getWargaKeperluan(): String = wargaKeperluanValue

    // Validation functions
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()
        val nik = getCurrentNik()
        val nama = getCurrentNama()
        val tempatLahir = getCurrentTempatLahir()
        val tanggalLahir = getCurrentTanggalLahir()
        val gender = getCurrentGender()
        val agama = getCurrentAgama()
        val alamat = getCurrentAlamat()
        val pekerjaan = getCurrentPekerjaan()

        if (nik.isBlank()) {
            errors["nik"] = "NIK tidak boleh kosong"
        } else if (nik.length != 16) {
            errors["nik"] = "NIK harus 16 digit"
        }

        if (nama.isBlank()) errors["nama"] = "Nama lengkap tidak boleh kosong"
        if (tempatLahir.isBlank()) errors["tempat_lahir"] = "Tempat lahir tidak boleh kosong"
        if (tanggalLahir.isBlank()) errors["tanggal_lahir"] = "Tanggal lahir tidak boleh kosong"
        if (gender.isBlank()) errors["jenis_kelamin"] = "Jenis kelamin harus dipilih"
        if (agama.isBlank()) errors["agama_id"] = "Agama harus dipilih"
        if (alamat.isBlank()) errors["alamat"] = "Alamat tidak boleh kosong"
        if (pekerjaan.isBlank()) errors["pekerjaan"] = "Pekerjaan tidak boleh kosong"

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()
        val namaPerusahaan = getCurrentNamaPerusahaan()
        val jenisUsaha = getCurrentJenisUsaha()
        val bidangUsaha = getCurrentBidangUsaha()
        val nomorAkta = getCurrentNomorAkta()
        val statusKepemilikan = getCurrentStatusKepemilikanBangunan()
        val jumlahKaryawan = getCurrentJumlahKaryawan()
        val alamatPerusahaan = getCurrentAlamatPerusahaan()

        if (namaPerusahaan.isBlank()) errors["nama_perusahaan"] = "Nama perusahaan tidak boleh kosong"
        if (jenisUsaha.isBlank()) errors["jenis_usaha_id"] = "Jenis usaha harus dipilih"
        if (bidangUsaha.isBlank()) errors["bidang_usaha_id"] = "Bidang usaha harus dipilih"
        if (nomorAkta.isBlank()) errors["nomor_akta_pendirian"] = "Nomor akta pendirian tidak boleh kosong"
        if (statusKepemilikan.isBlank()) errors["status_kepemilikan_bangunan"] = "Status kepemilikan bangunan harus dipilih"
        if (jumlahKaryawan.isBlank()) errors["jumlah_karyawan"] = "Jumlah karyawan tidak boleh kosong"
        if (alamatPerusahaan.isBlank()) errors["alamat_perusahaan"] = "Alamat perusahaan tidak boleh kosong"

        // Additional validation for warga desa
        if (currentTab == 0) {
            if (wargaNibValue.isBlank()) errors["nib"] = "NIB tidak boleh kosong"
        }

        // Additional validation for pendatang
        if (currentTab == 1) {
            val peruntukanBangunan = getPendatangPeruntukanBangunan()
            val luasTanah = getPendatangLuasTanah()
            val luasBangunan = getPendatangLuasBangunan()
            val npwp = getPendatangNpwp()

            if (peruntukanBangunan.isBlank()) errors["peruntukan_bangunan"] = "Peruntukan bangunan tidak boleh kosong"
            if (luasTanah == 0) errors["luas_tanah"] = "Luas tanah tidak boleh 0"
            if (luasBangunan == 0) errors["luas_bangunan"] = "Luas bangunan tidak boleh 0"
            if (npwp.isBlank()) errors["npwp"] = "NPWP tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (currentTab == 1) {
            // Pendatang - validasi keperluan
            if (getPendatangKeperluan().isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        } else if (currentTab == 0) {
            if (getWargaKeperluan().isBlank()) errors["keperluan"] = "Keperluan tidak boleh kosong"
        }

        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun validateStep1WithEvent(): Boolean {
        val isValid = validateStep1()
        if (!isValid) {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep2WithEvent(): Boolean {
        val isValid = validateStep2()
        if (!isValid) {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
            }
        }
        return isValid
    }

    private fun validateStep3WithEvent(): Boolean {
        val isValid = validateStep3()
        if (!isValid) {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
            }
        }
        return isValid
    }

    fun validateAllSteps(): Boolean {
        val step1Valid = validateStep1()
        val step2Valid = validateStep2()
        val step3Valid = validateStep3()
        return step1Valid && step2Valid && step3Valid
    }

    // Preview and confirmation dialogs
    fun showPreview() {
        if (validateAllSteps()) {
            showPreviewDialog = true
        } else {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
            }
        }
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    fun showConfirmationDialog() {
        if (validateAllSteps()) {
            showConfirmationDialog = true
        } else {
            viewModelScope.launch {
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.ValidationError)
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

    // Form submission
    private fun submitForm() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val request = SKDomisiliPerusahaanRequest(
                    agama_id = getCurrentAgama(),
                    alamat = getCurrentAlamat(),
                    alamat_perusahaan = getCurrentAlamatPerusahaan(),
                    bidang_usaha_id = getCurrentBidangUsaha(),
                    jenis_kelamin = getCurrentGender(),
                    jenis_usaha_id = getCurrentJenisUsaha(),
                    jumlah_karyawan = getCurrentJumlahKaryawan(),
                    keperluan = if (currentTab == 1) getPendatangKeperluan() else getWargaKeperluan(),
                    luas_bangunan = if (currentTab == 1) getPendatangLuasBangunan() else 0,
                    luas_tanah = if (currentTab == 1) getPendatangLuasTanah() else 0,
                    nama = getCurrentNama(),
                    nama_perusahaan = getCurrentNamaPerusahaan(),
                    nib = if (currentTab == 1) getPendatangNib() else wargaNibValue,
                    nik = getCurrentNik(),
                    nomor_akta_pendirian = getCurrentNomorAkta(),
                    npwp = if (currentTab == 1) getPendatangNpwp() else "",
                    pekerjaan = getCurrentPekerjaan(),
                    peruntukan_bangunan = if (currentTab == 1) getPendatangPeruntukanBangunan() else "",
                    status_kepemilikan_bangunan = getCurrentStatusKepemilikanBangunan(),
                    tanggal_lahir = getCurrentTanggalLahir(),
                    tempat_lahir = getCurrentTempatLahir()
                )

                when (val result = createSuratDomisiliPerusahaanUseCase(request)) {
                    is SuratDomisiliPerusahaanResult.Success -> {
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.SubmitSuccess)
                        resetForm()
                    }
                    is SuratDomisiliPerusahaanResult.Error -> {
                        errorMessage = result.message
                        _domisiliEvent.emit(SKDomisiliPerusahaanEvent.SubmitError(result.message))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
                _domisiliEvent.emit(SKDomisiliPerusahaanEvent.SubmitError(errorMessage!!))
            } finally {
                isLoading = false
            }
        }
    }

    // Validation helper functions
    fun getFieldError(fieldName: String): String? {
        return _validationErrors.value[fieldName]
    }

    fun hasFieldError(fieldName: String): Boolean {
        return _validationErrors.value.containsKey(fieldName)
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

    // Reset form
    private fun resetForm() {
        currentTab = 0
        currentStepWargaDesa = 1
        currentStepPendatang = 1
        useMyDataChecked = false

        // Reset Warga Desa fields
        wargaNikValue = ""
        wargaNamaValue = ""
        wargaTempatLahirValue = ""
        wargaTanggalLahirValue = ""
        wargaSelectedGender = ""
        wargaSelectedAgama = ""
        wargaAlamatValue = ""
        wargaPekerjaanValue = ""
        wargaNamaPerusahaanValue = ""
        wargaJenisUsahaValue = ""
        wargaBidangUsahaValue = ""
        wargaNomorAktaValue = ""
        wargaNibValue = ""
        wargaStatusKepemilikanBangunanValue = ""
        wargaJumlahKaryawanValue = ""
        wargaAlamatPerusahaanValue = ""

        // Reset Pendatang fields
        pendatangNikValue = ""
        pendatangNamaValue = ""
        pendatangTempatLahirValue = ""
        pendatangTanggalLahirValue = ""
        pendatangSelectedGender = ""
        pendatangSelectedAgama = ""
        pendatangAlamatValue = ""
        pendatangPekerjaanValue = ""
        pendatangNamaPerusahaanValue = ""
        pendatangJenisUsahaValue = ""
        pendatangBidangUsahaValue = ""
        pendatangNomorAktaValue = ""
        pendatangPeruntukanBangunanValue = ""
        pendatangLuasTanahValue = 0
        pendatangLuasBangunanValue = 0
        pendatangStatusKepemilikanBangunanValue = ""
        pendatangJumlahKaryawanValue = ""
        pendatangAlamatPerusahaanValue = ""
        pendatangNpwpValue = ""
        pendatangKeperluanValue = ""

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
        return if (currentTab == 0) {
            wargaNikValue.isNotBlank() || wargaNamaValue.isNotBlank() ||
                    wargaNamaPerusahaanValue.isNotBlank() || wargaAlamatPerusahaanValue.isNotBlank()
        } else {
            pendatangNikValue.isNotBlank() || pendatangNamaValue.isNotBlank() ||
                    pendatangNamaPerusahaanValue.isNotBlank() || pendatangNpwpValue.isNotBlank()
        }
    }

    // Get current step for UI
    fun getCurrentStepForUI(): Int = getCurrentStep()

    // Get preview data
    fun getPreviewData(): Map<String, String> {
        val commonData = mutableMapOf(
            "NIK" to getCurrentNik(),
            "Nama" to getCurrentNama(),
            "Tempat Lahir" to getCurrentTempatLahir(),
            "Tanggal Lahir" to getCurrentTanggalLahir(),
            "Jenis Kelamin" to getCurrentGender(),
            "Agama" to getCurrentAgama(),
            "Alamat" to getCurrentAlamat(),
            "Pekerjaan" to getCurrentPekerjaan(),
            "Nama Perusahaan" to getCurrentNamaPerusahaan(),
            "Jenis Usaha" to getCurrentJenisUsaha(),
            "Bidang Usaha" to getCurrentBidangUsaha(),
            "Nomor Akta Pendirian" to getCurrentNomorAkta(),
            "Status Kepemilikan Bangunan" to getCurrentStatusKepemilikanBangunan(),
            "Jumlah Karyawan" to getCurrentJumlahKaryawan(),
            "Alamat Perusahaan" to getCurrentAlamatPerusahaan()
        )

        if (currentTab == 0) {
            // Warga Desa specific fields
            commonData["NIB"] = wargaNibValue
            commonData["Keperluan"] = getWargaKeperluan()
        } else {
            // Pendatang specific fields
            commonData["Peruntukan Bangunan"] = getPendatangPeruntukanBangunan()
            commonData["Luas Tanah"] = getPendatangLuasTanah().toString()
            commonData["Luas Bangunan"] = getPendatangLuasBangunan().toString()
            commonData["NPWP"] = getPendatangNpwp()
            commonData["Keperluan"] = getPendatangKeperluan()
        }

        return commonData
    }


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

// UI State data class
data class SKDomisiliPerusahaanUiState(
    val isFormDirty: Boolean = false,
    val currentTab: Int = 0,
    val totalTabs: Int = 2,
    val currentStepWargaDesa: Int = 1,
    val currentStepPendatang: Int = 1,
    val totalStepsWargaDesa: Int = 3,
    val totalStepsPendatang: Int = 3
)