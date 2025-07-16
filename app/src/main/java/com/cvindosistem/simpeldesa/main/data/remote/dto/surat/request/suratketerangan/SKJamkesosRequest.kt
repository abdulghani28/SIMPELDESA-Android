package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKJamkesosRequest(
    val agama_id: String,
    val alamat: String,
    val berlaku_dari: String,
    val berlaku_sampai: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nik: String,
    val no_kartu: String,
    val pekerjaan: String,
    val pendidikan_id: String,
    val status_kawin_id: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)