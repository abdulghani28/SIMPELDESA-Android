package com.cvindosistem.simpeldesa.core.domain.model.file

sealed class FileUploadResult {
    data class Success(val data: FileUploadResponse) : FileUploadResult()
    data class Error(val message: String) : FileUploadResult()
}