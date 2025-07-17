package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKLahirMatiRequest(
    val agama_ibu_id: String,
    val alamat_ibu: String,
    val hubungan_id: String,
    val keperluan: String,
    val kewarganegaraan_ibu_id: String,
    val lama_dikandung: String,
    val nama: String,
    val nama_ibu: String,
    val nik: String,
    val nik_ibu: String,
    val pekerjaan_ibu: String,
    val tanggal_lahir_ibu: String,
    val tanggal_meninggal: String,
    val tempat_lahir_ibu: String,
    val tempat_meninggal: String
)