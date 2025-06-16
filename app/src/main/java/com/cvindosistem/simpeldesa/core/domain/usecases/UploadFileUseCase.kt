package com.cvindosistem.simpeldesa.core.domain.usecases

import com.cvindosistem.simpeldesa.core.domain.model.file.FileUploadResult
import com.cvindosistem.simpeldesa.core.domain.repository.FileRepository
import java.io.File

class UploadFileUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(file: File, mimeType: String): FileUploadResult {
        return fileRepository.uploadFile(file, mimeType)
    }
}