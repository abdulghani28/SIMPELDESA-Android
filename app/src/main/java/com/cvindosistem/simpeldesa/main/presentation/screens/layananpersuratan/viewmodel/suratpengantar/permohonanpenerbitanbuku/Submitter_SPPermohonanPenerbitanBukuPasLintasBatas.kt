package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku

import com.cvindosistem.simpeldesa.main.domain.model.SPPermohonanPenerbitanBukuPasLintasBatasResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPengantarPasLintasBatasUseCase

class SPPermohonanPenerbitanBukuPasLintasBatasFormSubmitter(
    private val createSPPermohonanPenerbitanBukuPasLintasBatasUseCase: CreateSuratPengantarPasLintasBatasUseCase,
    private val stateManager: SPPermohonanPenerbitanBukuPasLintasBatasStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPPermohonanPenerbitanBukuPasLintasBatasUseCase(request)) {
                is SPPermohonanPenerbitanBukuPasLintasBatasResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SPPermohonanPenerbitanBukuPasLintasBatasResult.Error -> {
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