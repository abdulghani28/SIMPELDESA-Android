package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.HubunganResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetHubunganUseCase

class SKLahirMatiDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getHubunganUseCase: GetHubunganUseCase,
    private val stateManager: SKLahirMatiStateManager,
    private val validator: SKLahirMatiValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf("nik", "nama"))
                    Result.success(Unit)
                }
                is UserInfoResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    stateManager.updateUseMyDataChecked(false)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data pengguna"
            stateManager.updateErrorMessage(errorMsg)
            stateManager.updateUseMyDataChecked(false)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateUserDataLoading(false)
        }
    }

    suspend fun loadAgama(): Result<Unit> {
        return try {
            stateManager.updateAgamaLoading(true)
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
            val errorMsg = e.message ?: "Gagal memuat data agama"
            stateManager.updateAgamaErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateAgamaLoading(false)
        }
    }

    suspend fun loadHubungan(): Result<Unit> {
        return try {
            stateManager.updateHubunganLoading(true)
            stateManager.updateHubunganErrorMessage(null)
            when (val result = getHubunganUseCase()) {
                is HubunganResult.Success -> {
                    stateManager.updateHubunganList(result.data.data)
                    Result.success(Unit)
                }
                is HubunganResult.Error -> {
                    stateManager.updateHubunganErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data hubungan"
            stateManager.updateHubunganErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateHubunganLoading(false)
        }
    }
}