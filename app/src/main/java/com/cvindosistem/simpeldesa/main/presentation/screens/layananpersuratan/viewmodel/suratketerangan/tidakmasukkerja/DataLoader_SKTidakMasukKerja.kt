package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmasukkerja

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase

class SKTidakMasukKerjaDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val stateManager: SKTidakMasukKerjaStateManager
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateLoadingUserData(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    Result.success(Unit)
                }
                is UserInfoResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    stateManager.updateUseMyDataChecked(false)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data pengguna"
            stateManager.updateErrorMessage(message)
            stateManager.updateUseMyDataChecked(false)
            Result.failure(e)
        } finally {
            stateManager.updateLoadingUserData(false)
        }
    }

    suspend fun loadAgama(): Result<Unit> {
        return try {
            stateManager.updateLoadingAgama(true)
            stateManager.updateAgamaErrorMessage(null)
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    stateManager.updateAgamaList(result.data.data)
                    Result.success(Unit)
                }
                is AgamaResult.Error -> {
                    stateManager.updateAgamaErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data agama"
            stateManager.updateAgamaErrorMessage(message)
            Result.failure(e)
        } finally {
            stateManager.updateLoadingAgama(false)
        }
    }
}