package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKUsahaRequest(
    val alamat: String,
    val alamat_usaha: String,
    val bidang_usaha_id: String,
    val disahkan_oleh: String,
    val jenis_kelamin: String,
    val jenis_usaha_id: String,
    val keperluan: String,
    val nama: String,
    val nama_usaha: String,
    val nik: String,
    val npwp: String,
    val pekerjaan: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)