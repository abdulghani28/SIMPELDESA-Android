package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKIzinOrangTuaRequest

class SKIzinOrangTuaStateManager {
    // Form Data States - Step 1 (Informasi Pemberi Izin - Orang Tua)
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var kewarganegaraanValue by mutableStateOf("")
        private set
    var memberiIzinValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Informasi yang Diberi Izin - Anak)
    var nik2Value by mutableStateOf("")
        private set
    var nama2Value by mutableStateOf("")
        private set
    var alamat2Value by mutableStateOf("")
        private set
    var pekerjaan2Value by mutableStateOf("")
        private set
    var tanggalLahir2Value by mutableStateOf("")
        private set
    var tempatLahir2Value by mutableStateOf("")
        private set
    var agama2IdValue by mutableStateOf("")
        private set
    var kewarganegaraan2Value by mutableStateOf("")
        private set
    var diberiIzinValue by mutableStateOf("")
        private set
    var statusPekerjaanValue by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Informasi Pelengkap)
    var namaPerusahaanValue by mutableStateOf("")
        private set
    var negaraTujuanValue by mutableStateOf("")
        private set
    var masaKontrakValue by mutableStateOf("")
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
    var isLoadingAgama by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set

    // Reference Data
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updateKewarganegaraan(value: String) { kewarganegaraanValue = value }
    fun updateMemberiIzin(value: String) { memberiIzinValue = value }

    // Step 2 Update Functions
    fun updateNik2(value: String) { nik2Value = value }
    fun updateNama2(value: String) { nama2Value = value }
    fun updateAlamat2(value: String) { alamat2Value = value }
    fun updatePekerjaan2(value: String) { pekerjaan2Value = value }
    fun updateTanggalLahir2(value: String) { tanggalLahir2Value = value }
    fun updateTempatLahir2(value: String) { tempatLahir2Value = value }
    fun updateAgama2Id(value: String) { agama2IdValue = value }
    fun updateKewarganegaraan2(value: String) { kewarganegaraan2Value = value }
    fun updateDiberiIzin(value: String) { diberiIzinValue = value }
    fun updateStatusPekerjaan(value: String) { statusPekerjaanValue = value }

    // Step 3 Update Functions
    fun updateNamaPerusahaan(value: String) { namaPerusahaanValue = value }
    fun updateNegaraTujuan(value: String) { negaraTujuanValue = value }
    fun updateMasaKontrak(value: String) { masaKontrakValue = value }
    fun updateKeperluan(value: String) { keperluanValue = value }

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
    fun updateAgamaLoading(loading: Boolean) { isLoadingAgama = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun clearError() { errorMessage = null }

    // Data Setter
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        alamatValue = userData.alamat
        pekerjaanValue = userData.pekerjaan
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        tempatLahirValue = userData.tempat_lahir
        agamaIdValue = userData.agama_id
        kewarganegaraanValue = userData.kewarganegaraan
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        pekerjaanValue = ""
        tanggalLahirValue = ""
        tempatLahirValue = ""
        agamaIdValue = ""
        kewarganegaraanValue = ""
        memberiIzinValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        nik2Value = ""
        nama2Value = ""
        alamat2Value = ""
        pekerjaan2Value = ""
        tanggalLahir2Value = ""
        tempatLahir2Value = ""
        agama2IdValue = ""
        kewarganegaraan2Value = ""
        diberiIzinValue = ""
        statusPekerjaanValue = ""
        namaPerusahaanValue = ""
        negaraTujuanValue = ""
        masaKontrakValue = ""
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                tanggalLahirValue.isNotBlank() || tempatLahirValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || kewarganegaraanValue.isNotBlank() ||
                memberiIzinValue.isNotBlank() || nik2Value.isNotBlank() ||
                nama2Value.isNotBlank() || alamat2Value.isNotBlank() ||
                pekerjaan2Value.isNotBlank() || tanggalLahir2Value.isNotBlank() ||
                tempatLahir2Value.isNotBlank() || agama2IdValue.isNotBlank() ||
                kewarganegaraan2Value.isNotBlank() || diberiIzinValue.isNotBlank() ||
                statusPekerjaanValue.isNotBlank() || namaPerusahaanValue.isNotBlank() ||
                negaraTujuanValue.isNotBlank() || masaKontrakValue.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    fun toRequest(): SKIzinOrangTuaRequest {
        return SKIzinOrangTuaRequest(
            agama_2_id = agama2IdValue,
            agama_id = agamaIdValue,
            alamat = alamatValue,
            alamat_2 = alamat2Value,
            diberi_izin = diberiIzinValue,
            keperluan = keperluanValue,
            kewarganegaraan = kewarganegaraanValue,
            kewarganegaraan_2 = kewarganegaraan2Value,
            masa_kontrak = masaKontrakValue,
            memberi_izin = memberiIzinValue,
            nama = namaValue,
            nama_2 = nama2Value,
            nama_perusahaan = namaPerusahaanValue,
            negara_tujuan = negaraTujuanValue,
            nik = nikValue,
            nik_2 = nik2Value,
            pekerjaan = pekerjaanValue,
            pekerjaan_2 = pekerjaan2Value,
            status_pekerjaan = statusPekerjaanValue,
            tanggal_lahir = tanggalLahirValue,
            tanggal_lahir_2 = tanggalLahir2Value,
            tempat_lahir = tempatLahirValue,
            tempat_lahir_2 = tempatLahir2Value
        )
    }
}