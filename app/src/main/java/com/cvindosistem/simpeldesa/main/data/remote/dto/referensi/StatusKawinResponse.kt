package com.cvindosistem.simpeldesa.main.data.remote.dto.referensi

data class StatusKawinResponse(
    val `data`: List<Data>,
    val meta: Meta
) {
    data class Data(
        val created_at: String,
        val deleted_at: Any,
        val id: String,
        val nama: String,
        val updated_at: String
    )

    data class Meta(
        val total: Int
    )
}