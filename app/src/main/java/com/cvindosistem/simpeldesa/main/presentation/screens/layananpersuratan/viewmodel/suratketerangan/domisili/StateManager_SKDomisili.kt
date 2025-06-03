package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKDomisiliRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SKDomisiliStateManager {
    // UI State
    private val _uiState = MutableStateFlow(SKDomisiliUiState())
    val uiState = _uiState.asStateFlow()

    // Form Fields State
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var selectedGender by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var agamaValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set

    // Pendatang Fields
    var selectedKewarganegaraan by mutableStateOf("")
        private set
    var alamatSesuaiKTP by mutableStateOf("")
        private set
    var alamatTinggalSekarang by mutableStateOf("")
        private set
    var jumlahPengikut by mutableStateOf("")
        private set

    // Dialog States
    var isShowConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Tab and Checkbox States
    var currentTab by mutableIntStateOf(0)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set

    // Field Update Functions - Copy langsung dari kode asli
    // Field Update Functions - Copy langsung dari kode asli
    fun updateNik(value: String) {
        nikValue = value
    }

    fun updateNama(value: String) {
        namaValue = value
    }

    fun updateTempatLahir(value: String) {
        tempatLahirValue = value
    }

    fun updateTanggalLahir(value: String) {
        tanggalLahirValue = dateFormatterToApiFormat(value)
    }

    fun updateGender(value: String) {
        selectedGender = value
    }

    fun updatePekerjaan(value: String) {
        pekerjaanValue = value
    }

    fun updateAlamat(value: String) {
        alamatValue = value
    }

    fun updateAgama(value: String) {
        agamaValue = value
    }

    fun updateKeperluan(value: String) {
        keperluanValue = value
    }

    // Pendatang specific field updates
    fun updateKewarganegaraan(value: String) {
        selectedKewarganegaraan = value
    }

    fun updateAlamatSesuaiKTP(value: String) {
        alamatSesuaiKTP = value
    }

    fun updateAlamatTinggalSekarang(value: String) {
        alamatTinggalSekarang = value
    }

    fun updateJumlahPengikut(value: String) {
        jumlahPengikut = value
    }

    fun updateCurrentTab(tab: Int) {
        currentTab = tab
    }

    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
    }

    fun showPreview() {
        showPreviewDialog = true
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    fun showConfirmationDialog() {
        isShowConfirmationDialog = true
    }

    fun dismissConfirmationDialog() {
        isShowConfirmationDialog = false
    }

    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        selectedGender = userData.jenis_kelamin
        pekerjaanValue = userData.pekerjaan
        alamatValue = userData.alamat
        agamaValue = userData.agama_id
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        agamaValue = ""
    }

    fun resetForm() {
        currentTab = 0
        useMyDataChecked = false
        clearUserData()
        // Reset pendatang fields
        selectedKewarganegaraan = ""
        alamatSesuaiKTP = ""
        alamatTinggalSekarang = ""
        jumlahPengikut = ""
        isShowConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                keperluanValue.isNotBlank() || alamatSesuaiKTP.isNotBlank() ||
                alamatTinggalSekarang.isNotBlank() || jumlahPengikut.isNotBlank()
    }

    fun getPreviewData(): Map<String, String> {
        val commonData = mapOf(
            "NIK" to nikValue,
            "Nama" to namaValue,
            "Tempat Lahir" to tempatLahirValue,
            "Tanggal Lahir" to tanggalLahirValue,
            "Jenis Kelamin" to selectedGender,
            "Pekerjaan" to pekerjaanValue,
            "Alamat" to alamatValue,
            "Agama" to agamaValue,
            "Keperluan" to keperluanValue
        )

        return if (currentTab == 0) {
            commonData
        } else {
            commonData + mapOf(
                "Kewarganegaraan" to selectedKewarganegaraan,
                "Alamat Sesuai Identitas" to alamatSesuaiKTP,
                "Alamat Tinggal Sekarang" to alamatTinggalSekarang,
                "Jumlah Pengikut" to jumlahPengikut
            )
        }
    }

    fun buildRequest(): SKDomisiliRequest {
        return if (currentTab == 0) {
            SKDomisiliRequest(
                alamat = alamatValue,
                alamat_identitas = alamatValue,
                disahkan_oleh = "",
                jenis_kelamin = selectedGender,
                jumlah_pengikut = 0,
                keperluan = keperluanValue,
                kewarganegaraan = "WNI",
                nama = namaValue,
                nik = nikValue,
                pekerjaan = pekerjaanValue,
                tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
                tempat_lahir = tempatLahirValue,
                warga_desa = true,
                agama_id = agamaValue
            )
        } else {
            SKDomisiliRequest(
                alamat = alamatTinggalSekarang,
                alamat_identitas = alamatSesuaiKTP,
                disahkan_oleh = "",
                jenis_kelamin = selectedGender,
                jumlah_pengikut = jumlahPengikut.toIntOrNull() ?: 0,
                keperluan = keperluanValue,
                kewarganegaraan = selectedKewarganegaraan,
                nama = namaValue,
                nik = nikValue,
                pekerjaan = pekerjaanValue,
                tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
                tempat_lahir = tempatLahirValue,
                warga_desa = false,
                agama_id = agamaValue
            )
        }
    }
}