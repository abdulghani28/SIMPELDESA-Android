package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKBelumMemilikiPBBRequest(
    val agama_id: String,
    val alamat: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nik: String,
    val pekerjaan: String,
    val status_kawin_id: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)