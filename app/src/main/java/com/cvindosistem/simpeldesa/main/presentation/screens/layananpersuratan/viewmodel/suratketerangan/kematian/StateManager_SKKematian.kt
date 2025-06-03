package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kematian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKKematianRequest

class SKKematianStateManager {
    // Step 1 - Informasi Pelapor
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var hubunganIdValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Mendiang
    var nikMendiangValue by mutableStateOf("")
        private set
    var namaMendiangValue by mutableStateOf("")
        private set
    var tempatLahirMendiangValue by mutableStateOf("")
        private set
    var tanggalLahirMendiangValue by mutableStateOf("")
        private set
    var jenisKelaminMendiangValue by mutableStateOf("")
        private set
    var alamatMendiangValue by mutableStateOf("")
        private set
    var hariMeninggalValue by mutableStateOf("")
        private set
    var tanggalMeninggalValue by mutableStateOf("")
        private set
    var tempatMeninggalValue by mutableStateOf("")
        private set
    var sebabMeninggalValue by mutableStateOf("")
        private set

    // Step 3 - Keperluan
    var keperluanValue by mutableStateOf("")
        private set

    // Step 1 Update Functions
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateHubunganId(value: String) { hubunganIdValue = value }

    // Step 2 Update Functions
    fun updateNikMendiang(value: String) { nikMendiangValue = value }

    fun updateNamaMendiang(value: String) { namaMendiangValue = value }

    fun updateTempatLahirMendiang(value: String) { tempatLahirMendiangValue = value }

    fun updateTanggalLahirMendiang(value: String) { tanggalLahirMendiangValue = value }

    fun updateJenisKelaminMendiang(value: String) { jenisKelaminMendiangValue = value }

    fun updateAlamatMendiang(value: String) { alamatMendiangValue = value }

    fun updateHariMeninggal(value: String) { hariMeninggalValue = value }

    fun updateTanggalMeninggal(value: String) { tanggalMeninggalValue = value }

    fun updateTempatMeninggal(value: String) { tempatMeninggalValue = value }

    fun updateSebabMeninggal(value: String) { sebabMeninggalValue = value }
    // Step 3 Update Functions
    fun updateKeperluan(value: String) { keperluanValue = value }

    fun resetAll() {
        // Step 1
        namaValue = ""
        alamatValue = ""
        hubunganIdValue = ""

        // Step 2 - copy semua reset dari kode asli
        nikMendiangValue = ""
        namaMendiangValue = ""
        tempatLahirMendiangValue = ""
        tanggalLahirMendiangValue = ""
        jenisKelaminMendiangValue = ""
        alamatMendiangValue = ""
        hariMeninggalValue = ""
        tanggalMeninggalValue = ""
        tempatMeninggalValue = ""
        sebabMeninggalValue = ""

        // Step 3
        keperluanValue = ""
    }

    fun hasFormData(): Boolean {
        return namaValue.isNotBlank() || alamatValue.isNotBlank() ||
                hubunganIdValue.isNotBlank() || nikMendiangValue.isNotBlank() ||
                namaMendiangValue.isNotBlank() || tempatLahirMendiangValue.isNotBlank() ||
                tanggalLahirMendiangValue.isNotBlank() || jenisKelaminMendiangValue.isNotBlank() ||
                alamatMendiangValue.isNotBlank() || hariMeninggalValue.isNotBlank() ||
                tanggalMeninggalValue.isNotBlank() || tempatMeninggalValue.isNotBlank() ||
                sebabMeninggalValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SKKematianRequest {
        return SKKematianRequest(
            disahkan_oleh = "",
            alamat = alamatValue,
            alamat_mendiang = alamatMendiangValue,
            hari_meninggal = hariMeninggalValue,
            hubungan_id = hubunganIdValue,
            jenis_kelamin_mendiang = jenisKelaminMendiangValue,
            keperluan = keperluanValue,
            nama = namaValue,
            nama_mendiang = namaMendiangValue,
            nik_mendiang = nikMendiangValue,
            sebab_meninggal = sebabMeninggalValue,
            tanggal_lahir_mendiang = tanggalLahirMendiangValue,
            tanggal_meninggal = tanggalMeninggalValue,
            tempat_lahir_mendiang = tempatLahirMendiangValue,
            tempat_meninggal = tempatMeninggalValue
        )
    }
}