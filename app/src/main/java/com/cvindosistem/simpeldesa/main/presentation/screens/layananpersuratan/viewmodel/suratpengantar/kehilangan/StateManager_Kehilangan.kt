package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.kehilangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest

class SPKehilanganStateManager {
    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Barang Hilang
    var jenisBarangValue by mutableStateOf("")
        private set
    var ciriCiriBarangValue by mutableStateOf("")
        private set
    var tempatKehilanganValue by mutableStateOf("")
        private set
    var tanggalKehilanganValue by mutableStateOf("")
        private set

    // UI States
    var currentStep by mutableIntStateOf(1)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Step 1 Updates
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateAlamat(value: String) { alamatValue = value }

    // Step 2 Updates
    fun updateTanggalKehilangan(value: String) { tanggalKehilanganValue = value }
    fun updateJenisBarang(value: String) { jenisBarangValue = value }
    fun updateCiriCiriBarang(value: String) { ciriCiriBarangValue = value }
    fun updateTempatKehilangan(value: String) { tempatKehilanganValue = value }

    // Navigation
    fun nextStep() { if (currentStep < 2) currentStep++ }
    fun previousStep() { if (currentStep > 1) currentStep-- }

    // Dialog Management
    fun showConfirmationDialog() { showConfirmationDialog = true }
    fun dismissConfirmationDialog() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Use My Data
    fun updateUseMyData(checked: Boolean) { useMyDataChecked = checked }

    // Data Population
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        tempatLahirValue = userData.tempat_lahir ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        jenisKelaminValue = userData.jenis_kelamin ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        alamatValue = userData.alamat ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        pekerjaanValue = ""
        alamatValue = ""
        jenisKelaminValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        jenisBarangValue = ""
        ciriCiriBarangValue = ""
        tempatKehilanganValue = ""
        tanggalKehilanganValue = ""
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank()
    }

    fun createRequest(): SPKehilanganRequest {
        return SPKehilanganRequest(
            disahkan_oleh = "",
            alamat = alamatValue,
            ciri = ciriCiriBarangValue,
            jenis_barang = jenisBarangValue,
            jenis_kelamin = jenisKelaminValue,
            keperluan = "",
            nama = namaValue,
            nik = nikValue,
            pekerjaan = pekerjaanValue,
            tanggal_lahir = tanggalLahirValue,
            tempat_kehilangan = tempatKehilanganValue,
            tempat_lahir = tempatLahirValue,
            waktu_kehilangan = tanggalKehilanganValue,
        )
    }
}