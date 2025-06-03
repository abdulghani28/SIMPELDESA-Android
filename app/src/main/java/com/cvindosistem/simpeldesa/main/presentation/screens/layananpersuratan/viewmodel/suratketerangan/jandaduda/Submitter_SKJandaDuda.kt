package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda

import com.cvindosistem.simpeldesa.main.domain.model.SuratJandaDudaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJandaDudaUseCase

class SKJandaDudaFormSubmitter(
    private val createSKJandaDudaUseCase: CreateSuratJandaDudaUseCase,
    private val stateManager: SKJandaDudaStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKJandaDudaUseCase(request)) {
                is SuratJandaDudaResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratJandaDudaResult.Error -> {
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