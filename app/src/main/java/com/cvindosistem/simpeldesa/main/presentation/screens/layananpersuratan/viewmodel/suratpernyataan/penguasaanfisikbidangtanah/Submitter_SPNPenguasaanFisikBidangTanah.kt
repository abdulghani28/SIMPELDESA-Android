package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah

import com.cvindosistem.simpeldesa.main.domain.model.SPNPenguasaanFisikBidangTanahResult
import com.cvindosistem.simpeldesa.main.domain.usecases.CreateSuratPernyataanSporadikUseCase

class SPNPenguasaanFisikBidangTanahFormSubmitter(
    private val createSPNPenguasaanFisikBidangTanahUseCase: CreateSuratPernyataanSporadikUseCase,
    private val stateManager: SPNPenguasaanFisikBidangTanahStateManager
) {
    suspend fun submitForm(): Result<Unit> {
        return try {
            stateManager.updateLoadingState(true)
            stateManager.updateErrorMessage(null)

            val request = stateManager.toRequest()
            when (val result = createSPNPenguasaanFisikBidangTanahUseCase(request)) {
                is SPNPenguasaanFisikBidangTanahResult.Success -> {
                    stateManager.resetForm()
                    Result.success(Unit)
                }
                is SPNPenguasaanFisikBidangTanahResult.Error -> {
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