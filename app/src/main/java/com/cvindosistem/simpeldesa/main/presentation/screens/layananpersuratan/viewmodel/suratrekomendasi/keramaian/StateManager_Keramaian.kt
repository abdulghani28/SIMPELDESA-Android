package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi.keramaian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest

class SRKeramaianStateManager {
    // Step navigation state
    var currentStep by mutableIntStateOf(1)
        private set

    var useMyDataChecked by mutableStateOf(false)
        private set

    var isLoadingUserData by mutableStateOf(false)
        private set

    var showConfirmationDialog by mutableStateOf(false)
        private set

    var showPreviewDialog by mutableStateOf(false)
        private set

    // Step 1 - Informasi Pelapor
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

    // Step 2 - Informasi Kegiatan
    var namaAcaraValue by mutableStateOf("")
        private set
    var tempatAcaraValue by mutableStateOf("")
        private set
    var hariValue by mutableStateOf("")
        private set
    var tanggalValue by mutableStateOf("")
        private set
    var jamMulaiValue by mutableStateOf("")
        private set
    var jamSelesaiValue by mutableStateOf("")
        private set
    var penanggungJawabValue by mutableStateOf("")
        private set
    var kontakValue by mutableStateOf("")
        private set

    // Step 1 field updates
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = dateFormatterToApiFormat(value) }
    fun updateGender(value: String) { selectedGender = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateAlamat(value: String) { alamatValue = value }

    // Step 2 field updates
    fun updateNamaAcara(value: String) {
        namaAcaraValue = value
    }

    fun updateTempatAcara(value: String) {
        tempatAcaraValue = value
    }

    fun updateHari(value: String) {
        hariValue = value
    }

    fun updateTanggal(value: String) {
        tanggalValue = value
    }

    fun updateJamMulai(value: String) {
        jamMulaiValue = value
    }

    fun updateJamSelesai(value: String) {
        jamSelesaiValue = value
    }

    fun updatePenanggungJawab(value: String) {
        penanggungJawabValue = value
    }

    fun updateKontak(value: String) {
        kontakValue = value
    }

    // Navigation functions
    fun nextStep() { if (currentStep < 2) currentStep += 1 }
    fun previousStep() { if (currentStep > 1) currentStep -= 1 }

    // Dialog functions
    fun showConfirmation() { showConfirmationDialog = true }
    fun dismissConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Use My Data functions
    fun setUseMyData(checked: Boolean) { useMyDataChecked = checked }
    fun updateLoadingUserData(loading: Boolean) { isLoadingUserData = loading }

    // Bulk update user data
    fun updateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        selectedGender = userData.jenis_kelamin
        pekerjaanValue = userData.pekerjaan
        alamatValue = userData.alamat
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        namaAcaraValue = ""
        tempatAcaraValue = ""
        hariValue = ""
        tanggalValue = ""
        jamMulaiValue = ""
        jamSelesaiValue = ""
        penanggungJawabValue = ""
        kontakValue = ""
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                namaAcaraValue.isNotBlank() || tempatAcaraValue.isNotBlank()
    }

    fun createSubmissionRequest(): SRKeramaianRequest {
        return SRKeramaianRequest(
            alamat = alamatValue,
            dimulai = jamMulaiValue,
            disahkan_oleh = "",
            hari = hariValue,
            jenis_kelamin = selectedGender,
            kontak = kontakValue,
            nama = namaValue,
            nama_acara = namaAcaraValue,
            nik = nikValue,
            pekerjaan = pekerjaanValue,
            penanggung_jawab = penanggungJawabValue,
            selesai = jamSelesaiValue,
            tanggal = dateFormatterToApiFormat(tanggalValue),
            tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
            tempat_acara = tempatAcaraValue,
            tempat_lahir = tempatLahirValue
        )
    }
}