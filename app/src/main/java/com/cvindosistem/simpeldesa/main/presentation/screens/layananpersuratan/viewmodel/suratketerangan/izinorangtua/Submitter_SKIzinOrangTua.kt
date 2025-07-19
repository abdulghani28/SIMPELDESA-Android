package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua

import com.cvindosistem.simpeldesa.main.domain.model.SKIzinOrangTuaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratKeteranganIzinOrangTuaUseCase

class SKIzinOrangTuaFormSubmitter(
    private val createSKIzinOrangTuaUseCase: CreateSuratKeteranganIzinOrangTuaUseCase,
    private val stateManager: SKIzinOrangTuaStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSKIzinOrangTuaUseCase(request)) {
                is SKIzinOrangTuaResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SKIzinOrangTuaResult.Error -> {
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