package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKuasaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKuasaUseCase

class SuratKuasaFormSubmitter(
    private val createSuratKuasaUseCase: CreateSuratKuasaUseCase
) {

    suspend fun submitForm(request: SuratKuasaRequest): SubmitResult {
        return try {
            when (val result = createSuratKuasaUseCase(request)) {
                is SuratKuasaResult.Success -> {
                    SubmitResult.Success
                }
                is SuratKuasaResult.Error -> {
                    SubmitResult.Error(result.message)
                }
            }
        } catch (e: Exception) {
            SubmitResult.Error(e.message ?: "Terjadi kesalahan")
        }
    }

    sealed class SubmitResult {
        data object Success : SubmitResult()
        data class Error(val message: String) : SubmitResult()
    }
}