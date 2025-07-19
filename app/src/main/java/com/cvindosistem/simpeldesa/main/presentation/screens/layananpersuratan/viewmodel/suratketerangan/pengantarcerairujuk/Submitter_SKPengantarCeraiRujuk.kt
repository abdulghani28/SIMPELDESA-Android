package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk

import com.cvindosistem.simpeldesa.main.domain.model.SKPengantarCeraiRujukResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPengantarCeraiRujukUseCase

class SKPengantarCeraiRujukFormSubmitter(
    private val createSKPengantarCeraiRujukUseCase: CreateSuratPengantarCeraiRujukUseCase,
    private val stateManager: SKPengantarCeraiRujukStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKPengantarCeraiRujukUseCase(request)) {
                is SKPengantarCeraiRujukResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SKPengantarCeraiRujukResult.Error -> {
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