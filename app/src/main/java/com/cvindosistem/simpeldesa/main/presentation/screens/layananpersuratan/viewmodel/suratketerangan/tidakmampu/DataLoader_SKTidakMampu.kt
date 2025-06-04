package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.tidakmampu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult
import com.cvindosistem.simpeldesa.auth.domain.usecases.GetUserInfoUseCase
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetAgamaUseCase
import com.cvindosistem.simpeldesa.main.domain.usecases.GetStatusKawinUseCase

class SKTidakMampuDataLoader(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAgamaUseCase: GetAgamaUseCase,
    private val getStatusKawinUseCase: GetStatusKawinUseCase
) {
    // Agama state
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var isLoadingAgama by mutableStateOf(false)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set

    // Status Kawin state
    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
        private set
    var isLoadingStatusKawin by mutableStateOf(false)
        private set
    var statusKawinErrorMessage by mutableStateOf<String?>(null)
        private set

    // User data loading state
    var isLoadingUserData by mutableStateOf(false)
        private set

    suspend fun loadUserData(): UserInfoResult {
        isLoadingUserData = true
        return try {
            val result = getUserInfoUseCase()
            result
        } finally {
            isLoadingUserData = false
        }
    }

    suspend fun loadAgama(): AgamaResult {
        isLoadingAgama = true
        agamaErrorMessage = null
        return try {
            val result = getAgamaUseCase()
            when (result) {
                is AgamaResult.Success -> {
                    agamaList = result.data.data
                }
                is AgamaResult.Error -> {
                    agamaErrorMessage = result.message
                }
            }
            result
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Gagal memuat data agama"
            agamaErrorMessage = errorMessage
            AgamaResult.Error(errorMessage)
        } finally {
            isLoadingAgama = false
        }
    }

    suspend fun loadStatusKawin(): StatusKawinResult {
        isLoadingStatusKawin = true
        statusKawinErrorMessage = null
        return try {
            val result = getStatusKawinUseCase()
            when (result) {
                is StatusKawinResult.Success -> {
                    statusKawinList = result.data.data
                }
                is StatusKawinResult.Error -> {
                    statusKawinErrorMessage = result.message
                }
            }
            result
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Gagal memuat data status kawin"
            statusKawinErrorMessage = errorMessage
            StatusKawinResult.Error(errorMessage)
        } finally {
            isLoadingStatusKawin = false
        }
    }
}