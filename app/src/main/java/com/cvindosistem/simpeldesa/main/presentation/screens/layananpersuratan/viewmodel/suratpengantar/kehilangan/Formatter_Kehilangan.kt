package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.kehilangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPKehilanganRequest
import com.cvindosistem.simpeldesa.main.domain.model.SuratKehilanganResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKehilanganUseCase

class SPKehilanganFormSubmitter(
    private val createSuratKehilanganUseCase: CreateSuratKehilanganUseCase
) {
    var isLoading by mutableStateOf(false)
        private set

    suspend fun submitForm(request: SPKehilanganRequest): Result<Unit> {
        isLoading = true
        return try {
            when (val result = createSuratKehilanganUseCase(request)) {
                is SuratKehilanganResult.Success -> {
                    Result.success(Unit)
                }
                is SuratKehilanganResult.Error -> {
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