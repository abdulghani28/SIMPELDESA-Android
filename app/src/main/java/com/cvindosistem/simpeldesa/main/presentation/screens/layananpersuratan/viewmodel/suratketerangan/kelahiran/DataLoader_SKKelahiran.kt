package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SKKelahiranDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKKelahiranStateManager,
    private val validator: SKKelahiranValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateLoadingUserData(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nama_ayah", "nik_ayah", "alamat_ayah",
                        "tempat_lahir_ayah", "tanggal_lahir_ayah"
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
            stateManager.updateLoadingUserData(false)
        }
    }
}