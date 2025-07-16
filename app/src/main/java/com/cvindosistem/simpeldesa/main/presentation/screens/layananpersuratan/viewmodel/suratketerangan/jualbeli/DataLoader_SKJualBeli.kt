package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SKJualBeliDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SKJualBeliStateManager,
    private val validator: SKJualBeliValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik_1", "nama_1", "alamat_1", "jenis_kelamin_1", "pekerjaan_1",
                        "tanggal_lahir_1", "tempat_lahir_1"
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