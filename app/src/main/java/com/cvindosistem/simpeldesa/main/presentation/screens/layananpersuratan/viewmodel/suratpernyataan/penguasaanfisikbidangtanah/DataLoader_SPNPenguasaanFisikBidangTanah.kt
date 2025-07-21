package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SPNPenguasaanFisikBidangTanahDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val stateManager: SPNPenguasaanFisikBidangTanahStateManager,
    private val validator: SPNPenguasaanFisikBidangTanahValidator
) {
    suspend fun loadUserData(): Result<Unit> {
        return try {
            stateManager.updateUserDataLoading(true)
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    validator.clearMultipleFieldErrors(listOf(
                        "nik_pemohon", "nama_pemohon", "tempat_lahir_pemohon",
                        "tanggal_lahir_pemohon", "pekerjaan"
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