package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKGhaibRequest(
    val alamat: String,
    val disahkan_oleh: String,
    val hilang_sejak: String,
    val hubungan_id: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nama_orang_hilang: String,
    val nik: String,
    val usia: Int
)