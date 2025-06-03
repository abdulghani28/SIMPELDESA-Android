package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SKPenghasilanDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {
    var isLoadingUserData by mutableStateOf(false)
        private set

    suspend fun loadUserData(): Result<UserInfoResponse.Data> = try {
        isLoadingUserData = true
        when (val result = getUserInfoUseCase()) {
            is UserInfoResult.Success -> {
                Result.success(result.data.data)
            }
            is UserInfoResult.Error -> {
                Result.failure(Exception(result.message))
            }
        }
    } catch (e: Exception) {
        Result.failure(e)
    } finally {
        isLoadingUserData = false
    }
}