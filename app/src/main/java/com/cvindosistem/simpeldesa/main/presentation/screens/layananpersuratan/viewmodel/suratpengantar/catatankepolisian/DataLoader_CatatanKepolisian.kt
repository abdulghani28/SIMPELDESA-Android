package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.catatankepolisian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SPCatatanKepolisianDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {
    var useMyDataChecked by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set

    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
    }

    suspend fun loadUserData(
        onSuccess: (UserInfoResponse.Data) -> Unit,
        onError: suspend (String) -> Unit
    ) {
        isLoadingUserData = true
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    onSuccess(result.data.data)
                }
                is UserInfoResult.Error -> {
                    useMyDataChecked = false
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Gagal memuat data pengguna"
            useMyDataChecked = false
            onError(errorMessage)
        } finally {
            isLoadingUserData = false
        }
    }
}