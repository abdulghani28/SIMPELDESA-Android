package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan

import com.cvindosistem.simpeldesa.main.domain.model.SuratPernikahanResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernikahanUseCase

class SPPernikahanFormSubmitter(
    private val createSuratPernikahanUseCase: CreateSuratPernikahanUseCase,
    private val stateManager: SPPernikahanStateManager
) {

    suspend fun submitForm(): Result<Unit> {
        stateManager.updateLoading(true)
        stateManager.updateErrorMessage(null)

        return try {
            val request = stateManager.buildSubmissionRequest()

            when (val result = createSuratPernikahanUseCase(request)) {
                is SuratPernikahanResult.Success -> {
                    stateManager.resetAllData()
                    Result.success(Unit)
                }
                is SuratPernikahanResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Terjadi kesalahan"
            stateManager.updateErrorMessage(message)
            Result.failure(e)
        } finally {
            stateManager.updateLoading(false)
        }
    }
}