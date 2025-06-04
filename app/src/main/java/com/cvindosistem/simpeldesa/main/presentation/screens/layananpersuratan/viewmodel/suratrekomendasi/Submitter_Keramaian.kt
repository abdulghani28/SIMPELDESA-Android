package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratrekomendasi.SRKeramaianRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKeramaianResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeramaianUseCase

class SRKeramaianFormSubmitter(
    private val createSuratKeramaianUseCase: CreateSuratKeramaianUseCase
) {
    suspend fun submitForm(request: SRKeramaianRequest): Result<Unit> = try {
        when (val result = createSuratKeramaianUseCase(request)) {
            is SuratKeramaianResult.Success -> Result.success(Unit)
            is SuratKeramaianResult.Error -> Result.failure(Exception(result.message))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}