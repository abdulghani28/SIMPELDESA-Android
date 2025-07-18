package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan

data class SPMDuplikatSuratNikahRequest(
    val agama_id: String,
    val alamat: String,
    val jenis_kelamin: String,
    val kecamatan_kua: String,
    val kepala_keluarga: String,
    val keperluan: String,
    val kewarganegaraan: String,
    val nama: String,
    val nama_pasangan: String,
    val nik: String,
    val no_kk: String,
    val pekerjaan: String,
    val pendidikan_id: String,
    val tanggal_lahir: String,
    val tanggal_nikah: String,
    val tempat_lahir: String
)