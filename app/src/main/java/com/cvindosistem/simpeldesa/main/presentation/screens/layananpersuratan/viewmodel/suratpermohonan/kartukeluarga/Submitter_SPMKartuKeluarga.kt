package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.kartukeluarga

import com.cvindosistem.simpeldesa.main.domain.model.SPMKartuKeluargaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPermohonanKartuKeluargaUseCase

class SPMKartuKeluargaFormSubmitter(
    private val createSPMKartuKeluargaUseCase: CreateSuratPermohonanKartuKeluargaUseCase,
    private val stateManager: SPMKartuKeluargaStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMKartuKeluargaUseCase(request)) {
                is SPMKartuKeluargaResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SPMKartuKeluargaResult.Error -> {
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