package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jamkesos

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase

class SKJamkesosDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase,
    private val stateManager: SKJamkesosStateManager,
    private val validator: SKJamkesosValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir", "jenis_kelamin",
                        "alamat", "pekerjaan", "agama_id", "status_kawin_id", "pendidikan_id"
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

    suspend fun loadAgamaData(): Result<AgamaResponse> {
        return try {
            stateManager.updateAgamaLoading(true)
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    Result.success(result.data)
                }
                is AgamaResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data agama"
            stateManager.updateErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateAgamaLoading(false)
        }
    }

    suspend fun loadStatusKawinData(): Result<StatusKawinResponse> {
        return try {
            stateManager.updateStatusKawinLoading(true)
            when (val result = getStatusKawinUseCase()) {
                is StatusKawinResult.Success -> {
                    Result.success(result.data)
                }
                is StatusKawinResult.Error -> {
                    stateManager.updateErrorMessage(result.message)
                    Result.failure(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data status kawin"
            stateManager.updateErrorMessage(errorMsg)
            Result.failure(Exception(errorMsg))
        } finally {
            stateManager.updateStatusKawinLoading(false)
        }
    }
}