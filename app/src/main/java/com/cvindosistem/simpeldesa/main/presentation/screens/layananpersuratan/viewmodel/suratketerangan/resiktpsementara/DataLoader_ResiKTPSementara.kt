package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.resiktpsementara

import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase

class SKResiKTPSementaraDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase
) {

    suspend fun loadUserData(
        stateManager: SKResiKTPSementaraStateManager,
        onError: (String) -> Unit
    ) {
        stateManager.isLoadingUserData = true
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    stateManager.populateUserData(result.data.data)
                    stateManager.clearMultipleFieldErrors(listOf(
                        "nik", "nama", "tempat_lahir", "tanggal_lahir",
                        "jenis_kelamin", "pekerjaan", "alamat", "agama_id"
                    ))
                }
                is UserInfoResult.Error -> {
                    stateManager.errorMessage = result.message
                    stateManager.useMyDataChecked = false
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data pengguna"
            stateManager.errorMessage = errorMsg
            stateManager.useMyDataChecked = false
            onError(errorMsg)
        } finally {
            stateManager.isLoadingUserData = false
        }
    }

    suspend fun loadAgama(
        stateManager: SKResiKTPSementaraStateManager,
        onError: (String) -> Unit
    ) {
        stateManager.isLoadingAgama = true
        stateManager.agamaErrorMessage = null
        try {
            when (val result = getAgamaUseCase()) {
                is AgamaResult.Success -> {
                    stateManager.agamaList = result.data.data
                }
                is AgamaResult.Error -> {
                    stateManager.agamaErrorMessage = result.message
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data agama"
            stateManager.agamaErrorMessage = errorMsg
            onError(errorMsg)
        } finally {
            stateManager.isLoadingAgama = false
        }
    }
}