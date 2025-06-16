package com.cvindosistem.simpeldesa.core.domain.model.notification

data class NotificationResponse(
    val `data`: List<Data>
) {
    data class Data(
        val created_at: String,
        val deleted_at: String,
        val id: String,
        val jenis_surat: String,
        val message: String,
        val surat_id: String,
        val title: String,
        val updated_at: String,
        val warga_id: String
    )
}