package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPernikahanRequest

class SPPernikahanStateManager {
    // Step navigation
    var currentStep by mutableIntStateOf(1)
        private set

    // Loading states
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set
    var isLoadingAgama by mutableStateOf(false)
        private set
    var isLoadingStatusKawin by mutableStateOf(false)
        private set

    // Error states
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set
    var statusKawinErrorMessage by mutableStateOf<String?>(null)
        private set

    // Dialog states
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set

    // Data lists
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
        private set

    // Step 1 - Informasi Calon Suami
    var nikSuamiValue by mutableStateOf("")
        private set
    var namaSuamiValue by mutableStateOf("")
        private set
    var tempatLahirSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirSuamiValue by mutableStateOf("")
        private set
    var pekerjaanSuamiValue by mutableStateOf("")
        private set
    var alamatSuamiValue by mutableStateOf("")
        private set
    var agamaSuamiIdValue by mutableStateOf("")
        private set
    var kewarganegaraanSuamiValue by mutableStateOf("")
        private set
    var jumlahIstriValue by mutableIntStateOf(0)
        private set
    var statusKawinSuamiIdValue by mutableStateOf("")
        private set
    var namaIstriSebelumnyaValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Orang Tua Suami
    var agamaAyahSuamiIdValue by mutableStateOf("")
        private set
    var alamatAyahSuamiValue by mutableStateOf("")
        private set
    var kewarganegaraanAyahSuamiValue by mutableStateOf("")
        private set
    var namaAyahSuamiValue by mutableStateOf("")
        private set
    var nikAyahSuamiValue by mutableStateOf("")
        private set
    var pekerjaanAyahSuamiValue by mutableStateOf("")
        private set
    var tempatLahirAyahSuamiValue by mutableStateOf("")
        private set
    var tanggalLahiAyahSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirAyahSuamiValue by mutableStateOf("")
        private set
    var agamaIbuSuamiIdValue by mutableStateOf("")
        private set
    var alamatIbuSuamiValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuSuamiValue by mutableStateOf("")
        private set
    var namaIbuSuamiValue by mutableStateOf("")
        private set
    var nikIbuSuamiValue by mutableStateOf("")
        private set
    var pekerjaanIbuSuamiValue by mutableStateOf("")
        private set
    var tanggalLahiIbuSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirIbuSuamiValue by mutableStateOf("")
        private set
    var tempatLahirIbuSuamiValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Calon Istri
    var agamaIstriIdValue by mutableStateOf("")
        private set
    var alamatIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanIstriValue by mutableStateOf("")
        private set
    var namaIstriValue by mutableStateOf("")
        private set
    var nikIstriValue by mutableStateOf("")
        private set
    var pekerjaanIstriValue by mutableStateOf("")
        private set
    var statusKawinIstriIdValue by mutableStateOf("")
        private set
    var tanggalLahirIstriValue by mutableStateOf("")
        private set
    var tempatLahirIstriValue by mutableStateOf("")
        private set
    var namaSuamiSebelumnyaValue by mutableStateOf("")
        private set

    // Step 4 - Informasi Orang Tua Calon Istri
    var agamaAyahIstriIdValue by mutableStateOf("")
        private set
    var agamaIbuIstriIdValue by mutableStateOf("")
        private set
    var alamatAyahIstriValue by mutableStateOf("")
        private set
    var alamatIbuIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanAyahIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuIstriValue by mutableStateOf("")
        private set
    var namaAyahIstriValue by mutableStateOf("")
        private set
    var namaIbuIstriValue by mutableStateOf("")
        private set
    var nikAyahIstriValue by mutableStateOf("")
        private set
    var nikIbuIstriValue by mutableStateOf("")
        private set
    var pekerjaanAyahIstriValue by mutableStateOf("")
        private set
    var pekerjaanIbuIstriValue by mutableStateOf("")
        private set
    var tanggalLahiAyahIstriValue by mutableStateOf("")
        private set
    var tanggalLahiIbuIstriValue by mutableStateOf("")
        private set
    var tanggalLahirAyahIstriValue by mutableStateOf("")
        private set
    var tanggalLahirIbuIstriValue by mutableStateOf("")
        private set
    var tempatLahirAyahIstriValue by mutableStateOf("")
        private set
    var tempatLahirIbuIstriValue by mutableStateOf("")
        private set

    // Step 5 - Informasi Pernikahan
    var jamPernikahanValue by mutableStateOf("")
        private set
    var tempatPernikahanValue by mutableStateOf("")
        private set
    var hariPernikahanValue by mutableStateOf("")
        private set
    var tanggalPernikahanValue by mutableStateOf("")
        private set

    // State update methods
    fun updateCurrentStep(step: Int) {
        currentStep = step
    }

    fun updateLoading(loading: Boolean) {
        isLoading = loading
    }

    fun updateUserDataLoading(loading: Boolean) {
        isLoadingUserData = loading
    }

    fun updateAgamaLoading(loading: Boolean) {
        isLoadingAgama = loading
    }

    fun updateStatusKawinLoading(loading: Boolean) {
        isLoadingStatusKawin = loading
    }

    fun updateErrorMessage(message: String?) {
        errorMessage = message
    }

    fun updateAgamaErrorMessage(message: String?) {
        agamaErrorMessage = message
    }

    fun updateStatusKawinErrorMessage(message: String?) {
        statusKawinErrorMessage = message
    }

    fun updateConfirmationDialog(show: Boolean) {
        showConfirmationDialog = show
    }

    fun updatePreviewDialog(show: Boolean) {
        showPreviewDialog = show
    }

    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
    }

    fun updateAgamaList(list: List<AgamaResponse.Data>) {
        agamaList = list
    }

    fun updateStatusKawinList(list: List<StatusKawinResponse.Data>) {
        statusKawinList = list
    }

    // Form field updates - Step 1
    fun updateNikSuami(value: String) {
        nikSuamiValue = value
    }

    fun updateNamaSuami(value: String) {
        namaSuamiValue = value
    }

    fun updateTempatLahirSuami(value: String) {
        tempatLahirSuamiValue = value
    }

    fun updateTanggalLahirSuami(value: String) {
        tanggalLahirSuamiValue = value
    }

    fun updatePekerjaanSuami(value: String) {
        pekerjaanSuamiValue = value
    }

    fun updateAlamatSuami(value: String) {
        alamatSuamiValue = value
    }

    fun updateAgamaSuamiId(value: String) {
        agamaSuamiIdValue = value
    }

    fun updateKewarganegaraanSuami(value: String) {
        kewarganegaraanSuamiValue = value
    }

    fun updateJumlahIstri(value: Int) {
        jumlahIstriValue = value
    }

    fun updateStatusKawinSuamiId(value: String) {
        statusKawinSuamiIdValue = value
    }

    fun updateNamaIstriSebelumnya(value: String) {
        namaIstriSebelumnyaValue = value
    }

    fun updateAgamaAyahSuamiId(value: String) { agamaAyahSuamiIdValue = value }
    fun updateAlamatAyahSuami(value: String) { alamatAyahSuamiValue = value }
    fun updateKewarganegaraanAyahSuami(value: String) { kewarganegaraanAyahSuamiValue = value }
    fun updateNamaAyahSuami(value: String) { namaAyahSuamiValue = value }
    fun updateNikAyahSuami(value: String) { nikAyahSuamiValue = value }
    fun updatePekerjaanAyahSuami(value: String) { pekerjaanAyahSuamiValue = value }
    fun updateTempatLahirAyahSuami(value: String) { tempatLahirAyahSuamiValue = value }
    fun updateTanggalLahiAyahSuami(value: String) { tanggalLahiAyahSuamiValue = value }
    fun updateTanggalLahirAyahSuami(value: String) { tanggalLahirAyahSuamiValue = value }

    fun updateAgamaIbuSuamiId(value: String) { agamaIbuSuamiIdValue = value }
    fun updateAlamatIbuSuami(value: String) { alamatIbuSuamiValue = value }
    fun updateKewarganegaraanIbuSuami(value: String) { kewarganegaraanIbuSuamiValue = value }
    fun updateNamaIbuSuami(value: String) { namaIbuSuamiValue = value }
    fun updateNikIbuSuami(value: String) { nikIbuSuamiValue = value }
    fun updatePekerjaanIbuSuami(value: String) { pekerjaanIbuSuamiValue = value }
    fun updateTanggalLahiIbuSuami(value: String) { tanggalLahiIbuSuamiValue = value }
    fun updateTanggalLahirIbuSuami(value: String) { tanggalLahirIbuSuamiValue = value }
    fun updateTempatLahirIbuSuami(value: String) { tempatLahirIbuSuamiValue = value }

    fun updateAgamaIstriId(value: String) { agamaIstriIdValue = value }
    fun updateAlamatIstri(value: String) { alamatIstriValue = value }
    fun updateKewarganegaraanIstri(value: String) { kewarganegaraanIstriValue = value }
    fun updateNamaIstri(value: String) { namaIstriValue = value }
    fun updateNikIstri(value: String) { nikIstriValue = value }
    fun updatePekerjaanIstri(value: String) { pekerjaanIstriValue = value }
    fun updateStatusKawinIstriId(value: String) { statusKawinIstriIdValue = value }
    fun updateTanggalLahirIstri(value: String) { tanggalLahirIstriValue = value }
    fun updateTempatLahirIstri(value: String) { tempatLahirIstriValue = value }
    fun updateNamaSuamiSebelumnya(value: String) { namaSuamiSebelumnyaValue = value }

    fun updateAgamaAyahIstriId(value: String) { agamaAyahIstriIdValue = value }
    fun updateAgamaIbuIstriId(value: String) { agamaIbuIstriIdValue = value }
    fun updateAlamatAyahIstri(value: String) { alamatAyahIstriValue = value }
    fun updateAlamatIbuIstri(value: String) { alamatIbuIstriValue = value }
    fun updateKewarganegaraanAyahIstri(value: String) { kewarganegaraanAyahIstriValue = value }
    fun updateKewarganegaraanIbuIstri(value: String) { kewarganegaraanIbuIstriValue = value }
    fun updateNamaAyahIstri(value: String) { namaAyahIstriValue = value }
    fun updateNamaIbuIstri(value: String) { namaIbuIstriValue = value }
    fun updateNikAyahIstri(value: String) { nikAyahIstriValue = value }
    fun updateNikIbuIstri(value: String) { nikIbuIstriValue = value }
    fun updatePekerjaanAyahIstri(value: String) { pekerjaanAyahIstriValue = value }
    fun updatePekerjaanIbuIstri(value: String) { pekerjaanIbuIstriValue = value }
    fun updateTanggalLahiAyahIstri(value: String) { tanggalLahiAyahIstriValue = value }
    fun updateTanggalLahiIbuIstri(value: String) { tanggalLahiIbuIstriValue = value }
    fun updateTanggalLahirAyahIstri(value: String) { tanggalLahirAyahIstriValue = value }
    fun updateTanggalLahirIbuIstri(value: String) { tanggalLahirIbuIstriValue = value }
    fun updateTempatLahirAyahIstri(value: String) { tempatLahirAyahIstriValue = value }
    fun updateTempatLahirIbuIstri(value: String) { tempatLahirIbuIstriValue = value }

    fun updateJamPernikahan(value: String) { jamPernikahanValue = value }
    fun updateTempatPernikahan(value: String) { tempatPernikahanValue = value }
    fun updateHariPernikahan(value: String) { hariPernikahanValue = value }
    fun updateTanggalPernikahan(value: String) { tanggalPernikahanValue = value }

    fun loadUserDataToState(userData: UserInfoResponse.Data) {
        nikSuamiValue = userData.nik ?: ""
        namaSuamiValue = userData.nama_warga ?: ""
        tempatLahirSuamiValue = userData.tempat_lahir ?: ""
        tanggalLahirSuamiValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        agamaSuamiIdValue = userData.agama_id ?: ""
        pekerjaanSuamiValue = userData.pekerjaan ?: ""
        alamatSuamiValue = userData.alamat ?: ""
        kewarganegaraanSuamiValue = userData.kewarganegaraan ?: ""
        statusKawinSuamiIdValue = userData.status_kawin_id ?: ""
    }

    fun clearUserData() {
        nikSuamiValue = ""
        namaSuamiValue = ""
        tempatLahirSuamiValue = ""
        tanggalLahirSuamiValue = ""
        pekerjaanSuamiValue = ""
        alamatSuamiValue = ""
        agamaSuamiIdValue = ""
        kewarganegaraanSuamiValue = ""
        statusKawinSuamiIdValue = ""
    }

    fun resetAllData() {
        currentStep = 1
        useMyDataChecked = false

        // Reset semua form data
        clearUserData()
        currentStep = 1
        useMyDataChecked = false

        // Step 2 - Orang Tua Suami
        agamaAyahSuamiIdValue = ""
        alamatAyahSuamiValue = ""
        kewarganegaraanAyahSuamiValue = ""
        namaAyahSuamiValue = ""
        nikAyahSuamiValue = ""
        pekerjaanAyahSuamiValue = ""
        tempatLahirAyahSuamiValue = ""
        tanggalLahiAyahSuamiValue = ""
        tanggalLahirAyahSuamiValue = ""

        agamaIbuSuamiIdValue = ""
        alamatIbuSuamiValue = ""
        kewarganegaraanIbuSuamiValue = ""
        namaIbuSuamiValue = ""
        nikIbuSuamiValue = ""
        pekerjaanIbuSuamiValue = ""
        tanggalLahiIbuSuamiValue = ""
        tanggalLahirIbuSuamiValue = ""
        tempatLahirIbuSuamiValue = ""

        // Step 3 - Calon Istri
        agamaIstriIdValue = ""
        alamatIstriValue = ""
        kewarganegaraanIstriValue = ""
        namaIstriValue = ""
        nikIstriValue = ""
        pekerjaanIstriValue = ""
        statusKawinIstriIdValue = ""
        tanggalLahirIstriValue = ""
        tempatLahirIstriValue = ""
        namaSuamiSebelumnyaValue = ""

        // Step 4 - Orang Tua Istri
        agamaAyahIstriIdValue = ""
        agamaIbuIstriIdValue = ""
        alamatAyahIstriValue = ""
        alamatIbuIstriValue = ""
        kewarganegaraanAyahIstriValue = ""
        kewarganegaraanIbuIstriValue = ""
        namaAyahIstriValue = ""
        namaIbuIstriValue = ""
        nikAyahIstriValue = ""
        nikIbuIstriValue = ""
        pekerjaanAyahIstriValue = ""
        pekerjaanIbuIstriValue = ""
        tanggalLahirAyahIstriValue = ""
        tanggalLahiIbuIstriValue = ""
        tanggalLahirAyahIstriValue = ""
        tanggalLahirIbuIstriValue = ""
        tempatLahirAyahIstriValue = ""
        tempatLahirIbuIstriValue = ""

        // Step 5 - Informasi Pernikahan
        jamPernikahanValue = ""
        tempatPernikahanValue = ""
        hariPernikahanValue = ""
        tanggalPernikahanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikSuamiValue.isNotBlank() || nikIstriValue.isNotBlank() ||
                nikAyahSuamiValue.isNotBlank() || nikAyahIstriValue.isNotBlank()
    }

    fun buildSubmissionRequest(): SPPernikahanRequest {
        return SPPernikahanRequest(
            disahkan_oleh = "",
            hari = hariPernikahanValue,
            tanggal = dateFormatterToApiFormat(tanggalPernikahanValue),

            agama_ayah_istri_id = agamaAyahIstriIdValue,
            agama_ayah_suami_id = agamaAyahSuamiIdValue,
            agama_ibu_istri_id = agamaIbuIstriIdValue,
            agama_ibu_suami_id = agamaIbuSuamiIdValue,
            agama_istri_id = agamaIstriIdValue,
            agama_suami_id = agamaSuamiIdValue,

            alamat_ayah_istri = alamatAyahIstriValue,
            alamat_ayah_suami = alamatAyahSuamiValue,
            alamat_ibu_istri = alamatIbuIstriValue,
            alamat_ibu_suami = alamatIbuSuamiValue,
            alamat_istri = alamatIstriValue,
            alamat_suami = alamatSuamiValue,

            jam = jamPernikahanValue,
            jumlah_istri = jumlahIstriValue,

            kewarganegaraan_ayah_istri = kewarganegaraanAyahIstriValue,
            kewarganegaraan_ayah_suami = kewarganegaraanAyahSuamiValue,
            kewarganegaraan_ibu_istri = kewarganegaraanIbuIstriValue,
            kewarganegaraan_ibu_suami = kewarganegaraanIbuSuamiValue,
            kewarganegaraan_istri = kewarganegaraanIstriValue,
            kewarganegaraan_suami = kewarganegaraanSuamiValue,

            nama_ayah_istri = namaAyahIstriValue,
            nama_ayah_suami = namaAyahSuamiValue,
            nama_ibu_istri = namaIbuIstriValue,
            nama_ibu_suami = namaIbuSuamiValue,
            nama_istri = namaIstriValue,
            nama_istri_sebelumnya = namaIstriSebelumnyaValue,
            nama_suami = namaSuamiValue,
            nama_suami_sebelumnya = namaSuamiSebelumnyaValue,

            nik_ayah_istri = nikAyahIstriValue,
            nik_ayah_suami = nikAyahSuamiValue,
            nik_ibu_istri = nikIbuIstriValue,
            nik_ibu_suami = nikIbuSuamiValue,
            nik_istri = nikIstriValue,
            nik_suami = nikSuamiValue,

            pekerjaan_ayah_istri = pekerjaanAyahIstriValue,
            pekerjaan_ayah_suami = pekerjaanAyahSuamiValue,
            pekerjaan_ibu_istri = pekerjaanIbuIstriValue,
            pekerjaan_ibu_suami = pekerjaanIbuSuamiValue,
            pekerjaan_istri = pekerjaanIstriValue,
            pekerjaan_suami = pekerjaanSuamiValue,

            status_kawin_istri_id = statusKawinIstriIdValue,
            status_kawin_suami_id = statusKawinSuamiIdValue,

            tanggal_lahi_ayah_istri = tanggalLahiAyahIstriValue,
            tanggal_lahi_ayah_suami = tanggalLahiAyahSuamiValue,
            tanggal_lahi_ibu_istri = tanggalLahiIbuIstriValue,
            tanggal_lahi_ibu_suami = tanggalLahiIbuSuamiValue,

            tanggal_lahir_ayah_istri = tanggalLahirAyahIstriValue,
            tanggal_lahir_ayah_suami = tanggalLahirAyahSuamiValue,
            tanggal_lahir_ibu_istri = tanggalLahirIbuIstriValue,
            tanggal_lahir_ibu_suami = tanggalLahirIbuSuamiValue,
            tanggal_lahir_istri = tanggalLahirIstriValue,
            tanggal_lahir_suami = tanggalLahirSuamiValue,

            tempat = tempatPernikahanValue,
            tempat_lahir_ayah_istri = tempatLahirAyahIstriValue,
            tempat_lahir_ayah_suami = tempatLahirAyahSuamiValue,
            tempat_lahir_ibu_istri = tempatLahirIbuIstriValue,
            tempat_lahir_ibu_suami = tempatLahirIbuSuamiValue,
            tempat_lahir_istri = tempatLahirIstriValue,
            tempat_lahir_suami = tempatLahirSuamiValue
        )
    }
}