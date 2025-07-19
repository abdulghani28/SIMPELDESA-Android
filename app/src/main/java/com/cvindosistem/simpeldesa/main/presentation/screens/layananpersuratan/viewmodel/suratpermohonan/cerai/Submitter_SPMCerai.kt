package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai

import com.cvindosistem.simpeldesa.main.domain.model.SPMCeraiResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanCeraiUseCase

class SPMCeraiFormSubmitter(
    private val createSPMCeraiUseCase: CreateSuratPermohonanCeraiUseCase,
    private val stateManager: SPMCeraiStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMCeraiUseCase(request)) {
                is SPMCeraiResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SPMCeraiResult.Error -> {
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