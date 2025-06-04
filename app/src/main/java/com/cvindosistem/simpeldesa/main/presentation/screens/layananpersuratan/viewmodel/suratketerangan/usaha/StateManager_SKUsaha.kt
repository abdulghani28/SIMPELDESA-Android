package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.usaha

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat

class SKUsahaStateManager {
    // Tab dan Step Management
    var currentTab by mutableIntStateOf(0)
        private set
    var currentStepWargaDesa by mutableIntStateOf(1)
        private set
    var currentStepPendatang by mutableIntStateOf(1)
        private set

    // UI States
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set

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
    var wargaPekerjaanValue by mutableStateOf("")
        private set
    var wargaAlamatValue by mutableStateOf("")
        private set
    var wargaNamaUsahaValue by mutableStateOf("")
        private set
    var wargaBidangUsahaValue by mutableStateOf("")
        private set
    var wargaJenisUsahaValue by mutableStateOf("")
        private set
    var wargaAlamatUsahaValue by mutableStateOf("")
        private set
    var wargaNpwpValue by mutableStateOf("")
        private set
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
    var pendatangPekerjaanValue by mutableStateOf("")
        private set
    var pendatangAlamatValue by mutableStateOf("")
        private set
    var pendatangNamaUsahaValue by mutableStateOf("")
        private set
    var pendatangBidangUsahaValue by mutableStateOf("")
        private set
    var pendatangJenisUsahaValue by mutableStateOf("")
        private set
    var pendatangAlamatUsahaValue by mutableStateOf("")
        private set
    var pendatangNpwpValue by mutableStateOf("")
        private set
    var pendatangKeperluanValue by mutableStateOf("")
        private set

    fun updateCurrentTab(tab: Int) {
        currentTab = tab
    }

    fun nextStep() {
        if (currentTab == 0 && currentStepWargaDesa < 3) {
            currentStepWargaDesa += 1
        } else if (currentTab == 1 && currentStepPendatang < 3) {
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

    fun getCurrentStep(): Int = if (currentTab == 0) currentStepWargaDesa else currentStepPendatang

    fun showConfirmationDialog() { showConfirmationDialog = true }
    fun dismissConfirmationDialog() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
    }

    // ===== FIELD UPDATE METHODS =====
    fun updateWargaNik(value: String) { wargaNikValue = value }
    fun updateWargaNama(value: String) { wargaNamaValue = value }
    fun updateWargaTempatLahir(value: String) { wargaTempatLahirValue = value }
    fun updateWargaTanggalLahir(value: String) { wargaTanggalLahirValue = dateFormatterToApiFormat(value) }
    fun updateWargaGender(value: String) { wargaSelectedGender = value }
    fun updateWargaPekerjaan(value: String) { wargaPekerjaanValue = value }
    fun updateWargaAlamat(value: String) { wargaAlamatValue = value }

    fun updateWargaNamaUsaha(value: String) { wargaNamaUsahaValue = value }
    fun updateWargaBidangUsaha(value: String) { wargaBidangUsahaValue = value }
    fun updateWargaJenisUsaha(value: String) { wargaJenisUsahaValue = value }
    fun updateWargaAlamatUsaha(value: String) { wargaAlamatUsahaValue = value }
    fun updateWargaNpwp(value: String) { wargaNpwpValue = value }
    fun updateWargaKeperluan(value: String) { wargaKeperluanValue = value }

    fun updatePendatangNik(value: String) { pendatangNikValue = value }
    fun updatePendatangNama(value: String) { pendatangNamaValue = value }
    fun updatePendatangTempatLahir(value: String) { pendatangTempatLahirValue = value }
    fun updatePendatangTanggalLahir(value: String) { pendatangTanggalLahirValue = dateFormatterToApiFormat(value) }
    fun updatePendatangGender(value: String) { pendatangSelectedGender = value }
    fun updatePendatangPekerjaan(value: String) { pendatangPekerjaanValue = value }
    fun updatePendatangAlamat(value: String) { pendatangAlamatValue = value }

    fun updatePendatangNamaUsaha(value: String) { pendatangNamaUsahaValue = value }
    fun updatePendatangBidangUsaha(value: String) { pendatangBidangUsahaValue = value }
    fun updatePendatangJenisUsaha(value: String) { pendatangJenisUsahaValue = value }
    fun updatePendatangAlamatUsaha(value: String) { pendatangAlamatUsahaValue = value }
    fun updatePendatangNpwp(value: String) { pendatangNpwpValue = value }
    fun updatePendatangKeperluan(value: String) { pendatangKeperluanValue = value }


    // ===== GETTER METHODS =====
    fun getCurrentNik(): String = if (currentTab == 0) wargaNikValue else pendatangNikValue
    fun getCurrentNama(): String = if (currentTab == 0) wargaNamaValue else pendatangNamaValue
    fun getCurrentTempatLahir(): String = if (currentTab == 0) wargaTempatLahirValue else pendatangTempatLahirValue
    fun getCurrentTanggalLahir(): String = if (currentTab == 0) wargaTanggalLahirValue else pendatangTanggalLahirValue
    fun getCurrentGender(): String = if (currentTab == 0) wargaSelectedGender else pendatangSelectedGender
    fun getCurrentPekerjaan(): String = if (currentTab == 0) wargaPekerjaanValue else pendatangPekerjaanValue
    fun getCurrentAlamat(): String = if (currentTab == 0) wargaAlamatValue else pendatangAlamatValue
    fun getCurrentNamaUsaha(): String = if (currentTab == 0) wargaNamaUsahaValue else pendatangNamaUsahaValue
    fun getCurrentBidangUsaha(): String = if (currentTab == 0) wargaBidangUsahaValue else pendatangBidangUsahaValue
    fun getCurrentJenisUsaha(): String = if (currentTab == 0) wargaJenisUsahaValue else pendatangJenisUsahaValue
    fun getCurrentAlamatUsaha(): String = if (currentTab == 0) wargaAlamatUsahaValue else pendatangAlamatUsahaValue
    fun getCurrentKeperluan(): String = if (currentTab == 0) wargaKeperluanValue else pendatangKeperluanValue
    fun getCurrentNpwp(): String = if (currentTab == 1) pendatangNpwpValue else wargaNpwpValue

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
        wargaPekerjaanValue = ""
        wargaAlamatValue = ""
        wargaNamaUsahaValue = ""
        wargaBidangUsahaValue = ""
        wargaJenisUsahaValue = ""
        wargaAlamatUsahaValue = ""
        wargaKeperluanValue = ""
        wargaNpwpValue = ""

        // Reset Pendatang fields
        pendatangNikValue = ""
        pendatangNamaValue = ""
        pendatangTempatLahirValue = ""
        pendatangTanggalLahirValue = ""
        pendatangSelectedGender = ""
        pendatangPekerjaanValue = ""
        pendatangAlamatValue = ""
        pendatangNamaUsahaValue = ""
        pendatangBidangUsahaValue = ""
        pendatangJenisUsahaValue = ""
        pendatangAlamatUsahaValue = ""
        pendatangNpwpValue = ""
        pendatangKeperluanValue = ""

        showConfirmationDialog = false
        showPreviewDialog = false
    }

    internal fun clearWargaUserData() {
        wargaNikValue = ""
        wargaNamaValue = ""
        wargaTempatLahirValue = ""
        wargaTanggalLahirValue = ""
        wargaSelectedGender = ""
        wargaPekerjaanValue = ""
        wargaAlamatValue = ""
    }

    fun hasFormData(): Boolean {
        return if (currentTab == 0) {
            wargaNikValue.isNotBlank() || wargaNamaValue.isNotBlank() ||
                    wargaNamaUsahaValue.isNotBlank() || wargaKeperluanValue.isNotBlank()
        } else {
            pendatangNikValue.isNotBlank() || pendatangNamaValue.isNotBlank() ||
                    pendatangNamaUsahaValue.isNotBlank() || pendatangNpwpValue.isNotBlank()
        }
    }

    fun getPreviewData(): Map<String, String> {
        return mapOf(
            "NIK" to getCurrentNik(),
            "Nama" to getCurrentNama(),
            "Tempat Lahir" to getCurrentTempatLahir(),
            "Tanggal Lahir" to getCurrentTanggalLahir(),
            "Jenis Kelamin" to getCurrentGender(),
            "Pekerjaan" to getCurrentPekerjaan(),
            "Alamat" to getCurrentAlamat(),
            "Nama Usaha" to getCurrentNamaUsaha(),
            "Bidang Usaha" to getCurrentBidangUsaha(),
            "Jenis Usaha" to getCurrentJenisUsaha(),
            "Alamat Usaha" to getCurrentAlamatUsaha(),
            "Keperluan" to getCurrentKeperluan(),
            "NPWP" to getCurrentNpwp()
        )
    }
}