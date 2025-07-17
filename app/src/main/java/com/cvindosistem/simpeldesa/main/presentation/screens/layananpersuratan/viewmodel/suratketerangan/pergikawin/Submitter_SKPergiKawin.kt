package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pergikawin

import com.cvindosistem.simpeldesa.main.domain.model.SuratPergiKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPergiKawinUseCase

class SKPergiKawinFormSubmitter(
    private val createSKPergiKawinUseCase: CreateSuratPergiKawinUseCase,
    private val stateManager: SKPergiKawinStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKPergiKawinUseCase(request)) {
                is SuratPergiKawinResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratPergiKawinResult.Error -> {
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