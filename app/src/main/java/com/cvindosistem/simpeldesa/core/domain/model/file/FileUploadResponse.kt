package com.cvindosistem.simpeldesa.core.domain.model.file

data class FileUploadResponse(
    val data: FileData
) {
    data class FileData(
        val id: String,
        val created_at: String
    )
}