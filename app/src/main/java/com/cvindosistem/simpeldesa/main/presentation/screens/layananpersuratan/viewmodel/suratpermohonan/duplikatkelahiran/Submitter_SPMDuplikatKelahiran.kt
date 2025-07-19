package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran

import com.cvindosistem.simpeldesa.main.domain.model.SuratDuplikatKelahiranResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratDuplikatKelahiranUseCase

class SPMDuplikatKelahiranFormSubmitter(
    private val createSPMDuplikatKelahiranUseCase: CreateSuratDuplikatKelahiranUseCase,
    private val stateManager: SPMDuplikatKelahiranStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPMDuplikatKelahiranUseCase(request)) {
                is SuratDuplikatKelahiranResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SuratDuplikatKelahiranResult.Error -> {
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