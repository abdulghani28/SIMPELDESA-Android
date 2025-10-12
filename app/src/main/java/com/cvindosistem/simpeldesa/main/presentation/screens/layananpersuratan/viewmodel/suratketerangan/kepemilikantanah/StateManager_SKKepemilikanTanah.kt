package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKepemilikanTanahRequest

class SKKepemilikanTanahStateManager {
    // Form Data States - Step 1 (Data Pemohon)
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Informasi Tanah)
    var alamatValue by mutableStateOf("")
        private set
    var luasTanahValue by mutableStateOf("")
        private set
    var jenisTanahValue by mutableStateOf("")
        private set
    var batasUtaraValue by mutableStateOf("")
        private set
    var batasTimurValue by mutableStateOf("")
        private set
    var batasSelatanValue by mutableStateOf("")
        private set
    var batasBaratValue by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Bukti Kepemilikan)
    var atasNamaValue by mutableStateOf("")
        private set
    var asalKepemilikanTanahValue by mutableStateOf("")
        private set
    var buktiKepemilikanTanahValue by mutableStateOf("")
        private set
    var buktiKepemilikanTanahTanahValue by mutableStateOf("")
        private set
    var nomorBuktiKepemilikanValue by mutableStateOf("")
        private set

    // Form Data States - Step 4 (Keperluan)
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

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }

    // Step 2 Update Functions
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateLuasTanah(value: String) { luasTanahValue = value }
    fun updateJenisTanah(value: String) { jenisTanahValue = value }
    fun updateBatasUtara(value: String) { batasUtaraValue = value }
    fun updateBatasTimur(value: String) { batasTimurValue = value }
    fun updateBatasSelatan(value: String) { batasSelatanValue = value }
    fun updateBatasBarat(value: String) { batasBaratValue = value }

    // Step 3 Update Functions
    fun updateAtasNama(value: String) { atasNamaValue = value }
    fun updateAsalKepemilikanTanah(value: String) { asalKepemilikanTanahValue = value }
    fun updateBuktiKepemilikanTanah(value: String) { buktiKepemilikanTanahValue = value }
    fun updateBuktiKepemilikanTanahTanah(value: String) { buktiKepemilikanTanahTanahValue = value }
    fun updateNomorBuktiKepemilikan(value: String) { nomorBuktiKepemilikanValue = value }

    // Step 4 Update Functions
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 4) currentStep++ }
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
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        tempatLahirValue = userData.tempat_lahir ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        jenisKelaminValue = userData.jenis_kelamin ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        pekerjaanValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        alamatValue = ""
        luasTanahValue = ""
        jenisTanahValue = ""
        batasUtaraValue = ""
        batasTimurValue = ""
        batasSelatanValue = ""
        batasBaratValue = ""
        atasNamaValue = ""
        asalKepemilikanTanahValue = ""
        buktiKepemilikanTanahValue = ""
        buktiKepemilikanTanahTanahValue = ""
        nomorBuktiKepemilikanValue = ""
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                alamatValue.isNotBlank() || luasTanahValue.isNotBlank() ||
                jenisTanahValue.isNotBlank() || batasUtaraValue.isNotBlank() ||
                batasTimurValue.isNotBlank() || batasSelatanValue.isNotBlank() ||
                batasBaratValue.isNotBlank() || atasNamaValue.isNotBlank() ||
                asalKepemilikanTanahValue.isNotBlank() || buktiKepemilikanTanahValue.isNotBlank() ||
                buktiKepemilikanTanahTanahValue.isNotBlank() || nomorBuktiKepemilikanValue.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    fun toRequest(): SKKepemilikanTanahRequest {
        return SKKepemilikanTanahRequest(
            nik = nikValue,
            nama = namaValue,
            tempat_lahir = tempatLahirValue,
            tanggal_lahir = tanggalLahirValue,
            jenis_kelamin = jenisKelaminValue,
            pekerjaan = pekerjaanValue,
            alamat = alamatValue,
            luas_tanah = luasTanahValue,
            jenis_tanah = jenisTanahValue,
            batas_utara = batasUtaraValue,
            batas_timur = batasTimurValue,
            batas_selatan = batasSelatanValue,
            batas_barat = batasBaratValue,
            atas_nama = atasNamaValue,
            asal_kepemilikan_tanah = asalKepemilikanTanahValue,
            bukti_kepemilikan_tanah = buktiKepemilikanTanahValue,
            bukti_kepemilikan_tanah_tanah = buktiKepemilikanTanahTanahValue,
            nomor_bukti_kepemilikan = nomorBuktiKepemilikanValue,
            keperluan = keperluanValue
        )
    }
}