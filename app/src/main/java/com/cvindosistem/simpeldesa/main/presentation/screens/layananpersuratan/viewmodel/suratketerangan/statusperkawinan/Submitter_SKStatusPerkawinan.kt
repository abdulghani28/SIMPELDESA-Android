package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan

import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKStatusPerkawinanRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratStatusPerkawinanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratStatusPerkawinanUseCase

class SKStatusPerkawinanFormSubmitter(
    private val createSuratCatatanStatusPerkawinanUseCase: CreateSuratStatusPerkawinanUseCase
) {
    suspend fun submitForm(
        request: SKStatusPerkawinanRequest,
        onSuccess: suspend () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        try {
            when (val result = createSuratCatatanStatusPerkawinanUseCase(request)) {
                is SuratStatusPerkawinanResult.Success -> {
                    onSuccess()
                }
                is SuratStatusPerkawinanResult.Error -> {
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            onError(e.message ?: "Terjadi kesalahan")
        }
    }
}