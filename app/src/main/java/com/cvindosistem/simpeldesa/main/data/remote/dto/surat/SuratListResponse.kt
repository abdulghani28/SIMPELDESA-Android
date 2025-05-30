package com.cvindosistem.simpeldesa.main.data.remote.dto.surat

data class SuratListResponse(
    val `data`: List<Data>,
    val meta: Meta
) {
    data class Data(
        val created_at: String,
        val diproses_oleh_id: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jenis_surat: String,
        val nama: String,
        val nik: String,
        val status: String
    )

    data class Meta(
        val end_date: String,
        val jenis_surat: String,
        val limit: Int,
        val page: Int,
        val search: String,
        val start_date: String,
        val status: String,
        val total: Int
    )
}