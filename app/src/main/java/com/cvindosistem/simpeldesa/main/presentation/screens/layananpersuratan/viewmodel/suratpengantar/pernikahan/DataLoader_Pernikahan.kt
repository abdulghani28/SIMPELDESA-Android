package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.pernikahan

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase

class SPPernikahanDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase,
    private val stateManager: SPPernikahanStateManager,
    private val validator: SPPernikahanValidator
) {

    suspend fun loadUserData(): Result<Unit> {
        stateManager.updateUserDataLoading(true)
        return try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.loadUserDataToState(result.data.data)

                    // Clear validation errors for filled fields
                    validator.clearMultipleFieldErrors(listOf(
                        "nik_suami", "nama_suami", "tempat_lahir_suami",
                        "tanggal_lahir_suami", "agama_suami", "pekerjaan_suami",
                        "alamat_suami", "kewarganegaraan_suami", "status_kawin_suami"
                    ))

                    Result.success(Unit)
                }
                is UserInfoResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    stateManager.updateUseMyData(false)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data pengguna"
            stateManager.updateErrorMessage(message)
            stateManager.updateUseMyData(false)
            Result.failure(e)
        } finally {
            stateManager.updateUserDataLoading(false)
        }
    }

    suspend fun loadAgama(): Result<Unit> {
        stateManager.updateAgamaLoading(true)
        stateManager.updateAgamaErrorMessage(null)

        return try {
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
            stateManager.updateAgamaLoading(false)
        }
    }

    suspend fun loadStatusKawin(): Result<Unit> {
        stateManager.updateStatusKawinLoading(true)
        stateManager.updateStatusKawinErrorMessage(null)

        return try {
            when (val result = getStatusKawinUseCase()) {
                is StatusKawinResult.Success -> {
                    stateManager.updateStatusKawinList(result.data.data)
                    Result.success(Unit)
                }
                is StatusKawinResult.Error -> {
                    stateManager.updateStatusKawinErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val message = e.message ?: "Gagal memuat data status kawin"
            stateManager.updateStatusKawinErrorMessage(message)
            Result.failure(e)
        } finally {
            stateManager.updateStatusKawinLoading(false)
        }
    }
}