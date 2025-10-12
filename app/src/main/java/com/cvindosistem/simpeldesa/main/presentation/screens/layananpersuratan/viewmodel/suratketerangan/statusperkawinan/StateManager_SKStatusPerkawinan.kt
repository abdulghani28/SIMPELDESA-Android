package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKStatusPerkawinanRequest

class SKStatusPerkawinanStateManager {
    // Form data states
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
    var statusKawinValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
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
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set

    // Update methods
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = dateFormatterToApiFormat(value) }
    fun updateGender(value: String) { selectedGender = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateAgama(value: String) { agamaValue = value }
    fun updateStatusKawin(value: String) { statusKawinValue = value }
    fun updateKeperluan(value: String) { keperluanValue = value }

    // State management methods
    fun updateLoading(loading: Boolean) { isLoading = loading }
    fun setError(error: String?) { errorMessage = error }
    fun setUserDataLoading(loading: Boolean) { isLoadingUserData = loading }

    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }
    fun showConfirmation() { showConfirmationDialog = true }
    fun dismissConfirmation() { showConfirmationDialog = false }

    fun setUseMyData(checked: Boolean) { useMyDataChecked = checked }

    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        tempatLahirValue = userData.tempat_lahir ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        selectedGender = userData.jenis_kelamin ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        alamatValue = userData.alamat ?: ""
        agamaValue = userData.agama_id ?: ""
        statusKawinValue = userData.status_kawin_id ?: ""
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
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun createRequest(): SKStatusPerkawinanRequest {
        return SKStatusPerkawinanRequest(
            alamat = alamatValue,
            disahkan_oleh = "",
            jenis_kelamin = selectedGender,
            nama = namaValue,
            nik = nikValue,
            pekerjaan = pekerjaanValue,
            tanggal_lahir = dateFormatterToApiFormat(tanggalLahirValue),
            tempat_lahir = tempatLahirValue,
            keperluan = keperluanValue,
            agama_id = agamaValue,
            status_kawin_id = statusKawinValue
        )
    }
}