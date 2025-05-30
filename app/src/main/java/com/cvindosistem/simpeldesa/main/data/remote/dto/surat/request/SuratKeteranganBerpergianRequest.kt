package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request

data class SuratKeteranganBerpergianRequest(
    val alamat: String,
    val disahkan_oleh: String,
    val jenis_kelamin: String,
    val jumlah_pengikut: Int,
    val keperluan: String,
    val lama: Int,
    val maksud_tujuan: String,
    val nama: String,
    val nik: String,
    val pekerjaan: String,
    val satuan_lama: String,
    val tanggal_keberangkatan: String,
    val tanggal_lahir: String,
    val tempat_lahir: String,
    val tempat_tujuan: String
)