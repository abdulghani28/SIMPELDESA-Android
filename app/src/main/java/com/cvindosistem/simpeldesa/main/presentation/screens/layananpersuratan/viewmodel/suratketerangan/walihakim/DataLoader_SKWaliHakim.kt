package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.walihakim

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.PendidikanResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetPendidikanUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase

class SKWaliHakimDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getPendidikanUseCase: GetPendidikanUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase,
    private val stateManager: SKWaliHakimStateManager,
    private val validator: SKWaliHakimValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir", "alamat",
                        "jenis_kelamin", "agama_id", "status_kawin_id", "pendidikan_id",
                        "pekerjaan", "kewarganegaraan"
                    ))
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

    suspend fun loadPendidikan(): Result<Unit> {
        return try {
            stateManager.updatePendidikanLoading(true)
            stateManager.updatePendidikanErrorMessage(null)
            when (val result = getPendidikanUseCase()) {
                is PendidikanResult.Success -> {
                    stateManager.updatePendidikanList(result.data.data)
                    Result.success(Unit)
                }
                is PendidikanResult.Error -> {
                    stateManager.updatePendidikanErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data pendidikan"
            stateManager.updatePendidikanErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updatePendidikanLoading(false)
        }
    }

    suspend fun loadStatusKawin(): Result<Unit> {
        return try {
            stateManager.updateStatusKawinLoading(true)
            stateManager.updateStatusKawinErrorMessage(null)
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
            val errorMsg = e.message ?: "Gagal memuat data status kawin"
            stateManager.updateStatusKawinErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateStatusKawinLoading(false)
        }
    }
}