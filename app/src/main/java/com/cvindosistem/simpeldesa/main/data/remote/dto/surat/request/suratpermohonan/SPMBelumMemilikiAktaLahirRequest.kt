package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan

data class SPMBelumMemilikiAktaLahirRequest(
    val alamat: String,
    val jenis_kelamin: String,
    val nama: String,
    val nama_ayah: String,
    val nama_ibu: String,
    val nik: String,
    val nik_ayah: String,
    val nik_ibu: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)