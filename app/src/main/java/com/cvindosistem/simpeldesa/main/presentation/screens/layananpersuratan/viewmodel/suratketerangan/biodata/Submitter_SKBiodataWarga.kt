package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata

import com.cvindosistem.simpeldesa.main.domain.model.SKBiodataWargaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganBiodataWargaUseCase

class SKBiodataWargaFormSubmitter(
    private val createSKBiodataWargaUseCase: CreateSuratKeteranganBiodataWargaUseCase,
    private val stateManager: SKBiodataWargaStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKBiodataWargaUseCase(request)) {
                is SKBiodataWargaResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SKBiodataWargaResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Terjadi kesalahan"
            stateManager.updateErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateLoadingState(false)
        }
    }
}