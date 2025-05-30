package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKPenghasilanRequest(
    val alamat: String,
    val alamat_ortu: String,
    val disahkan_oleh: String,
    val jenis_kelamin_anak: String,
    val jenis_kelamin_ortu: String,
    val kelas_anak: String,
    val keperluan: String,
    val nama: String,
    val nama_anak: String,
    val nama_ortu: String,
    val nama_sekolah_anak: String,
    val nik: String,
    val nik_anak: String,
    val nik_ortu: String,
    val pekerjaan_ortu: String,
    val penghasilan_ortu: Int,
    val tanggal_lahir_anak: String,
    val tanggal_lahir_ortu: String,
    val tanggungan_ortu: Int,
    val tempat_lahir_anak: String,
    val tempat_lahir_ortu: String
)