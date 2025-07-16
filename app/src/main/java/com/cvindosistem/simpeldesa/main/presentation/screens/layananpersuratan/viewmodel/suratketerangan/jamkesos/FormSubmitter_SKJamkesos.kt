package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jamkesos

import com.cvindosistem.simpeldesa.main.domain.model.SuratJamkesosResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJamkesosUseCase

class SKJamkesosFormSubmitter(
    private val createSKJamkesosUseCase: CreateSuratJamkesosUseCase,
    private val stateManager: SKJamkesosStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKJamkesosUseCase(request)) {
                is SuratJamkesosResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratJamkesosResult.Error -> {
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
