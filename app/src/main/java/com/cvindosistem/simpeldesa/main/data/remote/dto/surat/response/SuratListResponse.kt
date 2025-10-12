package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response

data class SuratListResponse(
    val data: List<Data>? = null,
    val meta: Meta? = null
) {
    data class Data(
        val created_at: String? = null,
        val diproses_oleh_id: String? = null,
        val disahkan_oleh_id: String? = null,
        val id: String? = null,
        val jenis_surat: String? = null,
        val nama: String? = null,
        val nik: String? = null,
        val status: String? = null
    )

    data class Meta(
        val end_date: String? = null,
        val jenis_surat: String? = null,
        val limit: Int? = null,
        val page: Int? = null,
        val search: String? = null,
        val start_date: String? = null,
        val status: String? = null,
        val total: Int? = null
    )
}
