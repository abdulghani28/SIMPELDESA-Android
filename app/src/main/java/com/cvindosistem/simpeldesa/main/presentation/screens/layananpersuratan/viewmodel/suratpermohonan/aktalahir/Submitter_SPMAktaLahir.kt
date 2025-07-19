package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir

import com.cvindosistem.simpeldesa.main.domain.model.SuratAktaLahirResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratAktaLahirUseCase

class SPMAktaLahirFormSubmitter(
    private val createSPMAktaLahirUseCase: CreateSuratAktaLahirUseCase,
    private val stateManager: SPMAktaLahirStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMAktaLahirUseCase(request)) {
                is SuratAktaLahirResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratAktaLahirResult.Error -> {
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
