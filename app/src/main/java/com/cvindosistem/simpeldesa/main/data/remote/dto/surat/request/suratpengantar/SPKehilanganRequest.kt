package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar

data class SPKehilanganRequest(
    val alamat: String,
    val ciri: String,
    val disahkan_oleh: String,
    val jenis_barang: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nik: String,
    val pekerjaan: String,
    val tanggal_lahir: String,
    val tempat_kehilangan: String,
    val tempat_lahir: String,
    val waktu_kehilangan: String
)