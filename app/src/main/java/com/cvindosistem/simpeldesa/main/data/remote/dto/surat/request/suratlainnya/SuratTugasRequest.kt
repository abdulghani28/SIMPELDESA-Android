package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya

data class SuratTugasRequest(
    val deskripsi: String,
    val disahkan_oleh: String,
    val ditugaskan_untuk: String,
    val penerima: List<Penerima>
) {
    data class Penerima(
        val jabatan: String,
        val nama: String,
        val nik: String
    )
}