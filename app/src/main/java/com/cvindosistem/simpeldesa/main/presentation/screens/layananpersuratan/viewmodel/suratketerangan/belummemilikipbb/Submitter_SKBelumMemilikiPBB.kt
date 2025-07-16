package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.belummemilikipbb

import com.cvindosistem.simpeldesa.main.domain.model.SuratBelumMemilikiPBBResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBelumMemilikiPBBUseCase

class SKBelumMemilikiPBBFormSubmitter(
    private val createSKBelumMemilikiPBBUseCase: CreateSuratBelumMemilikiPBBUseCase,
    private val stateManager: SKBelumMemilikiPBBStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKBelumMemilikiPBBUseCase(request)) {
                is SuratBelumMemilikiPBBResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratBelumMemilikiPBBResult.Error -> {
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