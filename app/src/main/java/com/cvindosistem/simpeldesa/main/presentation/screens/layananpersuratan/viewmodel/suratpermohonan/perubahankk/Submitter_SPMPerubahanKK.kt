package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.perubahankk

import com.cvindosistem.simpeldesa.main.domain.model.SPMPerubahanKKResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanPerubahanKKUseCase

class SPMPerubahanKKFormSubmitter(
    private val createSPMPerubahanKKUseCase: CreateSuratPermohonanPerubahanKKUseCase,
    private val stateManager: SPMPerubahanKKStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMPerubahanKKUseCase(request)) {
                is SPMPerubahanKKResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SPMPerubahanKKResult.Error -> {
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