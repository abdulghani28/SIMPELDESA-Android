package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratlainnya

data class SuratTugasResponse(
    val `data`: Data
) {
    data class Data(
        val created_at: String,
        val deleted_at: Any,
        val deskripsi: String,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val ditugaskan_untuk: String,
        val id: String,
        val nama: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val penerima: List<Penerima>,
        val status: String,
        val updated_at: String
    ) {
        data class Penerima(
            val jabatan: String,
            val nama: String,
            val nik: String
        )
    }
}