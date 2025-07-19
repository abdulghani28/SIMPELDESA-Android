package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.kartukeluarga

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase

class SPMKartuKeluargaDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val stateManager: SPMKartuKeluargaStateManager,
    private val validator: SPMKartuKeluargaValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "alamat", "no_kk", "jenis_kelamin",
                        "tanggal_lahir", "tempat_lahir", "kewarganegaraan",
                        "pekerjaan", "agama_id"
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
}