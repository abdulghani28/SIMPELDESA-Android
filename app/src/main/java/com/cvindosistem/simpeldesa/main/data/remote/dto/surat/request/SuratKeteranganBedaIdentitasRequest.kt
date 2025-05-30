package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request

data class SuratKeteranganBedaIdentitasRequest(
    val alamat_1: String,
    val alamat_2: String,
    val disahkan_oleh: String,
    val keperluan: String,
    val nama_1: String,
    val nama_2: String,
    val nomor_1: String,
    val nomor_2: String,
    val perbedaan_id: String,
    val tanggal_lahir_1: String,
    val tanggal_lahir_2: String,
    val tempat_lahir_1: String,
    val tempat_lahir_2: String,
    val tercantum_id: String,
    val tercantum_id_2: String
)