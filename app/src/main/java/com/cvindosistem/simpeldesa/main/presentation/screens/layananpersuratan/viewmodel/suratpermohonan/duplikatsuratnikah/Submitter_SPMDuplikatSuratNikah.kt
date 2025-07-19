package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatsuratnikah

import com.cvindosistem.simpeldesa.main.domain.model.SuratDuplikatSuratNikahResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDuplikatSuratNikahUseCase

class SPMDuplikatSuratNikahFormSubmitter(
    private val createSPMDuplikatSuratNikahUseCase: CreateSuratDuplikatSuratNikahUseCase,
    private val stateManager: SPMDuplikatSuratNikahStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMDuplikatSuratNikahUseCase(request)) {
                is SuratDuplikatSuratNikahResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratDuplikatSuratNikahResult.Error -> {
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