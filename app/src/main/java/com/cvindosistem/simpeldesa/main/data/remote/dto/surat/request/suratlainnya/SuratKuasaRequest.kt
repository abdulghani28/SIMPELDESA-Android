package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya

data class SuratKuasaRequest(
    val disahkan_oleh: String,
    val disposisi_kuasa_sebagai: String,
    val disposisi_kuasa_untuk: String,
    val jabatan_pemberi: String,
    val jabatan_penerima: String,
    val nama_pemberi: String,
    val nama_penerima: String,
    val nik_pemberi: String,
    val nik_penerima: String
)