package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi.keramaian

import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SRKeramaianDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {
    suspend fun loadUserData(): Result<UserInfoResponse.Data> = try {
        when (val result = getUserInfoUseCase()) {
            is UserInfoResult.Success -> Result.success(result.data.data)
            is UserInfoResult.Error -> Result.failure(Exception(result.message))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}