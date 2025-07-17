package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan

import com.cvindosistem.simpeldesa.main.domain.model.SuratKepemilikanKendaraanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKepemilikanKendaraanUseCase

class SKKepemilikanKendaraanFormSubmitter(
    private val createSKKepemilikanKendaraanUseCase: CreateSuratKepemilikanKendaraanUseCase,
    private val stateManager: SKKepemilikanKendaraanStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKKepemilikanKendaraanUseCase(request)) {
                is SuratKepemilikanKendaraanResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratKepemilikanKendaraanResult.Error -> {
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