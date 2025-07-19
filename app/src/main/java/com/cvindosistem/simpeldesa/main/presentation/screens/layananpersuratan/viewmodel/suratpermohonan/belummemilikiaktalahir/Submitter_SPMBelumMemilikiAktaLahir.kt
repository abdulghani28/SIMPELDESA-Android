package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.belummemilikiaktalahir

import com.cvindosistem.simpeldesa.main.domain.model.SuratBelumMemilikiAktaLahirResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratBelumMemilikiAktaLahirUseCase

class SPMBelumMemilikiAktaLahirFormSubmitter(
    private val createSPMBelumMemilikiAktaLahirUseCase: CreateSuratBelumMemilikiAktaLahirUseCase,
    private val stateManager: SPMBelumMemilikiAktaLahirStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMBelumMemilikiAktaLahirUseCase(request)) {
                is SuratBelumMemilikiAktaLahirResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratBelumMemilikiAktaLahirResult.Error -> {
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