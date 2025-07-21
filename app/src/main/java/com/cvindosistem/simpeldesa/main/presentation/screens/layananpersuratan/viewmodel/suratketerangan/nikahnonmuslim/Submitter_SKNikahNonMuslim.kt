package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim

import com.cvindosistem.simpeldesa.main.domain.model.SKNikahNonMuslimResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganNikahNonMuslimUseCase

class SKNikahNonMuslimFormSubmitter(
    private val createSKNikahNonMuslimUseCase: CreateSuratKeteranganNikahNonMuslimUseCase,
    private val stateManager: SKNikahWargaNonMuslimStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKNikahNonMuslimUseCase(request)) {
                is SKNikahNonMuslimResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SKNikahNonMuslimResult.Error -> {
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