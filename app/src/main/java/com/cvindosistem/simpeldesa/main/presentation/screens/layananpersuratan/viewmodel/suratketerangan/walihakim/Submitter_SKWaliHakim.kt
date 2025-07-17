package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.walihakim

import com.cvindosistem.simpeldesa.main.domain.model.SuratWaliHakimResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratWaliHakimUseCase

class SKWaliHakimFormSubmitter(
    private val createSKWaliHakimUseCase: CreateSuratWaliHakimUseCase,
    private val stateManager: SKWaliHakimStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKWaliHakimUseCase(request)) {
                is SuratWaliHakimResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratWaliHakimResult.Error -> {
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