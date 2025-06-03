package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ghaib

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest

class SKGhaibStateManager {
    // Form states
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var hubunganIdValue by mutableStateOf("")
        private set
    var namaOrangHilangValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var usiaValue by mutableIntStateOf(0)
        private set
    var alamatValue by mutableStateOf("")
        private set
    var hilangSejakValue by mutableStateOf("")
        private set

    // UI states
    var currentStep by mutableIntStateOf(1)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Update functions untuk Step 1
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateHubunganId(value: String) { hubunganIdValue = value }

    // Update functions untuk Step 2
    fun updateNamaOrangHilang(value: String) { namaOrangHilangValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateUsia(value: Int) { usiaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateHilangSejak(value: String) { hilangSejakValue = value }

    // Navigation functions
    fun nextStep() { if (currentStep < 2) currentStep++ }
    fun previousStep() { if (currentStep > 1) currentStep-- }

    // Dialog functions
    fun showConfirmation() { showConfirmationDialog = true }
    fun dismissConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Use My Data functions
    fun setUseMyData(checked: Boolean) { useMyDataChecked = checked }

    fun loadUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                hubunganIdValue.isNotBlank() || namaOrangHilangValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || usiaValue > 0 ||
                alamatValue.isNotBlank() || hilangSejakValue.isNotBlank()
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        nikValue = ""
        namaValue = ""
        hubunganIdValue = ""
        namaOrangHilangValue = ""
        jenisKelaminValue = ""
        usiaValue = 0
        alamatValue = ""
        hilangSejakValue = ""
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun createRequest(): SKGhaibRequest {
        return SKGhaibRequest(
            alamat = alamatValue,
            disahkan_oleh = "",
            hilang_sejak = hilangSejakValue,
            hubungan_id = hubunganIdValue,
            jenis_kelamin = jenisKelaminValue,
            keperluan = "",
            nama = namaValue,
            nama_orang_hilang = namaOrangHilangValue,
            nik = nikValue,
            usia = usiaValue
        )
    }
}