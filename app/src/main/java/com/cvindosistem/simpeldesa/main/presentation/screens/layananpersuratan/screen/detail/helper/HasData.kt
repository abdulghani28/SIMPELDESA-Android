package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper

import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

// Data Pribadi/Pemohon
internal fun hasPersonalData(suratDetail: SuratDetail): Boolean {
    val dataPribadi = suratDetail.dataPribadi
    return listOf(
        dataPribadi.nama,
        dataPribadi.nik,
        dataPribadi.alamat,
        dataPribadi.alamatIdentitas,
        dataPribadi.tempatLahir,
        dataPribadi.tanggalLahir,
        dataPribadi.jenisKelamin,
        dataPribadi.agama,
        dataPribadi.agamaId,
        dataPribadi.statusKawin,
        dataPribadi.statusKawinId,
        dataPribadi.pekerjaan,
        dataPribadi.kewarganegaraan,
        dataPribadi.kontak,
        dataPribadi.jabatan
    ).any { !it.isNullOrBlank() } || dataPribadi.wargaDesa == true || dataPribadi.jumlahPengikut != 0
}

// Data Acara (SR Keramaian)
internal fun hasEventData(suratDetail: SuratDetail): Boolean {
    val dataAcara = suratDetail.dataAcara ?: return false
    return listOf(
        dataAcara.namaAcara,
        dataAcara.tempatAcara,
        dataAcara.hari,
        dataAcara.tanggal,
        dataAcara.dimulai,
        dataAcara.selesai,
        dataAcara.penanggungJawab
    ).any { !it.isNullOrBlank() }
}

// Data Barang Hilang (SP Kehilangan)
internal fun hasLostItemData(suratDetail: SuratDetail): Boolean {
    val dataBarangHilang = suratDetail.dataBarangHilang ?: return false
    return listOf(
        dataBarangHilang.jenisBarang,
        dataBarangHilang.ciri,
        dataBarangHilang.tempatKehilangan,
        dataBarangHilang.waktuKehilangan
    ).any { !it.isNullOrBlank() }
}

// Data Suami (SP Pernikahan)
internal fun hasHusbandData(suratDetail: SuratDetail): Boolean {
    val dataSuami = suratDetail.dataSuami ?: return false
    return listOf(
        dataSuami.nikSuami,
        dataSuami.namaSuami,
        dataSuami.namaSuamiSebelumnya,
        dataSuami.tanggalLahirSuami,
        dataSuami.tempatLahirSuami,
        dataSuami.alamatSuami,
        dataSuami.agamaSuamiId,
        dataSuami.kewarganegaraanSuami,
        dataSuami.pekerjaanSuami,
        dataSuami.statusKawinSuamiId
    ).any { !it.isNullOrBlank() }
}

// Data Istri (SP Pernikahan)
internal fun hasWifeData(suratDetail: SuratDetail): Boolean {
    val dataIstri = suratDetail.dataIstri ?: return false
    return listOf(
        dataIstri.nikIstri,
        dataIstri.namaIstri,
        dataIstri.namaIstriSebelumnya,
        dataIstri.tanggalLahirIstri,
        dataIstri.tempatLahirIstri,
        dataIstri.alamatIstri,
        dataIstri.agamaIstriId,
        dataIstri.kewarganegaraanIstri,
        dataIstri.pekerjaanIstri,
        dataIstri.statusKawinIstriId
    ).any { !it.isNullOrBlank() }
}

// Data Ayah Suami (SP Pernikahan)
internal fun hasHusbandFatherData(suratDetail: SuratDetail): Boolean {
    val dataAyahSuami = suratDetail.dataAyahSuami ?: return false
    return listOf(
        dataAyahSuami.nikAyahSuami,
        dataAyahSuami.namaAyahSuami,
        dataAyahSuami.tanggalLahirAyahSuami,
        dataAyahSuami.tempatLahirAyahSuami,
        dataAyahSuami.alamatAyahSuami,
        dataAyahSuami.agamaAyahSuamiId,
        dataAyahSuami.kewarganegaraanAyahSuami,
        dataAyahSuami.pekerjaanAyahSuami
    ).any { !it.isNullOrBlank() }
}

// Data Ibu Suami (SP Pernikahan)
internal fun hasHusbandMotherData(suratDetail: SuratDetail): Boolean {
    val dataIbuSuami = suratDetail.dataIbuSuami ?: return false
    return listOf(
        dataIbuSuami.nikIbuSuami,
        dataIbuSuami.namaIbuSuami,
        dataIbuSuami.tanggalLahirIbuSuami,
        dataIbuSuami.tempatLahirIbuSuami,
        dataIbuSuami.alamatIbuSuami,
        dataIbuSuami.agamaIbuSuamiId,
        dataIbuSuami.kewarganegaraanIbuSuami,
        dataIbuSuami.pekerjaanIbuSuami
    ).any { !it.isNullOrBlank() }
}

// Data Ayah Istri (SP Pernikahan)
internal fun hasWifeFatherData(suratDetail: SuratDetail): Boolean {
    val dataAyahIstri = suratDetail.dataAyahIstri ?: return false
    return listOf(
        dataAyahIstri.nikAyahIstri,
        dataAyahIstri.namaAyahIstri,
        dataAyahIstri.tanggalLahirAyahIstri,
        dataAyahIstri.tempatLahirAyahIstri,
        dataAyahIstri.alamatAyahIstri,
        dataAyahIstri.agamaAyahIstriId,
        dataAyahIstri.kewarganegaraanAyahIstri,
        dataAyahIstri.pekerjaanAyahIstri
    ).any { !it.isNullOrBlank() }
}

// Data Ibu Istri (SP Pernikahan)
internal fun hasWifeMotherData(suratDetail: SuratDetail): Boolean {
    val dataIbuIstri = suratDetail.dataIbuIstri ?: return false
    return listOf(
        dataIbuIstri.nikIbuIstri,
        dataIbuIstri.namaIbuIstri,
        dataIbuIstri.tanggalLahirIbuIstri,
        dataIbuIstri.tempatLahirIbuIstri,
        dataIbuIstri.alamatIbuIstri,
        dataIbuIstri.agamaIbuIstriId,
        dataIbuIstri.kewarganegaraanIbuIstri,
        dataIbuIstri.pekerjaanIbuIstri
    ).any { !it.isNullOrBlank() }
}

// Data Pernikahan (SP Pernikahan)
internal fun hasMarriageData(suratDetail: SuratDetail): Boolean {
    val dataPernikahan = suratDetail.dataPernikahan ?: return false
    return listOf(
        dataPernikahan.hari,
        dataPernikahan.tanggal,
        dataPernikahan.jam,
        dataPernikahan.tempat
    ).any { dataPernikahan.jumlahIstri != null }
}

// Data Pindah (SP Pindah Domisili)
internal fun hasRelocationData(suratDetail: SuratDetail): Boolean {
    val dataPindah = suratDetail.dataPindah ?: return false
    return listOf(
        dataPindah.alamat,
        dataPindah.alamatPindah,
        dataPindah.alasanPindah
    ).any { !dataPindah.alamatPindah.isNullOrBlank() } || dataPindah.jumlahAnggota != null
}

// Data Pemberi Kuasa (Surat Kuasa)
internal fun hasGrantorData(suratDetail: SuratDetail): Boolean {
    val dataPemberiKuasa = suratDetail.dataPemberiKuasa ?: return false
    return listOf(
        dataPemberiKuasa.nikPemberi,
        dataPemberiKuasa.namaPemberi,
        dataPemberiKuasa.jabatanPemberi
    ).any { !it.isNullOrBlank() }
}

// Data Penerima Kuasa (Surat Kuasa)
internal fun hasReceiverData(suratDetail: SuratDetail): Boolean {
    val dataPenerimaKuasa = suratDetail.dataPenerimaKuasa ?: return false
    return listOf(
        dataPenerimaKuasa.nikPenerima,
        dataPenerimaKuasa.namaPenerima,
        dataPenerimaKuasa.jabatanPenerima
    ).any { !it.isNullOrBlank() }
}

// Data Kuasa (Surat Kuasa)
internal fun hasAuthorityData(suratDetail: SuratDetail): Boolean {
    val dataKuasa = suratDetail.dataKuasa ?: return false
    return listOf(
        dataKuasa.disposisiKuasaSebagai,
        dataKuasa.disposisiKuasaUntuk
    ).any { !it.isNullOrBlank() }
}

// Data Pemberi Tugas (Surat Tugas)
internal fun hasTaskAssignerData(suratDetail: SuratDetail): Boolean {
    val dataPemberiTugas = suratDetail.dataPemberiTugas ?: return false
    return listOf(
        dataPemberiTugas.jabatanPemberi,
        dataPemberiTugas.nama,
        dataPemberiTugas.nik,
        dataPemberiTugas.ditugaskanUntuk
    ).any { !dataPemberiTugas.ditugaskanUntuk.isNullOrBlank() }
}

// Data Tugas (Surat Tugas)
internal fun hasTaskData(suratDetail: SuratDetail): Boolean {
    val dataTugas = suratDetail.dataTugas ?: return false
    return listOf(
        dataTugas.deskripsi,
        dataTugas.ditugaskanUntuk
    ).any { !it.isNullOrBlank() }
}

// Data Penerima Tugas (Surat Tugas)
internal fun hasTaskReceiverData(suratDetail: SuratDetail): Boolean {
    return suratDetail.dataPenerimaTugas.isNotEmpty() &&
            suratDetail.dataPenerimaTugas.any { penerima ->
                listOf(penerima.nama, penerima.nik, penerima.jabatan).any { !it.isNullOrBlank() }
            }
}

// Data Pemohon (SK Beda Identitas)
internal fun hasApplicantData(suratDetail: SuratDetail): Boolean {
    val dataPemohon = suratDetail.dataPemohon ?: return false
    return !dataPemohon.diajukanOlehNik.isNullOrBlank()
}

// Data Identitas 1 (SK Beda Identitas)
internal fun hasIdentity1Data(suratDetail: SuratDetail): Boolean {
    val dataIdentitas1 = suratDetail.dataIdentitas1 ?: return false
    return listOf(
        dataIdentitas1.nama1,
        dataIdentitas1.nomor1,
        dataIdentitas1.tanggalLahir1,
        dataIdentitas1.tempatLahir1,
        dataIdentitas1.alamat1,
        dataIdentitas1.tercantumId
    ).any { !it.isNullOrBlank() }
}

// Data Identitas 2 (SK Beda Identitas)
internal fun hasIdentity2Data(suratDetail: SuratDetail): Boolean {
    val dataIdentitas2 = suratDetail.dataIdentitas2 ?: return false
    return listOf(
        dataIdentitas2.nama2,
        dataIdentitas2.nomor2,
        dataIdentitas2.tanggalLahir2,
        dataIdentitas2.tempatLahir2,
        dataIdentitas2.alamat2,
        dataIdentitas2.tercantumId2
    ).any { !it.isNullOrBlank() }
}

// Data Perbedaan (SK Beda Identitas)
internal fun hasDifferenceData(suratDetail: SuratDetail): Boolean {
    val dataPerbedaan = suratDetail.dataPerbedaan ?: return false
    return !dataPerbedaan.perbedaanId.isNullOrBlank()
}

// Data Perjalanan (SK Bepergian)
internal fun hasTravelData(suratDetail: SuratDetail): Boolean {
    val dataPerjalanan = suratDetail.dataPerjalanan ?: return false
    return listOf(
        dataPerjalanan.tempatTujuan,
        dataPerjalanan.tanggalKeberangkatan,
        dataPerjalanan.satuanLama,
        dataPerjalanan.maksudTujuan
    ).any { !dataPerjalanan.maksudTujuan.isNullOrBlank() }
}

// Data Orang Hilang (SK Ghaib)
internal fun hasMissingPersonData(suratDetail: SuratDetail): Boolean {
    val dataOrangHilang = suratDetail.dataOrangHilang ?: return false
    return listOf(
        dataOrangHilang.namaOrangHilang,
        dataOrangHilang.jenisKelamin,
        dataOrangHilang.hilangSejak
    ).any { !dataOrangHilang.hilangSejak.isNullOrBlank() } || dataOrangHilang.usia != null
}

// Data Pekerjaan (SK Izin Tidak Masuk Kerja)
internal fun hasJobData(suratDetail: SuratDetail): Boolean {
    val dataPekerjaan = suratDetail.dataPekerjaan ?: return false
    return listOf(
        dataPekerjaan.namaPerusahaan,
        dataPekerjaan.jabatan,
        dataPekerjaan.alasanIzin
    ).any { !dataPekerjaan.alasanIzin.isNullOrBlank() }
}

// Data Izin (SK Izin Tidak Masuk Kerja)
internal fun hasPermitData(suratDetail: SuratDetail): Boolean {
    val dataIzin = suratDetail.dataIzin ?: return false
    return listOf(
        dataIzin.alasanIzin,
        dataIzin.terhitungDari,
        dataIzin.maksudTujuan
    ).any { !dataIzin.alasanIzin.isNullOrBlank() }
}

// Data Status (SK Janda/Duda)
internal fun hasStatusData(suratDetail: SuratDetail): Boolean {
    val dataStatus = suratDetail.dataStatus ?: return false
    return listOf(
        dataStatus.dasarPengajuan,
        dataStatus.penyebab,
        dataStatus.nomorPengajuan
    ).any { !it.isNullOrBlank() }
}

// Data Bayi (SK Kelahiran)
internal fun hasBabyData(suratDetail: SuratDetail): Boolean {
    val dataBayi = suratDetail.dataBayi ?: return false
    return listOf(
        dataBayi.nama,
        dataBayi.jenisKelamin,
        dataBayi.tanggalLahir,
        dataBayi.jamLahir,
        dataBayi.tempatLahir
    ).any { dataBayi.anakKe != null }
}

// Data Ayah (SK Kelahiran)
internal fun hasFatherData(suratDetail: SuratDetail): Boolean {
    val dataAyah = suratDetail.dataAyah ?: return false
    return listOf(
        dataAyah.nikAyah,
        dataAyah.namaAyah,
        dataAyah.tanggalLahirAyah,
        dataAyah.tempatLahirAyah,
        dataAyah.alamatAyah
    ).any { !it.isNullOrBlank() }
}

// Data Ibu (SK Kelahiran)
internal fun hasMotherData(suratDetail: SuratDetail): Boolean {
    val dataIbu = suratDetail.dataIbu ?: return false
    return listOf(
        dataIbu.nikIbu,
        dataIbu.namaIbu,
        dataIbu.tanggalLahirIbu,
        dataIbu.tempatLahirIbu,
        dataIbu.alamatIbu
    ).any { !it.isNullOrBlank() }
}

// Data Almarhum (SK Kematian)
internal fun hasDeceasedData(suratDetail: SuratDetail): Boolean {
    val dataAlmarhum = suratDetail.dataAlmarhum ?: return false
    return listOf(
        dataAlmarhum.namaMendiang,
        dataAlmarhum.nikMendiang,
        dataAlmarhum.jenisKelaminMendiang,
        dataAlmarhum.tanggalLahirMendiang,
        dataAlmarhum.tempatLahirMendiang,
        dataAlmarhum.alamatMendiang,
        dataAlmarhum.tanggalMeninggal,
        dataAlmarhum.hariMeninggal,
        dataAlmarhum.tempatMeninggal,
        dataAlmarhum.sebabMeninggal
    ).any { !it.isNullOrBlank() }
}

// Data Orang Tua (SK Penghasilan)
internal fun hasParentData(suratDetail: SuratDetail): Boolean {
    val dataOrangTua = suratDetail.dataOrangTua ?: return false
    return listOf(
        dataOrangTua.nikOrtu,
        dataOrangTua.namaOrtu,
        dataOrangTua.jenisKelaminOrtu,
        dataOrangTua.tanggalLahirOrtu,
        dataOrangTua.tempatLahirOrtu,
        dataOrangTua.alamatOrtu,
        dataOrangTua.pekerjaanOrtu,
        dataOrangTua.penghasilanOrtu,
        dataOrangTua.tanggunganOrtu
    ).any { !it.isNullOrBlank() }
}

// Data Anak (SK Penghasilan)
internal fun hasChildData(suratDetail: SuratDetail): Boolean {
    val dataAnak = suratDetail.dataAnak ?: return false
    return listOf(
        dataAnak.nikAnak,
        dataAnak.namaAnak,
        dataAnak.jenisKelaminAnak,
        dataAnak.tanggalLahirAnak,
        dataAnak.tempatLahirAnak,
        dataAnak.namaSekolahAnak,
        dataAnak.kelasAnak
    ).any { !it.isNullOrBlank() }
}

// Data Usaha (SK Usaha)
internal fun hasBusinessData(suratDetail: SuratDetail): Boolean {
    val dataUsaha = suratDetail.dataUsaha ?: return false
    return listOf(
        dataUsaha.namaUsaha,
        dataUsaha.alamatUsaha,
        dataUsaha.bidangUsahaId,
        dataUsaha.jenisUsahaId,
        dataUsaha.npwp,
    ).any { !dataUsaha.namaUsaha.isNullOrBlank() }
}

// Data Perusahaan (SK Perusahaan)
internal fun hasCompanyData(suratDetail: SuratDetail): Boolean {
    val dataUsaha = suratDetail.dataPerusahaan ?: return false
    return listOf(
        dataUsaha.namaPerusahaan,
        dataUsaha.alamatPerusahaan,
        dataUsaha.bidangUsahaId,
        dataUsaha.jenisUsahaId,
        dataUsaha.noAkta,
        dataUsaha.npwp,
        dataUsaha.jumlahKaryawan,
        dataUsaha.luasBangunan,
        dataUsaha.luasTanah,
        dataUsaha.peruntukanBangunan,
        dataUsaha.statusKepemilikan,
        dataUsaha.nib
    ).any { !dataUsaha.npwp.isNullOrBlank() } ||
            dataUsaha.luasTanah != 0 || dataUsaha.luasBangunan != 0
}

// Helper untuk mengecek keperluan
internal fun hasPurposeData(suratDetail: SuratDetail): Boolean {
    return !suratDetail.keperluan.isNullOrBlank()
}

// Helper untuk mengecek hubungan (SK Ghaib dan SK Kehilangan)
internal fun hasRelationshipData(suratDetail: SuratDetail): Boolean {
    return !suratDetail.hubunganId.isNullOrBlank()
}