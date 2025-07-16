package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.ktpdalamproses

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase

class SKKTPDalamProsesDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val stateManager: SKKTPDalamProsesStateManager,
    private val validator: SKKTPDalamProsesValidator,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "alamat", "jenis_kelamin", "agama_id", "pekerjaan",
                        "status_kawin_id", "tanggal_lahir", "tempat_lahir", "kewarganegaraan"
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

    suspend fun loadAgamaData(): Result<Unit> {
        return try {
            stateManager.updateAgamaLoading(true)
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    stateManager.updateAgamaList(result.data.data)
                    Result.success(Unit)
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

    suspend fun loadStatusKawin(): Result<Unit> {
        return try {
            stateManager.updateStatusKawinLoading(true)
            when (val result = getStatusKawinUseCase()) {
                is StatusKawinResult.Success -> {
                    stateManager.updateStatusKawinList(result.data.data)
                    Result.success(Unit)
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