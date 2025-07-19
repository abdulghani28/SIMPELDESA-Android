package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKPengantarCeraiRujukRequest(
    val agama_id: String,
    val agama_id_pasangan: String,
    val alamat: String,
    val alamat_pasangan: String,
    val keperluan: String,
    val kewarganegaraan: String,
    val kewarganegaraan_pasangan: String,
    val nama: String,
    val nama_ayah: String,
    val nama_ayah_pasangan: String,
    val nama_pasangan: String,
    val nik: String,
    val nik_ayah: String,
    val nik_ayah_pasangan: String,
    val nik_pasangan: String,
    val pekerjaan: String,
    val pekerjaan_pasangan: String,
    val tanggal_lahir: String,
    val tanggal_lahir_pasangan: String,
    val tempat_lahir: String,
    val tempat_lahir_pasangan: String
)