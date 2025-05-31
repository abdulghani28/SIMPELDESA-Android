package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.main.domain.model.SuratDetailResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SuratDetailViewModel(
    private val getSuratDetailUseCase: GetSuratDetailUseCase
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(SuratDetailUiState())
    val uiState = _uiState.asStateFlow()

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Events
    private val _suratDetailEvent = MutableSharedFlow<SuratDetailEvent>()
    val suratDetailEvent = _suratDetailEvent.asSharedFlow()

    fun loadSuratDetail(id: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = getSuratDetailUseCase(id)) {
                is SuratDetailResult.Success -> {
                    val detailData = result.data.data
                    val suratDetail = SuratDetail(
                        id = detailData.id.orEmpty(),
                        jenisSurat = result.data.meta.jenis_surat,
                        nomorSurat = detailData.nomor_surat,
                        nomorPengajuan = detailData.nomor_pengajuan,
                        status = detailData.status.orEmpty(),
                        tanggalPengajuan = detailData.created_at,
                        tanggalSelesai = detailData.selesai,
                        diprosesOleh = detailData.diproses_oleh,
                        disahkanOleh = detailData.disahkan_oleh,

                        // Data Pemohon
                        nama = detailData.nama,
                        nik = detailData.nik,
                        alamat = detailData.alamat,
                        tempatLahir = detailData.tempat_lahir,
                        tanggalLahir = detailData.tanggal_lahir,
                        jenisKelamin = detailData.jenis_kelamin,
                        agama = detailData.agama,
                        statusKawin = detailData.status_kawin,
                        pekerjaan = detailData.pekerjaan,
                        kewarganegaraan = detailData.kewarganegaraan,
                        kontak = detailData.kontak,

                        // Data Keluarga
                        namaAyah = detailData.nama_ayah,
                        nikAyah = detailData.nik_ayah,
                        tempatLahirAyah = detailData.tempat_lahir_ayah,
                        tanggalLahirAyah = detailData.tanggal_lahir_ayah,
                        pekerjaanOrtu = detailData.pekerjaan_ortu,
                        penghasilanOrtu = detailData.penghasilan_ortu,
                        tanggunganOrtu = detailData.tanggungan_ortu,
                        alamatOrtu = detailData.alamat_ortu,

                        namaIbu = detailData.nama_ibu,
                        nikIbu = detailData.nik_ibu,
                        tempatLahirIbu = detailData.tempat_lahir_ibu,
                        tanggalLahirIbu = detailData.tanggal_lahir_ibu,

                        // Data Pasangan
                        namaSuami = detailData.nama_suami,
                        nikSuami = detailData.nik_suami,
                        alamatSuami = detailData.alamat_suami,
                        tempatLahirSuami = detailData.tempat_lahir_suami,
                        tanggalLahirSuami = detailData.tanggal_lahir_suami,
                        pekerjaanSuami = detailData.pekerjaan_suami,
                        agamaSuami = detailData.agama_suami,
                        statusKawinSuami = detailData.status_kawin_suami,
                        kewarganegaraanSuami = detailData.kewarganegaraan_suami,

                        namaIstri = detailData.nama_istri,
                        nikIstri = detailData.nik_istri,
                        alamatIstri = detailData.alamat_istri,
                        tempatLahirIstri = detailData.tempat_lahir_istri,
                        tanggalLahirIstri = detailData.tanggal_lahir_istri,
                        pekerjaanIstri = detailData.pekerjaan_istri,
                        agamaIstri = detailData.agama_istri,
                        statusKawinIstri = detailData.status_kawin_istri,
                        kewarganegaraanIstri = detailData.kewarganegaraan_istri,
                        jumlahIstri = detailData.jumlah_istri,

                        // Data Anak
                        namaAnak = detailData.nama_anak,
                        nikAnak = detailData.nik_anak,
                        tempatLahirAnak = detailData.tempat_lahir_anak,
                        tanggalLahirAnak = detailData.tanggal_lahir_anak,
                        jenisKelaminAnak = detailData.jenis_kelamin_anak,
                        anakKe = if (detailData.anak_ke > 0) detailData.anak_ke else null,
                        kelasAnak = detailData.kelas_anak,
                        namaSekolahAnak = detailData.nama_sekolah_anak,

                        // Data Usaha
                        namaUsaha = detailData.nama_usaha,
                        jenisUsaha = detailData.jenis_usaha,
                        bidangUsaha = detailData.bidang_usaha,
                        alamatUsaha = detailData.alamat_usaha,
                        namaPerusahaan = detailData.nama_perusahaan,
                        alamatPerusahaan = detailData.alamat_perusahaan,
                        jumlahKaryawan = detailData.jumlah_karyawan,
                        nib = detailData.nib,
                        npwp = detailData.npwp,
                        nomorAktaPendirian = detailData.nomor_akta_pendirian,

                        // Data Acara/Event
                        namaAcara = detailData.nama_acara,
                        tempatAcara = detailData.tempat_acara,
                        tanggal = detailData.tanggal,
                        hari = detailData.hari,
                        jam = detailData.jam,
                        lama = if (detailData.lama > 0) detailData.lama else null,
                        satuanLama = detailData.satuan_lama,
                        jumlahAnggota = if (detailData.jumlah_anggota > 0) detailData.jumlah_anggota else null,
                        penanggungJawab = detailData.penanggung_jawab,

                        // Data Pindah/Perjalanan
                        alamatPindah = detailData.alamat_pindah,
                        alasanPindah = detailData.alasan_pindah,
                        tempatTujuan = detailData.tempat_tujuan,
                        tanggalKeberangkatan = detailData.tanggal_keberangkatan,
                        jumlahPengikut = if (detailData.jumlah_pengikut > 0) detailData.jumlah_pengikut else null,
                        maksudTujuan = detailData.maksud_tujuan,
                        keperluan = detailData.keperluan,

                        // Data Kematian
                        namaMendiang = detailData.nama_mendiang,
                        nikMendiang = detailData.nik_mendiang,
                        tempatLahirMendiang = detailData.tempat_lahir_mendiang,
                        tanggalLahirMendiang = detailData.tanggal_lahir_mendiang,
                        jenisKelaminMendiang = detailData.jenis_kelamin_mendiang,
                        alamatMendiang = detailData.alamat_mendiang,
                        tanggalMeninggal = detailData.tanggal_meninggal,
                        hariMeninggal = detailData.hari_meninggal,
                        tempatMeninggal = detailData.tempat_meninggal,
                        sebabMeninggal = detailData.sebab_meninggal,
                        usia = if (detailData.usia > 0) detailData.usia else null,

                        // Data Kehilangan
                        namaOrangHilang = detailData.nama_orang_hilang,
                        jenisBarang = detailData.jenis_barang,
                        tempatKehilangan = detailData.tempat_kehilangan,
                        waktuKehilangan = detailData.waktu_kehilangan,
                        hilangSejak = detailData.hilang_sejak,
                        ciri = detailData.ciri,
                        penyebab = detailData.penyebab,

                        // Data Properti
                        luasTanah = if (detailData.luas_tanah > 0) detailData.luas_tanah else null,
                        luasBangunan = if (detailData.luas_bangunan > 0) detailData.luas_bangunan else null,
                        peruntukanBangunan = detailData.peruntukan_bangunan,
                        statusKepemilikanBangunan = detailData.status_kepemilikan_bangunan,

                        // Data Lainnya
                        hubungan = detailData.hubungan,
                        alasanIzin = detailData.alasan_izin,
                        deskripsi = detailData.deskripsi,
                        dasarPengajuan = detailData.dasar_pengajuan,
                        dimulai = detailData.dimulai,
                        terhitungDari = detailData.terhitung_dari,
                        jabatan = detailData.jabatan,
                        perbedaan = detailData.perbedaan,
                        tercantum = detailData.tercantum,
                        wargaDesa = detailData.warga_desa,

                        // Data Penerima
                        penerima = detailData.penerima?.map { penerima ->
                            PenerimaDetail(
                                nama = penerima.nama,
                                nik = penerima.nik,
                                jabatan = penerima.jabatan
                            )
                        } ?: emptyList()
                    )

                    _uiState.value = _uiState.value.copy(
                        suratDetail = suratDetail,
                        isDataLoaded = true
                    )

                    _suratDetailEvent.emit(SuratDetailEvent.DataLoaded)
                }
                is SuratDetailResult.Error -> {
                    errorMessage = result.message
                    _suratDetailEvent.emit(SuratDetailEvent.Error(result.message))
                }
            }
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }

    // Helper function to map string status to enum
    private fun mapStringToStatusSurat(status: String): StatusSurat {
        return when (status.lowercase()) {
            "menunggu", "pending" -> StatusSurat.MENUNGGU
            "diproses", "processing" -> StatusSurat.DIPROSES
            "selesai", "completed", "done" -> StatusSurat.SELESAI
            "dibatalkan", "rejected" -> StatusSurat.DIBATALKAN
            else -> StatusSurat.MENUNGGU
        }
    }

    sealed class SuratDetailEvent {
        data object DataLoaded : SuratDetailEvent()
        data class Error(val message: String) : SuratDetailEvent()
    }
}

// Data class untuk UI State
data class SuratDetailUiState(
    val suratDetail: SuratDetail? = null,
    val isDataLoaded: Boolean = false
)

// Data class untuk detail surat
data class SuratDetail(
    val id: String,
    val jenisSurat: String,
    val nomorSurat: String?,
    val nomorPengajuan: String?,
    val status: String,
    val tanggalPengajuan: String?,
    val tanggalSelesai: String?,
    val diprosesOleh: String?,
    val disahkanOleh: String?,

    // Data Pemohon
    val nama: String?,
    val nik: String?,
    val alamat: String?,
    val tempatLahir: String?,
    val tanggalLahir: String?,
    val jenisKelamin: String?,
    val agama: String?,
    val statusKawin: String?,
    val pekerjaan: String?,
    val kewarganegaraan: String?,
    val kontak: String?,

    // Data Keluarga
    val namaAyah: String?,
    val nikAyah: String?,
    val tempatLahirAyah: String?,
    val tanggalLahirAyah: String?,
    val pekerjaanOrtu: String?,
    val penghasilanOrtu: String?,
    val tanggunganOrtu: String?,
    val alamatOrtu: String?,

    val namaIbu: String?,
    val nikIbu: String?,
    val tempatLahirIbu: String?,
    val tanggalLahirIbu: String?,

    // Data Pasangan
    val namaSuami: String?,
    val nikSuami: String?,
    val alamatSuami: String?,
    val tempatLahirSuami: String?,
    val tanggalLahirSuami: String?,
    val pekerjaanSuami: String?,
    val agamaSuami: String?,
    val statusKawinSuami: String?,
    val kewarganegaraanSuami: String?,

    val namaIstri: String?,
    val nikIstri: String?,
    val alamatIstri: String?,
    val tempatLahirIstri: String?,
    val tanggalLahirIstri: String?,
    val pekerjaanIstri: String?,
    val agamaIstri: String?,
    val statusKawinIstri: String?,
    val kewarganegaraanIstri: String?,
    val jumlahIstri: String?,

    // Data Anak
    val namaAnak: String?,
    val nikAnak: String?,
    val tempatLahirAnak: String?,
    val tanggalLahirAnak: String?,
    val jenisKelaminAnak: String?,
    val anakKe: Int?,
    val kelasAnak: String?,
    val namaSekolahAnak: String?,

    // Data Usaha
    val namaUsaha: String?,
    val jenisUsaha: String?,
    val bidangUsaha: String?,
    val alamatUsaha: String?,
    val namaPerusahaan: String?,
    val alamatPerusahaan: String?,
    val jumlahKaryawan: String?,
    val nib: String?,
    val npwp: String?,
    val nomorAktaPendirian: String?,

    // Data Acara/Event
    val namaAcara: String?,
    val tempatAcara: String?,
    val tanggal: String?,
    val hari: String?,
    val jam: String?,
    val lama: Int?,
    val satuanLama: String?,
    val jumlahAnggota: Int?,
    val penanggungJawab: String?,

    // Data Pindah/Perjalanan
    val alamatPindah: String?,
    val alasanPindah: String?,
    val tempatTujuan: String?,
    val tanggalKeberangkatan: String?,
    val jumlahPengikut: Int?,
    val maksudTujuan: String?,
    val keperluan: String?,

    // Data Kematian
    val namaMendiang: String?,
    val nikMendiang: String?,
    val tempatLahirMendiang: String?,
    val tanggalLahirMendiang: String?,
    val jenisKelaminMendiang: String?,
    val alamatMendiang: String?,
    val tanggalMeninggal: String?,
    val hariMeninggal: String?,
    val tempatMeninggal: String?,
    val sebabMeninggal: String?,
    val usia: Int?,

    // Data Kehilangan
    val namaOrangHilang: String?,
    val jenisBarang: String?,
    val tempatKehilangan: String?,
    val waktuKehilangan: String?,
    val hilangSejak: String?,
    val ciri: String?,
    val penyebab: String?,

    // Data Properti
    val luasTanah: Int?,
    val luasBangunan: Int?,
    val peruntukanBangunan: String?,
    val statusKepemilikanBangunan: String?,

    // Data Lainnya
    val hubungan: String?,
    val alasanIzin: String?,
    val deskripsi: String?,
    val dasarPengajuan: String?,
    val dimulai: String?,
    val terhitungDari: String?,
    val jabatan: String?,
    val perbedaan: String?,
    val tercantum: String?,
    val wargaDesa: Boolean?,

    // Data Penerima
    val penerima: List<PenerimaDetail>
)

data class PenerimaDetail(
    val nama: String,
    val nik: String,
    val jabatan: String
)