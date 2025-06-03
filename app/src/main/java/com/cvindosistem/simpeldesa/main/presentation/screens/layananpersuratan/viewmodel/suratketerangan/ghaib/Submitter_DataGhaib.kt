package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ghaib

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKGhaibRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratGhaibResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratGhaibUseCase

class SKGhaibFormSubmitter(
    private val createSKGhaibUseCase: CreateSuratGhaibUseCase
) {
    var isLoading by mutableStateOf(false)
        private set

    suspend fun submitForm(request: SKGhaibRequest): Result<Unit> {
        isLoading = true
        return try {
            when (val result = createSKGhaibUseCase(request)) {
                is SuratGhaibResult.Success -> {
                    Result.success(Unit)
                }
                is SuratGhaibResult.Error -> {
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            isLoading = false
        }
    }
}
