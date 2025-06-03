package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.domain.model.SuratDomisiliResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDomisiliUseCase

class SKDomisiliFormSubmitter(
    private val createSuratDomisiliUseCase: CreateSuratDomisiliUseCase,
    private val stateManager: SKDomisiliStateManager
) {
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    suspend fun submitForm(): Result<Unit> {
        return try {
            isLoading = true
            errorMessage = null

            val request = stateManager.buildRequest()
            when (val result = createSuratDomisiliUseCase(request)) {
                is SuratDomisiliResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratDomisiliResult.Error -> {
                    errorMessage = result.message
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Terjadi kesalahan"
            Result.failure(e)
        } finally {
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }
}