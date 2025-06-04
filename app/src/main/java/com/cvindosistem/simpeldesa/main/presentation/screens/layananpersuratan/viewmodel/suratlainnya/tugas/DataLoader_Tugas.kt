package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase

class SuratTugasDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {
    var useMyDataChecked by mutableStateOf(false)
        private set

    var isLoadingUserData by mutableStateOf(false)
        private set

    suspend fun loadUserData(
        onSuccess: (UserInfoResponse.Data) -> Unit,
        onError: suspend (String) -> Unit
    ) {
        isLoadingUserData = true
        try {
            when (val result = getUserInfoUseCase()) {
                is UserInfoResult.Success -> {
                    val userData = result.data.data
                    onSuccess(userData)
                }
                is UserInfoResult.Error -> {
                    useMyDataChecked = false
                    onError(result.message)
                }
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Gagal memuat data pengguna"
            useMyDataChecked = false
            onError(errorMsg)
        } finally {
            isLoadingUserData = false
        }
    }

    fun updateUseMyData(checked: Boolean) {
        useMyDataChecked = checked
    }
}