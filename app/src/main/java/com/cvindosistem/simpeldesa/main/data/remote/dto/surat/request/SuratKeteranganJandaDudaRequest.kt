package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request

data class SuratKeteranganJandaDudaRequest(
    val agama_id: String,
    val alamat: String,
    val dasar_pengajuan: String,
    val disahkan_oleh: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nik: String,
    val nomor_pengajuan: String,
    val pekerjaan: String,
    val penyebab: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)