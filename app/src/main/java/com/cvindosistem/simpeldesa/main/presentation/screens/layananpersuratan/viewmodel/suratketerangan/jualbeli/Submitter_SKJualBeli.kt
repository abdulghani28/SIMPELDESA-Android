package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli

import com.cvindosistem.simpeldesa.main.domain.model.SuratJualBeliResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratJualBeliUseCase

class SKJualBeliFormSubmitter(
    private val createSKJualBeliUseCase: CreateSuratJualBeliUseCase,
    private val stateManager: SKJualBeliStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKJualBeliUseCase(request)) {
                is SuratJualBeliResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratJualBeliResult.Error -> {
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