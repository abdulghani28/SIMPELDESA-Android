package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah

import com.cvindosistem.simpeldesa.main.domain.model.SKKepemilikanTanahResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganKepemilikanTanahUseCase

class SKKepemilikanTanahFormSubmitter(
    private val createSKKepemilikanTanahUseCase: CreateSuratKeteranganKepemilikanTanahUseCase,
    private val stateManager: SKKepemilikanTanahStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKKepemilikanTanahUseCase(request)) {
                is SKKepemilikanTanahResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SKKepemilikanTanahResult.Error -> {
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