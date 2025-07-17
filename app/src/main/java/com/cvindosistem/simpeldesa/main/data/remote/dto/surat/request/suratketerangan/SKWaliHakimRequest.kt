package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKWaliHakimRequest(
    val agama_id: String,
    val alamat: String,
    val berlaku_mulai: String,
    val berlaku_sampai: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val kewarganegaraan: String,
    val nama: String,
    val nik: String,
    val pekerjaan: String,
    val pendidikan_id: String,
    val status_kawin_id: String,
    val tanggal_lahir: String,
    val tempat_lahir: String,
    val tujuan: String
)