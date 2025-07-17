package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati

import com.cvindosistem.simpeldesa.main.domain.model.SuratLahirMatiResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratLahirMatiUseCase

class SKLahirMatiFormSubmitter(
    private val createSKLahirMatiUseCase: CreateSuratLahirMatiUseCase,
    private val stateManager: SKLahirMatiStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKLahirMatiUseCase(request)) {
                is SuratLahirMatiResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratLahirMatiResult.Error -> {
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