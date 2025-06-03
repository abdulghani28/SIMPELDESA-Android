package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKDomisiliPerusahaanStateManager {
    // Tab and Step Management
    var currentTab by mutableIntStateOf(0)
        private set
    var currentStepWargaDesa by mutableIntStateOf(1)
        private set
    var currentStepPendatang by mutableIntStateOf(1)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _validationErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val validationErrors = _validationErrors.asStateFlow()

    // Use My Data state
    var useMyDataChecked by mutableStateOf(false)
        private set

    // Dialog states
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

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
    var wargaNpwpValue by mutableStateOf("")
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
    var pendatangLuasTanahValue by mutableIntStateOf(0)
        private set
    var pendatangLuasBangunanValue by mutableIntStateOf(0)
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

    // Navigation methods
    fun updateCurrentTab(tab: Int) {
        currentTab = tab
    }

    fun getCurrentStep(): Int {
        return if (currentTab == 0) currentStepWargaDesa else currentStepPendatang
    }

    fun nextStep() {
        if (currentTab == 0) {
            currentStepWargaDesa += 1
        } else {
            currentStepPendatang += 1
        }
    }

    fun previousStep() {
        if (currentTab == 0 && currentStepWargaDesa > 1) {
            currentStepWargaDesa -= 1
        } else if (currentTab == 1 && currentStepPendatang > 1) {
            currentStepPendatang -= 1
        }
    }

    // Step 1 - Informasi Pelapor Warga
    fun updateWargaNik(value: String) { wargaNikValue = value }

    fun updateWargaNama(value: String) { wargaNamaValue = value }

    fun updateWargaTempatLahir(value: String) { wargaTempatLahirValue = value }

    fun updateWargaTanggalLahir(value: String) { wargaTanggalLahirValue = dateFormatterToApiFormat(value) }

    fun updateWargaGender(value: String) { wargaSelectedGender = value }

    fun updateWargaAgama(value: String) { wargaSelectedAgama = value }

    fun updateWargaAlamat(value: String) { wargaAlamatValue = value }

    fun updateWargaPekerjaan(value: String) {
        wargaPekerjaanValue = value
    }

    // Step 2 - Informasi Perusahaan Warga
    fun updateWargaNamaPerusahaan(value: String) {
        wargaNamaPerusahaanValue = value
    }

    fun updateWargaJenisUsaha(value: String) {
        wargaJenisUsahaValue = value
    }

    fun updateWargaBidangUsaha(value: String) {
        wargaBidangUsahaValue = value
    }

    fun updateWargaNomorAkta(value: String) {
        wargaNomorAktaValue = value
    }

    fun updateWargaNib(value: String) {
        wargaNibValue = value
    }

    fun updateWargaStatusKepemilikanBangunan(value: String) {
        wargaStatusKepemilikanBangunanValue = value
    }

    fun updateWargaJumlahKaryawan(value: String) {
        wargaJumlahKaryawanValue = value
    }

    fun updateWargaAlamatPerusahaan(value: String) {
        wargaAlamatPerusahaanValue = value
    }

    // Step 3 - Informasi Pelengkap Warga
    fun updateWargaKeperluan(value: String) {
        wargaKeperluanValue = value
    }

    // ===== PENDATANG FIELD UPDATES =====
    // Step 1 - Informasi Pelapor Pendatang
    fun updatePendatangNik(value: String) {
        pendatangNikValue = value
    }

    fun updatePendatangNama(value: String) {
        pendatangNamaValue = value
    }

    fun updatePendatangTempatLahir(value: String) {
        pendatangTempatLahirValue = value
    }

    fun updatePendatangTanggalLahir(value: String) {
        pendatangTanggalLahirValue = dateFormatterToApiFormat(value)
    }

    fun updatePendatangGender(value: String) {
        pendatangSelectedGender = value
    }

    fun updatePendatangAgama(value: String) {
        pendatangSelectedAgama = value
    }

    fun updatePendatangAlamat(value: String) {
        pendatangAlamatValue = value
    }

    fun updatePendatangPekerjaan(value: String) {
        pendatangPekerjaanValue = value
    }

    // Step 2 - Informasi Perusahaan Pendatang
    fun updatePendatangNamaPerusahaan(value: String) {
        pendatangNamaPerusahaanValue = value
    }

    fun updatePendatangJenisUsaha(value: String) {
        pendatangJenisUsahaValue = value
    }

    fun updatePendatangBidangUsaha(value: String) {
        pendatangBidangUsahaValue = value
    }

    fun updatePendatangNomorAkta(value: String) {
        pendatangNomorAktaValue = value
    }

    fun updatePendatangPeruntukanBangunan(value: String) {
        pendatangPeruntukanBangunanValue = value
    }

    fun updatePendatangLuasTanah(value: Int) {
        pendatangLuasTanahValue = value
    }

    fun updatePendatangLuasBangunan(value: Int) {
        pendatangLuasBangunanValue = value
    }

    fun updatePendatangStatusKepemilikanBangunan(value: String) {
        pendatangStatusKepemilikanBangunanValue = value
    }

    fun updatePendatangJumlahKaryawan(value: String) {
        pendatangJumlahKaryawanValue = value
    }

    fun updatePendatangAlamatPerusahaan(value: String) {
        pendatangAlamatPerusahaanValue = value
    }

    fun updatePendatangNpwp(value: String) {
        pendatangNpwpValue = value
    }

    // Step 3 - Informasi Pelengkap Pendatang
    fun updatePendatangKeperluan(value: String) {
        pendatangKeperluanValue = value
    }

    // Dialog management
    fun showConfirmation() { showConfirmationDialog = true }
    fun dismissConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Use My Data
    fun updateUseMyData(checked: Boolean) { useMyDataChecked = checked }

    // Getter methods - Copy dari kode asli
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
    fun getWargaNib(): String = wargaNibValue
    fun getWargaNpwp(): String = wargaNpwpValue

    // Specific getters for pendatang only
    fun getPendatangNib(): String = "" // Not used in pendatang
    fun getPendatangPeruntukanBangunan(): String = pendatangPeruntukanBangunanValue
    fun getPendatangLuasTanah(): Int = pendatangLuasTanahValue
    fun getPendatangLuasBangunan(): Int = pendatangLuasBangunanValue
    fun getPendatangNpwp(): String = pendatangNpwpValue
    fun getPendatangKeperluan(): String = pendatangKeperluanValue
    fun getWargaKeperluan(): String = wargaKeperluanValue

    // Clear data methods
    fun clearWargaUserData() {
        wargaNikValue = ""
        wargaNamaValue = ""
        wargaTempatLahirValue = ""
        wargaTanggalLahirValue = ""
        wargaSelectedGender = ""
        wargaSelectedAgama = ""
        wargaAlamatValue = ""
        wargaPekerjaanValue = ""
    }

    internal fun resetForm() {
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

    fun hasFormData(): Boolean {
        return if (currentTab == 0) {
            wargaNikValue.isNotBlank() || wargaNamaValue.isNotBlank() ||
                    wargaNamaPerusahaanValue.isNotBlank() || wargaAlamatPerusahaanValue.isNotBlank()
        } else {
            pendatangNikValue.isNotBlank() || pendatangNamaValue.isNotBlank() ||
                    pendatangNamaPerusahaanValue.isNotBlank() || pendatangNpwpValue.isNotBlank()
        }
    }
}