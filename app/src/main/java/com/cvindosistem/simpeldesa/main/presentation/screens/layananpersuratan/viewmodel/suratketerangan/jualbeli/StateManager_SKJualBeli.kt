package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJualBeliRequest

class SKJualBeliStateManager {
    // Form Data States - Step 1 (Penjual)
    var nik1Value by mutableStateOf("")
        private set
    var nama1Value by mutableStateOf("")
        private set
    var alamat1Value by mutableStateOf("")
        private set
    var jenisKelamin1Value by mutableStateOf("")
        private set
    var pekerjaan1Value by mutableStateOf("")
        private set
    var tanggalLahir1Value by mutableStateOf("")
        private set
    var tempatLahir1Value by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Pembeli)
    var nik2Value by mutableStateOf("")
        private set
    var nama2Value by mutableStateOf("")
        private set
    var alamat2Value by mutableStateOf("")
        private set
    var jenisKelamin2Value by mutableStateOf("")
        private set
    var pekerjaan2Value by mutableStateOf("")
        private set
    var tanggalLahir2Value by mutableStateOf("")
        private set
    var tempatLahir2Value by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Detail Jual Beli)
    var jenisBarangValue by mutableStateOf("")
        private set
    var rincianBarangValue by mutableStateOf("")
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

    // Step 1 Update Functions (Penjual)
    fun updateNik1(value: String) { nik1Value = value }
    fun updateNama1(value: String) { nama1Value = value }
    fun updateAlamat1(value: String) { alamat1Value = value }
    fun updateJenisKelamin1(value: String) { jenisKelamin1Value = value }
    fun updatePekerjaan1(value: String) { pekerjaan1Value = value }
    fun updateTanggalLahir1(value: String) { tanggalLahir1Value = value }
    fun updateTempatLahir1(value: String) { tempatLahir1Value = value }

    // Step 2 Update Functions (Pembeli)
    fun updateNik2(value: String) { nik2Value = value }
    fun updateNama2(value: String) { nama2Value = value }
    fun updateAlamat2(value: String) { alamat2Value = value }
    fun updateJenisKelamin2(value: String) { jenisKelamin2Value = value }
    fun updatePekerjaan2(value: String) { pekerjaan2Value = value }
    fun updateTanggalLahir2(value: String) { tanggalLahir2Value = value }
    fun updateTempatLahir2(value: String) { tempatLahir2Value = value }

    // Step 3 Update Functions (Detail Jual Beli)
    fun updateJenisBarang(value: String) { jenisBarangValue = value }
    fun updateRincianBarang(value: String) { rincianBarangValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 3) currentStep++ }
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
        nik1Value = userData.nik ?: ""
        nama1Value = userData.nama_warga ?: ""
        alamat1Value = userData.alamat ?: ""
        jenisKelamin1Value = userData.jenis_kelamin ?: ""
        pekerjaan1Value = userData.pekerjaan ?: ""
        tanggalLahir1Value = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        tempatLahir1Value = userData.tempat_lahir ?: ""
    }

    fun clearUserData() {
        nik1Value = ""
        nama1Value = ""
        alamat1Value = ""
        jenisKelamin1Value = ""
        pekerjaan1Value = ""
        tanggalLahir1Value = ""
        tempatLahir1Value = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear step 2 data
        nik2Value = ""
        nama2Value = ""
        alamat2Value = ""
        jenisKelamin2Value = ""
        pekerjaan2Value = ""
        tanggalLahir2Value = ""
        tempatLahir2Value = ""

        // Clear step 3 data
        jenisBarangValue = ""
        rincianBarangValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nik1Value.isNotBlank() || nama1Value.isNotBlank() ||
                alamat1Value.isNotBlank() || jenisKelamin1Value.isNotBlank() ||
                pekerjaan1Value.isNotBlank() || tanggalLahir1Value.isNotBlank() ||
                tempatLahir1Value.isNotBlank() || nik2Value.isNotBlank() ||
                nama2Value.isNotBlank() || alamat2Value.isNotBlank() ||
                jenisKelamin2Value.isNotBlank() || pekerjaan2Value.isNotBlank() ||
                tanggalLahir2Value.isNotBlank() || tempatLahir2Value.isNotBlank() ||
                jenisBarangValue.isNotBlank() || rincianBarangValue.isNotBlank()
    }

    fun toRequest(): SKJualBeliRequest {
        return SKJualBeliRequest(
            nik_1 = nik1Value,
            nama_1 = nama1Value,
            alamat_1 = alamat1Value,
            jenis_kelamin_1 = jenisKelamin1Value,
            pekerjaan_1 = pekerjaan1Value,
            tanggal_lahir_1 = tanggalLahir1Value,
            tempat_lahir_1 = tempatLahir1Value,
            nik_2 = nik2Value,
            nama_2 = nama2Value,
            alamat_2 = alamat2Value,
            jenis_kelamin_2 = jenisKelamin2Value,
            pekerjaan_2 = pekerjaan2Value,
            tanggal_lahir_2 = tanggalLahir2Value,
            tempat_lahir_2 = tempatLahir2Value,
            jenis_barang = jenisBarangValue,
            rincian_barang = rincianBarangValue
        )
    }
}