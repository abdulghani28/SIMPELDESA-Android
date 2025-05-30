package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi

data class SRKeramaianRequest(
    val alamat: String,
    val dimulai: String,
    val disahkan_oleh: String,
    val hari: String,
    val jenis_kelamin: String,
    val kontak: String,
    val nama: String,
    val nama_acara: String,
    val nik: String,
    val pekerjaan: String,
    val penanggung_jawab: String,
    val selesai: String,
    val tanggal: String,
    val tanggal_lahir: String,
    val tempat_acara: String,
    val tempat_lahir: String
)