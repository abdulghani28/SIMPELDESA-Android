package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKBiodataWargaRequest(
    val agama_id: String,
    val akta_cerai: String,
    val akta_kawin: String,
    val akta_lahir: String,
    val alamat: String,
    val disabilitas_id: Any?,
    val golongan_darah: String,
    val hubungan: String,
    val keperluan: String,
    val nama: String,
    val nama_ayah: String,
    val nama_ibu: String,
    val nik: String,
    val nik_ayah: String,
    val nik_ibu: String,
    val pekerjaan: String,
    val pendidikan_id: String,
    val status: String,
    val tanggal_cerai: String,
    val tanggal_kawin: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)