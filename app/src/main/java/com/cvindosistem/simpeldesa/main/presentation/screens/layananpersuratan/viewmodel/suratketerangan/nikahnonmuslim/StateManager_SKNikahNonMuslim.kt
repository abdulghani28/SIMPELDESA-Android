package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKNikahWargaNonMuslimRequest

class SKNikahWargaNonMuslimStateManager {
    // ==================== STEP 1: DATA SUAMI & ISTRI ====================
    // Data Suami
    var nikSuamiValue by mutableStateOf("")
        private set
    var namaSuamiValue by mutableStateOf("")
        private set
    var tempatLahirSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirSuamiValue by mutableStateOf("")
        private set
    var kewarganegaraanSuamiValue by mutableStateOf("")
        private set
    var wargaNegaraSuamiValue by mutableStateOf("")
        private set
    var pekerjaanSuamiValue by mutableStateOf("")
        private set
    var pendidikanIdSuamiValue by mutableStateOf("")
        private set
    var statusKawinSuamiValue by mutableStateOf("")
        private set
    var perkawinanKeSuamiValue by mutableStateOf("")
        private set
    var pasporSuamiValue by mutableStateOf("")
        private set
    var noKkSuamiValue by mutableStateOf("")
        private set
    var namaOrganisasiSuamiValue by mutableStateOf("")
        private set
    var agamaSuamiIdValue by mutableStateOf("")
        private set
    var teleponSuamiValue by mutableStateOf("")
        private set

    // Data Istri
    var nikIstriValue by mutableStateOf("")
        private set
    var namaIstriValue by mutableStateOf("")
        private set
    var tempatLahirIstriValue by mutableStateOf("")
        private set
    var tanggalLahirIstriValue by mutableStateOf("")
        private set
    var kewarganegaraanIstriValue by mutableStateOf("")
        private set
    var wargaNegaraIstriValue by mutableStateOf("")
        private set
    var pekerjaanIstriValue by mutableStateOf("")
        private set
    var pendidikanIdIstriValue by mutableStateOf("")
        private set
    var statusKawinIstriValue by mutableStateOf("")
        private set
    var perkawinanKeIstriValue by mutableStateOf("")
        private set
    var pasporIstriValue by mutableStateOf("")
        private set
    var noKkIstriValue by mutableStateOf("")
        private set
    var namaOrganisasiIstriValue by mutableStateOf("")
        private set
    var agamaIstriIdValue by mutableStateOf("")
        private set
    var teleponIstriValue by mutableStateOf("")
        private set
    var isIstriWargaDesaValue by mutableStateOf(false)
        private set

    // ==================== STEP 2: ALAMAT & ANAK ====================
    var alamatSuamiValue by mutableStateOf("")
        private set
    var alamatIstriValue by mutableStateOf("")
        private set
    var anakKeSuamiValue by mutableStateOf("")
        private set
    var anakKeIstriValue by mutableStateOf("")
        private set
    var jumlahAnakYangDiakuiValue by mutableStateOf("")
        private set
    var namaAnak1Value by mutableStateOf("")
        private set
    var noAktaLahir1Value by mutableStateOf("")
        private set
    var tanggalLahir1Value by mutableStateOf("")
        private set
    var namaAnak2Value by mutableStateOf("")
        private set
    var noAktaLahir2Value by mutableStateOf("")
        private set
    var tanggalLahir2Value by mutableStateOf("")
        private set

    // ==================== STEP 3: ORANG TUA SUAMI ====================
    // Ayah Suami
    var nikAyahSuamiValue by mutableStateOf("")
        private set
    var namaAyahSuamiValue by mutableStateOf("")
        private set
    var tempatLahirAyahSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirAyahSuamiValue by mutableStateOf("")
        private set
    var pekerjaanAyahSuamiValue by mutableStateOf("")
        private set
    var alamatAyahSuamiValue by mutableStateOf("")
        private set
    var teleponAyahSuamiValue by mutableStateOf("")
        private set
    var agamaAyahSuamiIdValue by mutableStateOf("")
        private set
    var kewarganegaraanAyahSuamiValue by mutableStateOf("")
        private set
    var namaOrganisasiAyahSuamiValue by mutableStateOf("")
        private set

    // Ibu Suami
    var nikIbuSuamiValue by mutableStateOf("")
        private set
    var namaIbuSuamiValue by mutableStateOf("")
        private set
    var tempatLahirIbuSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirIbuSuamiValue by mutableStateOf("")
        private set
    var pekerjaanIbuSuamiValue by mutableStateOf("")
        private set
    var alamatIbuSuamiValue by mutableStateOf("")
        private set
    var teleponIbuSuamiValue by mutableStateOf("")
        private set
    var agamaIbuSuamiIdValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuSuamiValue by mutableStateOf("")
        private set
    var namaOrganisasiIbuSuamiValue by mutableStateOf("")
        private set

    // ==================== STEP 4: ORANG TUA ISTRI ====================
    // Ayah Istri
    var nikAyahIstriValue by mutableStateOf("")
        private set
    var namaAyahIstriValue by mutableStateOf("")
        private set
    var tempatLahirAyahIstriValue by mutableStateOf("")
        private set
    var tanggalLahirAyahIstriValue by mutableStateOf("")
        private set
    var pekerjaanAyahIstriValue by mutableStateOf("")
        private set
    var alamatAyahIstriValue by mutableStateOf("")
        private set
    var teleponAyahIstriValue by mutableStateOf("")
        private set
    var agamaAyahIstriIdValue by mutableStateOf("")
        private set
    var kewarganegaraanAyahIstriValue by mutableStateOf("")
        private set
    var namaOrganisasiAyahIstriValue by mutableStateOf("")
        private set

    // Ibu Istri
    var nikIbuIstriValue by mutableStateOf("")
        private set
    var namaIbuIstriValue by mutableStateOf("")
        private set
    var tempatLahirIbuIstriValue by mutableStateOf("")
        private set
    var tanggalLahirIbuIstriValue by mutableStateOf("")
        private set
    var pekerjaanIbuIstriValue by mutableStateOf("")
        private set
    var alamatIbuIstriValue by mutableStateOf("")
        private set
    var teleponIbuIstriValue by mutableStateOf("")
        private set
    var agamaIbuIstriIdValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuIstriValue by mutableStateOf("")
        private set
    var namaOrganisasiIbuIstriValue by mutableStateOf("")
        private set

    // ==================== STEP 5: SAKSI PERNIKAHAN ====================
    // Saksi 1
    var nikSaksi1Value by mutableStateOf("")
        private set
    var namaSaksi1Value by mutableStateOf("")
        private set
    var tempatLahirSaksi1Value by mutableStateOf("")
        private set
    var tanggalLahirSaksi1Value by mutableStateOf("")
        private set
    var pekerjaanSaksi1Value by mutableStateOf("")
        private set
    var alamatSaksi1Value by mutableStateOf("")
        private set
    var teleponSaksi1Value by mutableStateOf("")
        private set
    var agamaSaksi1IdValue by mutableStateOf("")
        private set
    var kewarganegaraanSaksi1Value by mutableStateOf("")
        private set
    var namaOrganisasiSaksi1Value by mutableStateOf("")
        private set
    var namaAyahSaksi1Value by mutableStateOf("")
        private set
    var isSaksi1WargaDesaValue by mutableStateOf(false)
        private set

    // Saksi 2
    var nikSaksi2Value by mutableStateOf("")
        private set
    var namaSaksi2Value by mutableStateOf("")
        private set
    var tempatLahirSaksi2Value by mutableStateOf("")
        private set
    var tanggalLahirSaksi2Value by mutableStateOf("")
        private set
    var pekerjaanSaksi2Value by mutableStateOf("")
        private set
    var alamatSaksi2Value by mutableStateOf("")
        private set
    var teleponSaksi2Value by mutableStateOf("")
        private set
    var agamaSaksi2IdValue by mutableStateOf("")
        private set
    var kewarganegaraanSaksi2Value by mutableStateOf("")
        private set
    var namaOrganisasiSaksi2Value by mutableStateOf("")
        private set
    var namaAyahSaksi2Value by mutableStateOf("")
        private set
    var isSaksi2WargaDesaValue by mutableStateOf(false)
        private set

    // ==================== STEP 6: PERNIKAHAN & PEMUKA AGAMA ====================
    var agamaPernikahanIdValue by mutableStateOf("")
        private set
    var namaOrganisasiPernikahanValue by mutableStateOf("")
        private set
    var namaPemukaAgamaValue by mutableStateOf("")
        private set
    var tanggalPemberkatanPernikahanValue by mutableStateOf("")
        private set
    var tanggalMelaporPernikahanValue by mutableStateOf("")
        private set
    var badanPeradilanPernikahanValue by mutableStateOf("")
        private set

    // ==================== STEP 7: LEGALITAS & PUTUSAN ====================
    var nomorPutusanPengadilanValue by mutableStateOf("")
        private set
    var tanggalPutusanPengadilanValue by mutableStateOf("")
        private set
    var nomorIzinPerwakilanValue by mutableStateOf("")
        private set

    // ==================== UI STATES ====================
    var currentStep by mutableIntStateOf(1)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // ==================== LOADING STATES ====================
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set
    var isLoadingAgama by mutableStateOf(false)
        private set
    var isLoadingPendidikan by mutableStateOf(false)
        private set
    var isLoadingStatusKawin by mutableStateOf(false)
        private set

    // ==================== ERROR STATES ====================
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set
    var pendidikanErrorMessage by mutableStateOf<String?>(null)
        private set
    var statusKawinErrorMessage by mutableStateOf<String?>(null)
        private set

    // ==================== REFERENCE DATA ====================
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var pendidikanList by mutableStateOf<List<PendidikanResponse.Data>>(emptyList())
        private set
    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
        private set

    // ==================== STEP 1 UPDATE FUNCTIONS ====================
    // Data Suami Updates
    fun updateNikSuami(value: String) { nikSuamiValue = value }
    fun updateNamaSuami(value: String) { namaSuamiValue = value }
    fun updateTempatLahirSuami(value: String) { tempatLahirSuamiValue = value }
    fun updateTanggalLahirSuami(value: String) { tanggalLahirSuamiValue = value }
    fun updateKewarganegaraanSuami(value: String) { kewarganegaraanSuamiValue = value }
    fun updateWargaNegaraSuami(value: String) { wargaNegaraSuamiValue = value }
    fun updatePekerjaanSuami(value: String) { pekerjaanSuamiValue = value }
    fun updatePendidikanIdSuami(value: String) { pendidikanIdSuamiValue = value }
    fun updateStatusKawinSuami(value: String) { statusKawinSuamiValue = value }
    fun updatePerkawinanKeSuami(value: String) { perkawinanKeSuamiValue = value }
    fun updatePasporSuami(value: String) { pasporSuamiValue = value }
    fun updateNoKkSuami(value: String) { noKkSuamiValue = value }
    fun updateNamaOrganisasiSuami(value: String) { namaOrganisasiSuamiValue = value }
    fun updateAgamaSuamiId(value: String) { agamaSuamiIdValue = value }
    fun updateTeleponSuami(value: String) { teleponSuamiValue = value }

    // Data Istri Updates
    fun updateNikIstri(value: String) { nikIstriValue = value }
    fun updateNamaIstri(value: String) { namaIstriValue = value }
    fun updateTempatLahirIstri(value: String) { tempatLahirIstriValue = value }
    fun updateTanggalLahirIstri(value: String) { tanggalLahirIstriValue = value }
    fun updateKewarganegaraanIstri(value: String) { kewarganegaraanIstriValue = value }
    fun updateWargaNegaraIstri(value: String) { wargaNegaraIstriValue = value }
    fun updatePekerjaanIstri(value: String) { pekerjaanIstriValue = value }
    fun updatePendidikanIdIstri(value: String) { pendidikanIdIstriValue = value }
    fun updateStatusKawinIstri(value: String) { statusKawinIstriValue = value }
    fun updatePerkawinanKeIstri(value: String) { perkawinanKeIstriValue = value }
    fun updatePasporIstri(value: String) { pasporIstriValue = value }
    fun updateNoKkIstri(value: String) { noKkIstriValue = value }
    fun updateNamaOrganisasiIstri(value: String) { namaOrganisasiIstriValue = value }
    fun updateAgamaIstriId(value: String) { agamaIstriIdValue = value }
    fun updateTeleponIstri(value: String) { teleponIstriValue = value }
    fun updateIsIstriWargaDesa(value: Boolean) { isIstriWargaDesaValue = value }

    // ==================== STEP 2 UPDATE FUNCTIONS ====================
    fun updateAlamatSuami(value: String) { alamatSuamiValue = value }
    fun updateAlamatIstri(value: String) { alamatIstriValue = value }
    fun updateAnakKeSuami(value: String) { anakKeSuamiValue = value }
    fun updateAnakKeIstri(value: String) { anakKeIstriValue = value }
    fun updateJumlahAnakYangDiakui(value: String) { jumlahAnakYangDiakuiValue = value }
    fun updateNamaAnak1(value: String) { namaAnak1Value = value }
    fun updateNoAktaLahir1(value: String) { noAktaLahir1Value = value }
    fun updateTanggalLahir1(value: String) { tanggalLahir1Value = value }
    fun updateNamaAnak2(value: String) { namaAnak2Value = value }
    fun updateNoAktaLahir2(value: String) { noAktaLahir2Value = value }
    fun updateTanggalLahir2(value: String) { tanggalLahir2Value = value }

    // ==================== STEP 3 UPDATE FUNCTIONS ====================
    // Ayah Suami Updates
    fun updateNikAyahSuami(value: String) { nikAyahSuamiValue = value }
    fun updateNamaAyahSuami(value: String) { namaAyahSuamiValue = value }
    fun updateTempatLahirAyahSuami(value: String) { tempatLahirAyahSuamiValue = value }
    fun updateTanggalLahirAyahSuami(value: String) { tanggalLahirAyahSuamiValue = value }
    fun updatePekerjaanAyahSuami(value: String) { pekerjaanAyahSuamiValue = value }
    fun updateAlamatAyahSuami(value: String) { alamatAyahSuamiValue = value }
    fun updateTeleponAyahSuami(value: String) { teleponAyahSuamiValue = value }
    fun updateAgamaAyahSuamiId(value: String) { agamaAyahSuamiIdValue = value }
    fun updateKewarganegaraanAyahSuami(value: String) { kewarganegaraanAyahSuamiValue = value }
    fun updateNamaOrganisasiAyahSuami(value: String) { namaOrganisasiAyahSuamiValue = value }

    // Ibu Suami Updates
    fun updateNikIbuSuami(value: String) { nikIbuSuamiValue = value }
    fun updateNamaIbuSuami(value: String) { namaIbuSuamiValue = value }
    fun updateTempatLahirIbuSuami(value: String) { tempatLahirIbuSuamiValue = value }
    fun updateTanggalLahirIbuSuami(value: String) { tanggalLahirIbuSuamiValue = value }
    fun updatePekerjaanIbuSuami(value: String) { pekerjaanIbuSuamiValue = value }
    fun updateAlamatIbuSuami(value: String) { alamatIbuSuamiValue = value }
    fun updateTeleponIbuSuami(value: String) { teleponIbuSuamiValue = value }
    fun updateAgamaIbuSuamiId(value: String) { agamaIbuSuamiIdValue = value }
    fun updateKewarganegaraanIbuSuami(value: String) { kewarganegaraanIbuSuamiValue = value }
    fun updateNamaOrganisasiIbuSuami(value: String) { namaOrganisasiIbuSuamiValue = value }

    // ==================== STEP 4 UPDATE FUNCTIONS ====================
    // Ayah Istri Updates
    fun updateNikAyahIstri(value: String) { nikAyahIstriValue = value }
    fun updateNamaAyahIstri(value: String) { namaAyahIstriValue = value }
    fun updateTempatLahirAyahIstri(value: String) { tempatLahirAyahIstriValue = value }
    fun updateTanggalLahirAyahIstri(value: String) { tanggalLahirAyahIstriValue = value }
    fun updatePekerjaanAyahIstri(value: String) { pekerjaanAyahIstriValue = value }
    fun updateAlamatAyahIstri(value: String) { alamatAyahIstriValue = value }
    fun updateTeleponAyahIstri(value: String) { teleponAyahIstriValue = value }
    fun updateAgamaAyahIstriId(value: String) { agamaAyahIstriIdValue = value }
    fun updateKewarganegaraanAyahIstri(value: String) { kewarganegaraanAyahIstriValue = value }
    fun updateNamaOrganisasiAyahIstri(value: String) { namaOrganisasiAyahIstriValue = value }

    // Ibu Istri Updates
    fun updateNikIbuIstri(value: String) { nikIbuIstriValue = value }
    fun updateNamaIbuIstri(value: String) { namaIbuIstriValue = value }
    fun updateTempatLahirIbuIstri(value: String) { tempatLahirIbuIstriValue = value }
    fun updateTanggalLahirIbuIstri(value: String) { tanggalLahirIbuIstriValue = value }
    fun updatePekerjaanIbuIstri(value: String) { pekerjaanIbuIstriValue = value }
    fun updateAlamatIbuIstri(value: String) { alamatIbuIstriValue = value }
    fun updateTeleponIbuIstri(value: String) { teleponIbuIstriValue = value }
    fun updateAgamaIbuIstriId(value: String) { agamaIbuIstriIdValue = value }
    fun updateKewarganegaraanIbuIstri(value: String) { kewarganegaraanIbuIstriValue = value }
    fun updateNamaOrganisasiIbuIstri(value: String) { namaOrganisasiIbuIstriValue = value }

    // ==================== STEP 5 UPDATE FUNCTIONS ====================
    // Saksi 1 Updates
    fun updateNikSaksi1(value: String) { nikSaksi1Value = value }
    fun updateNamaSaksi1(value: String) { namaSaksi1Value = value }
    fun updateTempatLahirSaksi1(value: String) { tempatLahirSaksi1Value = value }
    fun updateTanggalLahirSaksi1(value: String) { tanggalLahirSaksi1Value = value }
    fun updatePekerjaanSaksi1(value: String) { pekerjaanSaksi1Value = value }
    fun updateAlamatSaksi1(value: String) { alamatSaksi1Value = value }
    fun updateTeleponSaksi1(value: String) { teleponSaksi1Value = value }
    fun updateAgamaSaksi1Id(value: String) { agamaSaksi1IdValue = value }
    fun updateKewarganegaraanSaksi1(value: String) { kewarganegaraanSaksi1Value = value }
    fun updateNamaOrganisasiSaksi1(value: String) { namaOrganisasiSaksi1Value = value }
    fun updateNamaAyahSaksi1(value: String) { namaAyahSaksi1Value = value }
    fun updateIsSaksi1WargaDesa(value: Boolean) { isSaksi1WargaDesaValue = value }

    // Saksi 2 Updates
    fun updateNikSaksi2(value: String) { nikSaksi2Value = value }
    fun updateNamaSaksi2(value: String) { namaSaksi2Value = value }
    fun updateTempatLahirSaksi2(value: String) { tempatLahirSaksi2Value = value }
    fun updateTanggalLahirSaksi2(value: String) { tanggalLahirSaksi2Value = value }
    fun updatePekerjaanSaksi2(value: String) { pekerjaanSaksi2Value = value }
    fun updateAlamatSaksi2(value: String) { alamatSaksi2Value = value }
    fun updateTeleponSaksi2(value: String) { teleponSaksi2Value = value }
    fun updateAgamaSaksi2Id(value: String) { agamaSaksi2IdValue = value }
    fun updateKewarganegaraanSaksi2(value: String) { kewarganegaraanSaksi2Value = value }
    fun updateNamaOrganisasiSaksi2(value: String) { namaOrganisasiSaksi2Value = value }
    fun updateNamaAyahSaksi2(value: String) { namaAyahSaksi2Value = value }
    fun updateIsSaksi2WargaDesa(value: Boolean) { isSaksi2WargaDesaValue = value }

    // ==================== STEP 6 UPDATE FUNCTIONS ====================
    fun updateAgamaPernikahanId(value: String) { agamaPernikahanIdValue = value }
    fun updateNamaOrganisasiPernikahan(value: String) { namaOrganisasiPernikahanValue = value }
    fun updateNamaPemukaAgama(value: String) { namaPemukaAgamaValue = value }
    fun updateTanggalPemberkatanPernikahan(value: String) { tanggalPemberkatanPernikahanValue = value }
    fun updateTanggalMelaporPernikahan(value: String) { tanggalMelaporPernikahanValue = value }
    fun updateBadanPeradilanPernikahan(value: String) { badanPeradilanPernikahanValue = value }

    // ==================== STEP 7 UPDATE FUNCTIONS ====================
    fun updateNomorPutusanPengadilan(value: String) { nomorPutusanPengadilanValue = value }
    fun updateTanggalPutusanPengadilan(value: String) { tanggalPutusanPengadilanValue = value }
    fun updateNomorIzinPerwakilan(value: String) { nomorIzinPerwakilanValue = value }

    // ==================== NAVIGATION FUNCTIONS ====================
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 7) currentStep++ }
    fun previousStep() { if (currentStep > 1) currentStep-- }

    // ==================== DIALOG FUNCTIONS ====================
    fun showConfirmation() { showConfirmationDialog = true }
    fun hideConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun hidePreview() { showPreviewDialog = false }

    // ==================== USE MY DATA FUNCTION ====================
    fun updateUseMyDataChecked(checked: Boolean) { useMyDataChecked = checked }

    // ==================== LOADING STATE FUNCTIONS ====================
    fun updateLoadingState(loading: Boolean) { isLoading = loading }
    fun updateUserDataLoading(loading: Boolean) { isLoadingUserData = loading }
    fun updateAgamaLoading(loading: Boolean) { isLoadingAgama = loading }
    fun updatePendidikanLoading(loading: Boolean) { isLoadingPendidikan = loading }
    fun updateStatusKawinLoading(loading: Boolean) { isLoadingStatusKawin = loading }

    // ==================== ERROR HANDLING ====================
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun updatePendidikanErrorMessage(message: String?) { pendidikanErrorMessage = message }
    fun updateStatusKawinErrorMessage(message: String?) { statusKawinErrorMessage = message }
    fun clearError() { errorMessage = null }

    // ==================== DATA SETTER ====================
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }
    fun updatePendidikanList(list: List<PendidikanResponse.Data>) { pendidikanList = list }
    fun updateStatusKawinList(list: List<StatusKawinResponse.Data>) { statusKawinList = list }

    // ==================== POPULATE / CLEAR USER DATA ====================
    fun populateUserData(userData: UserInfoResponse.Data) {
        // Populate only personal data for the applicant (assuming this would be the husband/suami)
        nikSuamiValue = userData.nik
        namaSuamiValue = userData.nama_warga
        tempatLahirSuamiValue = userData.tempat_lahir
        tanggalLahirSuamiValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        alamatSuamiValue = userData.alamat
        agamaSuamiIdValue = userData.agama_id
        statusKawinSuamiValue = userData.status_kawin_id
        pendidikanIdSuamiValue = userData.pendidikan_id
        pekerjaanSuamiValue = userData.pekerjaan
        kewarganegaraanSuamiValue = userData.kewarganegaraan
    }

    fun clearUserData() {
        // Clear only the main applicant data (suami)
        nikSuamiValue = ""
        namaSuamiValue = ""
        tempatLahirSuamiValue = ""
        tanggalLahirSuamiValue = ""
        alamatSuamiValue = ""
        agamaSuamiIdValue = ""
        statusKawinSuamiValue = ""
        pendidikanIdSuamiValue = ""
        pekerjaanSuamiValue = ""
        kewarganegaraanSuamiValue = ""
    }

    // ==================== FORM MANAGEMENT ====================
    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearAllData()
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    private fun clearAllData() {
        // Step 1: Data Suami & Istri
        nikSuamiValue = ""
        namaSuamiValue = ""
        tempatLahirSuamiValue = ""
        tanggalLahirSuamiValue = ""
        kewarganegaraanSuamiValue = ""
        wargaNegaraSuamiValue = ""
        pekerjaanSuamiValue = ""
        pendidikanIdSuamiValue = ""
        statusKawinSuamiValue = ""
        perkawinanKeSuamiValue = ""
        pasporSuamiValue = ""
        noKkSuamiValue = ""
        namaOrganisasiSuamiValue = ""
        agamaSuamiIdValue = ""
        teleponSuamiValue = ""

        nikIstriValue = ""
        namaIstriValue = ""
        tempatLahirIstriValue = ""
        tanggalLahirIstriValue = ""
        kewarganegaraanIstriValue = ""
        wargaNegaraIstriValue = ""
        pekerjaanIstriValue = ""
        pendidikanIdIstriValue = ""
        statusKawinIstriValue = ""
        perkawinanKeIstriValue = ""
        pasporIstriValue = ""
        noKkIstriValue = ""
        namaOrganisasiIstriValue = ""
        agamaIstriIdValue = ""
        teleponIstriValue = ""
        isIstriWargaDesaValue = false

        // Step 2: Alamat & Anak
        alamatSuamiValue = ""
        alamatIstriValue = ""
        anakKeSuamiValue = ""
        anakKeIstriValue = ""
        jumlahAnakYangDiakuiValue = ""
        namaAnak1Value = ""
        noAktaLahir1Value = ""
        tanggalLahir1Value = ""
        namaAnak2Value = ""
        noAktaLahir2Value = ""
        tanggalLahir2Value = ""

        // Step 3: Orang Tua Suami
        nikAyahSuamiValue = ""
        namaAyahSuamiValue = ""
        tempatLahirAyahSuamiValue = ""
        tanggalLahirAyahSuamiValue = ""
        pekerjaanAyahSuamiValue = ""
        alamatAyahSuamiValue = ""
        teleponAyahSuamiValue = ""
        agamaAyahSuamiIdValue = ""
        kewarganegaraanAyahSuamiValue = ""
        namaOrganisasiAyahSuamiValue = ""

        nikIbuSuamiValue = ""
        namaIbuSuamiValue = ""
        tempatLahirIbuSuamiValue = ""
        tanggalLahirIbuSuamiValue = ""
        pekerjaanIbuSuamiValue = ""
        alamatIbuSuamiValue = ""
        teleponIbuSuamiValue = ""
        agamaIbuSuamiIdValue = ""
        kewarganegaraanIbuSuamiValue = ""
        namaOrganisasiIbuSuamiValue = ""

        // Step 4: Orang Tua Istri
        nikAyahIstriValue = ""
        namaAyahIstriValue = ""
        tempatLahirAyahIstriValue = ""
        tanggalLahirAyahIstriValue = ""
        pekerjaanAyahIstriValue = ""
        alamatAyahIstriValue = ""
        teleponAyahIstriValue = ""
        agamaAyahIstriIdValue = ""
        kewarganegaraanAyahIstriValue = ""
        namaOrganisasiAyahIstriValue = ""

        nikIbuIstriValue = ""
        namaIbuIstriValue = ""
        tempatLahirIbuIstriValue = ""
        tanggalLahirIbuIstriValue = ""
        pekerjaanIbuIstriValue = ""
        alamatIbuIstriValue = ""
        teleponIbuIstriValue = ""
        agamaIbuIstriIdValue = ""
        kewarganegaraanIbuIstriValue = ""
        namaOrganisasiIbuIstriValue = ""

        // Step 5: Saksi Pernikahan
        nikSaksi1Value = ""
        namaSaksi1Value = ""
        tempatLahirSaksi1Value = ""
        tanggalLahirSaksi1Value = ""
        pekerjaanSaksi1Value = ""
        alamatSaksi1Value = ""
        teleponSaksi1Value = ""
        agamaSaksi1IdValue = ""
        kewarganegaraanSaksi1Value = ""
        namaOrganisasiSaksi1Value = ""
        namaAyahSaksi1Value = ""
        isSaksi1WargaDesaValue = false

        nikSaksi2Value = ""
        namaSaksi2Value = ""
        tempatLahirSaksi2Value = ""
        tanggalLahirSaksi2Value = ""
        pekerjaanSaksi2Value = ""
        alamatSaksi2Value = ""
        teleponSaksi2Value = ""
        agamaSaksi2IdValue = ""
        kewarganegaraanSaksi2Value = ""
        namaOrganisasiSaksi2Value = ""
        namaAyahSaksi2Value = ""
        isSaksi2WargaDesaValue = false

        // Step 6: Pernikahan & Pemuka Agama
        agamaPernikahanIdValue = ""
        namaOrganisasiPernikahanValue = ""
        namaPemukaAgamaValue = ""
        tanggalPemberkatanPernikahanValue = ""
        tanggalMelaporPernikahanValue = ""
        badanPeradilanPernikahanValue = ""

        // Step 7: Legalitas & Putusan
        nomorPutusanPengadilanValue = ""
        tanggalPutusanPengadilanValue = ""
        nomorIzinPerwakilanValue = ""
    }

    fun hasFormData(): Boolean {
        return nikSuamiValue.isNotBlank() || namaSuamiValue.isNotBlank() ||
                tempatLahirSuamiValue.isNotBlank() || tanggalLahirSuamiValue.isNotBlank() ||
                alamatSuamiValue.isNotBlank() || kewarganegaraanSuamiValue.isNotBlank() ||
                wargaNegaraSuamiValue.isNotBlank() || pekerjaanSuamiValue.isNotBlank() ||
                pendidikanIdSuamiValue.isNotBlank() || statusKawinSuamiValue.isNotBlank() ||
                perkawinanKeSuamiValue.isNotBlank() || pasporSuamiValue.isNotBlank() ||
                noKkSuamiValue.isNotBlank() || namaOrganisasiSuamiValue.isNotBlank() ||
                agamaSuamiIdValue.isNotBlank() || teleponSuamiValue.isNotBlank() ||
                nikIstriValue.isNotBlank() || namaIstriValue.isNotBlank() ||
                tempatLahirIstriValue.isNotBlank() || tanggalLahirIstriValue.isNotBlank() ||
                alamatIstriValue.isNotBlank() || kewarganegaraanIstriValue.isNotBlank() ||
                wargaNegaraIstriValue.isNotBlank() || pekerjaanIstriValue.isNotBlank() ||
                pendidikanIdIstriValue.isNotBlank() || statusKawinIstriValue.isNotBlank() ||
                perkawinanKeIstriValue.isNotBlank() || pasporIstriValue.isNotBlank() ||
                noKkIstriValue.isNotBlank() || namaOrganisasiIstriValue.isNotBlank() ||
                agamaIstriIdValue.isNotBlank() || teleponIstriValue.isNotBlank() ||
                anakKeSuamiValue.isNotBlank() || anakKeIstriValue.isNotBlank() ||
                jumlahAnakYangDiakuiValue.isNotBlank() || namaAnak1Value.isNotBlank() ||
                noAktaLahir1Value.isNotBlank() || tanggalLahir1Value.isNotBlank() ||
                namaAnak2Value.isNotBlank() || noAktaLahir2Value.isNotBlank() ||
                tanggalLahir2Value.isNotBlank() || nikAyahSuamiValue.isNotBlank() ||
                namaAyahSuamiValue.isNotBlank() || nikIbuSuamiValue.isNotBlank() ||
                namaIbuSuamiValue.isNotBlank() || nikAyahIstriValue.isNotBlank() ||
                namaAyahIstriValue.isNotBlank() || nikIbuIstriValue.isNotBlank() ||
                namaIbuIstriValue.isNotBlank() || nikSaksi1Value.isNotBlank() ||
                namaSaksi1Value.isNotBlank() || nikSaksi2Value.isNotBlank() ||
                namaSaksi2Value.isNotBlank() || agamaPernikahanIdValue.isNotBlank() ||
                namaOrganisasiPernikahanValue.isNotBlank() || namaPemukaAgamaValue.isNotBlank() ||
                tanggalPemberkatanPernikahanValue.isNotBlank() || tanggalMelaporPernikahanValue.isNotBlank() ||
                badanPeradilanPernikahanValue.isNotBlank() || nomorPutusanPengadilanValue.isNotBlank() ||
                tanggalPutusanPengadilanValue.isNotBlank() || nomorIzinPerwakilanValue.isNotBlank()
    }

    // ==================== VALIDATION FUNCTIONS ====================
    fun validateStep1(): Boolean {
        return nikSuamiValue.isNotBlank() && namaSuamiValue.isNotBlank() &&
                tempatLahirSuamiValue.isNotBlank() && tanggalLahirSuamiValue.isNotBlank() &&
                nikIstriValue.isNotBlank() && namaIstriValue.isNotBlank() &&
                tempatLahirIstriValue.isNotBlank() && tanggalLahirIstriValue.isNotBlank()
    }

    fun validateStep2(): Boolean {
        return alamatSuamiValue.isNotBlank() && alamatIstriValue.isNotBlank()
    }

    fun validateStep3(): Boolean {
        return namaAyahSuamiValue.isNotBlank() && namaIbuSuamiValue.isNotBlank()
    }

    fun validateStep4(): Boolean {
        return namaAyahIstriValue.isNotBlank() && namaIbuIstriValue.isNotBlank()
    }

    fun validateStep5(): Boolean {
        return nikSaksi1Value.isNotBlank() && namaSaksi1Value.isNotBlank() &&
                nikSaksi2Value.isNotBlank() && namaSaksi2Value.isNotBlank()
    }

    fun validateStep6(): Boolean {
        return agamaPernikahanIdValue.isNotBlank() && namaPemukaAgamaValue.isNotBlank() &&
                tanggalPemberkatanPernikahanValue.isNotBlank()
    }

    fun validateStep7(): Boolean {
        return true // Step 7 fields are optional based on the use case
    }

    fun validateCurrentStep(): Boolean {
        return when (currentStep) {
            1 -> validateStep1()
            2 -> validateStep2()
            3 -> validateStep3()
            4 -> validateStep4()
            5 -> validateStep5()
            6 -> validateStep6()
            7 -> validateStep7()
            else -> false
        }
    }

    // ==================== REQUEST MAPPING ====================
    fun toRequest(): SKNikahWargaNonMuslimRequest {
        return SKNikahWargaNonMuslimRequest(
            agama_ayah_istri_id = agamaAyahIstriIdValue,
            agama_ayah_suami_id = agamaAyahSuamiIdValue,
            agama_ibu_istri_id = agamaIbuIstriIdValue,
            agama_ibu_suami_id = agamaIbuSuamiIdValue,
            agama_istri_id = agamaIstriIdValue,
            agama_pernikahan_id = agamaPernikahanIdValue,
            agama_saksi1_id = agamaSaksi1IdValue,
            agama_saksi2_id = agamaSaksi2IdValue,
            agama_suami_id = agamaSuamiIdValue,
            alamat_ayah_istri = alamatAyahIstriValue,
            alamat_ayah_suami = alamatAyahSuamiValue,
            alamat_ibu_istri = alamatIbuIstriValue,
            alamat_ibu_suami = alamatIbuSuamiValue,
            alamat_istri = alamatIstriValue,
            alamat_saksi1 = alamatSaksi1Value,
            alamat_saksi2 = alamatSaksi2Value,
            alamat_suami = alamatSuamiValue,
            anak_ke_istri = anakKeIstriValue,
            anak_ke_suami = anakKeSuamiValue,
            badan_peradilan_pernikahan = badanPeradilanPernikahanValue,
            is_istri_warga_desa = isIstriWargaDesaValue,
            is_saksi1_warga_desa = isSaksi1WargaDesaValue,
            is_saksi2_warga_desa = isSaksi2WargaDesaValue,
            istri_ke = "", // This field is not in the form, needs clarification
            jumlah_anak_yang_diakui = jumlahAnakYangDiakuiValue,
            kewarganegaraan_ayah_istri = kewarganegaraanAyahIstriValue,
            kewarganegaraan_ayah_suami = kewarganegaraanAyahSuamiValue,
            kewarganegaraan_ibu_istri = kewarganegaraanIbuIstriValue,
            kewarganegaraan_ibu_suami = kewarganegaraanIbuSuamiValue,
            kewarganegaraan_istri = kewarganegaraanIstriValue,
            kewarganegaraan_saksi1 = kewarganegaraanSaksi1Value,
            kewarganegaraan_saksi2 = kewarganegaraanSaksi2Value,
            kewarganegaraan_suami = kewarganegaraanSuamiValue,
            nama_anak1 = namaAnak1Value,
            nama_anak2 = namaAnak2Value,
            nama_ayah_istri = namaAyahIstriValue,
            nama_ayah_saksi1 = namaAyahSaksi1Value,
            nama_ayah_saksi2 = namaAyahSaksi2Value,
            nama_ayah_suami = namaAyahSuamiValue,
            nama_ibu_istri = namaIbuIstriValue,
            nama_ibu_suami = namaIbuSuamiValue,
            nama_istri = namaIstriValue,
            nama_organisasi_ayah_istri = namaOrganisasiAyahIstriValue,
            nama_organisasi_ayah_suami = namaOrganisasiAyahSuamiValue,
            nama_organisasi_ibu_istri = namaOrganisasiIbuIstriValue,
            nama_organisasi_ibu_suami = namaOrganisasiIbuSuamiValue,
            nama_organisasi_istri = namaOrganisasiIstriValue,
            nama_organisasi_pernikahan = namaOrganisasiPernikahanValue,
            nama_organisasi_saksi1 = namaOrganisasiSaksi1Value,
            nama_organisasi_saksi2 = namaOrganisasiSaksi2Value,
            nama_organisasi_suami = namaOrganisasiSuamiValue,
            nama_pemuka_agama = namaPemukaAgamaValue,
            nama_saksi1 = namaSaksi1Value,
            nama_saksi2 = namaSaksi2Value,
            nama_suami = namaSuamiValue,
            nik_ayah_istri = nikAyahIstriValue,
            nik_ayah_suami = nikAyahSuamiValue,
            nik_ibu_istri = nikIbuIstriValue,
            nik_ibu_suami = nikIbuSuamiValue,
            nik_istri = nikIstriValue,
            nik_saksi1 = nikSaksi1Value,
            nik_saksi2 = nikSaksi2Value,
            nik_suami = nikSuamiValue,
            no_akta_lahir1 = noAktaLahir1Value,
            no_akta_lahir2 = noAktaLahir2Value,
            no_kk_istri = noKkIstriValue,
            no_kk_suami = noKkSuamiValue,
            nomor_izin_perwakilan = nomorIzinPerwakilanValue,
            nomor_putusan_pengadilan = nomorPutusanPengadilanValue,
            paspor_istri = pasporIstriValue,
            paspor_suami = pasporSuamiValue,
            pekerjaan_ayah_istri = pekerjaanAyahIstriValue,
            pekerjaan_ayah_suami = pekerjaanAyahSuamiValue,
            pekerjaan_ibu_istri = pekerjaanIbuIstriValue,
            pekerjaan_ibu_suami = pekerjaanIbuSuamiValue,
            pekerjaan_istri = pekerjaanIstriValue,
            pekerjaan_saksi1 = pekerjaanSaksi1Value,
            pekerjaan_saksi2 = pekerjaanSaksi2Value,
            pekerjaan_suami = pekerjaanSuamiValue,
            pendidikan_id_istri = pendidikanIdIstriValue,
            pendidikan_id_suami = pendidikanIdSuamiValue,
            perkawinan_ke_istri = perkawinanKeIstriValue,
            perkawinan_ke_suami = perkawinanKeSuamiValue,
            status_kawin_istri = statusKawinIstriValue,
            status_kawin_suami = statusKawinSuamiValue,
            tanggal_lahir1 = tanggalLahir1Value,
            tanggal_lahir2 = tanggalLahir2Value,
            tanggal_lahir_ayah_istri = tanggalLahirAyahIstriValue,
            tanggal_lahir_ayah_suami = tanggalLahirAyahSuamiValue,
            tanggal_lahir_ibu_istri = tanggalLahirIbuIstriValue,
            tanggal_lahir_ibu_suami = tanggalLahirIbuSuamiValue,
            tanggal_lahir_istri = tanggalLahirIstriValue,
            tanggal_lahir_saksi1 = tanggalLahirSaksi1Value,
            tanggal_lahir_saksi2 = tanggalLahirSaksi2Value,
            tanggal_lahir_suami = tanggalLahirSuamiValue,
            tanggal_melapor_pernikahan = tanggalMelaporPernikahanValue,
            tanggal_pemberkatan_pernikahan = tanggalPemberkatanPernikahanValue,
            tanggal_putusan_pengadilan = tanggalPutusanPengadilanValue,
            telepon_ayah_istri = teleponAyahIstriValue,
            telepon_ayah_suami = teleponAyahSuamiValue,
            telepon_ibu_istri = teleponIbuIstriValue,
            telepon_ibu_suami = teleponIbuSuamiValue,
            telepon_istri = teleponIstriValue,
            telepon_suami = teleponSuamiValue,
            tempat_lahir_ayah_istri = tempatLahirAyahIstriValue,
            tempat_lahir_ayah_suami = tempatLahirAyahSuamiValue,
            tempat_lahir_ibu_istri = tempatLahirIbuIstriValue,
            tempat_lahir_ibu_suami = tempatLahirIbuSuamiValue,
            tempat_lahir_istri = tempatLahirIstriValue,
            tempat_lahir_saksi1 = tempatLahirSaksi1Value,
            tempat_lahir_saksi2 = tempatLahirSaksi2Value,
            tempat_lahir_suami = tempatLahirSuamiValue,
            warga_negara_istri = wargaNegaraIstriValue,
            warga_negara_suami = wargaNegaraSuamiValue
        )
    }
}