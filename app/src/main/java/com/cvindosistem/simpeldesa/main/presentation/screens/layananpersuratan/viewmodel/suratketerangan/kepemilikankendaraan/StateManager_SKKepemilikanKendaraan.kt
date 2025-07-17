package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKepemilikanKendaraanRequest

class SKKepemilikanKendaraanStateManager {
    // Form Data States - Step 1 (Data Pemilik)
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Data Kendaraan)
    var merkValue by mutableStateOf("")
        private set
    var tahunPembuatanValue by mutableStateOf("")
        private set
    var warnaValue by mutableStateOf("")
        private set
    var nomorPolisiValue by mutableStateOf("")
        private set
    var nomorMesinValue by mutableStateOf("")
        private set
    var nomorRangkaValue by mutableStateOf("")
        private set
    var nomorBpkbValue by mutableStateOf("")
        private set
    var bahanBakarValue by mutableStateOf("")
        private set
    var isiSilinderValue by mutableStateOf("")
        private set
    var atasNamaValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
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

    // Loading States
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Step 1 Update Functions (Data Pemilik)
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }

    // Step 2 Update Functions (Data Kendaraan)
    fun updateMerk(value: String) { merkValue = value }
    fun updateTahunPembuatan(value: String) { tahunPembuatanValue = value }
    fun updateWarna(value: String) { warnaValue = value }
    fun updateNomorPolisi(value: String) { nomorPolisiValue = value }
    fun updateNomorMesin(value: String) { nomorMesinValue = value }
    fun updateNomorRangka(value: String) { nomorRangkaValue = value }
    fun updateNomorBpkb(value: String) { nomorBpkbValue = value }
    fun updateBahanBakar(value: String) { bahanBakarValue = value }
    fun updateIsiSilinder(value: String) { isiSilinderValue = value }
    fun updateAtasNama(value: String) { atasNamaValue = value }
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 2) currentStep++ }
    fun previousStep() { if (currentStep > 1) currentStep-- }

    // Dialog Functions
    fun showConfirmation() { showConfirmationDialog = true }
    fun hideConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun hidePreview() { showPreviewDialog = false }

    // Use My Data Function
    fun updateUseMyDataChecked(checked: Boolean) { useMyDataChecked = checked }

    // Loading State Functions
    fun updateLoadingState(loading: Boolean) { isLoading = loading }
    fun updateUserDataLoading(loading: Boolean) { isLoadingUserData = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun clearError() { errorMessage = null }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        alamatValue = userData.alamat
        jenisKelaminValue = userData.jenis_kelamin
        pekerjaanValue = userData.pekerjaan
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        alamatValue = ""
        jenisKelaminValue = ""
        pekerjaanValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear step 2 data
        merkValue = ""
        tahunPembuatanValue = ""
        warnaValue = ""
        nomorPolisiValue = ""
        nomorMesinValue = ""
        nomorRangkaValue = ""
        nomorBpkbValue = ""
        bahanBakarValue = ""
        isiSilinderValue = ""
        atasNamaValue = ""
        keperluanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                alamatValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                pekerjaanValue.isNotBlank() || merkValue.isNotBlank() ||
                tahunPembuatanValue.isNotBlank() || warnaValue.isNotBlank() ||
                nomorPolisiValue.isNotBlank() || nomorMesinValue.isNotBlank() ||
                nomorRangkaValue.isNotBlank() || nomorBpkbValue.isNotBlank() ||
                bahanBakarValue.isNotBlank() || isiSilinderValue.isNotBlank() ||
                atasNamaValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SKKepemilikanKendaraanRequest {
        return SKKepemilikanKendaraanRequest(
            nik = nikValue,
            nama = namaValue,
            tempat_lahir = tempatLahirValue,
            tanggal_lahir = tanggalLahirValue,
            alamat = alamatValue,
            jenis_kelamin = jenisKelaminValue,
            pekerjaan = pekerjaanValue,
            merk = merkValue,
            tahun_pembuatan = tahunPembuatanValue,
            warna = warnaValue,
            nomor_polisi = nomorPolisiValue,
            nomor_mesin = nomorMesinValue,
            nomor_rangka = nomorRangkaValue,
            nomor_bpkb = nomorBpkbValue,
            bahan_bakar = bahanBakarValue,
            isi_silinder = isiSilinderValue,
            atas_nama = atasNamaValue,
            keperluan = keperluanValue
        )
    }
}