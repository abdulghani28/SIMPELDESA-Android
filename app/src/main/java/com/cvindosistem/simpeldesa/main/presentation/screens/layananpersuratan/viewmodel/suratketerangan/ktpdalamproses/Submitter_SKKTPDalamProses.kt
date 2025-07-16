package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ktpdalamproses

import com.cvindosistem.simpeldesa.main.domain.model.SuratKTPDalamProsesResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKTPDalamProsesUseCase

class SKKTPDalamProsesFormSubmitter(
    private val createSKKTPDalamProsesUseCase: CreateSuratKTPDalamProsesUseCase,
    private val stateManager: SKKTPDalamProsesStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKKTPDalamProsesUseCase(request)) {
                is SuratKTPDalamProsesResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratKTPDalamProsesResult.Error -> {
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