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
                        // Data Umum
                        dataUmum = DataUmum(
                            id = detailData.id.orEmpty(),
                            jenisSurat = result.data.meta.jenis_surat,
                            nomorSurat = detailData.nomor_surat,
                            nomorPengajuan = detailData.nomor_pengajuan,
                            status = detailData.status.orEmpty(),
                            organisasiId = detailData.organisasi_id,
                            tanggalPengajuan = detailData.created_at,
                            tanggalSelesai = detailData.selesai,
                            diprosesOleh = detailData.diproses_oleh,
                            diprosesOlehId = detailData.diproses_oleh_id,
                            disahkanOleh = detailData.disahkan_oleh,
                            disahkanOlehId = detailData.disahkan_oleh_id,
                            createdAt = detailData.created_at,
                            updatedAt = detailData.updated_at,
                            deletedAt = detailData.deleted_at
                        ),

                        // Data Keperluan (hampir selalu ada)
                        keperluan = detailData.keperluan,

                        // Data Pribadi/Pemohon
                        dataPribadi = DataPribadi(
                            nama = detailData.nama,
                            nik = detailData.nik,
                            alamat = detailData.alamat,
                            alamatIdentitas = detailData.alamat_identitas,
                            tempatLahir = detailData.tempat_lahir,
                            tanggalLahir = detailData.tanggal_lahir,
                            jenisKelamin = detailData.jenis_kelamin,
                            agama = detailData.agama,
                            agamaId = detailData.agama_id,
                            statusKawin = detailData.status_kawin,
                            statusKawinId = detailData.status_kawin_id,
                            pekerjaan = detailData.pekerjaan,
                            kewarganegaraan = detailData.kewarganegaraan,
                            kontak = detailData.kontak,
                            jabatan = detailData.jabatan,
                            jumlahPengikut = detailData.jumlah_pengikut,
                            wargaDesa = detailData.warga_desa
                        ),

                        // Data Acara (untuk SR Keramaian)
                        dataAcara = DataAcara(
                            namaAcara = detailData.nama_acara,
                            tempatAcara = detailData.tempat_acara,
                            hari = detailData.hari,
                            tanggal = detailData.tanggal,
                            dimulai = detailData.dimulai,
                            selesai = null, // tidak ada dalam API response
                            penanggungJawab = detailData.penanggung_jawab
                        ),

                        // Data Barang Hilang (untuk SP Kehilangan)
                        dataBarangHilang = DataBarangHilang(
                            jenisBarang = detailData.jenis_barang,
                            ciri = detailData.ciri,
                            tempatKehilangan = detailData.tempat_kehilangan,
                            waktuKehilangan = detailData.waktu_kehilangan
                        ),

                        // Data Suami (untuk SP Pernikahan)
                        dataSuami = DataSuami(
                            nikSuami = detailData.nik_suami,
                            namaSuami = detailData.nama_suami,
                            namaSuamiSebelumnya = detailData.nama_suami_sebelumnya,
                            tanggalLahirSuami = detailData.tanggal_lahir_suami,
                            tempatLahirSuami = detailData.tempat_lahir_suami,
                            alamatSuami = detailData.alamat_suami,
                            agamaSuamiId = detailData.agama_suami_id,
                            kewarganegaraanSuami = detailData.kewarganegaraan_suami,
                            pekerjaanSuami = detailData.pekerjaan_suami,
                            statusKawinSuamiId = detailData.status_kawin_suami_id
                        ),

                        // Data Istri (untuk SP Pernikahan)
                        dataIstri = DataIstri(
                            nikIstri = detailData.nik_istri,
                            namaIstri = detailData.nama_istri,
                            namaIstriSebelumnya = detailData.nama_istri_sebelumnya,
                            tanggalLahirIstri = detailData.tanggal_lahir_istri,
                            tempatLahirIstri = detailData.tempat_lahir_istri,
                            alamatIstri = detailData.alamat_istri,
                            agamaIstriId = detailData.agama_istri_id,
                            kewarganegaraanIstri = detailData.kewarganegaraan_istri,
                            pekerjaanIstri = detailData.pekerjaan_istri,
                            statusKawinIstriId = detailData.status_kawin_istri_id
                        ),

                        // Data Ayah Suami (untuk SP Pernikahan)
                        dataAyahSuami = DataAyahSuami(
                            nikAyahSuami = detailData.nik_ayah_suami,
                            namaAyahSuami = detailData.nama_ayah_suami,
                            tanggalLahirAyahSuami = detailData.tanggal_lahir_ayah_suami,
                            tempatLahirAyahSuami = detailData.tempat_lahir_ayah_suami,
                            alamatAyahSuami = detailData.alamat_ayah_suami,
                            agamaAyahSuamiId = detailData.agama_ayah_suami_id,
                            kewarganegaraanAyahSuami = detailData.kewarganegaraan_ayah_suami,
                            pekerjaanAyahSuami = detailData.pekerjaan_ayah_suami
                        ),

                        // Data Ibu Suami (untuk SP Pernikahan)
                        dataIbuSuami = DataIbuSuami(
                            nikIbuSuami = detailData.nik_ibu_suami,
                            namaIbuSuami = detailData.nama_ibu_suami,
                            tanggalLahirIbuSuami = detailData.tanggal_lahir_ibu_suami,
                            tempatLahirIbuSuami = detailData.tempat_lahir_ibu_suami,
                            alamatIbuSuami = detailData.alamat_ibu_suami,
                            agamaIbuSuamiId = detailData.agama_ibu_suami_id,
                            kewarganegaraanIbuSuami = detailData.kewarganegaraan_ibu_suami,
                            pekerjaanIbuSuami = detailData.pekerjaan_ibu_suami
                        ),

                        // Data Ayah Istri (untuk SP Pernikahan)
                        dataAyahIstri = DataAyahIstri(
                            nikAyahIstri = detailData.nik_ayah_istri,
                            namaAyahIstri = detailData.nama_ayah_istri,
                            tanggalLahirAyahIstri = detailData.tanggal_lahir_ayah_istri,
                            tempatLahirAyahIstri = detailData.tempat_lahir_ayah_istri,
                            alamatAyahIstri = detailData.alamat_ayah_istri,
                            agamaAyahIstriId = detailData.agama_ayah_istri_id,
                            kewarganegaraanAyahIstri = detailData.kewarganegaraan_ayah_istri,
                            pekerjaanAyahIstri = detailData.pekerjaan_ayah_istri
                        ),

                        // Data Ibu Istri (untuk SP Pernikahan)
                        dataIbuIstri = DataIbuIstri(
                            nikIbuIstri = detailData.nik_ibu_istri,
                            namaIbuIstri = detailData.nama_ibu_istri,
                            tanggalLahirIbuIstri = detailData.tanggal_lahir_ibu_istri,
                            tempatLahirIbuIstri = detailData.tempat_lahir_ibu_istri,
                            alamatIbuIstri = detailData.alamat_ibu_istri,
                            agamaIbuIstriId = detailData.agama_ibu_istri_id,
                            kewarganegaraanIbuIstri = detailData.kewarganegaraan_ibu_istri,
                            pekerjaanIbuIstri = detailData.pekerjaan_ibu_istri
                        ),

                        // Data Pernikahan (untuk SP Pernikahan)
                        dataPernikahan = DataPernikahan(
                            hari = detailData.hari,
                            tanggal = detailData.tanggal,
                            jam = detailData.jam,
                            tempat = detailData.tempat,
                            jumlahIstri = detailData.jumlah_istri
                        ),

                        // Data Pindah (untuk SP Pindah Domisili)
                        dataPindah = DataPindah(
                            alamat = detailData.alamat,
                            alamatPindah = detailData.alamat_pindah,
                            alasanPindah = detailData.alasan_pindah,
                            jumlahAnggota = if (detailData.jumlah_anggota > 0) detailData.jumlah_anggota else null
                        ),

                        // Data Pemberi Kuasa (untuk Surat Kuasa)
                        dataPemberiKuasa = DataPemberiKuasa(
                            nikPemberi = detailData.nik_pemberi,
                            namaPemberi = detailData.nama_pemberi,
                            jabatanPemberi = detailData.jabatan_pemberi
                        ),

                        // Data Penerima Kuasa (untuk Surat Kuasa)
                        dataPenerimaKuasa = DataPenerimaKuasa(
                            nikPenerima = detailData.nik_penerima,
                            namaPenerima = detailData.nama_penerima,
                            jabatanPenerima = detailData.jabatan_penerima
                        ),

                        // Data Kuasa (untuk Surat Kuasa)
                        dataKuasa = DataKuasa(
                            disposisiKuasaSebagai = detailData.disposisi_kuasa_sebagai,
                            disposisiKuasaUntuk = detailData.disposisi_kuasa_untuk
                        ),

                        // Data Pemberi Tugas (untuk Surat Tugas)
                        dataPemberiTugas = DataPemberiTugas(
                            nama = detailData.nama,
                            nik = detailData.nik,
                            jabatanPemberi = detailData.nama_pejabat,
                            ditugaskanUntuk = detailData.ditugaskan_untuk,
                        ),

                        // Data Tugas (untuk Surat Tugas)
                        dataTugas = DataTugas(
                            deskripsi = detailData.deskripsi,
                            ditugaskanUntuk = detailData.ditugaskan_untuk
                        ),

                        // Data Penerima Tugas (untuk Surat Tugas) - dalam list
                        dataPenerimaTugas = detailData.penerima?.map { penerima ->
                            DataPenerimaTugas(
                                nama = penerima.nama,
                                nik = penerima.nik,
                                jabatan = penerima.jabatan
                            )
                        } ?: emptyList(),

                        // Data Pemohon (untuk SK Beda Identitas)
                        dataPemohon = DataPemohon(
                            diajukanOlehNik = detailData.diajukan_oleh_nik
                        ),

                        // Data Identitas 1 (untuk SK Beda Identitas)
                        dataIdentitas1 = DataIdentitas1(
                            nama1 = detailData.nama_1,
                            nomor1 = detailData.nomor_1,
                            tanggalLahir1 = detailData.tanggal_lahir_1,
                            tempatLahir1 = detailData.tempat_lahir_1,
                            alamat1 = detailData.alamat_1,
                            tercantumId = detailData.tercantum_id
                        ),

                        // Data Identitas 2 (untuk SK Beda Identitas)
                        dataIdentitas2 = DataIdentitas2(
                            nama2 = detailData.nama_2,
                            nomor2 = detailData.nomor_2,
                            tanggalLahir2 = detailData.tanggal_lahir_2,
                            tempatLahir2 = detailData.tempat_lahir_2,
                            alamat2 = detailData.alamat_2,
                            tercantumId2 = detailData.tercantum_id_2
                        ),

                        // Data Perbedaan (untuk SK Beda Identitas)
                        dataPerbedaan = DataPerbedaan(
                            perbedaanId = detailData.perbedaan_id
                        ),

                        // Data Perjalanan (untuk SK Bepergian)
                        dataPerjalanan = DataPerjalanan(
                            tempatTujuan = detailData.tempat_tujuan,
                            tanggalKeberangkatan = detailData.tanggal_keberangkatan,
                            lama = if (detailData.lama > 0) detailData.lama else null,
                            satuanLama = detailData.satuan_lama,
                            maksudTujuan = detailData.maksud_tujuan,
                            jumlahPengikut = if (detailData.jumlah_pengikut > 0) detailData.jumlah_pengikut else null
                        ),

                        // Data Domisili (untuk SK Domisili & SK Domisili Perusahaan)
                        dataDomisili = DataDomisili(
                            wargaDesa = detailData.warga_desa,
                            jumlahPengikut = if (detailData.jumlah_pengikut > 0) detailData.jumlah_pengikut else null
                        ),

                        // Data Orang Hilang (untuk SK Ghaib)
                        dataOrangHilang = DataOrangHilang(
                            namaOrangHilang = detailData.nama_orang_hilang,
                            jenisKelamin = detailData.jenis_kelamin,
                            usia = if (detailData.usia > 0) detailData.usia else null,
                            hilangSejak = detailData.hilang_sejak
                        ),

                        // Data Pekerjaan (untuk SK Izin Tidak Masuk Kerja)
                        dataPekerjaan = DataPekerjaan(
                            namaPerusahaan = detailData.nama_perusahaan,
                            jabatan = detailData.jabatan,
                            alasanIzin = detailData.alasan_izin
                        ),

                        // Data Izin (untuk SK Izin Tidak Masuk Kerja)
                        dataIzin = DataIzin(
                            alasanIzin = detailData.alasan_izin,
                            lama = if (detailData.lama > 0) detailData.lama else null,
                            terhitungDari = detailData.terhitung_dari,
                            maksudTujuan = detailData.maksud_tujuan,

                        ),

                        // Data Status (untuk SK Janda/Duda)
                        dataStatus = DataStatus(
                            dasarPengajuan = detailData.dasar_pengajuan,
                            penyebab = detailData.penyebab,
                            nomorPengajuan = detailData.nomor_pengajuan
                        ),

                        // Data Bayi (untuk SK Kelahiran)
                        dataBayi = DataBayi(
                            nama = detailData.nama_anak,
                            jenisKelamin = detailData.jenis_kelamin_anak,
                            tanggalLahir = detailData.tanggal_lahir_anak,
                            jamLahir = detailData.jam_lahir,
                            tempatLahir = detailData.tempat_lahir_anak,
                            anakKe = if (detailData.anak_ke > 0) detailData.anak_ke else null
                        ),

                        // Data Ayah (untuk SK Kelahiran)
                        dataAyah = DataAyah(
                            nikAyah = detailData.nik_ayah,
                            namaAyah = detailData.nama_ayah,
                            tanggalLahirAyah = detailData.tanggal_lahir_ayah,
                            tempatLahirAyah = detailData.tempat_lahir_ayah,
                            alamatAyah = detailData.alamat_ayah
                        ),

                        // Data Ibu (untuk SK Kelahiran)
                        dataIbu = DataIbu(
                            nikIbu = detailData.nik_ibu,
                            namaIbu = detailData.nama_ibu,
                            tanggalLahirIbu = detailData.tanggal_lahir_ibu,
                            tempatLahirIbu = detailData.tempat_lahir_ibu,
                            alamatIbu = detailData.alamat_ibu
                        ),

                        // Data Almarhum (untuk SK Kematian)
                        dataAlmarhum = DataAlmarhum(
                            namaMendiang = detailData.nama_mendiang,
                            nikMendiang = detailData.nik_mendiang,
                            jenisKelaminMendiang = detailData.jenis_kelamin_mendiang,
                            tanggalLahirMendiang = detailData.tanggal_lahir_mendiang,
                            tempatLahirMendiang = detailData.tempat_lahir_mendiang,
                            alamatMendiang = detailData.alamat_mendiang,
                            tanggalMeninggal = detailData.tanggal_meninggal,
                            hariMeninggal = detailData.hari_meninggal,
                            tempatMeninggal = detailData.tempat_meninggal,
                            sebabMeninggal = detailData.sebab_meninggal
                        ),

                        // Data Orang Tua (untuk SK Penghasilan)
                        dataOrangTua = DataOrangTua(
                            nikOrtu = detailData.nik_ortu,
                            namaOrtu = detailData.nama_ortu,
                            jenisKelaminOrtu = detailData.jenis_kelamin_ortu,
                            tanggalLahirOrtu = detailData.tanggal_lahir_ortu,
                            tempatLahirOrtu = detailData.tempat_lahir_ortu,
                            alamatOrtu = detailData.alamat_ortu,
                            pekerjaanOrtu = detailData.pekerjaan_ortu,
                            penghasilanOrtu = detailData.penghasilan_ortu,
                            tanggunganOrtu = detailData.tanggungan_ortu
                        ),

                        // Data Anak (untuk SK Penghasilan)
                        dataAnak = DataAnak(
                            nikAnak = detailData.nik_anak,
                            namaAnak = detailData.nama_anak,
                            jenisKelaminAnak = detailData.jenis_kelamin_anak,
                            tanggalLahirAnak = detailData.tanggal_lahir_anak,
                            tempatLahirAnak = detailData.tempat_lahir_anak,
                            namaSekolahAnak = detailData.nama_sekolah_anak,
                            kelasAnak = detailData.kelas_anak
                        ),

                        // Data Usaha (untuk SK Usaha)
                        dataUsaha = DataUsaha(
                            namaUsaha = detailData.nama_usaha,
                            alamatUsaha = detailData.alamat_usaha,
                            bidangUsahaId = detailData.bidang_usaha_id,
                            jenisUsahaId = detailData.jenis_usaha_id,
                            npwp = detailData.npwp,
                            wargaDesa = detailData.warga_desa,
                        ),

                        // Data Usaha (untuk SK Usaha)
                        dataPerusahaan = DataPerusahaan(
                            namaPerusahaan = detailData.nama_perusahaan,
                            alamatPerusahaa = detailData.alamat_perusahaan,
                            bidangUsahaId = detailData.bidang_usaha_id,
                            jenisUsahaId = detailData.jenis_usaha_id,
                            npwp = detailData.npwp,
                            wargaDesa = detailData.warga_desa,
                            statusKepemilikan = detailData.status_kepemilikan_bangunan,
                            jumlahKaryawan = detailData.jumlah_karyawan,
                            peruntukanBangunan = detailData.peruntukan_bangunan,
                            luasTanah = detailData.luas_tanah,
                            luasBangunan = detailData.luas_bangunan,
                            noAkta = detailData.nomor_akta_pendirian,
                            nib = detailData.nib
                        ),

                        // Data untuk keperluan hubungan (SK Kematian, SK Ghaib)
                        hubunganId = detailData.hubungan_id
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

// Main SuratDetail data class dengan pengelompokan yang lebih tepat
data class SuratDetail(
    // Data Umum pada Semua Surat
    val dataUmum: DataUmum,

    // Data Keperluan (hampir selalu ada di setiap surat)
    val keperluan: String?,

    // Data Pribadi/Pemohon (hampir selalu ada)
    val dataPribadi: DataPribadi,

    // Data spesifik per jenis surat
    val dataAcara: DataAcara? = null, // SR Keramaian
    val dataBarangHilang: DataBarangHilang? = null, // SP Kehilangan
    val dataSuami: DataSuami? = null, // SP Pernikahan
    val dataIstri: DataIstri? = null, // SP Pernikahan
    val dataAyahSuami: DataAyahSuami? = null, // SP Pernikahan
    val dataIbuSuami: DataIbuSuami? = null, // SP Pernikahan
    val dataAyahIstri: DataAyahIstri? = null, // SP Pernikahan
    val dataIbuIstri: DataIbuIstri? = null, // SP Pernikahan
    val dataPernikahan: DataPernikahan? = null, // SP Pernikahan
    val dataPindah: DataPindah? = null, // SP Pindah Domisili
    val dataPemberiKuasa: DataPemberiKuasa? = null, // Surat Kuasa
    val dataPenerimaKuasa: DataPenerimaKuasa? = null, // Surat Kuasa
    val dataKuasa: DataKuasa? = null, // Surat Kuasa
    val dataPemberiTugas: DataPemberiTugas? = null, // Surat Tugas
    val dataTugas: DataTugas? = null, // Surat Tugas
    val dataPenerimaTugas: List<DataPenerimaTugas> = emptyList(), // Surat Tugas
    val dataPemohon: DataPemohon? = null, // SK Beda Identitas
    val dataIdentitas1: DataIdentitas1? = null, // SK Beda Identitas
    val dataIdentitas2: DataIdentitas2? = null, // SK Beda Identitas
    val dataPerbedaan: DataPerbedaan? = null, // SK Beda Identitas
    val dataPerjalanan: DataPerjalanan? = null, // SK Bepergian
    val dataDomisili: DataDomisili? = null, // SK Domisili & SK Domisili Perusahaan
    val dataOrangHilang: DataOrangHilang? = null, // SK Ghaib
    val dataPekerjaan: DataPekerjaan? = null, // SK Izin Tidak Masuk Kerja
    val dataIzin: DataIzin? = null, // SK Izin Tidak Masuk Kerja
    val dataStatus: DataStatus? = null, // SK Janda/Duda
    val dataBayi: DataBayi? = null, // SK Kelahiran
    val dataAyah: DataAyah? = null, // SK Kelahiran
    val dataIbu: DataIbu? = null, // SK Kelahiran
    val dataAlmarhum: DataAlmarhum? = null, // SK Kematian
    val dataOrangTua: DataOrangTua? = null, // SK Penghasilan
    val dataAnak: DataAnak? = null, // SK Penghasilan
    val dataUsaha: DataUsaha? = null, // SK Usaha
    val dataPerusahaan: DataPerusahaan? = null, // SK Perusahaan

    // Data tambahan untuk hubungan (digunakan SK Ghaib dan SK Kehilangan)
    val hubunganId: String? = null
)

// Data classes untuk setiap kelompok
data class DataUmum(
    val id: String,
    val jenisSurat: String,
    val nomorSurat: String?,
    val nomorPengajuan: String?,
    val status: String,
    val organisasiId: String?,
    val tanggalPengajuan: String?,
    val tanggalSelesai: String?,
    val diprosesOleh: String?,
    val diprosesOlehId: String?,
    val disahkanOleh: String?,
    val disahkanOlehId: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?
)

data class DataPribadi(
    val nama: String?,
    val nik: String?,
    val alamat: String?,
    val alamatIdentitas: String?,
    val tempatLahir: String?,
    val tanggalLahir: String?,
    val jenisKelamin: String?,
    val agama: String?,
    val agamaId: String?,
    val statusKawin: String?,
    val statusKawinId: String?,
    val pekerjaan: String?,
    val kewarganegaraan: String?,
    val kontak: String?,
    val jabatan: String?,
    val jumlahPengikut: Int?,
    val wargaDesa: Boolean?
)

data class DataAcara(
    val namaAcara: String?,
    val tempatAcara: String?,
    val hari: String?,
    val tanggal: String?,
    val dimulai: String?,
    val selesai: String?,
    val penanggungJawab: String?
)

data class DataBarangHilang(
    val jenisBarang: String?,
    val ciri: String?,
    val tempatKehilangan: String?,
    val waktuKehilangan: String?
)

data class DataSuami(
    val nikSuami: String?,
    val namaSuami: String?,
    val namaSuamiSebelumnya: String?,
    val tanggalLahirSuami: String?,
    val tempatLahirSuami: String?,
    val alamatSuami: String?,
    val agamaSuamiId: String?,
    val kewarganegaraanSuami: String?,
    val pekerjaanSuami: String?,
    val statusKawinSuamiId: String?
)

data class DataIstri(
    val nikIstri: String?,
    val namaIstri: String?,
    val namaIstriSebelumnya: String?,
    val tanggalLahirIstri: String?,
    val tempatLahirIstri: String?,
    val alamatIstri: String?,
    val agamaIstriId: String?,
    val kewarganegaraanIstri: String?,
    val pekerjaanIstri: String?,
    val statusKawinIstriId: String?
)

// Melengkapi DataAyahSuami yang terpotong
data class DataAyahSuami(
    val nikAyahSuami: String?,
    val namaAyahSuami: String?,
    val tanggalLahirAyahSuami: String?,
    val tempatLahirAyahSuami: String?,
    val alamatAyahSuami: String?,
    val agamaAyahSuamiId: String?,
    val kewarganegaraanAyahSuami: String?,
    val pekerjaanAyahSuami: String?
)

data class DataIbuSuami(
    val nikIbuSuami: String?,
    val namaIbuSuami: String?,
    val tanggalLahirIbuSuami: String?,
    val tempatLahirIbuSuami: String?,
    val alamatIbuSuami: String?,
    val agamaIbuSuamiId: String?,
    val kewarganegaraanIbuSuami: String?,
    val pekerjaanIbuSuami: String?
)

data class DataAyahIstri(
    val nikAyahIstri: String?,
    val namaAyahIstri: String?,
    val tanggalLahirAyahIstri: String?,
    val tempatLahirAyahIstri: String?,
    val alamatAyahIstri: String?,
    val agamaAyahIstriId: String?,
    val kewarganegaraanAyahIstri: String?,
    val pekerjaanAyahIstri: String?
)

data class DataIbuIstri(
    val nikIbuIstri: String?,
    val namaIbuIstri: String?,
    val tanggalLahirIbuIstri: String?,
    val tempatLahirIbuIstri: String?,
    val alamatIbuIstri: String?,
    val agamaIbuIstriId: String?,
    val kewarganegaraanIbuIstri: String?,
    val pekerjaanIbuIstri: String?
)

data class DataPernikahan(
    val hari: String?,
    val tanggal: String?,
    val jam: String?,
    val tempat: String?,
    val jumlahIstri: Int?
)

data class DataPindah(
    val alamat: String?,
    val alamatPindah: String?,
    val alasanPindah: String?,
    val jumlahAnggota: Int?
)

data class DataPemberiKuasa(
    val nikPemberi: String?,
    val namaPemberi: String?,
    val jabatanPemberi: String?
)

data class DataPenerimaKuasa(
    val nikPenerima: String?,
    val namaPenerima: String?,
    val jabatanPenerima: String?
)

data class DataKuasa(
    val disposisiKuasaSebagai: String?,
    val disposisiKuasaUntuk: String?
)

data class DataPemberiTugas(
    val nama: String?,
    val nik: String?,
    val jabatanPemberi: String?,
    val ditugaskanUntuk: String?
)

data class DataTugas(
    val deskripsi: String?,
    val ditugaskanUntuk: String?
)

data class DataPenerimaTugas(
    val nama: String?,
    val nik: String?,
    val jabatan: String?
)

data class DataPemohon(
    val diajukanOlehNik: String?
)

data class DataIdentitas1(
    val nama1: String?,
    val nomor1: String?,
    val tanggalLahir1: String?,
    val tempatLahir1: String?,
    val alamat1: String?,
    val tercantumId: String?
)

data class DataIdentitas2(
    val nama2: String?,
    val nomor2: String?,
    val tanggalLahir2: String?,
    val tempatLahir2: String?,
    val alamat2: String?,
    val tercantumId2: String?
)

data class DataPerbedaan(
    val perbedaanId: String?
)

data class DataPerjalanan(
    val tempatTujuan: String?,
    val tanggalKeberangkatan: String?,
    val lama: Int?,
    val satuanLama: String?,
    val maksudTujuan: String?,
    val jumlahPengikut: Int?
)

data class DataDomisili(
    val wargaDesa: Boolean?,
    val jumlahPengikut: Int?
)

data class DataOrangHilang(
    val namaOrangHilang: String?,
    val jenisKelamin: String?,
    val usia: Int?,
    val hilangSejak: String?
)

data class DataPekerjaan(
    val namaPerusahaan: String?,
    val jabatan: String?,
    val alasanIzin: String?
)

data class DataIzin(
    val alasanIzin: String?,
    val lama: Int?,
    val terhitungDari: String?,
    val maksudTujuan: String?
)

data class DataStatus(
    val dasarPengajuan: String?,
    val penyebab: String?,
    val nomorPengajuan: String?
)

data class DataBayi(
    val nama: String?,
    val jenisKelamin: String?,
    val tanggalLahir: String?,
    val jamLahir: String?,
    val tempatLahir: String?,
    val anakKe: Int?
)

data class DataAyah(
    val nikAyah: String?,
    val namaAyah: String?,
    val tanggalLahirAyah: String?,
    val tempatLahirAyah: String?,
    val alamatAyah: String?
)

data class DataIbu(
    val nikIbu: String?,
    val namaIbu: String?,
    val tanggalLahirIbu: String?,
    val tempatLahirIbu: String?,
    val alamatIbu: String?
)

data class DataAlmarhum(
    val namaMendiang: String?,
    val nikMendiang: String?,
    val jenisKelaminMendiang: String?,
    val tanggalLahirMendiang: String?,
    val tempatLahirMendiang: String?,
    val alamatMendiang: String?,
    val tanggalMeninggal: String?,
    val hariMeninggal: String?,
    val tempatMeninggal: String?,
    val sebabMeninggal: String?
)

data class DataOrangTua(
    val nikOrtu: String?,
    val namaOrtu: String?,
    val jenisKelaminOrtu: String?,
    val tanggalLahirOrtu: String?,
    val tempatLahirOrtu: String?,
    val alamatOrtu: String?,
    val pekerjaanOrtu: String?,
    val penghasilanOrtu: String?,
    val tanggunganOrtu: String?
)

data class DataAnak(
    val nikAnak: String?,
    val namaAnak: String?,
    val jenisKelaminAnak: String?,
    val tanggalLahirAnak: String?,
    val tempatLahirAnak: String?,
    val namaSekolahAnak: String?,
    val kelasAnak: String?
)

data class DataUsaha(
    val namaUsaha: String?,
    val alamatUsaha: String?,
    val bidangUsahaId: String?,
    val jenisUsahaId: String?,
    val npwp: String?,
    val wargaDesa: Boolean?
)

data class DataPerusahaan(
    val namaPerusahaan: String?,
    val alamatPerusahaa : String?,
    val bidangUsahaId: String?,
    val jenisUsahaId: String?,
    val statusKepemilikan: String?,
    val noAkta: String?,
    val jumlahKaryawan: String?,
    val peruntukanBangunan: String?,
    val luasTanah: Int?,
    val luasBangunan: Int?,
    val npwp: String?,
    val nib: String?,
    val wargaDesa: Boolean?
)