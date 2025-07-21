package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikantanah

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SKKepemilikanTanahDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKKepemilikanTanahStateManager,
    private val validator: SKKepemilikanTanahValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan"
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
}