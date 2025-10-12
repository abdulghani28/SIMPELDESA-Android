package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpernyataan.SPNPenguasaanFisikBidangTanahRequest

class SPNPenguasaanFisikBidangTanahStateManager {
    // Step 1: Data Pemohon
    var nikPemohonValue by mutableStateOf("")
        private set
    var namaPemohonValue by mutableStateOf("")
        private set
    var tempatLahirPemohonValue by mutableStateOf("")
        private set
    var tanggalLahirPemohonValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set

    // Step 2: Lokasi & Identitas Tanah
    var alamatValue by mutableStateOf("")
        private set
    var alamat1Value by mutableStateOf("")
        private set
    var alamat2Value by mutableStateOf("")
        private set
    var rtRwValue by mutableStateOf("")
        private set
    var jalanValue by mutableStateOf("")
        private set
    var desaValue by mutableStateOf("")
        private set
    var kecamatanValue by mutableStateOf("")
        private set
    var kabupatenValue by mutableStateOf("")
        private set
    var nibValue by mutableStateOf("")
        private set
    var luasTanahValue by mutableStateOf("")
        private set
    var statusTanahValue by mutableStateOf("")
        private set
    var diperggunakanValue by mutableStateOf("")
        private set

    // Step 3: Perolehan & Batas Tanah
    var diperolehDariValue by mutableStateOf("")
        private set
    var diperolehDenganValue by mutableStateOf("")
        private set
    var diperolehSejakValue by mutableStateOf("")
        private set
    var batasUtaraValue by mutableStateOf("")
        private set
    var batasTimurValue by mutableStateOf("")
        private set
    var batasSelatanValue by mutableStateOf("")
        private set
    var batasBaratValue by mutableStateOf("")
        private set

    // Step 4: Data Saksi
    var nik1Value by mutableStateOf("")
        private set
    var nama1Value by mutableStateOf("")
        private set
    var pekerjaan1Value by mutableStateOf("")
        private set
    var isSaksi1WargaDesaValue by mutableStateOf(false)
        private set
    var nik2Value by mutableStateOf("")
        private set
    var nama2Value by mutableStateOf("")
        private set
    var pekerjaan2Value by mutableStateOf("")
        private set
    var isSaksi2WargaDesaValue by mutableStateOf(false)
        private set

    // Step 5: Keperluan
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
    fun updateNikPemohon(value: String) { nikPemohonValue = value }
    fun updateNamaPemohon(value: String) { namaPemohonValue = value }
    fun updateTempatLahirPemohon(value: String) { tempatLahirPemohonValue = value }
    fun updateTanggalLahirPemohon(value: String) { tanggalLahirPemohonValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }

    // Step 2 Update Functions
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateAlamat1(value: String) { alamat1Value = value }
    fun updateAlamat2(value: String) { alamat2Value = value }
    fun updateRtRw(value: String) { rtRwValue = value }
    fun updateJalan(value: String) { jalanValue = value }
    fun updateDesa(value: String) { desaValue = value }
    fun updateKecamatan(value: String) { kecamatanValue = value }
    fun updateKabupaten(value: String) { kabupatenValue = value }
    fun updateNib(value: String) { nibValue = value }
    fun updateLuasTanah(value: String) { luasTanahValue = value }
    fun updateStatusTanah(value: String) { statusTanahValue = value }
    fun updateDipergunakan(value: String) { diperggunakanValue = value }

    // Step 3 Update Functions
    fun updateDiperolehDari(value: String) { diperolehDariValue = value }
    fun updateDiperolehDengan(value: String) { diperolehDenganValue = value }
    fun updateDiperolehSejak(value: String) { diperolehSejakValue = value }
    fun updateBatasUtara(value: String) { batasUtaraValue = value }
    fun updateBatasTimur(value: String) { batasTimurValue = value }
    fun updateBatasSelatan(value: String) { batasSelatanValue = value }
    fun updateBatasBarat(value: String) { batasBaratValue = value }

    // Step 4 Update Functions
    fun updateNik1(value: String) { nik1Value = value }
    fun updateNama1(value: String) { nama1Value = value }
    fun updatePekerjaan1(value: String) { pekerjaan1Value = value }
    fun updateIsSaksi1WargaDesa(value: Boolean) { isSaksi1WargaDesaValue = value }
    fun updateNik2(value: String) { nik2Value = value }
    fun updateNama2(value: String) { nama2Value = value }
    fun updatePekerjaan2(value: String) { pekerjaan2Value = value }
    fun updateIsSaksi2WargaDesa(value: Boolean) { isSaksi2WargaDesaValue = value }

    // Step 5 Update Functions
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 5) currentStep++ }
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
        nikPemohonValue = userData.nik ?: ""
        namaPemohonValue = userData.nama_warga ?: ""
        tempatLahirPemohonValue = userData.tempat_lahir ?: ""
        tanggalLahirPemohonValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        pekerjaanValue = userData.pekerjaan ?: ""
    }

    fun clearUserData() {
        nikPemohonValue = ""
        namaPemohonValue = ""
        tempatLahirPemohonValue = ""
        tanggalLahirPemohonValue = ""
        pekerjaanValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear Step 2
        alamatValue = ""
        alamat1Value = ""
        alamat2Value = ""
        rtRwValue = ""
        jalanValue = ""
        desaValue = ""
        kecamatanValue = ""
        kabupatenValue = ""
        nibValue = ""
        luasTanahValue = ""
        statusTanahValue = ""
        diperggunakanValue = ""

        // Clear Step 3
        diperolehDariValue = ""
        diperolehDenganValue = ""
        diperolehSejakValue = ""
        batasUtaraValue = ""
        batasTimurValue = ""
        batasSelatanValue = ""
        batasBaratValue = ""

        // Clear Step 4
        nik1Value = ""
        nama1Value = ""
        pekerjaan1Value = ""
        isSaksi1WargaDesaValue = false
        nik2Value = ""
        nama2Value = ""
        pekerjaan2Value = ""
        isSaksi2WargaDesaValue = false

        // Clear Step 5
        keperluanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikPemohonValue.isNotBlank() || namaPemohonValue.isNotBlank() ||
                tempatLahirPemohonValue.isNotBlank() || tanggalLahirPemohonValue.isNotBlank() ||
                pekerjaanValue.isNotBlank() || alamatValue.isNotBlank() ||
                alamat1Value.isNotBlank() || alamat2Value.isNotBlank() ||
                rtRwValue.isNotBlank() || jalanValue.isNotBlank() ||
                desaValue.isNotBlank() || kecamatanValue.isNotBlank() ||
                kabupatenValue.isNotBlank() || nibValue.isNotBlank() ||
                luasTanahValue.isNotBlank() || statusTanahValue.isNotBlank() ||
                diperggunakanValue.isNotBlank() || diperolehDariValue.isNotBlank() ||
                diperolehDenganValue.isNotBlank() || diperolehSejakValue.isNotBlank() ||
                batasUtaraValue.isNotBlank() || batasTimurValue.isNotBlank() ||
                batasSelatanValue.isNotBlank() || batasBaratValue.isNotBlank() ||
                nik1Value.isNotBlank() || nama1Value.isNotBlank() ||
                pekerjaan1Value.isNotBlank() || nik2Value.isNotBlank() ||
                nama2Value.isNotBlank() || pekerjaan2Value.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    fun toRequest(): SPNPenguasaanFisikBidangTanahRequest {
        return SPNPenguasaanFisikBidangTanahRequest(
            alamat = alamatValue,
            alamat_1 = alamat1Value,
            alamat_2 = alamat2Value,
            batas_barat = batasBaratValue,
            batas_selatan = batasSelatanValue,
            batas_timur = batasTimurValue,
            batas_utara = batasUtaraValue,
            desa = desaValue,
            dipergunakan = diperggunakanValue,
            diperoleh_dari = diperolehDariValue,
            diperoleh_dengan = diperolehDenganValue,
            diperoleh_sejak = diperolehSejakValue,
            is_saksi1_warga_desa = isSaksi1WargaDesaValue,
            is_saksi2_warga_desa = isSaksi2WargaDesaValue,
            jalan = jalanValue,
            kabupaten = kabupatenValue,
            kecamatan = kecamatanValue,
            keperluan = keperluanValue,
            luas_tanah = luasTanahValue,
            nama_1 = nama1Value,
            nama_2 = nama2Value,
            nama_pemohon = namaPemohonValue,
            nib = nibValue,
            nik_1 = nik1Value,
            nik_2 = nik2Value,
            nik_pemohon = nikPemohonValue,
            pekerjaan = pekerjaanValue,
            pekerjaan_1 = pekerjaan1Value,
            pekerjaan_2 = pekerjaan2Value,
            rt_rw = rtRwValue,
            status_tanah = statusTanahValue,
            tanggal_lahir_pemohon = tanggalLahirPemohonValue,
            tempat_lahir_pemohon = tempatLahirPemohonValue
        )
    }
}