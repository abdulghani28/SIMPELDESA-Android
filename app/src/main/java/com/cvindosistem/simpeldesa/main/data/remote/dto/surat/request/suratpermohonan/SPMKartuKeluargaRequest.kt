package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan

data class SPMKartuKeluargaRequest(
    val agama_id: String,
    val alamat: String,
    val alasan_permohonan: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val kewarganegaraan: String,
    val nama: String,
    val nik: String,
    val no_kk: String,
    val pekerjaan: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)