package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar

data class SPCatatanKepolisianRequest(
    val alamat: String,
    val disahkan_oleh: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nik: String,
    val pekerjaan: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)